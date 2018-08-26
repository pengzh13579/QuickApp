package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemMenuEntity;
import java.util.List;
import java.util.Map;

public interface MenuMapper {

    List<SystemMenuEntity> getMenuList(Integer[] roleIds);

}
