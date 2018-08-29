package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.dao.first.entity.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;

import cn.pzh.system.web.project.sys.vo.MenuInfo;
import java.util.List;

public interface MenuService {
    List<MenuNode> getMenuList(Integer[] roleIds);

    List<ZTreeNode> getMenuTreeList();

    List<SystemMenuEntity> getMenus();

    String checkRepeatMenuCode(String code);

    Boolean insertMenu(MenuInfo menuInfo);
}
