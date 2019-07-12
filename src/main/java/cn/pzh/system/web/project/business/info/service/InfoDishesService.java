package cn.pzh.system.web.project.business.info.service;

import cn.pzh.system.web.project.dao.first.entity.info.InfoDishesEntity;
import java.util.List;

public interface InfoDishesService {

    /***
     * 列表信息查询
     * @param infoDishesEntity 查询实体类
     * @return 列表信息
     */
    List<InfoDishesEntity> listDishes(InfoDishesEntity infoDishesEntity);

    /***
     * 添加信息
     * @param info 信息
     * @return 添加记录数
     */
    int insert(InfoDishesEntity info);

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    InfoDishesEntity get(Integer id);

    /***
     * 修改信息
     * @param info 信息
     * @return 修改记录数
     */
    int update(InfoDishesEntity info);

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 删除记录数
     */
    int delete(Integer id);
}
