package cn.pzh.system.web.project.business.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemScheduleEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.sys.ScheduleMapper;
import cn.pzh.system.web.project.business.sys.service.SystemScheduleService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class SystemScheduleServiceImpl implements SystemScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    /***
     * 自定义定时任务列表信息查询
     * @param systemScheduleEntity 查询实体类
     * @return 自定义定时任务列表信息
     */
    @Override
    public List<SystemScheduleEntity> listSchedules(SystemScheduleEntity systemScheduleEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(systemScheduleEntity.getPageNumber(), systemScheduleEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(systemScheduleEntity.getSortName()) + " " + systemScheduleEntity.getSortOrder());

        return scheduleMapper.listSchedules(systemScheduleEntity);
    }

    /***
     * 添加自定义定时任务信息
     * @param schedule 自定义定时任务信息
     * @return 添加自定义定时任务记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(SystemScheduleEntity schedule) {
        CommonFieldUtils.setAdminCommon(schedule, true);
        return scheduleMapper.save(schedule);
    }

    /***
     * 根据自定义定时任务ID获得自定义定时任务信息
     * @param id 自定义定时任务ID
     * @return 自定义定时任务信息
     */
    @Override
    public SystemScheduleEntity get(Integer id) {
        return scheduleMapper.selectScheduleById(id);
    }

    /***
     * 修改自定义定时任务信息
     * @param schedule 自定义定时任务信息
     * @return 修改自定义定时任务记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(SystemScheduleEntity schedule) {
        CommonFieldUtils.setAdminCommon(schedule, false);
        return scheduleMapper.update(schedule);
    }

    /***
     * 删除自定义定时任务--将disFlag变为1
     * @param id 自定义定时任务ID
     * @return 修改自定义定时任务记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int delete(Integer id) {

        // 根据自定义定时任务ID获得自定义定时任务信息
        SystemScheduleEntity schedule = scheduleMapper.selectScheduleById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(schedule);

        // 更新自定义定时任务信息
        return scheduleMapper.update(schedule);
    }
}
