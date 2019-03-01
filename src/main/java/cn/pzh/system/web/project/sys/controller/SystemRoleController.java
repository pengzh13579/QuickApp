package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemRoleEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.sys.service.RoleService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemRoleController")
public class SystemRoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list() {
        return ViewConstants.ROLE_LIST;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return ViewConstants.ROLE_FORM;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("info", roleService.get(id));
        return ViewConstants.ROLE_FORM;
    }

    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {
        model.addAttribute("info", roleService.get(id));
        return ViewConstants.ROLE_READ_FORM;
    }

    @RequestMapping("/listRoles")
    @ResponseBody
    public String listRoles(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        List<SystemRoleEntity> infos = roleService.listRoles();
        // 将users对象绑定到pageInfo
        PageInfo<SystemRoleEntity> page = new PageInfo<SystemRoleEntity>(infos);
        // 获取总记录数
        long total = page.getTotal();

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put("total", total);

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put("rows", infos);
        return result.toJSONString();
    }

    @RequestMapping(value = "/addRole")
    @ResponseBody
    public AjaxJson addRole(SystemRoleEntity info,
                            HttpServletRequest request)
            throws IOException, NoSuchAlgorithmException {

        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        Boolean flag = roleService.insert(info);
        if (flag) {
            j.setMsg("角色添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("角色添加失败，请联系管理员");
        return j;
    }

    @RequestMapping(value = "/editRole")
    @ResponseBody
    public AjaxJson editRole(SystemRoleEntity info, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
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

    @RequestMapping("/delete")
    public AjaxJson delete(Integer id) {
        AjaxJson j = new AjaxJson();
        roleService.delete(id);
        j.setMsg("角色删除成功!");
        j.setSuccess(true);
        return j;
    }

}
