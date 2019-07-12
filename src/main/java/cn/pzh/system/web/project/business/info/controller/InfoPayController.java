package cn.pzh.system.web.project.business.info.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.dao.first.entity.info.InfoPayEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.business.info.service.InfoPayService;
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
@RequestMapping("/infoPayController")
public class InfoPayController {

    @Autowired
    private InfoPayService payService;

    /***
     * 列表页面
     * @return 列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return "/info/pay/pay_list";
    }

    /***
     * 添加页面
     * @return 添加页面
     */
    @RequestMapping("/add")
    public String add() {
        return "/info/pay/pay_form";
    }

    /***
     * 修改页面
     * @param id ID
     * @param model 模型
     * @return 修改页面
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("info", payService.get(id));
        return "/info/pay/pay_form";
    }

    /***
     * 信息页面
     * @param id ID
     * @param model 模型
     * @return 信息页面
     */
    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {
        model.addAttribute("info", payService.get(id));
        return "/info/pay/pay_read_form";
    }

    /***
     * 列表信息查询
     * @param infoPayEntity 查询实体类
     * @return 列表信息
     */
    @RequestMapping("/payList")
    @ResponseBody
    public String listPays(InfoPayEntity infoPayEntity) {

        // 根据查询实体类得到列表
        List<InfoPayEntity> list = payService.listPays(infoPayEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<InfoPayEntity> page = new PageInfo<InfoPayEntity>(list);

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
    @RequestMapping(value = "/addPay")
    @ResponseBody
    public AjaxJson addEntity(InfoPayEntity info) {

        AjaxJson j = new AjaxJson();

        // 插入
        if (payService.insert(info) > 0) {
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
    @RequestMapping(value = "/editPay")
    @ResponseBody
    public AjaxJson editEntity(InfoPayEntity info) {

        AjaxJson j = new AjaxJson();

        // 更新
        if (payService.update(info) > 0) {
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
     * @param id ID
     * @return 删除结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(Integer id) {

        AjaxJson j = new AjaxJson();

        // 逻辑删除
        if (payService.delete(id) > 0) {
            j.setMsg("删除成功");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("删除失败，请联系管理员！");
        return j;
    }

}
