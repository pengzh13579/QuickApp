package cn.pzh.system.web.project.dao.first.mapper.fix;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedProvinceEntity;
import java.util.List;

public interface ProvinceMapper {

    /***
     * 列表信息查询语句
     * @return 列表
     */
    List<FixedProvinceEntity> listProvinces();

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    FixedProvinceEntity selectProvinceById(String id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<FixedProvinceEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(FixedProvinceEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(FixedProvinceEntity role);
}
