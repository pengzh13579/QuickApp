package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.RoleMapper;
import cn.pzh.system.web.project.sys.service.RoleService;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /***
     * 角色列表信息查询
     * @param systemRoleEntity 查询实体类
     * @return 角色列表信息
     */
    @Override
    public List<SystemRoleEntity> listRoles(SystemRoleEntity systemRoleEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(systemRoleEntity.getPageNumber(), systemRoleEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(systemRoleEntity.getSortName()) + " " + systemRoleEntity.getSortOrder());

        return roleMapper.listRoles(systemRoleEntity);
    }

    /***
     * 添加角色信息
     * @param info 角色信息
     * @return 添加角色结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean insert(SystemRoleEntity info) {
        CommonFieldUtils.setAdminCommon(info, true);
        return roleMapper.save(info);
    }

    /***
     * 根据角色ID获得角色信息
     * @param id 角色ID
     * @return 角色信息
     */
    @Override
    public SystemRoleEntity get(Integer id) {
        return roleMapper.selectRoleById(id);
    }

    /***
     * 修改角色信息
     * @param info 角色信息
     * @return 修改角色结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean update(SystemRoleEntity info) {
        CommonFieldUtils.setAdminCommon(info, false);
        return roleMapper.update(info);
    }

    /***
     * 删除角色--将disFlag变为1
     * @param id 角色ID
     */
    @Override
    @Transactional (readOnly = false)
    public void delete(Integer id) {

        // 根据角色ID获得角色信息
        SystemRoleEntity role = roleMapper.selectRoleById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(role);

        // 更新角色信息
        roleMapper.update(role);
    }
}
