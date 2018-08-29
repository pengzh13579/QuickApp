package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserNativePlaceEntity;
import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.common.model.ProvinceModel;
import java.util.List;

public interface UserNativePlaceMapper {

    List<SystemUserNativePlaceEntity> selectNativePlaceByUserName(String userName);

    void saveNativePlace (List<SystemUserNativePlaceEntity> userNativePlaces);

    void deleteNativePlaceByUserName(String userName);
}
