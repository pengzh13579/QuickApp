package cn.pzh.system.web.project.dao.first.mapper.info;

import cn.pzh.system.web.project.dao.first.entity.info.InfoPayEntity;
import java.util.List;

public interface InfoPayMapper {

    /***
     * 列表信息查询语句
     * @param infoPayEntity 查询实体类
     * @return 列表
     */
    List<InfoPayEntity> listPays(InfoPayEntity infoPayEntity);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    InfoPayEntity selectPayById(Integer id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<InfoPayEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(InfoPayEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(InfoPayEntity role);
}
