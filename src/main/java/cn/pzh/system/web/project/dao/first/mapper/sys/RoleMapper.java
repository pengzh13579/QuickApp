package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import java.util.List;

public interface RoleMapper {

    List<SystemRoleEntity> listRoles();

    SystemRoleEntity selectRoleById(Integer id);

    void saveList(List<SystemRoleEntity> list);

    Boolean save(SystemRoleEntity role);

    Boolean update(SystemRoleEntity role);
}
