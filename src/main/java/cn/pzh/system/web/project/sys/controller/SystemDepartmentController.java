package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.model.PageInfo;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.sys.service.DepartmentService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
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
@RequestMapping("/systemDepartmentController")
public class SystemDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("departmentTree", departmentService.listDepartmentTree(null));
        return "/sys/department/department_list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "/sys/department/department_form";
    }

    @RequestMapping("/departmentList")
    @ResponseBody
    public String getList(HttpServletRequest request) {

        // JSONObject
        JSONObject result = new JSONObject();

        return result.toJSONString();
    }

    @RequestMapping("/getDepartmentInfo")
    @ResponseBody
    public SystemDepartmentEntity getDepartmentInfo(String code) {
        return departmentService.getDepartmentByCode(code);
    }

    @RequestMapping("/listDepartmentUsers/{code}")
    @ResponseBody
    public String listUsers(@PathVariable String code, HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        List<SystemDepartmentEntity> departments = departmentService.listDepartmentUsers(code);
        // 将users对象绑定到pageInfo
        PageInfo<SystemDepartmentEntity> pageUser = new PageInfo<SystemDepartmentEntity>(departments);
        // 获取总记录数
        long total = pageUser.getTotal();

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put("total", total);

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put("rows", departments);
        return result.toJSONString();
    }


    @RequestMapping(value = "/addDepartment")
    @ResponseBody
    public AjaxJson addEntity(SystemDepartmentEntity info,
                            HttpServletRequest request)
            throws IOException, NoSuchAlgorithmException {

        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        Boolean flag = departmentService.insert(info);
        if (flag) {
            j.setMsg("角色添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("角色添加失败，请联系管理员");
        return j;
    }

    @RequestMapping(value = "/editDepartment")
    @ResponseBody
    public AjaxJson editEntity(SystemDepartmentEntity info, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Boolean flag = departmentService.update(info);
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
        departmentService.delete(id);
        j.setMsg("删除成功!");
        j.setSuccess(true);
        return j;
    }

}