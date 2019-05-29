package cn.pzh.system.web.project.dao.first.mapper.info;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionItemEntity;
import java.util.List;

public interface InfoQuestionItemMapper {

    /***
     * 列表信息查询语句
     * @param infoQuestionItemEntity 查询实体类
     * @return 列表
     */
    List<InfoQuestionItemEntity> listQuestionItems(InfoQuestionItemEntity infoQuestionItemEntity);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    InfoQuestionItemEntity selectQuestionItemById(Integer id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<InfoQuestionItemEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(InfoQuestionItemEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(InfoQuestionItemEntity role);
}
