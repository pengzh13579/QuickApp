package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.dao.first.model.SystemUserEntity;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    List<SystemUserEntity> getAll();

    Boolean registration(SystemUserEntity userEntity) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    String loginCheck(String userName, String password, Boolean rememberFlag)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    void updateOnlineStatus(String userName);
}
