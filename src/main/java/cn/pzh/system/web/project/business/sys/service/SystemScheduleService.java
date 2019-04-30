package cn.pzh.system.web.project.business.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemScheduleEntity;
import org.quartz.SchedulerException;

import java.util.List;

public interface SystemScheduleService {

    /***
     * 自定义定时任务列表信息查询
     * @param systemScheduleEntity 查询实体类
     * @return 自定义定时任务列表信息
     */
    List<SystemScheduleEntity> listSchedules(SystemScheduleEntity systemScheduleEntity);

    /***
     * 添加自定义定时任务信息
     * @param info 自定义定时任务信息
     * @return 添加自定义定时任务记录数
     */
    int insert(SystemScheduleEntity info) throws SchedulerException;

    /***
     * 根据自定义定时任务ID获得自定义定时任务信息
     * @param id 自定义定时任务ID
     * @return 自定义定时任务信息
     */
    SystemScheduleEntity get(Integer id);

    /***
     * 修改自定义定时任务信息
     * @param info 自定义定时任务信息
     * @return 修改自定义定时任务记录数
     */
    int update(SystemScheduleEntity info) throws SchedulerException;

    /***
     * 删除自定义定时任务--将disFlag变为1
     * @param id 自定义定时任务ID
     * @param disFlag 是否可用
     * @return 记录数
     */
    int updateDisFlag(Integer id, Integer disFlag) throws SchedulerException;
}
