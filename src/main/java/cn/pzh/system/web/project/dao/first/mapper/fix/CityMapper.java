package cn.pzh.system.web.project.dao.first.mapper.fix;

import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.common.model.ProvinceModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {

    List<ProvinceModel> list();

    List<CityModel> getCityByProvinceId(@Param("id")String id);

    List<AreaModel> getAreaByCityId(@Param("id")String id);
}
