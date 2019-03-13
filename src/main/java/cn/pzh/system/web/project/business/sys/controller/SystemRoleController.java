package cn.pzh.system.web.project.business.sys.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.business.sys.service.RoleService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemRoleController")
public class SystemRoleController {

    @Autowired
    private RoleService roleService;

    /***
     * 角色列表页面
     * @return 角色列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return ViewConstants.ROLE_LIST;
    }

    /***
     * 角色添加页面
     * @return 角色添加页面
     */
    @RequestMapping("/add")
    public String add() {
        return ViewConstants.ROLE_FORM;
    }

    /***
     * 角色修改页面
     * @param id 角色ID
     * @param model 模型
     * @return 角色修改页面
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("info", roleService.get(id));
        return ViewConstants.ROLE_FORM;
    }

    /***
     * 角色信息页面
     * @param id 角色ID
     * @param model 模型
     * @return 角色信息页面
     */
    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {
        model.addAttribute("info", roleService.get(id));
        return ViewConstants.ROLE_READ_FORM;
    }

    /***
     * 角色列表信息查询
     * @param systemRoleEntity 查询实体类
     * @return 角色列表信息
     */
    @RequestMapping("/listRoles")
    @ResponseBody
    public String listRoles(SystemRoleEntity systemRoleEntity) {

        // 根据查询实体类得到列表
        List<SystemRoleEntity> list = roleService.listRoles(systemRoleEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<SystemRoleEntity> page = new PageInfo<>(list);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, page.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        return result.toJSONString();
    }

    /***
     * 添加角色信息
     * @param info 页面角色信息
     * @return 角色添加结果
     */
    @RequestMapping(value = "/addRole")
    @ResponseBody
    public AjaxJson addRole(SystemRoleEntity info) {

        AjaxJson j = new AjaxJson();
        j.setSuccess(false);

        // 插入角色
        Boolean flag = roleService.insert(info);
        if (flag) {
            j.setMsg("角色添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("角色添加失败，请联系管理员");
        return j;
    }

    /***
     * 修改角色信息
     * @param info 页面角色信息
     * @return 角色修改结果
     */
    @RequestMapping(value = "/editRole")
    @ResponseBody
    public AjaxJson editRole(SystemRoleEntity info) {

        // 更新角色
        Boolean flag = roleService.update(info);
        AjaxJson j = new AjaxJson();
        if (flag) {
            j.setMsg("角色修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("角色修改失败，请联系管理员");
        j.setSuccess(false);
        return j;
    }

    /***
     * 删除角色--将disFlag变为1
     * @param id 角色ID
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public AjaxJson delete(Integer id) {

        AjaxJson j = new AjaxJson();

        // 删除角色
        roleService.delete(id);
        j.setMsg("角色删除成功!");
        j.setSuccess(true);
        return j;
    }

    /***
     * 插入或更新角色关联菜单信息
     * @param roleId 角色ID
     * @param menuIds 菜单ID
     * @return 更新结果
     */
    @RequestMapping("/insertRoleRelateMenu")
    @ResponseBody
    public AjaxJson insertRoleRelateMenu(@RequestParam("roleId") Integer roleId,
                                 @RequestParam("menuIds[]") List<Integer> menuIds) {

        AjaxJson j = new AjaxJson();

        // 关联菜单
        roleService.insertRoleRelateMenu(roleId, menuIds);
        j.setMsg("关联菜单成功！");
        j.setSuccess(true);
        return j;

    }
}
