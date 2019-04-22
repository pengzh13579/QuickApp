package cn.pzh.system.web.project.dao.first.mapper.fix;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedDistrictEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DistrictMapper {

    /***
     * 列表信息查询语句
     * @param id 父ID
     * @return 列表
     */
    List<FixedDistrictEntity> listDistricts(@Param("id")String id);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    FixedDistrictEntity selectDistrictById(@Param("id")String id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<FixedDistrictEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(FixedDistrictEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(FixedDistrictEntity role);
}
