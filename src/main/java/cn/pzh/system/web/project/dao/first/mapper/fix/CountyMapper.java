package cn.pzh.system.web.project.dao.first.mapper.fix;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedCountyEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CountyMapper {

    /***
     * 省级固定存储列表信息查询语句
     * @param id 父ID
     * @return 省级固定存储列表
     */
    List<FixedCountyEntity> listCountys(@Param("id") String id);

    /***
     * 根据省级固定存储ID查询省级固定存储信息
     * @param id 省级固定存储ID
     * @return 省级固定存储信息
     */
    FixedCountyEntity selectCountyById(@Param("id")String id);

    /***
     * 批量保存省级固定存储信息
     * @param list 省级固定存储信息
     * @return 保存记录数
     */
    int saveList(List<FixedCountyEntity> list);

    /***
     * 保存省级固定存储信息
     * @param role 省级固定存储信息
     * @return 保存记录数
     */
    int save(FixedCountyEntity role);

    /***
     * 更新省级固定存储信息
     * @param role 省级固定存储信息
     * @return 更新记录数
     */
    int update(FixedCountyEntity role);
}
