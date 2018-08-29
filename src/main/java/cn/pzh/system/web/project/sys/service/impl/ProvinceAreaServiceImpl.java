package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.common.model.ProvinceModel;
import cn.pzh.system.web.project.sys.dao.mapper.CityMapper;
import cn.pzh.system.web.project.sys.service.ProvinceAreaService;
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

    @Override
    public List<ProvinceModel> getAllProvince() {
        return cityMapper.list();
    }

    @Override
    public List<CityModel> getCityByProvinceId(String id) {
        return cityMapper.getCityByProvinceId(id);
    }

    @Override
    public List<AreaModel> getAreaByCityId(String id) {
        return cityMapper.getAreaByCityId(id);
    }
}
