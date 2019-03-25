package cn.pzh.system.web.project.dao.first.mapper.work;

import cn.pzh.system.web.project.dao.first.entity.work.WorkTaskEntity;
import java.util.List;

public interface WorkTaskMapper {

    /***
     * 列表信息查询语句
     * @param workTaskEntity 查询实体类
     * @return 列表
     */
    List<WorkTaskEntity> listTasks(WorkTaskEntity workTaskEntity);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    WorkTaskEntity selectTaskById(Integer id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<WorkTaskEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(WorkTaskEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(WorkTaskEntity role);
}
