package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.constant.MessageConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemMenuEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.common.utils.Convert;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.sys.service.LoginLogService;
import cn.pzh.system.web.project.sys.service.MenuService;
import cn.pzh.system.web.project.sys.service.UserService;
import cn.pzh.system.web.project.sys.vo.ChangePasswordInfo;
import cn.pzh.system.web.project.sys.vo.MenuInfo;
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

    @RequestMapping ("/getMenus")
    @ResponseBody
    public String getMenus(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        List<SystemMenuEntity> menus = menuService.getMenus();
        // 将users对象绑定到pageInfo
        PageInfo<SystemMenuEntity> pageMenu = new PageInfo<SystemMenuEntity>(menus);
        // 获取总记录数

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put("total", pageMenu.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put("rows", menus);
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    @RequestMapping (value= "/addMenu")
    @ResponseBody
    public AjaxJson addMenu(MenuInfo menuInfo, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        String checkName = menuService.checkRepeatMenuCode(menuInfo.getCode());
        if(null != checkName){
            j.setMsg("菜单编号已被【"+checkName+"】菜单使用，请换一个！");
            return j;
        }
        Boolean flag = menuService.insertMenu(menuInfo);
        if(flag){
            j.setMsg("菜单添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("菜单添加失败，请联系管理员");
        return j;

    }
}
