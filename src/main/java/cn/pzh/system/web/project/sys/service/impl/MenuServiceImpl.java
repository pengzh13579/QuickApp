package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.MenuMapper;
import cn.pzh.system.web.project.sys.service.MenuService;

import cn.pzh.system.web.project.sys.vo.MenuInfoVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /***
     * 首页菜单的显示
     * @param roleIds 角色ID
     * @return 首页菜单树
     */
    @Override
    public List<MenuNode> listIndexMenus(Integer[] roleIds) {
        List<SystemMenuEntity> menuList = menuMapper.listIndexMenus(roleIds);
        List<SystemMenuEntity> parentMenuList = menuList.stream().filter(i -> (0 == i.getPid())).collect(Collectors.toList());
        List<MenuNode> menuNodeList = new ArrayList<>();
        parentMenuList.forEach(item -> {
            MenuNode<SystemMenuEntity> mn = new MenuNode<SystemMenuEntity>();
            mn.setName(item.getMenuRealName());
            mn.setOpenFlag(item.getOpenFlag());
            mn.setId(item.getId());
            mn.setCode(item.getCode());
            mn.setChildren(menuList.stream().filter(i -> (item.getId() == i.getPid())).collect(Collectors.toList()));
            menuNodeList.add(mn);
        });
        return menuNodeList;
    }

    /***
     * 获得菜单树 构建ZTree
     * @return 菜单树
     */
    @Override
    public List<ZTreeNode> getMenuTreeList() {
        List<ZTreeNode> treeList = menuMapper.listMenuTree();
        treeList.add(ZTreeNode.createParent());
        return treeList;
    }

    /***
     * 菜单列表信息查询
     * @param systemMenuEntity 查询实体类
     * @return 菜单列表信息
     */
    @Override
    public List<SystemMenuEntity> listMenus(SystemMenuEntity systemMenuEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(systemMenuEntity.getPageNumber(), systemMenuEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(systemMenuEntity.getSortName()) + " " + systemMenuEntity.getSortOrder());

        return menuMapper.listMenus();
    }

    /***
     * 判断菜单编码是否重复
     * @param code 菜单编码
     * @return null：不重复<br/>
     *          非null：重复
     */
    @Override
    public String checkRepeatMenuCode(String code) {
        SystemMenuEntity menuEntity = menuMapper.getMenuByMenuCode(code);
        return null == menuEntity ? null : menuEntity.getMenuRealName();
    }

    /***
     * 插入菜单信息
     * @param menuInfo 菜单信息
     * @return 插入结果
     */
    @Override
    @Transactional(readOnly = false)
    public Boolean insertMenu(MenuInfoVO menuInfo) {
        SystemMenuEntity menuEntity = new SystemMenuEntity();
        BeanUtils.copyProperties(menuInfo, menuEntity);
        menuEntity.setPid(menuInfo.getPId());
        CommonFieldUtils.setAdminCommon(menuEntity, true);
        menuMapper.saveMenu(menuEntity);
        return true;
    }

    /***
     * 根据菜单编码获得菜单信息
     * @param menuCode 菜单编码
     * @return 菜单信息
     */
    @Override
    public SystemMenuEntity getMenu(String menuCode) {
        return menuMapper.getMenuByMenuCode(menuCode);
    }

    /***
     * 根据菜单ID获得菜单信息
     * @param id 菜单ID
     * @return 菜单信息
     */
    @Override
    public SystemMenuEntity getMenu(Integer id) {
        return menuMapper.getMenuByMenuId(id);
    }

    /***
     * 更新菜单信息
     * @param menuEntity 菜单信息
     * @return 更新结果
     */
    @Override
    @Transactional(readOnly = false)
    public Boolean updateMenu(SystemMenuEntity menuEntity) {
        return menuMapper.updateMenu(menuEntity);
    }
}
