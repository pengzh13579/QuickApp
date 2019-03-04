package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
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
}
