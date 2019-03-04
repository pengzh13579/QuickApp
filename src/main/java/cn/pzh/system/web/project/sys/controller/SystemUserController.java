package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.conf.UploadFileConfig;
import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.constant.MessageConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.dao.first.entity.monitor.LoginLogEntity;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.sys.service.*;
import cn.pzh.system.web.project.sys.vo.ChangePasswordVO;
import cn.pzh.system.web.project.sys.vo.UserInfoVO;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/systemUserController")
public class SystemUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private ProvinceAreaService provinceAreaService;

    @Autowired
    private FileService fileService;

    /***
     * 用户列表页面
     * @return 用户列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return ViewConstants.USER_LIST;
    }

    /***
     * 用户添加页面
     * @param model 模型
     * @return 用户添加页面
     */
    @RequestMapping("/add")
    public String userAdd(Model model) {
//        model.addAttribute("provinceList", provinceAreaService.getAllProvince());
        return ViewConstants.USER_FORM;
    }

    /***
     * 用户修改页面
     * @param userName 用户名
     * @param model 模型
     * @return 用户修改页面
     */
    @RequestMapping("/edit/{userName}")
    public String userEdit(@PathVariable String userName, Model model) {
        model.addAttribute("user", userService.getUser(userName));
//        model.addAttribute("provinceList", provinceAreaService.getAllProvince());
        return ViewConstants.USER_FORM;
    }

    /***
     * 用户信息页面
     * @param userName 用户名
     * @param model 模型
     * @return 用户信息页面
     */
    @RequestMapping("/userInfo/{userName}")
    public String userInfo(@PathVariable String userName, Model model) {
        model.addAttribute("user", userService.getUser(userName));
        return ViewConstants.USER_READ_FORM;
    }

    /***
     * 修改密码界面
     * @return 修改密码界面
     */
    @RequestMapping("/changePasswordForm")
    public String changePassword() {
        return ViewConstants.CHANGE_PASSWORD;
    }

    /***
     * 根据已经登录的用户名显示个人资料/用户信息页面
     * @param model 模型
     * @return 用户信息页面
     */
    @RequestMapping("/personInfo")
    public String personInfo(Model model) {
        model.addAttribute("user", userService.getUser(ShiroKit.getUser().getUserName()));
        return ViewConstants.USER_READ_FORM;
    }

    /***
     * 用户关联角色页面
     * @param id 用户ID
     * @param model 模型
     * @return 用户关联角色页面
     */
    @RequestMapping("/relatedRole/{id}")
    public String relatedRole(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return ViewConstants.USER_RELATED_ROLE;
    }

    /***
     * 用户列表信息查询
     * @param systemUserEntity 查询实体类
     * @return 用户列表信息
     */
    @RequestMapping("/listUsers")
    @ResponseBody
    public String listUsers(SystemUserEntity systemUserEntity) {

        // 根据查询实体类得到列表
        List<SystemUserEntity> users = userService.listUsers(systemUserEntity);

        // 将users对象绑定到pageInfo
        PageInfo<SystemUserEntity> pageUser = new PageInfo<>(users);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, pageUser.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, users);
        return result.toJSONString();
    }

    /***
     * 添加用户信息
     * @param userInfo 页面用户信息
     * @param uploadFile 头像文件
     * @return 添加结果
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/addUser")
    @ResponseBody
    public AjaxJson addUser(UserInfoVO userInfo,
                            @RequestParam(value = "avatar") MultipartFile uploadFile)
            throws IOException, NoSuchAlgorithmException {

        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        // 判断用户名是否重复
        Boolean checkUserNameFlag = userService.checkRepeatUserName(userInfo.getUserName());
        if (checkUserNameFlag) {
            j.setMsg("用户名已存在，请换一个！");
            return j;
        }

        // 头像文件保存
        Integer avatarId = fileService.uploadFile(uploadFile, "avatar");

        // 添加用户
        Boolean flag = userService.registration(userInfo, avatarId);
        if (flag) {
            j.setMsg("用户添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("用户添加失败，请联系管理员");
        return j;
    }

    /***
     * 修改用户信息
     * @param userInfo 页面用户信息
     * @param request request
     * @return 修改结果
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value = "/editUser")
    @ResponseBody
    public AjaxJson editUser(UserInfoVO userInfo, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        // 更新用户信息
        Boolean flag = userService.updateUserInfo(userInfo);
        AjaxJson j = new AjaxJson();
        if (flag) {
            j.setMsg("用户修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("用户修改失败，请联系管理员");
        j.setSuccess(false);
        return j;
    }

    /***
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param rememberFlag 记住我
     * @param request 请求
     * @param attr 重定向参数
     * @return 登录结果
     * @throws Exception
     */
    @RequestMapping("/userLogin")
    public String userLogin(String userName, String password,
            String rememberFlag, HttpServletRequest request,
            RedirectAttributes attr)
            throws Exception {
        AjaxJson j = new AjaxJson();
        String view = ViewConstants.LOGIN;
        j.setSuccess(false);

        // 用户名和密码非空check
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            j.setMsg(MessageConstants.USER_INFO_EMPTY_MSG);
            return view;
        }
        UserInfoVO userInfo = null;
        try {

            // 用户名和密码登录check
            userInfo = userService.userLogin(userName, password, "on".equals(rememberFlag) ? true : false);
        } catch (CredentialsException e) {
            j.setMsg(MessageConstants.LOGIN_ERROR_MSG);
        } catch (LockedAccountException e) {
            j.setMsg(MessageConstants.USER_LOCKED_MSG);
        }

        // 判断用户是否不在线
        if (userInfo != null && userInfo.getIsOnline() == 0) {
            j.setMsg(MessageConstants.LOGIN_SUCCESS_MSG);
            j.setSuccess(true);
            view = ViewConstants.INDEX;
            // 修改用户表在线状态
            userService.updateOnlineStatus(1, ShiroKit.getUser().userName);
        } else {

            // 判断用户是否在线
            if (userInfo != null && userInfo.getIsOnline() == 1) {
                j.setMsg(MessageConstants.USER_IS_ONLINE_MSG);
                attr.addFlashAttribute("userName", userName);
            }
        }
        attr.addFlashAttribute("info", j);

        // 添加用户登录日志
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogService.insertLoginLog(loginLogEntity, j, request, userName);
        return "redirect:/" + view;
    }

    /***
     * 退出
     * @return 登录页面
     */
    @RequestMapping("/logout")
    public String logout() {

        // 修改用户表在线状态
        userService.updateOnlineStatus(0, ShiroKit.getUser().userName);

        // 用户退出
        SecurityUtils.getSubject().logout();
        return ViewConstants.LOGIN;
    }

    /***
     * 用户强制退出
     * @param userName 用户名
     * @return 登录页面
     */
    @RequestMapping("/userOnlineOut")
    @ResponseBody
    public String userOnlineOut(String userName) {

        // 修改用户表在线状态
        userService.updateOnlineStatus(0, userName);

        // 用户退出
        SecurityUtils.getSubject().logout();
        return ViewConstants.LOGIN;
    }

    /***
     * 修改密码
     * @param passwordInfo 密码信息
     * @return 修改密码结果
     */
    @RequestMapping("/changePassword")
    @ResponseBody
    public AjaxJson changePassword(ChangePasswordVO passwordInfo) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);

        // 密码非空check
        if (passwordInfo.getNewPwd() != null
                && passwordInfo.getAgainPwd() != null
                && passwordInfo.getNewPwd().equals(passwordInfo.getAgainPwd())) {

            // 根据当前登陆者登录用户信息
            SystemUserEntity systemUserEntity = userService.getUserEntity(ShiroKit.getUser().getUserName());

            // 页面输入的旧密码加密
            String oldPassword = ShiroKit.md5(passwordInfo.getOldPwd(), systemUserEntity.getSalt());

            // 加密后的旧密码与表中的密码比较
            if (!oldPassword.equals(systemUserEntity.getPassword())) {
                j.setMsg("旧密码不正确!");
                return j;
            }

            // 修改密码
            userService.changePassword(passwordInfo.getNewPwd());

            // 设置用户离线
            userService.updateOnlineStatus(0, ShiroKit.getUser().userName);

            // 用户退出
            SecurityUtils.getSubject().logout();
            j.setMsg("密码修改成功请重新登录!");
            j.setSuccess(true);

        } else {
            j.setMsg("两次新密码不一致!");
        }
        return j;
    }

    /***
     * 修改用户关联角色
     * @param roleIds 需要关联的角色
     * @param userName 用户名
     * @return
     */
    @RequestMapping("/relatedRoleInfo")
    @ResponseBody
    public AjaxJson relatedRoleInfo(String roleIds, String userName) {
        AjaxJson j = new AjaxJson();

        // 修改用户关联的角色
        userService.updateUserRoleId(roleIds, userName);
        j.setMsg("锁定成功!");
        j.setSuccess(true);
        return j;
    }

    /***
     * 锁定用户--将disFlag变为2
     * @param userName 用户名
     * @return
     */
    @RequestMapping("/lockUser/{userName}")
    @ResponseBody
    public AjaxJson lockUser(@PathVariable String userName) {
        AjaxJson j = new AjaxJson();

        // 修改用户状态为2
        userService.changeUserStatus(userName, 2);

        // 如果是已经登录的用户则需要退出
        if (ShiroKit.getUser().getUserName().equals(userName))
            SecurityUtils.getSubject().logout();
        j.setMsg("锁定成功!");
        j.setSuccess(true);
        return j;
    }

    /***
     * 删除用户--将disFlag变为1
     * @param userName 用户名
     * @return
     */
    @RequestMapping("/deleteUser/{userName}")
    @ResponseBody
    public AjaxJson deleteUser(@PathVariable String userName) {
        AjaxJson j = new AjaxJson();

        // 修改用户状态为1
        userService.changeUserStatus(userName, 1);

        // 如果是已经登录的用户则需要退出
        if (ShiroKit.getUser().getUserName().equals(userName))
            SecurityUtils.getSubject().logout();
        j.setMsg("删除成功!");
        j.setSuccess(true);
        return j;
    }

}
