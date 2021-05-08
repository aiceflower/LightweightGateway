package com.scnu.lwg.web.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Kin
 * @description humiture vo
 * @email kinsanities@sina.com
 * @time 2021/5/3 9:39 下午
 */

@Data
public class HumitureVo {
	List<Long> times;
	List<Float> temperatures;
	List<Float> humidities;
}
