package com.scnu.lwg.controller;

import com.scnu.lwg.config.Constants;
import com.scnu.lwg.service.IValidateCodeService;
import com.scnu.lwg.util.CacheUtils;
import com.scnu.lwg.config.ResponseCode;
import com.scnu.lwg.config.Result;
import com.scnu.lwg.entity.TokenInfoEntity;
import com.scnu.lwg.entity.UserInfoEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Kin
 * @description token api
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

@RestController
@RequestMapping(value = "/lwg")
public class TokenController
{

    @Resource
    IValidateCodeService validateCodeService;

    @RequestMapping(value = "/token/refresh", method = {RequestMethod.POST,RequestMethod.GET})
    public Result refreshToken(@RequestParam String appId,
                               @RequestParam String appSecret){
        //return token value
        String tokenStr = null;

        //appId is null
        if(StringUtils.isEmpty(appId))
        {
            return Result.fail(ResponseCode.APP_ID_ERROR);
        }
        //appSecret is null
        else if(StringUtils.isEmpty(appSecret))
        {
            return Result.fail(ResponseCode.APP_SECRET_ERROR);
        }
        else
        {
            //query userinfo by appId. current use cache. Use database queries if necessary
            UserInfoEntity userInfo = CacheUtils.getUser(UserInfoEntity.class, appId);

            //if not exists
            if (userInfo == null)
            {
                return Result.fail(ResponseCode.APP_ID_ERROR.code(), "appId : " + appId + ", is not found!");
            }
            //verify appSecret
            else if (!Objects.equals(userInfo.getAppSecret(), appSecret))
            {
                return Result.fail(ResponseCode.APP_SECRET_NOT_EFFECTIVE);
            }
            else
            {
                //verify token
                TokenInfoEntity tokenInfo = CacheUtils.getToken(TokenInfoEntity.class, appId);

                //tokenInfo == null -> gen token -> save -> write memory -> return new token
                if(tokenInfo == null)
                {
                    //gen jwt token
                    tokenStr = createNewToken(appId);

                    tokenInfo = new TokenInfoEntity();
                    tokenInfo.setAppId(userInfo.getAppId());
                    tokenInfo.setBuildTime(String.valueOf(System.currentTimeMillis()));
                    tokenInfo.setToken(tokenStr.getBytes());
                    //write to cache or db
                    CacheUtils.putToken(tokenInfo.getAppId(), tokenInfo);

                }
                //tokenInfo != null -> verify timeout ->
                //no -> return token
                //yes -> gen new token -> update token -> update memory token -> return new token
                else
                {
                    //verify token timeout
                    //gen time
                    long genTime = Long.valueOf(tokenInfo.getBuildTime());
                    //current time
                    long currentTime = System.currentTimeMillis();
                    //if current - gen time < 7200 prove it can be used normally
                    long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - genTime);
                    if (second > 0 && second < 7200) {
                        tokenStr = new String(tokenInfo.getToken());
                    }
                    //timeout
                    else{
                        //gen new token
                        tokenStr = createNewToken(appId);
                        //update token
                        tokenInfo.setToken(tokenStr.getBytes());
                        //update gen time
                        tokenInfo.setBuildTime(String.valueOf(System.currentTimeMillis()));
                        //update
                        CacheUtils.putToken(appId, tokenInfo);
                    }
                }
            }
        }
        return Result.success(tokenStr);
    }

    /**
     * get token，update token
     * @param appId  user name
     * @param appSecret  user password
     * @return
     */
    @RequestMapping(value = "/token", method = {RequestMethod.POST,RequestMethod.GET})
    public Result token
            (
                    @RequestParam String appId,
                    @RequestParam String appSecret,
                    @RequestParam String deviceId,
                    @RequestParam String captcha
            )
    {
        //return token value
        String tokenStr = null;

        //appId is null
        if(StringUtils.isEmpty(appId))
        {
            return Result.fail(ResponseCode.APP_ID_ERROR);
        }
        //appSecret is null
        else if(StringUtils.isEmpty(appSecret))
        {
            return Result.fail(ResponseCode.APP_SECRET_ERROR);
        }
        else
        {
            validateCodeService.validate(deviceId, captcha);
            //query userinfo by appId. current use cache. Use database queries if necessary
            UserInfoEntity userInfo = CacheUtils.getUser(UserInfoEntity.class, appId);

            //if not exists
            if (userInfo == null)
            {
                return Result.fail(ResponseCode.APP_ID_ERROR.code(), "appId : " + appId + ", is not found!");
            }
            //verify appSecret
            else if (!Objects.equals(userInfo.getAppSecret(), appSecret))
            {
                return Result.fail(ResponseCode.APP_SECRET_NOT_EFFECTIVE);
            }
            else
            {
                //verify token
                TokenInfoEntity tokenInfo = CacheUtils.getToken(TokenInfoEntity.class, appId);

                //tokenInfo == null -> gen token -> save -> write memory -> return new token
                if(tokenInfo == null)
                {
                    //gen jwt token
                    tokenStr = createNewToken(appId);

                    tokenInfo = new TokenInfoEntity();
                    tokenInfo.setAppId(userInfo.getAppId());
                    tokenInfo.setBuildTime(String.valueOf(System.currentTimeMillis()));
                    tokenInfo.setToken(tokenStr.getBytes());
                    //write to cache or db
                    CacheUtils.putToken(tokenInfo.getAppId(), tokenInfo);

                }
                //tokenInfo != null -> verify timeout ->
                //no -> return token
                //yes -> gen new token -> update token -> update memory token -> return new token
                else
                {
                    //verify token timeout
                    //gen time
                    long genTime = Long.valueOf(tokenInfo.getBuildTime());
                    //current time
                    long currentTime = System.currentTimeMillis();
                    //if current - gen time < 7200 prove it can be used normally
                    long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - genTime);
                    if (second > 0 && second < 7200) {
                        tokenStr = new String(tokenInfo.getToken());
                    }
                    //timeout
                    else{
                        //gen new token
                        tokenStr = createNewToken(appId);
                        //update token
                        tokenInfo.setToken(tokenStr.getBytes());
                        //update gen time
                        tokenInfo.setBuildTime(String.valueOf(System.currentTimeMillis()));
                        //update
                        CacheUtils.putToken(appId, tokenInfo);
                    }
                }
            }
        }
        return Result.success(tokenStr);
    }
    /**
     * gen new token
     * @param appId
     * @return
     */
    private String createNewToken(String appId){
        //get current time
        Date now = new Date(System.currentTimeMillis());
        //expire time
        Date expiration = new Date(now.getTime() + 7200000);
        return Jwts
                .builder()
                .setSubject(appId)
                //.claim(targetRoles, db.getRoles())
                .setIssuedAt(now)
                .setIssuer(Constants.ON_LINE_BUILDER)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .compact();
    }
}
