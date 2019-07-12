package cn.pzh.system.web.project.business.info.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionOptionEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.business.info.service.InfoQuestionOptionService;
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
@RequestMapping("/infoQuestionOptionController")
public class InfoQuestionOptionController {

    @Autowired
    private InfoQuestionOptionService questionOptionService;

    /***
     * 列表页面
     * @return 列表页面
     */
    @RequestMapping("/list/{itemId}")
    public String list(@PathVariable Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "/info/questionOption/questionOption_list";
    }

    /***
     * 添加页面
     * @return 添加页面
     */
    @RequestMapping("/add/{itemId}")
    public String add(@PathVariable Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "/info/questionOption/questionOption_form";
    }

    /***
     * 修改页面
     * @param model 模型
     * @return 修改页面
     */
    @RequestMapping("/edit/{itemId}/{optionCd}")
    public String edit(@PathVariable Integer itemId, @PathVariable Integer optionCd, Model model) {
        model.addAttribute("info", questionOptionService.get(itemId, optionCd));
        return "/info/questionOption/questionOption_form";
    }

    /***
     * 信息页面
     * @param model 模型
     * @return 信息页面
     */
    @RequestMapping("/info/{itemId}/{optionCd}")
    public String info(@PathVariable Integer itemId, @PathVariable Integer optionCd, Model model) {
        model.addAttribute("info", questionOptionService.get(itemId, optionCd));
        return "/info/questionOption/questionOption_read_form";
    }

    /***
     * 列表信息查询
     * @param infoQuestionOptionEntity 查询实体类
     * @return 列表信息
     */
    @RequestMapping("/questionOptionList")
    @ResponseBody
    public String listQuestionOptions(InfoQuestionOptionEntity infoQuestionOptionEntity) {

        // 根据查询实体类得到列表
        List<InfoQuestionOptionEntity> list = questionOptionService.listQuestionOptions(infoQuestionOptionEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<InfoQuestionOptionEntity> page = new PageInfo<InfoQuestionOptionEntity>(list);

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
    @RequestMapping(value = "/addQuestionOption")
    @ResponseBody
    public AjaxJson addEntity(InfoQuestionOptionEntity info) {

        AjaxJson j = new AjaxJson();

        // 插入
        if (questionOptionService.insert(info) > 0) {
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
    @RequestMapping(value = "/editQuestionOption")
    @ResponseBody
    public AjaxJson editEntity(InfoQuestionOptionEntity info) {

        AjaxJson j = new AjaxJson();

        // 更新
        if (questionOptionService.update(info) > 0) {
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
     * @return 删除结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxJson delete(Integer itemId, Integer optionCd) {

        AjaxJson j = new AjaxJson();

        // 逻辑删除
        if (questionOptionService.delete(itemId, optionCd) > 0) {
            j.setMsg("删除成功");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("删除失败，请联系管理员！");
        return j;
    }

}
