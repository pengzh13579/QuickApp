package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import cn.pzh.system.web.project.sys.service.MenuService;
import cn.pzh.system.web.project.sys.vo.MenuInfo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping ("/list")
    public String menuList() {
        return ViewConstants.MENU_LIST;
    }

    @RequestMapping ("/add")
    public String menuAdd() {
        return ViewConstants.MENU_FORM;
    }

    @RequestMapping ("/edit/{menuCode}")
    public String menuEdit(@PathVariable String menuCode, Model model) {
        model.addAttribute("menu", menuService.getMenu(menuCode));
        return ViewConstants.MENU_FORM;
    }

    @RequestMapping ("/listMenus")
    @ResponseBody
    public String listMenus(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        List<SystemMenuEntity> menus = menuService.listMenus();
        // 将users对象绑定到pageInfo
        PageInfo<SystemMenuEntity> pageMenu = new PageInfo<SystemMenuEntity>(menus);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put("total", pageMenu.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put("rows", menus);
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

    @RequestMapping(value = "/editMenu")
    @ResponseBody
    public AjaxJson editMenu(MenuInfo menuInfo, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SystemMenuEntity menuEntity = new SystemMenuEntity();
        BeanUtils.copyProperties(menuInfo, menuEntity);
        Boolean flag = menuService.updateMenu(menuEntity);
        AjaxJson j = new AjaxJson();
        if (flag) {
            j.setMsg("菜单修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("菜单修改失败，请联系管理员");
        j.setSuccess(false);
        return j;
    }

    @RequestMapping("/delete")
    public AjaxJson delete(Integer id) {
        SystemMenuEntity menuEntity =menuService.getMenu(id);
        menuEntity.setDisFlag(1);
        CommonFieldUtils.setDeleteCommon(menuEntity);
        AjaxJson j = new AjaxJson();
        menuService.updateMenu(menuEntity);
        j.setMsg("菜单删除成功!");
        j.setSuccess(true);
        return j;
    }
}
