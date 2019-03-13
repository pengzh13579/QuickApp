package cn.pzh.system.web.project.business.sys.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemMenuEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.ZTreeNode;
import cn.pzh.system.web.project.business.sys.service.MenuService;
import cn.pzh.system.web.project.business.sys.vo.MenuInfoVO;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping ("/systemMenuController")
public class SystemMenuController {

    @Autowired
    private MenuService menuService;

    /***
     * 获取菜单列表(选择父级菜单用)
     * @return 菜单树
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        return menuService.getMenuTreeList();
    }

    /***
     * 菜单列表页面
     * @return 菜单列表页面
     */
    @RequestMapping ("/list")
    public String menuList() {
        return ViewConstants.MENU_LIST;
    }

    /***
     * 菜单添加页面
     * @return 菜单添加页面
     */
    @RequestMapping ("/add")
    public String menuAdd() {
        return ViewConstants.MENU_FORM;
    }

    /***
     * 菜单修改页面
     * @param menuCode 菜单编码
     * @param model 模型
     * @return 菜单修改页面
     */
    @RequestMapping ("/edit/{menuCode}")
    public String menuEdit(@PathVariable String menuCode, Model model) {
        model.addAttribute("menu", menuService.getMenu(menuCode));
        return ViewConstants.MENU_FORM;
    }

    /***
     * 菜单信息页面
     * @param menuCode 菜单编码
     * @param model 模型
     * @return 菜单信息页面
     */
    @RequestMapping("/info/{menuCode}")
    public String info(@PathVariable String menuCode, Model model) {
        model.addAttribute("menu", menuService.getMenu(menuCode));
        return ViewConstants.ROLE_READ_FORM;
    }

    /***
     * 菜单列表信息查询
     * @param systemMenuEntity 查询实体类
     * @return 菜单列表信息
     */
    @RequestMapping ("/listMenus")
    @ResponseBody
    public String listMenus(SystemMenuEntity systemMenuEntity) {

        // 根据查询实体类得到列表
        List<SystemMenuEntity> menus = menuService.listMenus(systemMenuEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<SystemMenuEntity> pageMenu = new PageInfo<>(menus);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, pageMenu.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, menus);

        return result.toJSONString();
    }

    /***
     * 添加菜单信息
     * @param menuInfo 页面菜单信息
     * @return 添加结果
     */
    @RequestMapping (value= "/addMenu")
    @ResponseBody
    public AjaxJson addMenu(MenuInfoVO menuInfo) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);

        // 判断菜单的编码是否重复
        String checkName = menuService.checkRepeatMenuCode(menuInfo.getCode());
        if(null != checkName){
            j.setMsg("菜单编码已被【"+checkName+"】菜单使用，请换一个！");
            return j;
        }

        // 添加菜单信息
        Boolean flag = menuService.insertMenu(menuInfo);
        if(flag){
            j.setMsg("菜单添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("菜单添加失败，请联系管理员");
        return j;
    }

    /***
     * 修改菜单信息
     * @param menuInfo 页面菜单信息
     * @return 修改结果
     */
    @RequestMapping(value = "/editMenu")
    @ResponseBody
    public AjaxJson editMenu(MenuInfoVO menuInfo) {

        // 将页面信息复制到DB实体中
        SystemMenuEntity menuEntity = new SystemMenuEntity();
        BeanUtils.copyProperties(menuInfo, menuEntity);

        // 更新菜单信息
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

    /***
     * 删除菜单--将disFlag变为1
     * @param id 菜单ID
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public AjaxJson delete(Integer id) {

        // 根据菜单ID获得菜单信息
        SystemMenuEntity menuEntity =menuService.getMenu(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(menuEntity);
        AjaxJson j = new AjaxJson();

        // 更新菜单信息
        menuService.updateMenu(menuEntity);
        j.setMsg("菜单删除成功!");
        j.setSuccess(true);
        return j;
    }

    /***
     * 根据角色ID获取所关联的菜单
     * @param roleId 角色ID
     * @return 关联菜单信息
     */
    @RequestMapping(value = "/getMenuIdByRoleId")
    @ResponseBody
    public AjaxJson getMenuIdByRoleId(Integer roleId) {

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);

        // 根据角色ID获取所关联的菜单
        j.setObj(menuService.getMenuIdByRoleId(roleId));
        return j;
    }
}
