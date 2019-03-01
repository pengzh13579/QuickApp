package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;

import cn.pzh.system.web.project.sys.vo.MenuInfo;
import java.util.List;

public interface MenuService {

    List<MenuNode> listIndexMenus(Integer[] roleIds);

    List<ZTreeNode> getMenuTreeList();

    List<SystemMenuEntity> listMenus();

    String checkRepeatMenuCode(String code);

    Boolean insertMenu(MenuInfo menuInfo);

    SystemMenuEntity getMenu(String menuCode);

    SystemMenuEntity getMenu(Integer id);

    Boolean updateMenu(SystemMenuEntity menuEntity);
}
