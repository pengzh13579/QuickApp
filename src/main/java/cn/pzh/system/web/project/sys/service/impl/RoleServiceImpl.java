package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.RoleMapper;
import cn.pzh.system.web.project.sys.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<SystemRoleEntity> listRoles(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);

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
