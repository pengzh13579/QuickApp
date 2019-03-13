package cn.pzh.system.web.project.business.fix.service;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedDictionaryEntity;
import java.util.List;

public interface DictionaryService {

    /***
     * 字典列表信息查询
     * @param fixedDictionaryEntity 查询实体类
     * @return 字典列表信息
     */
    List<FixedDictionaryEntity> listDictionarys(FixedDictionaryEntity fixedDictionaryEntity);

    /***
     * 添加字典信息
     * @param info 字典信息
     * @return 添加字典记录数
     */
    int insert(FixedDictionaryEntity info);

    /***
     * 根据字典ID获得字典信息
     * @param id 字典ID
     * @return 字典信息
     */
    FixedDictionaryEntity get(Integer id);

    /***
     * 根据字典编码获得字典信息
     * @param dictionaryCode 字典编码
     * @return 字典信息
     */
    FixedDictionaryEntity get(String dictionaryCode);

    /***
     * 修改字典信息
     * @param info 字典信息
     * @return 修改字典记录数
     */
    int update(FixedDictionaryEntity info);

    /***
     * 删除字典--将disFlag变为1
     * @param id 字典ID
     * @return 删除字典记录数
     */
    int delete(Integer id);

    /***
     * 根据父ID查询其下所有数据字典集合
     * @param pid 父ID
     * @return 数据字典集合
     */
    List<FixedDictionaryEntity> getDictionarys(Integer pid);
}
