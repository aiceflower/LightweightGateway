package com.scnu.lwg.web.jpa;

import com.scnu.lwg.web.entity.Humiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Kin
 * @description humiture repository
 * @email kinsanities@sina.com
 * @time 2021/5/2 3:05 下午
 */

public interface HumitureRepository extends JpaRepository<Humiture, Long> {

	/**
	 * 查询最后一条
	 * @return
	 */
	@Query(value = "select * from  ht ORDER BY id desc limit 1 ",nativeQuery = true)
	Humiture findLast();

	/**
	 * 获取数据
	 * @param range
	 * @param time
	 * @return
	 */
	@Query(value = "select * from  ht WHERE DATE_FORMAT(NOW(), '%i') % ? = 0 AND create_time >= DATE_SUB(NOW(), INTERVAL ? MINUTE) ORDER BY create_time",nativeQuery = true)
	List<Humiture> getData(String range, String time);
}
