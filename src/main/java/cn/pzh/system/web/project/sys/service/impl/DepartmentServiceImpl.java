package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.model.MenuNode;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.dao.first.mapper.sys.DepartmentMapper;
import cn.pzh.system.web.project.sys.service.DepartmentService;
import cn.pzh.system.web.project.sys.vo.DepartmentTreeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentTreeInfo> listDepartmentTree(String code) {
        List<SystemDepartmentEntity> departmentList = departmentMapper.getDepartmentList();

        return getDepartmentTree(departmentList, "0");
    }

    private List<DepartmentTreeInfo> getDepartmentTree(List<SystemDepartmentEntity> departmentList,
            String pCode){
        List<SystemDepartmentEntity> parentDepartmentList = departmentList.stream().filter(i
                -> (i.getPcode().equals(pCode))).collect(Collectors.toList());
        List<DepartmentTreeInfo> departmentTreeNodes = new ArrayList<>();
        if (null != parentDepartmentList && parentDepartmentList.size() > 0) {
            parentDepartmentList.forEach(item -> {
                DepartmentTreeInfo departmentTreeNode = new DepartmentTreeInfo();
                departmentTreeNode.setDepartSimpleName(item.getDepartSimpleName());
                departmentTreeNode.setId(item.getId());
                departmentTreeNode.setCode(item.getCode());
                departmentTreeNode.setOpenFlag(item.getOpenFlag());
                departmentTreeNode.setDepartmentChildren(getDepartmentTree(departmentList, item.getCode()));
                departmentTreeNodes.add(departmentTreeNode);
            });
        }
        return departmentTreeNodes;
    }

    @Override
    @Transactional (readOnly = false)
    public Boolean insert(SystemDepartmentEntity department) {
        return departmentMapper.save(department);
    }

    @Override
    public SystemDepartmentEntity get(Integer id) {
        return departmentMapper.selectDepartmentById(id);
    }

    @Override
    @Transactional (readOnly = false)
    public Boolean update(SystemDepartmentEntity department) {
        return departmentMapper.update(department);
    }

    @Override
    @Transactional (readOnly = false)
    public void delete(Integer id) {
        SystemDepartmentEntity department = departmentMapper.selectDepartmentById(id);
        CommonFieldUtils.setDeleteCommon(department);
        departmentMapper.update(department);
    }

    @Override
    public List<SystemDepartmentEntity> listDepartmentUsers(String code){
        return null;
    }

    @Override
    public SystemDepartmentEntity getDepartmentByCode(String code) {
        return departmentMapper.selectDepartmentByCode(code);
    }
}
