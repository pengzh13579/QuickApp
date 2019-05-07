package cn.pzh.system.web.project.dao.first.mapper.fix;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedDictionaryEntity;
import java.util.List;

public interface DictionaryMapper {

    /***
     * 字典列表信息查询语句
     * @param systemDictionaryEntity 查询实体类
     * @return 字典列表
     */
    List<FixedDictionaryEntity> listDictionarys(FixedDictionaryEntity systemDictionaryEntity);

    /***
     * 字典列表信息查询语句,列表排序
     * @param systemDictionaryEntity 查询实体类
     * @return 字典列表
     */
    List<FixedDictionaryEntity> listDictionarysOrder(FixedDictionaryEntity systemDictionaryEntity);

    /***
     * 根据字典ID查询字典信息
     * @param id 字典ID
     * @return 字典信息
     */
    FixedDictionaryEntity selectDictionaryById(Integer id);

    /***
     * 批量保存字典信息
     * @param list 字典信息
     * @return 保存记录数
     */
    int saveList(List<FixedDictionaryEntity> list);

    /***
     * 保存字典信息
     * @param role 字典信息
     * @return 保存记录数
     */
    int save(FixedDictionaryEntity role);

    /***
     * 更新字典信息
     * @param role 字典信息
     * @return 更新记录数
     */
    int update(FixedDictionaryEntity role);

    /***
     * 根据字典CODE查询父字典信息
     * @param dictionaryCode 字典CODE
     * @return 字典信息
     */
    FixedDictionaryEntity selectParentDictionaryByCode(String dictionaryCode);
}
