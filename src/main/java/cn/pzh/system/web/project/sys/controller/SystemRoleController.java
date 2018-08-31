package cn.pzh.system.web.project.sys.controller;

import cn.pzh.system.web.project.common.conf.UploadFileConfig;
import cn.pzh.system.web.project.common.constant.MessageConstants;
import cn.pzh.system.web.project.common.constant.ViewConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemRoleEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.sys.service.FileService;
import cn.pzh.system.web.project.sys.service.LoginLogService;
import cn.pzh.system.web.project.sys.service.ProvinceAreaService;
import cn.pzh.system.web.project.sys.service.RoleService;
import cn.pzh.system.web.project.sys.service.UserService;
import cn.pzh.system.web.project.sys.vo.ChangePasswordInfo;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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

@Controller
@RequestMapping("/systemRoleController")
public class SystemRoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getList")
    @ResponseBody
    public String getList(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
        Integer pageNo = Integer.valueOf(request.getParameter("pageNumber"));

        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        // 默认从第一页开始，每页五条
        PageHelper.startPage(pageNo, pageSize);
        List<SystemRoleEntity> infos = roleService.getList();
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
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    @RequestMapping(value = "/addEntity")
    @ResponseBody
    public AjaxJson addEntity(SystemRoleEntity info,
                            @RequestParam(value = "avatar") MultipartFile uploadFile,
                            HttpServletRequest request)
            throws IOException, NoSuchAlgorithmException {
        ;
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        Boolean flag = roleService.insert(info);
        if (flag) {
            j.setMsg("用户添加成功！");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("用户添加失败，请联系管理员");
        return j;
    }

    @RequestMapping(value = "/editEntity")
    @ResponseBody
    public AjaxJson editEntity(SystemRoleEntity info, HttpServletRequest request)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Boolean flag = roleService.update(info);
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

    @RequestMapping("/list")
    public String list() {
        return ViewConstants.USER_LIST;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return ViewConstants.USER_FORM;
    }

    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {
        model.addAttribute("info", roleService.get(id));
        return ViewConstants.USER_READ_FORM;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("info", roleService.get(id));
        return ViewConstants.USER_FORM;
    }

    @RequestMapping("/delete")
    public AjaxJson delete(Integer id) {
        AjaxJson j = new AjaxJson();
        roleService.delete(id);
        j.setMsg("删除成功!");
        j.setSuccess(true);
        return j;
    }

}
