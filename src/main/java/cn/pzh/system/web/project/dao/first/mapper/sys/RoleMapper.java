package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    /***
     * 角色列表信息查询语句
     * @param systemRoleEntity 查询实体类
     * @return 角色列表
     */
    List<SystemRoleEntity> listRoles(SystemRoleEntity systemRoleEntity);

    /***
     * 根据角色ID查询角色信息
     * @param id 角色ID
     * @return 角色信息
     */
    SystemRoleEntity selectRoleById(Integer id);

    /***
     * 批量保存角色信息
     * @param list 角色信息
     */
    void saveList(List<SystemRoleEntity> list);

    /***
     * 保存角色信息
     * @param role 角色信息
     * @return 保存结果
     */
    Boolean save(SystemRoleEntity role);

    /***
     * 更新角色信息
     * @param role 角色信息
     * @return 更新结果
     */
    Boolean update(SystemRoleEntity role);

    /***
     * 插入或更新角色关联菜单信息
     * @param roleId 角色ID
     * @param menuIds 菜单ID
     * @return 更新结果
     */
    Boolean insertRoleRelateMenu(@Param("roleId") Integer roleId,
                                         @Param("menuIds") List<Integer> menuIds);

    /***
     * 删除角色所有的菜单
     * @param roleId 角色ID
     * @return 更新结果
     */
    void deleteRoleRelateMenu(Integer roleId);
}
