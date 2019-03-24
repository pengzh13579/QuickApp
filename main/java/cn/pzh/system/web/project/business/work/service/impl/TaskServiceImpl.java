package cn.pzh.system.web.project.business.work.service.impl;

import cn.pzh.system.web.project.dao.first.entity.work.WorkTaskEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.work.TaskMapper;
import cn.pzh.system.web.project.business.work.service.TaskService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    /***
     * 列表信息查询
     * @param workTaskEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<WorkTaskEntity> listTasks(WorkTaskEntity workTaskEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(workTaskEntity.getPageNumber(), workTaskEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(workTaskEntity.getSortName()) + " " + workTaskEntity.getSortOrder());

        return taskMapper.listTasks(workTaskEntity);
    }

    /***
     * 添加信息
     * @param task 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(WorkTaskEntity task) {
        CommonFieldUtils.setAdminCommon(task, true);
        return taskMapper.save(task);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public WorkTaskEntity get(Integer id) {
        return taskMapper.selectTaskById(id);
    }

    /***
     * 修改信息
     * @param task 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(WorkTaskEntity task) {
        CommonFieldUtils.setAdminCommon(task, false);
        return taskMapper.update(task);
    }

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int delete(Integer id) {

        // 根据ID获得信息
        WorkTaskEntity task = taskMapper.selectTaskById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(task);

        // 更新信息
        return taskMapper.update(task);
    }
}
