package cn.pzh.system.web.project.business.info.controller;

import cn.pzh.system.web.project.business.info.service.InfoQuestionService;
import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionEntity;
import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionItemEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.business.info.service.InfoQuestionItemService;
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
@RequestMapping("/infoQuestionItemController")
public class InfoQuestionItemController {

    @Autowired
    private InfoQuestionItemService questionItemService;

    @Autowired
    private InfoQuestionService questionService;

    /***
     * 列表页面
     * @return 列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return "/info/questionItem/questionItem_list";
    }

    /***
     * 添加页面
     * @return 添加页面
     */
    @RequestMapping("/add/{questionId}")
    public String add(@PathVariable Integer questionId, Model model) {
        model.addAttribute("questionId", questionId);
        InfoQuestionEntity question = questionService.get(questionId);
        model.addAttribute("questionName", question.getQuestionName());
        return "/info/questionItem/questionItem_form";
    }

    /***
     * 修改页面
     * @param id ID
     * @param model 模型
     * @return 修改页面
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("info", questionItemService.get(id));
        return "/info/questionItem/questionItem_form";
    }

    /***
     * 信息页面
     * @param id ID
     * @param model 模型
     * @return 信息页面
     */
    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {
        model.addAttribute("info", questionItemService.get(id));
        return "/info/questionItem/questionItem_read_form";
    }

    /***
     * 列表信息查询
     * @param infoQuestionItemEntity 查询实体类
     * @return 列表信息
     */
    @RequestMapping("/questionItemList")
    @ResponseBody
    public String listQuestionItems(InfoQuestionItemEntity infoQuestionItemEntity) {

        // 根据查询实体类得到列表
        List<InfoQuestionItemEntity> list = questionItemService.listQuestionItems(infoQuestionItemEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<InfoQuestionItemEntity> page = new PageInfo<InfoQuestionItemEntity>(list);

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
    @RequestMapping(value = "/addQuestionItem")
    @ResponseBody
    public AjaxJson addEntity(InfoQuestionItemEntity info) {

        AjaxJson j = new AjaxJson();

        // 插入
        if (questionItemService.insert(info) > 0) {
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
    @RequestMapping(value = "/editQuestionItem")
    @ResponseBody
    public AjaxJson editEntity(InfoQuestionItemEntity info) {

        AjaxJson j = new AjaxJson();

        // 更新
        if (questionItemService.update(info) > 0) {
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
        if (questionItemService.delete(id) > 0) {
            j.setMsg("删除成功");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("删除失败，请联系管理员！");
        return j;
    }

}
