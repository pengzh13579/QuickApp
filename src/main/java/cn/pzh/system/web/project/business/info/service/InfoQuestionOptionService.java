package cn.pzh.system.web.project.business.info.service;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionOptionEntity;
import java.util.List;

public interface InfoQuestionOptionService {

    /***
     * 列表信息查询
     * @param infoQuestionOptionEntity 查询实体类
     * @return 列表信息
     */
    List<InfoQuestionOptionEntity> listQuestionOptions(InfoQuestionOptionEntity infoQuestionOptionEntity);

    /***
     * 添加信息
     * @param info 信息
     * @return 添加记录数
     */
    int insert(InfoQuestionOptionEntity info);

    /***
     * 根据ID获得信息
     * @param itemId ID
     * @param optionCd
     * @return 信息
     */
    InfoQuestionOptionEntity get(Integer itemId, Integer optionCd);

    /***
     * 修改信息
     * @param info 信息
     * @return 修改记录数
     */
    int update(InfoQuestionOptionEntity info);

    /***
     * 删除--将disFlag变为1
     * @param itemId ID
     * @return 删除记录数
     */
    int delete(Integer itemId, Integer optionCd);
}
