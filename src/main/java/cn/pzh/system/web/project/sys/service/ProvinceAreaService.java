package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.common.model.ProvinceModel;
import java.util.List;

public interface ProvinceAreaService {

    List<ProvinceModel> getAllProvince();

    List<CityModel> getCityByProvinceId(String id) ;

    List<AreaModel> getAreaByCityId(String id);
}
