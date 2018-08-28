package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.dao.first.entity.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.sys.dao.mapper.MenuMapper;
import cn.pzh.system.web.project.sys.service.MenuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuNode> getMenuList(Integer[] roleIds) {
        List<SystemMenuEntity> menuList = menuMapper.getMenuList(roleIds);
        List<SystemMenuEntity> parentMenuList = menuList.stream().filter(i -> "0".equals(i.getPcode())).collect(Collectors.toList());
        List<MenuNode> menuNodeList = new ArrayList<>();
        parentMenuList.forEach(item -> {
            MenuNode<SystemMenuEntity> mn = new MenuNode<SystemMenuEntity>();
            mn.setName(item.getName());
            mn.setCode(item.getCode());
            mn.setChildren(menuList.stream().filter(i -> item.getCode().equals(i.getPcode())).collect(Collectors.toList()));
            menuNodeList.add(mn);
        });
        return menuNodeList;
    }

    @Override
    public List<ZTreeNode> getMenuTreeList() {
        List<ZTreeNode> treeList = menuMapper.menuTreeList();
        treeList.add(ZTreeNode.createParent());
        return treeList;
    }
}
