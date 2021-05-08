package com.scnu.lwg.web.service.impl;

import com.scnu.lwg.web.entity.SystemMenu;
import com.scnu.lwg.web.jpa.SysMenuRepository;
import com.scnu.lwg.web.service.SysMenuService;
import com.scnu.lwg.web.util.TreeUtil;
import com.scnu.lwg.web.vo.MenuVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kin
 * @description sys menu service impl
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:55 下午
 */

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Resource
	private SysMenuRepository sysMenuRepository;
	@Override
	public Map<String, Object> menu() {
		Map<String, Object> map = new HashMap<>(16);
		Map<String,Object> home = new HashMap<>(16);
		Map<String,Object> logo = new HashMap<>(16);
		List<SystemMenu> menuList = sysMenuRepository.findAllByStatusOrderBySort(true);
		List<MenuVo> menuInfo = new ArrayList<>();
		for (SystemMenu e : menuList) {
			MenuVo menuVO = new MenuVo();
			menuVO.setId(e.getKey().getId());
			menuVO.setPid(e.getPid());
			menuVO.setHref(e.getKey().getHref());
			menuVO.setTitle(e.getKey().getTitle());
			menuVO.setIcon(e.getIcon());
			menuVO.setTarget(e.getTarget());
			menuInfo.add(menuVO);
		}
		map.put("menuInfo", TreeUtil.toTree(menuInfo, 0L));
		home.put("title","首页");
		//控制器路由,自行定义
		home.put("href","/page/welcome-1");
		logo.put("title","后台管理系统");
		//静态资源文件路径,可使用默认的logo.png
		logo.put("image","/templates/images/back.jpg");
		map.put("homeInfo", "{title: '首页',href: '/page/welcome.html'}}");
		map.put("logoInfo", "{title: 'RUGE ADMIN',image: 'images/logo2.png'}");
		return map;
	}
}
