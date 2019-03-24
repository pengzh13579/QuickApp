package cn.pzh.system.web.project.business.work.service;

import cn.pzh.system.web.project.dao.first.entity.sys.WorkTaskEntity;
import java.util.List;

public interface TaskService {

    /***
     * 列表信息查询
     * @param workTaskEntity 查询实体类
     * @return 列表信息
     */
    List<WorkTaskEntity> listTasks(WorkTaskEntity workTaskEntity);

    /***
     * 添加信息
     * @param info 信息
     * @return 添加记录数
     */
    int insert(WorkTaskEntity info);

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    WorkTaskEntity get(Integer id);

    /***
     * 修改信息
     * @param info 信息
     * @return 修改记录数
     */
    int update(WorkTaskEntity info);

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 删除记录数
     */
    int delete(Integer id);
}
