package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RoleService {

    /***
     * 角色列表信息查询
     * @param systemRoleEntity 查询实体类
     * @return 角色列表信息
     */
    List<SystemRoleEntity> listRoles(SystemRoleEntity systemRoleEntity);

    /***
     * 添加角色信息
     * @param info 角色信息
     * @return 添加角色结果
     */
    Boolean insert(SystemRoleEntity info);

    /***
     * 根据角色ID获得角色信息
     * @param id 角色ID
     * @return 角色信息
     */
    SystemRoleEntity get(Integer id);

    /***
     * 修改角色信息
     * @param info 角色信息
     * @return 修改角色结果
     */
    Boolean update(SystemRoleEntity info);

    /***
     * 删除角色--将disFlag变为1
     * @param id 角色ID
     */
    void delete(Integer id);

    /***
     * 插入或更新角色关联菜单信息
     * @param roleId 角色ID
     * @param menuIds 菜单ID
     */
    void insertRoleRelateMenu(Integer roleId, List<Integer> menuIds);
}
