package cn.pzh.system.web.project.sys.shiro.factory;

import cn.pzh.system.web.project.common.model.ShiroUserModel;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.ManagerStatus;
import cn.pzh.system.web.project.common.utils.Convert;
import cn.pzh.system.web.project.common.utils.SpringContextHolder;
import cn.pzh.system.web.project.sys.dao.mapper.MenuMapper;
import cn.pzh.system.web.project.sys.dao.mapper.UserMapper;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuMapper departMapper;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public SystemUserEntity getUser(String userName) {

        SystemUserEntity user = userMapper.getUserByLoginName(userName);

        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getDisFlag() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUserModel shiroUser(SystemUserEntity user) {
        ShiroUserModel shiroUser = new ShiroUserModel();

        shiroUser.setId(user.getId());            // 账号id
        shiroUser.setUserName(user.getUserName());// 账号
        shiroUser.setDeptId(user.getDeptId());    // 部门id
        shiroUser.setDeptName(user.getDeptName());// 部门名称
        shiroUser.setRealName(user.getRealName());        // 用户名称

        shiroUser.setRoleId(user.getRoleId());

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        //TODO
        //List<String> resUrls = menuMapper.getMenu(roleId);
        //return resUrls;
        return null;
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        //TODO
        //return ConstantFactory.me().getSingleRoleTip(roleId);
        return null;
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUserModel shiroUser, SystemUserEntity user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
