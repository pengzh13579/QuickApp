package cn.pzh.system.web.project.business.sys.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.utils.CreateValidateCodeUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/commonController")
public class CommonController {

    @Resource
    private DefaultKaptcha captchaProducer;

    /**
     * 登录验证码图片
     */
    @RequestMapping ("/loginValidateCode")
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CreateValidateCodeUtils.validateCode(request,response,
                captchaProducer, KeyConstants.LOGIN_VALIDATE_CODE);
    }
}
