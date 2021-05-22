package com.scnu.lwg.web.jpa;

import com.scnu.lwg.web.entity.SystemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Kin
 * @description sys menu jpa
 * @email kinsanities@sina.com
 * @time 2021/5/2 3:05 下午
 */

public interface SysMenuRepository extends JpaRepository<SystemMenu, Long> {
	/**
	 * 查询
	 * @param status
	 * @param sort
	 * @return
	 */
	@Query(value = "select * from  system_menu where STATUS = 1  ORDER BY  sort ",nativeQuery = true)
	List<SystemMenu> getSystemMenuByStatusAndSort(Long status, Integer sort);

	/**
	 * 查询全部通过状态，并排序
	 * @param status
	 * @return
	 */
	List<SystemMenu> findAllByStatusOrderBySort(Boolean status);
}
