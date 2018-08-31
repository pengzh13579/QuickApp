package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.dao.first.entity.SystemRoleEntity;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface RoleService {

    List<SystemRoleEntity> getList();

    Boolean insert(SystemRoleEntity info);

    SystemRoleEntity get(Integer id);

    @Transactional (readOnly = false)
    Boolean update(SystemRoleEntity info);

    void delete(Integer id);

    void updateDisFlag(Integer id);
}
