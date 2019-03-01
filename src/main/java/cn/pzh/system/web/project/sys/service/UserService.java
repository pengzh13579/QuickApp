package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    List<SystemUserEntity> listUsers();

    Boolean registration(UserInfo userInfo, Integer avatarId) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    UserInfo userLogin(String userName, String password, Boolean rememberFlag)
            throws Exception;

    @Transactional (readOnly = false)
    void updateOnlineStatus(Integer isOnline, String UserName);

    SystemUserEntity getUserEntity(String userName);

    UserInfo getUser(String userName);

    UserInfo getUser(Integer id);

    @Transactional (readOnly = false)
    void changePassword(String newPwd);

    @Transactional (readOnly = false)
    Boolean updateUserInfo(UserInfo userInfo)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Transactional (readOnly = false)
    void changeUserStatus(String userName, Integer disFlag);

    Boolean checkRepeatUserName(String userName);

    void updateUserRoleId(String roleIds, String userName);
}
