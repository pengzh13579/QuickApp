package cn.pzh.system.web.project.business.sys.controller;

import cn.pzh.system.web.project.common.conf.schedule.DynamicScheduleTask;
import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemScheduleEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.business.sys.service.SystemScheduleService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemScheduleController")
public class SystemScheduleController {

    @Autowired
    private SystemScheduleService scheduleService;

    /***
     * 自定义定时任务列表页面
     * @return 自定义定时任务列表页面
     */
    @RequestMapping("/list")
    public String list() {
        return "/sys/schedule/schedule_list";
    }

    /***
     * 自定义定时任务添加页面
     * @return 自定义定时任务添加页面
     */
    @RequestMapping("/add")
    public String add() {
        return "/sys/schedule/schedule_form";
    }

    /***
     * 自定义定时任务修改页面
     * @param id 自定义定时任务ID
     * @param model 模型
     * @return 自定义定时任务修改页面
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("info", scheduleService.get(id));
        return "/sys/schedule/schedule_form";
    }

    /***
     * 自定义定时任务信息页面
     * @param id 自定义定时任务ID
     * @param model 模型
     * @return 自定义定时任务信息页面
     */
    @RequestMapping("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {
        model.addAttribute("info", scheduleService.get(id));
        return "/sys/schedule/schedule_read_form";
    }

    /***
     * 自定义定时任务列表信息查询
     * @param systemScheduleEntity 查询实体类
     * @return 自定义定时任务列表信息
     */
    @RequestMapping("/scheduleList")
    @ResponseBody
    public String listSchedules(SystemScheduleEntity systemScheduleEntity) {

        // 根据查询实体类得到列表
        List<SystemScheduleEntity> list = scheduleService.listSchedules(systemScheduleEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<SystemScheduleEntity> page = new PageInfo<SystemScheduleEntity>(list);

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
     * 添加自定义定时任务信息
     * @param info 页面自定义定时任务信息
     * @return 自定义定时任务添加结果
     */
    @RequestMapping(value = "/addSchedule")
    @ResponseBody
    public AjaxJson addEntity(SystemScheduleEntity info) throws SchedulerException {

        AjaxJson j = new AjaxJson();

        // 插入自定义定时任务
        if (scheduleService.insert(info) > 0) {
            j.setMsg("自定义定时任务添加成功！");
            j.setSuccess(true);
            DynamicScheduleTask.schedulerAdd(info.getScheduleName(), info.getScheduleCron(), info.getScheduleParam());
            return j;
        }

        j.setSuccess(false);
        j.setMsg("自定义定时任务添加失败，请联系管理员！");
        return j;
    }

    /***
     * 修改自定义定时任务信息
     * @param info 页面自定义定时任务信息
     * @return 自定义定时任务修改结果
     */
    @RequestMapping(value = "/editSchedule")
    @ResponseBody
    public AjaxJson editEntity(SystemScheduleEntity info) {

        AjaxJson j = new AjaxJson();

        // 更新自定义定时任务
        if (scheduleService.update(info) > 0) {
            j.setMsg("自定义定时任务修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("自定义定时任务修改失败，请联系管理员！");
        j.setSuccess(false);
        return j;
    }

    /***
     * 逻辑删除自定义定时任务--将disFlag变为1
     * @param id 自定义定时任务ID
     * @return 自定义定时任务删除结果
     */
    @RequestMapping("/delete")
    public AjaxJson delete(Integer id) {

        AjaxJson j = new AjaxJson();

        // 逻辑删除自定义定时任务
        if (scheduleService.delete(id) > 0) {
            j.setMsg("自定义定时任务删除成功");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("自定义定时任务删除失败，请联系管理员！");
        return j;
    }

}
