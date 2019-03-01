package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserNativePlaceEntity;
import java.util.List;

public interface UserNativePlaceMapper {

    List<SystemUserNativePlaceEntity> selectNativePlaceByUserName(String userName);

    void saveNativePlace (List<SystemUserNativePlaceEntity> userNativePlaces);

    void deleteNativePlaceByUserName(String userName);
}
