package cn.pzh.system.web.project.business.wxclient.controller;

import cn.pzh.system.web.project.base.cache.OpenIDCache;
import cn.pzh.system.web.project.business.wx.service.WxUserService;
import cn.pzh.system.web.project.common.conf.wx.WxConfig;
import cn.pzh.system.web.project.common.utils.HttpUtil;
import cn.pzh.system.web.project.common.utils.WeixinUtil;
import cn.pzh.system.web.project.dao.first.entity.wx.WxUserEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/wxController")
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxUserService wxUserService;

    /**
     *
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
        try
        {
            String resData = HttpUtil.requestCommonByHttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+wxConfig.getAppid()+"&secret="+wxConfig.getSecret()+"&js_code="+mycode+"&grant_type=authorization_code", null, true);
            JSONObject accessObj = JSONObject.parseObject(resData);
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
    public Map<String, String> decodeWxInfo(HttpServletRequest request)
    {
        Map<String, String> ret = new HashMap<String, String>();
        try
        {
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

    @RequestMapping(value = "/getTaskList", method = RequestMethod.POST, consumes="application/json")
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
//            int start = ( new Integer(pageIndex) - 1) * new Integer(pageSize);
//            String sql = "SELECT * FROM market_task  order by createtime desc limit "+start+","+pageSize+"";
//            if(keyword != null && keyword.length() > 0) {
//                sql = "SELECT * FROM market_task where title like '%"+keyword+"%' or des like '%"+keyword+"%' order by createtime desc limit "+start+","+pageSize+"";
//            }
//            List<Object[]> list = marketTaskService.doExecuteSql(sql);
//
//            JSONArray jsonArr = new JSONArray();
//            for(Object[] oneObj : list)
//            {
//                JSONObject detail = new JSONObject();
//                detail.put("id", oneObj[0]);
//                detail.put("title", oneObj[1]);
//                detail.put("des", oneObj[2]);
//                if(oneObj[3] != null && oneObj[3].toString().length() > 0)
//                {
//                    if(oneObj[3].toString().equals("0"))
//                    {
//                        detail.put("type", "项目外包");
//                    } else
//                    {
//                        detail.put("type", "功能外包");
//                    }
//                }
//                detail.put("money", oneObj[4]);
//                detail.put("workday", oneObj[5]);
//                if(oneObj[6] != null && oneObj[6].toString().length() > 0)
//                {
//                    String writeTime = DateHandle.format(oneObj[6].toString());
//                    detail.put("writetime", writeTime);
//                }
//
//                if(oneObj[7] != null && oneObj[7].toString().length() > 0)
//                {
//                    if(oneObj[7].toString().equals("1"))
//                    {
//                        detail.put("status", "竞标中");
//                    } else if(oneObj[7].toString().equals("2"))
//                    {
//                        detail.put("status", "开发中");
//                    } else if(oneObj[7].toString().equals("3"))
//                    {
//                        detail.put("status", "已完成");
//                    }
//                }
//
//                if(oneObj[8] != null && oneObj[8].toString().length() > 0)
//                {
//                    detail.put("onlyID", oneObj[8].toString());
//                }
//
//                //查询该记录下的图片
//                String imgSql = "SELECT * FROM market_taskimg where type= 1 and mainid=" + oneObj[0];
//                List<Object[]> imgList = taskImgService.doExecuteSql(imgSql);
//                JSONArray imgArr = new JSONArray();
//                int imgIndex = 0;
//                if(imgList != null && imgList.size() > 0)
//                {
//                    for(Object[] oneImg : imgList)
//                    {
//                        JSONObject imgDetail = new JSONObject();
//                        imgDetail.put("img", Constant.HOST + oneImg[5]);
//                        imgDetail.put("imgID", oneObj[0].toString() + imgIndex);
//                        imgArr.add(imgDetail);
//                        imgIndex++;
//                    }
//                }
//                detail.put("imglist", imgArr);
//
//                //查询该记录下的竞标者
//                String bidderSql = "SELECT b.headimg FROM market_bidder a, wxuser b  where a.mainid=" + oneObj[0] + " and a.applyid=b.openid";
//                List<Object[]> bidderList = taskImgService.doExecuteSql(bidderSql);
//                JSONArray  bidderArr = new JSONArray();
//                int headimgIndex = 0;
//                if(bidderList != null && bidderList.size() > 0)
//                {
//                    for(int i = 0; i < bidderList.size(); i++)
//                    {
//                        JSONObject oneDetail = new JSONObject();
//                        oneDetail.put("headimg", bidderList.get(i));
//                        oneDetail.put("headimgID", "headimg" + oneObj[0].toString() + imgIndex);
//                        bidderArr.add(oneDetail);
//                    }
//                }
//                detail.put("bidderlist", bidderArr);
//
//                String checkSql = "select id from market_collect where collectid='"+openid+"' and mainid=" + oneObj[0];
//                List<Object[]> checkList = marketTaskService.doExecuteSql(checkSql);
//                if(checkList != null && checkList.size() > 0)
//                {
//                    detail.put("isCollect", "1");
//                } else
//                {
//                    detail.put("isCollect", "0");
//                }
//
//                String wxUserSql = "select nickname, headimg from wxuser where openid='"+oneObj[8].toString()+"'";
//                List<Object[]> wxUserList = commonService.doExecuteSql(wxUserSql);
//                String nickName = "";
//                String headimg = "";
//                if(wxUserList != null && wxUserList.size() > 0)
//                {
//                    nickName = wxUserList.get(0)[0].toString();
//                    headimg = wxUserList.get(0)[1].toString();
//                }
//
//                detail.put("nickName", nickName);
//                detail.put("headimg", headimg);
//
//                jsonArr.add(detail);
//
//            }
//
//            ret.put("data", jsonArr);
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
}
