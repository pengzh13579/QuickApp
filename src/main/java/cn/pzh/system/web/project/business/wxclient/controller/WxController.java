package cn.pzh.system.web.project.business.wxclient.controller;

import cn.pzh.system.web.project.base.cache.OpenIDCache;
import cn.pzh.system.web.project.business.fix.service.ProvinceAreaService;
import cn.pzh.system.web.project.business.info.service.InfoPageService;
import cn.pzh.system.web.project.business.wx.service.WxUserService;
import cn.pzh.system.web.project.common.conf.wx.WxConfig;
import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.constant.WxProgramConstants;
import cn.pzh.system.web.project.common.utils.HttpUtil;
import cn.pzh.system.web.project.common.utils.RandomUtils;
import cn.pzh.system.web.project.common.utils.WeixinUtil;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedProvinceEntity;
import cn.pzh.system.web.project.dao.first.entity.info.InfoPageEntity;
import cn.pzh.system.web.project.dao.first.entity.wx.WxUserEntity;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/wxController")
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private ProvinceAreaService provinceAreaService;

    @Autowired
    private InfoPageService pageService;

    /**
     * 微信用户登录系统，返回UUID作为缓存key
     * @param request
     * @return
     */
    @RequestMapping(value="login",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> ret = new HashMap<String, String>();
        try {
            String code = request.getParameter(WxProgramConstants.GET_CODE);
            // 第一次访问
            if (StringUtils.isBlank(code)) {
                String backURL = request.getRequestURL().toString();
                if (request.getQueryString() != null) {
                    backURL += "?" + request.getQueryString();
                }
                response.sendRedirect(String.format(WxProgramConstants.HTTP_OAUTH2_AUTHORIZE,
                        wxConfig.getAppid(),URLEncoder.encode(backURL, "UTF-8")));
                return null;
            }
            String resData = HttpUtil.requestCommonByHttpGet(String.format(WxProgramConstants.HTTP_SNS_JSCODE2SESSION,
                    wxConfig.getAppid(), wxConfig.getSecret(), code), null, true);
            JSONObject accessObj = JSONObject.parseObject(resData);

            // expires_in 会话有效期, 以秒为单位, 例如2592000代表会话有效期为30天
            // session_key 会话密钥
            String sessionKey = accessObj.getString(WxProgramConstants.GET_SESSION_KEY);
            // openid 用户唯一标识 "oswcO0eJrrPRkZHjLEfypgJiL24o"
            String openid = accessObj.getString(WxProgramConstants.GET_OPEN_ID);
            // 第三方session
            String thirdSession = UUID.randomUUID().toString().replaceAll("-", "");
            OpenIDCache.getCacheMap().put(thirdSession, openid + WxProgramConstants.SLIPT_CACHE_CODE + sessionKey);

            WxUserEntity wxUserEntity = new WxUserEntity();
            wxUserEntity.setAccesstoken(sessionKey);
            wxUserEntity.setOpenid(openid);
            wxUserEntity.setCreateTime(new Date());
            wxUserService.insertOrUpdate(wxUserEntity);

            ret.put(WxProgramConstants.SET_CODE, thirdSession);
            ret.put(WxProgramConstants.SET_STATUS_CODE, WxProgramConstants.STATUS_SUCCESS_CODE);
        } catch (Exception e)
        {
        }
        return ret;
    }

    /**
     *
     * 解密微信用户加密信息入数据库
     * @param request
     * @return
     */
    @RequestMapping(value="decodeWxInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> decodeWxInfo(HttpServletRequest request) {
        Map<String, String> ret = new HashMap<String, String>();
        try {
            String dianToken = request.getParameter("dianToken");
            String encryptedData = request.getParameter("encryptedData");
            String iv = request.getParameter("iv");
            String sessionKey = OpenIDCache.getCacheMap().get(dianToken).split(WxProgramConstants.SLIPT_CACHE_CODE)[1];
            JSONObject obj = WeixinUtil.getUserInfo(encryptedData, sessionKey, iv);

            // 解密后的openId
            String openId = obj.getString(WxProgramConstants.GET_OPEN_ID_UPPER);

            WxUserEntity wxUserEntity = wxUserService.getWxUserByOpenId(openId);
            if(wxUserEntity == null) {

                //保存微信用户信息
                JSONObject watermarkObj = obj.getJSONObject(WxProgramConstants.GET_WATER_MARK);
                String appid = watermarkObj.getString(WxProgramConstants.GET_WATER_MARK);

                wxUserEntity = new WxUserEntity();
                wxUserEntity.setAccesstoken(sessionKey);
                wxUserEntity.setOpenid(openId);
                wxUserEntity.setNickname(obj.getString(WxProgramConstants.GET_NICK_NAME));
                wxUserEntity.setHeadimg(obj.getString(WxProgramConstants.GET_AVATAR_URL));
                wxUserEntity.setCreateTime(new Date());
                wxUserEntity.setProvice(obj.getString(WxProgramConstants.GET_PROVINCE));
                wxUserEntity.setCity(obj.getString(WxProgramConstants.GET_CITY));
                wxUserEntity.setAppid(obj.getString(WxProgramConstants.GET_APP_ID));
                wxUserEntity.setGender(obj.getString(WxProgramConstants.GET_GENDER));

                wxUserService.insert(wxUserEntity);
            }
        } catch(Exception e) {
        }

        return ret;
    }

    @RequestMapping(value = "/getTaskList", consumes="application/json")
    @ResponseBody
    public Map<String, Object> getTaskList(HttpServletRequest request,
                                           @RequestBody Map<String, String> json) {

        Map<String, Object> ret = new HashMap<String, Object>();
        // 一页多少条
        String pageSize = json.get(WxProgramConstants.GET_PAGE_SIZE);
        // 第几页
        String pageIndex = json.get(WxProgramConstants.GET_PAGE_INDEX);

        // 下拉列表只存在全部(1;1)、省级(1;0)、市级(0;1)这3个等级
        // 省级是否可见，0不可见；1可见
        String provinceFlag = json.get(WxProgramConstants.GET_PROVINCE_FLAG);
        // 市级是否可见，0不可见；1可见
        String cityFlag = json.get(WxProgramConstants.GET_CITY_FLAG);
        // 所属行业
        String industry = json.get(WxProgramConstants.GET_INDUSTRY_CODES);
        // 所查省
        String provinceId = json.get(WxProgramConstants.GET_PROVINCE_ID);
        // 所查市
        String cityId = json.get(WxProgramConstants.GET_CITY_ID);
        // 所查地区
        String areaId = json.get(WxProgramConstants.GET_AREA_ID);
        //String openid = OpenIDCache.getCacheMap().get(dianToken).split("#")[0];
        try {
            InfoPageEntity infoPageEntity = new InfoPageEntity();
            infoPageEntity.setPageNumber(Integer.valueOf(pageIndex));
            infoPageEntity.setPageSize(Integer.valueOf(pageSize));
            // 全部
            if (provinceFlag.equals("1") && cityFlag.equals("1")) {
                // 全部的话将A传入，方便取省级，省级的话就是：areaId等于cityId或者areaId等于cityId取前2位而后加0000000000
                infoPageEntity.setAreaFlag("A");
                infoPageEntity.setAreaId(cityId);

                // 省级
            } else if (provinceFlag.equals("1") && cityFlag.equals("0")) {
                infoPageEntity.setAreaFlag("1");
                infoPageEntity.setAreaId(provinceId);

                // 市级
            } else if (provinceFlag.equals("0") && cityFlag.equals("1")) {
                infoPageEntity.setAreaFlag("2");
                infoPageEntity.setAreaId(cityId);

            }
            pageService.listPages(infoPageEntity);
        } catch(Exception e) {
        }
        return ret;
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

        // JSONObject
        JSONObject result = new JSONObject();

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

    /***
     * 省级固定存储列表信息查询
     * @return 省级固定存储列表信息
     */
    @RequestMapping("/countyList")
    @ResponseBody
    public String listCountys() {

        // 根据查询实体类得到列表
        List<FixedCountyEntity> list = provinceAreaService.listCountys(null);

        // JSONObject
        JSONObject result = new JSONObject();

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

    /***
     * 列表信息查询
     * @return 列表信息
     */
    @RequestMapping("/districtList")
    @ResponseBody
    public String listDistricts() {

        // 根据查询实体类得到列表
        List<FixedDistrictEntity> list = provinceAreaService.listDistricts(null);

        // JSONObject
        JSONObject result = new JSONObject();

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

}
