package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    List<SystemUserEntity> getAll();

    Boolean registration(SystemUserEntity userEntity) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    UserInfo userLogin(String userName, String password, Boolean rememberFlag)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    void updateOnlineStatus(String userName, Integer isOnline);
}
