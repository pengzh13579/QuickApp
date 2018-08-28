package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;

import java.util.List;

public interface MenuService {
    List<MenuNode> getMenuList(Integer[] roleIds);

    List<ZTreeNode> getMenuTreeList();
}
