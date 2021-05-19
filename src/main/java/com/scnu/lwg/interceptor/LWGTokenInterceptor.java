package com.scnu.lwg.interceptor;

import com.scnu.lwg.config.Constants;
import com.scnu.lwg.config.ResponseCode;
import com.scnu.lwg.config.Result;
import com.scnu.lwg.entity.TokenInfoEntity;
import com.scnu.lwg.exception.SignatureException;
import com.scnu.lwg.util.CacheUtils;
import com.scnu.lwg.util.JSONUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Kin
 * @description request interceptor
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

public class LWGTokenInterceptor implements HandlerInterceptor
{

    /**
     * pre request
     * @param request request object
     * @param response response object
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        //exclude gen token, and if options request is cors，set allow
        if(request.getRequestURI().startsWith(Constants.GEN_TOKEN) || RequestMethod.OPTIONS.toString().equals(request.getMethod()))
        {
            return true;
        }

        //pass validate code
        if(request.getRequestURI().startsWith("/lwg/validate/code"))
        {
            return true;
        }

        //pass humiture data
        if(request.getRequestURI().startsWith("/lwg/humiture"))
        {
            return true;
        }

        //get other request header
        String authHeader = request.getHeader(Constants.TOKEN_HEADER);

        //if not request token in header
        if (StringUtils.isEmpty(authHeader)){
            authHeader = request.getParameter(Constants.TOKEN);
        }

        try {
            //if not token
            if (StringUtils.isEmpty(authHeader)) {
                throw new SignatureException(Constants.NOT_FOUND_TOKEN);
            }

            //get jwt entity object
            final Claims claims = Jwts.parser().setSigningKey(Constants.SIGNING_KEY)
                    .parseClaimsJws(authHeader).getBody();
            String appId = claims.getSubject();

            if (StringUtils.isEmpty(appId)){
                throw new SignatureException(Constants.NOT_FOUND_TOKEN_INFO);
            }

            //get token，current use cache. Use database queries if necessary
            TokenInfoEntity token = CacheUtils.getToken(TokenInfoEntity.class, appId);

            //token value
            String tokenVal = new String(token.getToken());
            //token not exists, prompt the client to retrieve token
            if(StringUtils.isEmpty(tokenVal)) {
                throw new SignatureException(Constants.NOT_FOUND_TOKEN_INFO);
            }
            //verify token
            if(!tokenVal.equals(authHeader))
            {
                throw new SignatureException(Constants.NOT_FOUND_TOKEN_INFO);
            }

        }
        //deal with exception
        catch (SignatureException | ExpiredJwtException e)
        {
            //output obj
            PrintWriter writer = response.getWriter();

            //output error message
            writer.write(e.getMessage());
            writer.close();
            return false;
        }
        //other exception
        catch (final Exception e)
        {
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtils.objToJson(Result.fail(ResponseCode.TOKEN_PARSE_ERROR)));
            writer.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
