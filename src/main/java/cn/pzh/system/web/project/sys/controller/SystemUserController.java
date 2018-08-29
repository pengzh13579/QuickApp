package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.constant.MessageConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.utils.Convert;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.sys.service.LoginLogService;
import cn.pzh.system.web.project.sys.service.MenuService;
import cn.pzh.system.web.project.sys.service.ProvinceAreaService;
import cn.pzh.system.web.project.sys.service.UserService;
import cn.pzh.system.web.project.sys.vo.ChangePasswordInfo;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping ("/systemUserController")
public class SystemUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private ProvinceAreaService provinceAreaService;

    @RequestMapping ("/getUsers")
    @ResponseBody
    public String getUsers(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        List<SystemUserEntity> users = userService.getUsers();
        // 将users对象绑定到pageInfo
        PageInfo<SystemUserEntity> pageUser = new PageInfo<SystemUserEntity>(users);
        // 获取总记录数
        long total = pageUser.getTotal();

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put("total", total);

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put("rows", users);
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    @RequestMapping(value = "/avatarUpload", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String avatarUpload(@RequestParam("avatar") MultipartFile file,
                                 HttpServletRequest request) throws IOException {
        // 1、解析文件数据，并存入车检数据库
        InputStream fileInput = fileInput = file.getInputStream() ;
        String name = file.getOriginalFilename();
        fileInput.close();
        return "上传成功："+name;
    }

    @RequestMapping (value= "/addUser")
    @ResponseBody
    public AjaxJson addUser(UserInfo userInfo, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        Boolean checkUserNameFlag = userService.checkRepeatUserName(userInfo.getUserName());
        if(checkUserNameFlag){
            j.setMsg("用户名已存在，请换一个！");
            return j;
        }
        Boolean flag = userService.registration(userInfo);
        if(flag){
            j.setMsg("用户添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("用户添加失败，请联系管理员");
        return j;
    }

    @RequestMapping (value= "/editUser")
    @ResponseBody
    public AjaxJson editUser(UserInfo userInfo, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Boolean flag = userService.updateUserInfo(userInfo);
        AjaxJson j = new AjaxJson();
        if(flag){
            j.setMsg("用户修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("用户修改失败，请联系管理员");
        j.setSuccess(false);
        return j;
    }

    @RequestMapping ("/userLogin")
    public String userLogin(String userName, String password, String rememberFlag, HttpServletRequest request, RedirectAttributes attr)
            throws Exception {
        AjaxJson j = new AjaxJson();
        String view = ViewConstants.LOGIN;
        j.setSuccess(false);
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            j.setMsg(MessageConstants.USER_INFO_EMPTY_MSG);
            return view;
        }
        UserInfo userInfo = null;
        try{
            userInfo = userService.userLogin(userName, password, "on".equals(rememberFlag) ? true : false);
        }catch(CredentialsException e){
            j.setMsg(MessageConstants.LOGIN_ERROR_MSG);
        }catch (LockedAccountException e){
            j.setMsg(MessageConstants.USER_LOCKED_MSG);
        }
        if (userInfo != null && userInfo .getIsOnline() == 0 ) {
            j.setMsg(MessageConstants.LOGIN_SUCCESS_MSG);
            j.setSuccess(true);
            view = ViewConstants.INDEX;
            // 修改用户表在线状态
            userService.updateOnlineStatus(1);
        }else{
            if (userInfo != null && userInfo .getIsOnline() == 1 ) {
                j.setMsg(MessageConstants.USER_IS_ONLINE_MSG);
                attr.addFlashAttribute("userName", userName);
            }
        }
        attr.addFlashAttribute("info", j);
        SystemLoginLogEntity loginLogEntity = new SystemLoginLogEntity();
        loginLogService.insertLoginLog(loginLogEntity, j, request, userName);
        return "redirect:/"+view;
    }

    /**
     * 退出
     *
     * @return 退出
     */
    @RequestMapping ("/logout")
    public String logout() {
        // 修改用户表在线状态
        userService.updateOnlineStatus( 0);
        SecurityUtils.getSubject().logout();
        return ViewConstants.LOGIN;
    }
    @RequestMapping ("/userOnlineOut")
    @ResponseBody
    public String userOnlineOut(String userName) {
        // 修改用户表在线状态
        userService.updateOnlineStatus( 0, userName);
        SecurityUtils.getSubject().logout();
        return ViewConstants.LOGIN;
    }

    @RequestMapping ("/userList")
    public String userList() {
        return ViewConstants.USER_LIST;
    }

    @RequestMapping ("/add")
    public String userAdd(Model model) {
        model.addAttribute("provinceList", provinceAreaService.getAllProvince());
        return ViewConstants.USER_FORM;
    }

    @RequestMapping ("/userInfo/{userName}")
    public String userInfo(@PathVariable String userName, Model model) {
        model.addAttribute("user", userService.getUser(userName));
        return ViewConstants.USER_READ_FORM;
    }

    @RequestMapping ("/edit/{userName}")
    public String userEdit(@PathVariable String userName, Model model) {
        model.addAttribute("user", userService.getUser(userName));
        model.addAttribute("provinceList", provinceAreaService.getAllProvince());
        return ViewConstants.USER_FORM;
    }

    @RequestMapping ("/personInfo")
    public String personInfo(Model model) {
        model.addAttribute("user", userService.getUser(ShiroKit.getUser().getUserName()));
        return ViewConstants.USER_READ_FORM;
    }

    @RequestMapping ("/changePasswordForm")
    public String changePassword() {
        return ViewConstants.CHANGE_PASSWORD;
    }

    @RequestMapping ("/changePassword")
    @ResponseBody
    public AjaxJson changePassword(ChangePasswordInfo passwordInfo) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        if(passwordInfo.getNewPwd()!=null && passwordInfo.getAgainPwd()!=null && passwordInfo.getNewPwd().equals(passwordInfo.getAgainPwd())){
            SystemUserEntity systemUserEntity = userService.getUserEntity(ShiroKit.getUser().getUserName());
            String oldPassword = ShiroKit.md5(passwordInfo.getOldPwd(), systemUserEntity.getSalt());
            if(!oldPassword.equals(systemUserEntity.getPassword())){
                j.setMsg("旧密码不正确!");
                return j;
            }
            if(!oldPassword.equals(systemUserEntity.getPassword())){
                j.setMsg("旧密码不正确!");
                return j;
            }
            userService.changePassword(passwordInfo.getNewPwd());
            userService.updateOnlineStatus( 0);
            SecurityUtils.getSubject().logout();
            j.setMsg("密码修改成功请重新登录!");
            j.setSuccess(true);

        }else{
            j.setMsg("两次新密码不一致!");
        }
        return j;
    }

    @RequestMapping ("/lockUser")
    public AjaxJson lockUser(String userName) {
        AjaxJson j = new AjaxJson();
        userService.changeUserStatus(userName, 2);
        SecurityUtils.getSubject().logout();
        j.setMsg("锁定成功!");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping ("/deleteUser")
    public AjaxJson deleteUser(String userName) {
        AjaxJson j = new AjaxJson();
        userService.changeUserStatus(userName, 1);
        SecurityUtils.getSubject().logout();
        j.setMsg("删除成功!");
        j.setSuccess(true);
        return j;
    }

}
