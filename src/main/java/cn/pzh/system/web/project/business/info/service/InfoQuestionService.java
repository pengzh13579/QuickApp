package cn.pzh.system.web.project.business.info.service;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionEntity;
import java.util.List;

public interface InfoQuestionService {

    /***
     * 列表信息查询
     * @param infoQuestionEntity 查询实体类
     * @return 列表信息
     */
    List<InfoQuestionEntity> listQuestions(InfoQuestionEntity infoQuestionEntity);

    /***
     * 添加信息
     * @param info 信息
     * @return 添加记录数
     */
    int insert(InfoQuestionEntity info);

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    InfoQuestionEntity get(Integer id);

    /***
     * 修改信息
     * @param info 信息
     * @return 修改记录数
     */
    int update(InfoQuestionEntity info);

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 删除记录数
     */
    int delete(Integer id);

    InfoQuestionEntity getFullQuestion(InfoQuestionEntity infoQuestionEntity);
}
