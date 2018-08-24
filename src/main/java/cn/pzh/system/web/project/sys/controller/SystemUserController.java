package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.constant.MessageConstants;
import cn.pzh.system.web.project.common.constant.WebConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.PageInfo;
import cn.pzh.system.web.project.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping ("/systemUserController")
public class SystemUserController {

    @Autowired
    private UserService userService;

    @RequestMapping ("/getUsers")
    public List<SystemUserEntity> getUsers() {
        // 默认从第一页开始，每页五条
        PageHelper.startPage(1, 5);
        List<SystemUserEntity> users = userService.getAll();
        // 将users对象绑定到pageInfo
        PageInfo<SystemUserEntity> pageUser = new PageInfo<SystemUserEntity>(users);

        return users;
    }

    @RequestMapping ("/addUser")
    public List<SystemUserEntity> addUser(SystemUserEntity userEntity, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Boolean flag = userService.registration(userEntity);
        return null;
    }

    @RequestMapping ("/userLogin")
    public ModelAndView checkUser(String userName, String password, String rememberFlag, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ModelAndView mav = new ModelAndView();
        AjaxJson j = new AjaxJson();
        String flag = userService.loginCheck(userName, password, "on".equals(rememberFlag) ? true : false);
        if (flag.equals(WebConstants.LOGIN_SUCCESS)) {
            j.setMsg(MessageConstants.LOGIN_SUCCESS_MSG);
            j.setSuccess(true);
            mav.setViewName("index");
        } else {
            j.setMsg(flag.equals(WebConstants.IS_ONLINE)
                    ? MessageConstants.USER_IS_ONLINE_MSG
                    : MessageConstants.LOGIN_ERROR_MSG);
            j.setSuccess(false);
            mav.setViewName("login");
        }
        mav.addObject("info", j);
        return mav;
    }

    /**
     * 退出
     *
     * @return 退出
     */
    @RequestMapping ("/logout")
    public String logout() {
        String userName = SecurityUtils.getSubject().getPrincipal().toString();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        // 修改用户表在线状态
        userService.updateOnlineStatus(userName);
        return "login";
    }
}
