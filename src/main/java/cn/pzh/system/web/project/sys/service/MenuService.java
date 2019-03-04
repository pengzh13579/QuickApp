package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;

import cn.pzh.system.web.project.sys.vo.MenuInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MenuService {

    /***
     * 首页菜单的显示
     * @param roleIds 角色ID
     * @return 首页菜单树
     */
    List<MenuNode> listIndexMenus(Integer[] roleIds);

    /***
     * 获得菜单树 构建ZTree
     * @return 菜单树
     */
    List<ZTreeNode> getMenuTreeList();

    /***
     * 菜单列表信息查询
     * @param systemMenuEntity 查询实体类
     * @return 菜单列表信息
     */
    List<SystemMenuEntity> listMenus(SystemMenuEntity systemMenuEntity);

    /***
     * 判断菜单编码是否重复
     * @param code 菜单编码
     * @return null：不重复<br/>
     *          非null：重复
     */
    String checkRepeatMenuCode(String code);

    /***
     * 插入菜单信息
     * @param menuInfo 菜单信息
     * @return 插入结果
     */
    Boolean insertMenu(MenuInfoVO menuInfo);

    /***
     * 根据菜单编码获得菜单信息
     * @param menuCode 菜单编码
     * @return 菜单信息
     */
    SystemMenuEntity getMenu(String menuCode);

    /***
     * 根据菜单ID获得菜单信息
     * @param id 菜单ID
     * @return 菜单信息
     */
    SystemMenuEntity getMenu(Integer id);

    /***
     * 更新菜单信息
     * @param menuEntity 菜单信息
     * @return 更新结果
     */
    Boolean updateMenu(SystemMenuEntity menuEntity);
}
