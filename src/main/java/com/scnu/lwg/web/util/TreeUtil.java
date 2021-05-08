package com.scnu.lwg.web.util;

import com.scnu.lwg.web.vo.MenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kin
 * @description tree util
 * @email kinsanities@sina.com
 * @time 2021/5/2 3:11 下午
 */
public class TreeUtil {

	public static List<MenuVo> toTree(List<MenuVo> treeList, Long pid) {
		List<MenuVo> retList = new ArrayList<MenuVo>();
		for (MenuVo parent : treeList) {
			if (pid.equals(parent.getPid())) {
				retList.add(findChildren(parent, treeList));
			}
		}
		return retList;
	}
	private static MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
		for (MenuVo child : treeList) {
			if (parent.getId().equals(child.getPid())) {
				if (parent.getChild() == null) {
					parent.setChild(new ArrayList<>());
				}
				parent.getChild().add(findChildren(child, treeList));
			}
		}
		return parent;
	}
}