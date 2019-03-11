package cn.pzh.system.web.project.fix.controller;

import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import cn.pzh.system.web.project.fix.service.ProvinceAreaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping ("/provinceAreaController")
public class ProvinceAreaController {

    @Autowired
    private ProvinceAreaService provinceAreaService;

    /***
     * 根据省份id获取城市数据后直接使用@ResponseBody装成json数据
     * @param id 省份id
     * @return 查询结果
     */
    @RequestMapping (value = "/getCityByProvinceId/{id}")
    @ResponseBody
    public AjaxJson getCityByProvinceId(@PathVariable ("id") String id){
        AjaxJson j = new AjaxJson();
        List<FixedCountyEntity> cityList = provinceAreaService.listCountys(id);
        if (cityList!=null){
            j.setSuccess(true);
            j.setObj(cityList);
            return j;
        } else {
            j.setSuccess(false);
            return j;
        }
    }

    /***
     * 根据城市id获取区域数据后直接使用@ResponseBody装成json数据
     * @param id 城市id
     * @return 查询结果
     */
    @RequestMapping(value = "/getAreaByCityId/{id}")
    @ResponseBody
    public AjaxJson getAreaByCityId(@PathVariable("id") String id){
        AjaxJson j = new AjaxJson();
        List<FixedDistrictEntity> areaList = provinceAreaService.listDistricts(id);
        if (areaList!=null){
            j.setSuccess(true);
            j.setObj(areaList);
            return j;
        } else {
            j.setSuccess(false);
            return j;
        }
    }
}
