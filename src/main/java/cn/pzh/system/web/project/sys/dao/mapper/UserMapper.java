package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import java.util.List;

public interface UserMapper {
    List<SystemUserEntity> getUsers();

    SystemUserEntity getUserByUserName(String userName);

    SystemUserEntity getUserByLoginName(String loginName);

    void saveUser(SystemUserEntity userEntity);

}
