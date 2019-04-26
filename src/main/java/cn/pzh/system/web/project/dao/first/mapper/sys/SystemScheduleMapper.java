package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemScheduleEntity;
import java.util.List;

public interface SystemScheduleMapper {

    /***
     * 自定义定时任务列表信息查询语句
     * @param systemScheduleEntity 查询实体类
     * @return 自定义定时任务列表
     */
    List<SystemScheduleEntity> listSchedules(SystemScheduleEntity systemScheduleEntity);

    /***
     * 根据自定义定时任务ID查询自定义定时任务信息
     * @param id 自定义定时任务ID
     * @return 自定义定时任务信息
     */
    SystemScheduleEntity selectScheduleById(Integer id);

    /***
     * 批量保存自定义定时任务信息
     * @param list 自定义定时任务信息
     * @return 保存记录数
     */
    int saveList(List<SystemScheduleEntity> list);

    /***
     * 保存自定义定时任务信息
     * @param role 自定义定时任务信息
     * @return 保存记录数
     */
    int save(SystemScheduleEntity role);

    /***
     * 更新自定义定时任务信息
     * @param role 自定义定时任务信息
     * @return 更新记录数
     */
    int update(SystemScheduleEntity role);
}
