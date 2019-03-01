package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.common.model.ProvinceModel;
import java.util.List;

public interface CityMapper {

    List<ProvinceModel> list();

    List<CityModel> getCityByProvinceId(String id);

    List<AreaModel> getAreaByCityId(String id);
}
