package cn.pzh.system.web.project.business.wxclient.controller;

import cn.pzh.system.web.project.base.cache.OpenIDCache;
import cn.pzh.system.web.project.business.fix.service.ProvinceAreaService;
import cn.pzh.system.web.project.business.wx.service.WxUserService;
import cn.pzh.system.web.project.common.conf.wx.WxConfig;
import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.model.PageInfo;
import cn.pzh.system.web.project.common.utils.HttpUtil;
import cn.pzh.system.web.project.common.utils.WeixinUtil;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedProvinceEntity;
import cn.pzh.system.web.project.dao.first.entity.wx.WxUserEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 获取用户基本信息的接口
     * @param request
     * @return
     */
    @RequestMapping(value="login",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> doWebChat2(HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        String mycode = request.getParameter("code");
        String session_key = "";
        try {
            String resData = HttpUtil.requestCommonByHttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+wxConfig.getAppid()+"&secret="+wxConfig.getSecret()+"&js_code="+mycode+"&grant_type=authorization_code", null, true);
            JSONObject accessObj = JSONObject.parseObject(resData);
            // openid 用户唯一标识
            // session_key 会话密钥
            // expires_in 会话有效期, 以秒为单位, 例如2592000代表会话有效期为30天
            session_key = accessObj.getString("session_key");
            String openid = accessObj.getString("openid");
            //String openid = "oswcO0eJrrPRkZHjLEfypgJiL24o";
            //session_key = "abc";
            String thirdSession = getRandomStr(); //第三方session
            //CacheUtil.getCacheMap().put(thirdSession, openid + "#" + session_key);
            OpenIDCache.getCacheMap().put(thirdSession, openid + "#" + session_key);

            ret.put("code", thirdSession);
            ret.put("statusCode", "200");
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
            String sessionKey = OpenIDCache.getCacheMap().get(dianToken).split("#")[1];
            JSONObject obj = WeixinUtil.getUserInfo(encryptedData, sessionKey, iv);
            String openId = obj.getString("openId");

            WxUserEntity wxUserEntity = wxUserService.getWxUserByOpenId(openId);
            if(wxUserEntity == null) {

                //保存微信用户信息
                JSONObject watermarkObj = obj.getJSONObject("watermark");
                String appid = watermarkObj.getString("appid");

                wxUserEntity = new WxUserEntity();
                wxUserEntity.setAccesstoken(sessionKey);
                wxUserEntity.setOpenid(openId);
                wxUserEntity.setNickname(obj.getString("nickName"));
                wxUserEntity.setHeadimg(obj.getString("avatarUrl"));
                wxUserEntity.setCreateTime(new Date());
                wxUserEntity.setProvice(obj.getString("province"));
                wxUserEntity.setCity(obj.getString("city"));
                wxUserEntity.setAppid(obj.getString("appid"));
                wxUserEntity.setGender(obj.getString("gender"));

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
        String pageSize = json.get("pageSize");
        String pageIndex = json.get("pageIndex");
        String keyword = json.get("keyword");
        String dianToken = json.get("dian_token");
        String openid = OpenIDCache.getCacheMap().get(dianToken).split("#")[0];
        try {

        } catch(Exception e) {
        }
        return ret;
    }

    //获取随机的16位字符串
    public static String getRandomStr()
    {
        String originStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 16; i++)
        {
            int randomNumber = new Random().nextInt(46);
            sb.append(originStr.charAt(randomNumber));
        }
        return sb.toString();
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
