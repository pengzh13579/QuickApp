package cn.pzh.system.web.project.business.fix.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.PageInfo;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import cn.pzh.system.web.project.business.fix.service.ProvinceAreaService;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedProvinceEntity;
import com.alibaba.fastjson.JSONObject;
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
     * 列表页面
     * @return 列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return "/fix/city/city_list";
    }

    /***
     * 列表信息查询
     * @return 列表信息
     */
    @RequestMapping("/provinceList")
    @ResponseBody
    public String listProvinces() {

        // 根据查询实体类得到列表
        List<FixedProvinceEntity> list = provinceAreaService.getAllProvince();

        // 将列表信息绑定到pageInfo
        PageInfo<FixedProvinceEntity> page = new PageInfo<FixedProvinceEntity>(list);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, page.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

    /***
     * 省级固定存储列表信息查询
     * @param id 省份id
     * @return 省级固定存储列表信息
     */
    @RequestMapping("/countyList/{id}")
    @ResponseBody
    public String listCountys(@PathVariable ("id") String id) {

        // 根据查询实体类得到列表
        List<FixedCountyEntity> list = provinceAreaService.listCountys(id);

        // 将列表信息绑定到pageInfo
        PageInfo<FixedCountyEntity> page = new PageInfo<FixedCountyEntity>(list);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, page.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

    /***
     * 列表信息查询
     * @param id 城市id
     * @return 列表信息
     */
    @RequestMapping("/districtList/{id}")
    @ResponseBody
    public String listDistricts(@PathVariable ("id") String id) {

        // 根据查询实体类得到列表
        List<FixedDistrictEntity> list = provinceAreaService.listDistricts(id);

        // 将列表信息绑定到pageInfo
        PageInfo<FixedDistrictEntity> page = new PageInfo<FixedDistrictEntity>(list);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, page.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

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
