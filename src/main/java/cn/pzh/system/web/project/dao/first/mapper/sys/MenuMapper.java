package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.ZTreeNode;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {

    List<SystemMenuEntity> listIndexMenus(Integer[] roleIds);

    List<ZTreeNode> listMenuTree();

    List<SystemMenuEntity> listMenus();

    SystemMenuEntity getMenuByMenuCode(String code);

    void saveMenu(SystemMenuEntity menuEntity);

    String selectCodeById(String pId);

    SystemMenuEntity getMenuByMenuId(Integer id);

    Boolean updateMenu(SystemMenuEntity menuEntity);

    List<Integer> getMenuIdByRoleId(Integer roleId);

    Boolean insertOrUpdateRoleRelateMenu(@Param ("id") Integer id, @Param("menuIds") List<Integer> menuIds);
}
