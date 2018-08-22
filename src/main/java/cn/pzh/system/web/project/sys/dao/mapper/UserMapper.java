package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import java.util.List;

public interface UserMapper {
    List<SystemUserEntity> getAll();

    SystemUserEntity getUserByUserName(String userName);

    void saveUser(SystemUserEntity userEntity);

    void saveContact(SystemContactEntity contactEntity);
}
