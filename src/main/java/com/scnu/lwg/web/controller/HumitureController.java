package com.scnu.lwg.web.controller;

import com.scnu.lwg.config.Result;
import com.scnu.lwg.web.entity.Humiture;
import com.scnu.lwg.web.service.HumitureService;
import com.scnu.lwg.web.vo.HumitureVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Kin
 * @description Humiture controller
 * @email kinsanities@sina.com
 * @time 2021/5/3 7:27 下午
 */
@RestController
@RequestMapping(value = "/lwg/humiture")
public class HumitureController {

	@Resource
	HumitureService humitureService;

	@PostMapping("/save")
	public Result save(@RequestBody Humiture humiture) {
		humitureService.save(humiture);
		return Result.success(null);
	}

	@GetMapping("/last")
	public Result last() {
		Humiture humiture = humitureService.findCurrentHumiture();
		return Result.success(humiture);
	}

	@GetMapping("/getData")
	public Result getData(String range, String time) {
		HumitureVo vo = humitureService.getData(range, time);
		return Result.success(vo);
	}
}
