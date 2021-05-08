package com.scnu.lwg.web.service;

import com.scnu.lwg.web.entity.Humiture;
import com.scnu.lwg.web.vo.HumitureVo;

/**
 * @author Kin
 * @description Humiture Service
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:55 下午
 */

public interface HumitureService {
	/**
	 * save
	 * @param humiture
	 * @return
	 */
	Humiture save(Humiture humiture);

	/**
	 * 查询当前
	 * @return
	 */
	Humiture findCurrentHumiture();

	/**
	 * 查询数据
	 * @param range
	 * @param time
	 * @return
	 */
	HumitureVo getData(String range, String time);
}
