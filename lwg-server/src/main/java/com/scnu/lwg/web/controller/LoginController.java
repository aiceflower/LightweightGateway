package com.scnu.lwg.web.controller;

import com.scnu.lwg.web.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Kin
 * @description login controller
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:54 下午
 */

@RestController
@RequestMapping("login")
public class LoginController {
	@Resource
	private SysMenuService sysMenuService;

	@GetMapping("/menu")
	public Map<String, Object> menu() {
		return sysMenuService.menu();
	}
}