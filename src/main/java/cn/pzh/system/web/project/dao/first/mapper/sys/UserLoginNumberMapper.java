package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLoginNumberMapper {

    Integer selectUserLoginNumber(String userName);

    void insertUserLoginNumber(@Param ("userName") String userName, @Param ("loginNumber") Integer loginNumber);

    void updateUserLoginNumber(@Param ("userName") String userName, @Param ("loginNumber") Integer loginNumber);

}
