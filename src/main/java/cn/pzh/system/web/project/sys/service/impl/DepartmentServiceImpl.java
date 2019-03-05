package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.DepartmentMapper;
import cn.pzh.system.web.project.sys.service.DepartmentService;
import cn.pzh.system.web.project.sys.vo.DepartmentTreeVO;
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

    /***
     * 部门列表信息查询
     * @param code 部门编码
     * @return 部门列表信息
     */
    @Override
    public List<DepartmentTreeVO> listDepartmentTree(String code) {
        List<SystemDepartmentEntity> departmentList = departmentMapper.getDepartmentList();

        return getDepartmentTree(departmentList, "0");
    }

    /***
     * 添加部门信息
     * @param info 部门信息
     * @return 添加部门结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean insert(SystemDepartmentEntity info) {
        return departmentMapper.save(info);
    }

    /***
     * 根据部门ID获得部门信息
     * @param id 部门ID
     * @return 部门信息
     */
    @Override
    public SystemDepartmentEntity get(Integer id) {
        return departmentMapper.selectDepartmentById(id);
    }

    /***
     * 修改部门信息
     * @param info 部门信息
     * @return 修改部门结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean update(SystemDepartmentEntity info) {
        return departmentMapper.update(info);
    }

    /***
     * 删除部门--将disFlag变为1
     * @param code 部门编号
     */
    @Override
    @Transactional (readOnly = false)
    public void delete(String code) {

        // 根据角色ID获得部门信息
        SystemDepartmentEntity department = departmentMapper.selectDepartmentByCode(code);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(department);

        // 更新部门信息
        departmentMapper.update(department);
    }

    /***
     * 根据部门编码获得关联人员
     * @param code 部门编码
     * @return 部门关联人员信息
     */
    @Override
    public List<SystemDepartmentEntity> listDepartmentUsers(String code){
        return null;
    }

    /***
     * 根据部门编码获得部门信息
     * @param code 部门编码
     * @return 部门信息
     */
    @Override
    public SystemDepartmentEntity getDepartmentByCode(String code) {
        return departmentMapper.selectDepartmentByCode(code);
    }

    /***
     * 部门人员关联
     * @param id 部门ID
     * @param userIds 人员ID
     * @return 关联结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean relatedUsers(Integer id, List<Integer> userIds) {
        return departmentMapper.insertOrUpdateRelatedUsers(id, userIds);
    }

    /***
     * 删除部门关联用户
     * @param id 部门ID
     * @param userIds 用户ID
     * @return 删除结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean deleteRelatedUsers(Integer id, List<Integer> userIds) {
        return departmentMapper.deleteRelatedUsers(id, userIds);
    }

    /***
     * 根据父部门编码从部门列表中循环迭代子部门 <br/>
     * 生成部门树返回
     * @param departmentList 部门列表
     * @param pCode 父部门编码
     * @return 部门树
     */
    private List<DepartmentTreeVO> getDepartmentTree(List<SystemDepartmentEntity> departmentList,
            String pCode){

        // 根据父部门编码获得下面的部门列表
        List<SystemDepartmentEntity> parentDepartmentList = departmentList.stream().filter(i
                -> (i.getPcode().equals(pCode))).collect(Collectors.toList());

        // 构建部门树子节点
        List<DepartmentTreeVO> departmentTreeNodes = new ArrayList<>();

        // 部门下存在数据时
        if (null != parentDepartmentList && parentDepartmentList.size() > 0) {

            // 像部门树的子节点中插入数据
            parentDepartmentList.forEach(item -> {
                DepartmentTreeVO departmentTreeNode = new DepartmentTreeVO();

                // 部门名
                departmentTreeNode.setDepartSimpleName(item.getDepartSimpleName());

                // 部门ID
                departmentTreeNode.setId(item.getId());

                // 部门编码
                departmentTreeNode.setCode(item.getCode());

                // 部门打开标记位
                departmentTreeNode.setOpenFlag(item.getOpenFlag());

                // 循环迭代子节点
                departmentTreeNode.setDepartmentChildren(getDepartmentTree(departmentList, item.getCode()));
                departmentTreeNodes.add(departmentTreeNode);
            });
        }
        return departmentTreeNodes;
    }
}
