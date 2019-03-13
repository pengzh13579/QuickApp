package cn.pzh.system.web.project.business.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import cn.pzh.system.web.project.business.sys.vo.DepartmentTreeVO;
import java.util.List;

public interface DepartmentService {

    /***
     * 部门列表信息查询
     * @param code 部门编码
     * @return 部门列表信息
     */
    List<DepartmentTreeVO> listDepartmentTree(String code);

    /***
     * 添加部门信息
     * @param info 部门信息
     * @return 添加部门结果
     */
    Boolean insert(SystemDepartmentEntity info);

    /***
     * 根据部门ID获得部门信息
     * @param id 部门ID
     * @return 部门信息
     */
    SystemDepartmentEntity get(Integer id);

    /***
     * 修改部门信息
     * @param info 部门信息
     * @return 修改部门结果
     */
    Boolean update(SystemDepartmentEntity info);

    /***
     * 删除部门--将disFlag变为1
     * @param code 部门编号
     */
    void delete(String code);

    /***
     * 根据部门编码获得关联人员
     * @param code 部门编码
     * @return 部门关联人员信息
     */
    List<SystemDepartmentEntity> listDepartmentUsers(String code);

    /***
     * 根据部门编码获得部门信息
     * @param code 部门编码
     * @return 部门信息
     */
    SystemDepartmentEntity getDepartmentByCode(String code);

    /***
     * 部门人员关联
     * @param id 部门ID
     * @param userIds 人员ID
     * @return 关联结果
     */
    Boolean relatedUsers(Integer id, List<Integer> userIds);

    /***
     * 删除部门关联用户
     * @param id 部门ID
     * @param userIds 用户ID
     * @return 删除结果
     */
    Boolean deleteRelatedUsers(Integer id, List<Integer> userIds);
}
