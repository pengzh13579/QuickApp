package cn.pzh.system.web.project.common.conf.wx;

import cn.pzh.system.web.project.base.cache.OpenIDCache;
import cn.pzh.system.web.project.common.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

public class WxRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private WxConfig wxConfig;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception {
        //
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView e) throws Exception {
        //
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

        HttpSession session = request.getSession();
        String userAgent = request.getHeader("User-Agent");
        //判断是否是微信浏览器
        if (userAgent != null && !userAgent.toLowerCase().contains("micromessenger")) {
            return true;
        }
        JSONObject wxUserInfoJson = (JSONObject) session.getAttribute("sessionWxUserInfoJson");
        if (wxUserInfoJson != null) {
            return true;
        }
        String token = request.getParameter("token");
        if (token != null) {
            String sessionKey = OpenIDCache.getCacheMap().get(token).split("#")[1];
        }
        return true;
    }

    private JSONObject getOpenId(String appId, String appSecret, String code) throws Exception {
        String resData = HttpUtil.requestCommonByHttpGet("https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code", null, true);
        JSONObject accessObj = JSONObject.parseObject(resData);
        return accessObj;
    }
//
//    private JSONObject getWxUserInfo(String accessToken, String openId) throws Exception {
//        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
//                + accessToken
//                + "&openid="
//                + openId
//                + "&lang=zh_CN";
//        String res = HttpUtil.getSSL(url, null, 0, 0);
//        return JSONObject.parseObject(res);
//    }
}