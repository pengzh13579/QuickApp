package cn.pzh.system.web.project.business.fix.service;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedProvinceEntity;

import java.util.List;

public interface ProvinceAreaService {

    List<FixedProvinceEntity> getAllProvince();

    List<FixedCountyEntity> listCountys(String id) ;

    List<FixedDistrictEntity> listDistricts(String id);
}
