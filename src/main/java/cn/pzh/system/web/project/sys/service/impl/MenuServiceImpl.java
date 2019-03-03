package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.MenuMapper;
import cn.pzh.system.web.project.sys.service.MenuService;

import cn.pzh.system.web.project.sys.vo.MenuInfo;
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

    @Override
    public List<ZTreeNode> getMenuTreeList() {
        List<ZTreeNode> treeList = menuMapper.listMenuTree();
        treeList.add(ZTreeNode.createParent());
        return treeList;
    }

    @Override
    public List<SystemMenuEntity> listMenus(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        return menuMapper.listMenus();
    }

    @Override
    public String checkRepeatMenuCode(String code) {
        SystemMenuEntity menuEntity = menuMapper.getMenuByMenuCode(code);
        return null == menuEntity ? null : menuEntity.getMenuRealName();
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean insertMenu(MenuInfo menuInfo) {
        SystemMenuEntity menuEntity = new SystemMenuEntity();
        BeanUtils.copyProperties(menuInfo, menuEntity);
        menuEntity.setPid(menuInfo.getPId());
        CommonFieldUtils.setAdminCommon(menuEntity, true);
        menuMapper.saveMenu(menuEntity);
        return true;
    }

    @Override
    public SystemMenuEntity getMenu(String menuCode) {
        return menuMapper.getMenuByMenuCode(menuCode);
    }

    @Override
    public SystemMenuEntity getMenu(Integer id) {
        return menuMapper.getMenuByMenuId(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean updateMenu(SystemMenuEntity menuEntity) {
        return menuMapper.updateMenu(menuEntity);
    }
}
