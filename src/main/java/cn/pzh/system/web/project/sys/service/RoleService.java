package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface RoleService {

    List<SystemRoleEntity> listRoles();

    Boolean insert(SystemRoleEntity info);

    SystemRoleEntity get(Integer id);

    Boolean update(SystemRoleEntity info);

    void delete(Integer id);
}
