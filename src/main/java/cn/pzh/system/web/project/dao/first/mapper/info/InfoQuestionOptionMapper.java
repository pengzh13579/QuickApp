package cn.pzh.system.web.project.dao.first.mapper.info;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionOptionEntity;
import java.util.List;

public interface InfoQuestionOptionMapper {

    /***
     * 列表信息查询语句
     * @param infoQuestionOptionEntity 查询实体类
     * @return 列表
     */
    List<InfoQuestionOptionEntity> listQuestionOptions(InfoQuestionOptionEntity infoQuestionOptionEntity);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    InfoQuestionOptionEntity selectQuestionOptionById(Integer id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<InfoQuestionOptionEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(InfoQuestionOptionEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(InfoQuestionOptionEntity role);
}
