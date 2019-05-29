package cn.pzh.system.web.project.dao.first.mapper.info;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionEntity;
import java.util.List;

public interface InfoQuestionMapper {

    /***
     * 列表信息查询语句
     * @param infoQuestionEntity 查询实体类
     * @return 列表
     */
    List<InfoQuestionEntity> listQuestions(InfoQuestionEntity infoQuestionEntity);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    InfoQuestionEntity selectQuestionById(Integer id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<InfoQuestionEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(InfoQuestionEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(InfoQuestionEntity role);

    InfoQuestionEntity getFullQuestion(InfoQuestionEntity infoQuestionEntity);
}
