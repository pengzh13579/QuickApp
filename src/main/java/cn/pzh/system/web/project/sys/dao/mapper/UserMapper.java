package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import java.util.List;

public interface UserMapper {
    List<SystemUserEntity> getUsers();

    SystemUserEntity getUserByUserName(String userName);

    SystemUserEntity getUserByLoginName(String loginName);

    void updateOnlineStatus(SystemUserEntity systemUserEntity);

    void saveUser(SystemUserEntity userEntity);

    void updatePassword(SystemUserEntity systemUserEntity);

    void updateUserInfo(SystemUserEntity userEntity);

    void updateUserDisFlag(SystemUserEntity userEntity);
}
