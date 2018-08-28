package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.constant.MessageConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.common.utils.Convert;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.sys.service.LoginLogService;
import cn.pzh.system.web.project.sys.service.MenuService;
import cn.pzh.system.web.project.sys.service.UserService;
import cn.pzh.system.web.project.sys.vo.ChangePasswordInfo;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping ("/systemMenuController")
public class SystemMenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        return menuService.getMenuTreeList();
    }

    @RequestMapping ("/add")
    public String menuAdd() {
        return ViewConstants.MENU_FORM;
    }

    @RequestMapping ("/menuList")
    public String menuList() {
        return ViewConstants.MENU_LIST;
    }
}
