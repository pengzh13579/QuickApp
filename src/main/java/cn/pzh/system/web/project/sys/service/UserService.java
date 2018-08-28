package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    List<SystemUserEntity> getUsers();

    Boolean registration(UserInfo userInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    UserInfo userLogin(String userName, String password, Boolean rememberFlag)
            throws Exception;

    void updateOnlineStatus(Integer isOnline, String UserName);

    void updateOnlineStatus( Integer isOnline);

    SystemUserEntity getUserEntity(String userName);

    UserInfo getUser(String userName);

    void changePassword(String newPwd);

    @Transactional (readOnly = false)
    Boolean updateUserInfo(UserInfo userInfo)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    void changeUserStatus(String userName, Integer disFlag);

    Boolean checkRepeatUserName(String userName);
}
