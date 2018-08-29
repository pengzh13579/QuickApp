package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.AreaModel;
import cn.pzh.system.web.project.common.model.CityModel;
import cn.pzh.system.web.project.sys.service.ProvinceAreaService;
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

    // 根据省份id获取城市数据后直接使用@ResponseBody装成json数据
    @RequestMapping (value = "/getCityByProvinceId/{id}")
    @ResponseBody
    public AjaxJson getCityByProvinceId(@PathVariable ("id") String id){
        AjaxJson j = new AjaxJson();
        List<CityModel> cityList = provinceAreaService.getCityByProvinceId(id);
        if (cityList!=null){
            j.setSuccess(true);
            j.setObj(cityList);
            return j;
        } else {
            j.setSuccess(false);
            return j;
        }
    }
    //    根据城市id获取区域数据后直接使用@ResponseBody装成json数据
    @RequestMapping(value = "/getAreaByCityId/{id}")
    @ResponseBody
    public AjaxJson getAreaByCityId(@PathVariable("id") String id){
        AjaxJson j = new AjaxJson();
        List<AreaModel> areaList = provinceAreaService.getAreaByCityId(id);
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
