package cn.pzh.system.web.project.business.wx.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.dao.first.entity.wx.WxUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.business.wx.service.WxUserService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wxUserController")
public class WxUserController {

    @Autowired
    private WxUserService userService;

    /***
     * 列表页面
     * @return 列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return "/wx/user/user_list";
    }

    /***
     * 添加页面
     * @return 添加页面
     */
    @RequestMapping("/add")
    public String add() {
        return "/wx/user/user_form";
    }

    /***
     * 修改页面
     * @param openid openid
     * @param model 模型
     * @return 修改页面
     */
    @RequestMapping("/edit/{openid}")
    public String edit(@PathVariable String openid, Model model) {
        model.addAttribute("info", userService.getWxUserByOpenId(openid));
        return "/wx/user/user_form";
    }

    /***
     * 信息页面
     * @param openid ID
     * @param model 模型
     * @return 信息页面
     */
    @RequestMapping("/info/{openid}")
    public String info(@PathVariable String openid, Model model) {
        model.addAttribute("info", userService.getWxUserByOpenId(openid));
        return "/wx/user/user_read_form";
    }

    /***
     * 列表信息查询
     * @param wxUserEntity 查询实体类
     * @return 列表信息
     */
    @RequestMapping("/userList")
    @ResponseBody
    public String listUsers(WxUserEntity wxUserEntity) {

        // 根据查询实体类得到列表
        List<WxUserEntity> list = userService.listUsers(wxUserEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<WxUserEntity> page = new PageInfo<WxUserEntity>(list);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, page.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    /***
     * 添加信息
     * @param info 页面信息
     * @return 添加结果
     */
    @RequestMapping(value = "/addUser")
    @ResponseBody
    public AjaxJson addEntity(WxUserEntity info) {

        AjaxJson j = new AjaxJson();

        // 插入
        if (userService.insert(info) > 0) {
            j.setMsg("添加成功！");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("添加失败，请联系管理员！");
        return j;
    }

    /***
     * 修改信息
     * @param info 页面信息
     * @return 修改结果
     */
    @RequestMapping(value = "/editUser")
    @ResponseBody
    public AjaxJson editEntity(WxUserEntity info) {

        AjaxJson j = new AjaxJson();

        // 更新
        if (userService.update(info) > 0) {
            j.setMsg("修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("修改失败，请联系管理员！");
        j.setSuccess(false);
        return j;
    }

    /***
     * 逻辑删除--将disFlag变为1
     * @param openid ID
     * @return 删除结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(String openid) {

        AjaxJson j = new AjaxJson();

        // 逻辑删除
        if (userService.delete(openid) > 0) {
            j.setMsg("删除成功");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("删除失败，请联系管理员！");
        return j;
    }

}
