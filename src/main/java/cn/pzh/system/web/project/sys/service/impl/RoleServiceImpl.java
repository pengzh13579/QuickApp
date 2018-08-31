package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.dao.first.entity.SystemFileEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemRoleEntity;
import cn.pzh.system.web.project.sys.dao.mapper.FileMapper;
import cn.pzh.system.web.project.sys.service.FileService;
import cn.pzh.system.web.project.sys.service.RoleService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Override
    public List<SystemRoleEntity> getList() {
        return null;
    }

    @Override
    public Boolean insert(SystemRoleEntity info) {
        return null;
    }

    @Override
    public SystemRoleEntity get(Integer id) {
        return null;
    }

    @Override
    public Boolean update(SystemRoleEntity info) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void updateDisFlag(Integer id) {

    }
}
