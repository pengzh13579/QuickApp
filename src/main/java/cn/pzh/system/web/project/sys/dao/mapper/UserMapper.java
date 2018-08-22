package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.model.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.model.SystemUserEntity;
import java.util.List;

public interface UserMapper {
    List<SystemUserEntity> getAll();

    SystemUserEntity getUserNameByName(String userName);

    void saveUser(SystemUserEntity userEntity);

    void saveContact(SystemContactEntity contactEntity);
}
