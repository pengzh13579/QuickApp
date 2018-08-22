package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.dao.first.model.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.sys.dao.mapper.MenuMapper;
import cn.pzh.system.web.project.sys.service.MenuService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly=true,rollbackFor=Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuNode> getMenuList() {
        Map<String,Object> map = new HashMap<>();
        map.put("userName",SecurityUtils.getSubject().getPrincipal().toString());
        map.put("parentMenuCode","0");
        List<SystemMenuEntity> parentMenus = menuMapper.getMenu(map);
        List<MenuNode> menuNodeList = new ArrayList<>();
        parentMenus.forEach(item->{
            MenuNode menuNode = new MenuNode();
            map.put("parentMenuCode",item.getMenuCode());
            menuNode.setParent(item);
            menuNode.setChildren(menuMapper.getMenu(map));
            menuNodeList.add(menuNode);
        });
        return menuNodeList;
    }
}
