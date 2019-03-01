package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    List<SystemUserEntity> listUsers();

    SystemUserEntity getUserByUserName(String userName);

    SystemUserEntity getUserById(Integer id);

    SystemUserEntity getUserByLoginName(String loginName);

    void saveUser(SystemUserEntity systemUserEntity);

    void updateUser(SystemUserEntity systemUserEntity);
}
