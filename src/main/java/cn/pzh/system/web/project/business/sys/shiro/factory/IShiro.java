package cn.pzh.system.web.project.business.sys.shiro.factory;

import cn.pzh.system.web.project.common.model.ShiroUserModel;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.List;

/**
 * 定义shirorealm所需数据的接口
 *
 * @author fengshuonan
 * @date 2016年12月5日 上午10:23:34
 */
public interface IShiro {

    /**
     * 根据账号获取登录用户
     *
     * @param userName 账号
     */
    SystemUserEntity getUser(String userName);

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    ShiroUserModel shiroUser(SystemUserEntity user);

    /**
     * 获取权限列表通过角色id
     *
     * @param roleId 角色id
     */
    List<String> findPermissionsByRoleId(Integer roleId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     */
    String findRoleNameByRoleId(Integer roleId);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUserModel shiroUser, SystemUserEntity user, String realmName);

}
