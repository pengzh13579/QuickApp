package cn.pzh.system.web.project.common.constant;

public class WxProgramConstants {

    public final static String GET_PAGE_SIZE = "pageSize";
    public final static String GET_PAGE_INDEX = "pageIndex";
    public final static String GET_PROVINCE_FLAG = "provinceFlag";
    public final static String GET_CITY_FLAG = "cityFlag";
    public final static String GET_INDUSTRY_CODES = "industryCodes";
    public final static String GET_PROVINCE_ID = "provinceId";
    public final static String GET_CITY_ID = "cityId";
    public final static String GET_AREA_ID = "areaId";

    public final static String GET_SESSION_KEY = "session_key";
    public final static String GET_OPEN_ID = "openid";
    public final static String GET_CODE = "code";

    // 解密后的openId
    public final static String GET_OPEN_ID_UPPER = "openId";

    public final static String GET_WATER_MARK = "watermark";
    public final static String GET_NICK_NAME = "nickName";
    public final static String GET_AVATAR_URL = "avatarUrl";
    public final static String GET_PROVINCE = "province";
    public final static String GET_CITY = "city";
    public final static String GET_APP_ID = "appid";
    public final static String GET_GENDER = "gender";

    public final static String SET_CODE = "code";
    public final static String SET_STATUS_CODE = "statusCode";

    public final static String STATUS_SUCCESS_CODE = "200";

    public final static String SLIPT_CACHE_CODE = "#";

    public final static String HTTP_OAUTH2_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
    public final static String HTTP_SNS_JSCODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

}
