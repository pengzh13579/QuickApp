package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.common.model.ProvinceModel;
import java.util.List;

public interface CityMapper {

    List<ProvinceModel> list();

    List<CityModel> getCityByProvinceId(String id);

    List<AreaModel> getAreaByCityId(String id);
}
