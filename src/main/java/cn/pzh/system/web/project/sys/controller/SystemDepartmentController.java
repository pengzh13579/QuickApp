package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.sys.service.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemDepartmentController")
public class SystemDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /***
     * 部门列表页面
     * @return 部门列表页面
     */
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("departmentTree", departmentService.listDepartmentTree(null));
        return "/sys/department/department_list";
    }

    /***
     * 根据部门编码获得部门信息
     * @param code 部门编码
     * @return 部门信息
     */
    @RequestMapping("/getDepartmentInfo")
    @ResponseBody
    public SystemDepartmentEntity getDepartmentInfo(String code) {
        return departmentService.getDepartmentByCode(code);
    }

    /***
     * 部门人员关联
     * @param id 部门编码
     * @param userIds 人员ID
     * @return 关联结果
     */
    @RequestMapping("/relatedUsers")
    @ResponseBody
    public AjaxJson relatedUsers(@RequestParam ("id") Integer id,
            @RequestParam("userIds[]") List<Integer> userIds) {

        AjaxJson j = new AjaxJson();
        j.setSuccess(false);

        Boolean flag = departmentService.relatedUsers(id, userIds);
        if (flag) {
            j.setMsg("关联人员成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("关联人员失败，请联系管理员");
        return j;

    }

    /***
     * 添加部门信息
     * @param info 页面部门信息
     * @return 部门添加结果
     */
    @RequestMapping(value = "/addDepartment")
    @ResponseBody
    public AjaxJson addEntity(SystemDepartmentEntity info) {

        AjaxJson j = new AjaxJson();
        j.setSuccess(false);

        // 添加部门信息
        Boolean flag = departmentService.insert(info);
        if (flag) {
            j.setMsg("部门添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("部门添加失败，请联系管理员");
        return j;
    }

    /***
     * 修改部门信息
     * @param info 页面部门信息
     * @return 部门修改结果
     */
    @RequestMapping(value = "/editDepartment")
    @ResponseBody
    public AjaxJson editEntity(SystemDepartmentEntity info) {

        // 修改部门信息
        Boolean flag = departmentService.update(info);
        AjaxJson j = new AjaxJson();
        if (flag) {
            j.setMsg("部门修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("部门修改失败，请联系管理员");
        j.setSuccess(false);
        return j;
    }

    /***
     * 删除部门--将disFlag变为1
     * @param code 部门编号
     * @return 删除结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(String code) {
        AjaxJson j = new AjaxJson();

        // 删除部门
        departmentService.delete(code);
        j.setMsg("删除成功!");
        j.setSuccess(true);
        return j;
    }

    /***
     * 删除部门关联用户
     * @param id 部门ID
     * @param userIds 用户ID
     * @return 删除结果
     */
    @RequestMapping("/deleteRelatedUsers")
    @ResponseBody
    public AjaxJson deleteRelatedUsers(@RequestParam("id") Integer id,
                                       @RequestParam("userIds[]") List<Integer> userIds) {
        AjaxJson j = new AjaxJson();

        // 删除部门关联用户
        departmentService.deleteRelatedUsers(id, userIds);
        j.setMsg("删除关联用户成功!");
        j.setSuccess(true);
        return j;
    }

}
