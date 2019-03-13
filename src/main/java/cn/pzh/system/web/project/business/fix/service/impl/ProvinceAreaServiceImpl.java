package cn.pzh.system.web.project.business.fix.service.impl;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedProvinceEntity;
import cn.pzh.system.web.project.dao.first.mapper.fix.CityMapper;
import cn.pzh.system.web.project.dao.first.mapper.fix.CountyMapper;
import cn.pzh.system.web.project.dao.first.mapper.fix.DistrictMapper;
import cn.pzh.system.web.project.dao.first.mapper.fix.ProvinceMapper;
import cn.pzh.system.web.project.business.fix.service.ProvinceAreaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class ProvinceAreaServiceImpl implements ProvinceAreaService {

    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private CountyMapper countyMapper;
    @Autowired
    private DistrictMapper districtMapper;
    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public List<FixedProvinceEntity> getAllProvince() {
        return provinceMapper.listProvinces();
    }

    @Override
    public List<FixedCountyEntity> listCountys(String id) {
        return countyMapper.listCountys(id);
    }

    @Override
    public List<FixedDistrictEntity> listDistricts(String id) {
        return districtMapper.listDistricts(id);
    }
}
