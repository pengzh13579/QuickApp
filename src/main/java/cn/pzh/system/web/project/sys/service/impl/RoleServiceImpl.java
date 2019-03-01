package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.RoleMapper;
import cn.pzh.system.web.project.sys.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<SystemRoleEntity> listRoles() {
        return roleMapper.listRoles();
    }

    @Override
    @Transactional (readOnly = false)
    public Boolean insert(SystemRoleEntity role) {
        return roleMapper.save(role);
    }

    @Override
    public SystemRoleEntity get(Integer id) {
        return roleMapper.selectRoleById(id);
    }

    @Override
    @Transactional (readOnly = false)
    public Boolean update(SystemRoleEntity role) {
        return roleMapper.update(role);
    }

    @Override
    @Transactional (readOnly = false)
    public void delete(Integer id) {
        SystemRoleEntity role = roleMapper.selectRoleById(id);
        role.setDisFlag(1);
        CommonFieldUtils.setAdminCommon(role, false);
        roleMapper.update(role);
    }
}
