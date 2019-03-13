package cn.pzh.system.web.project.business.fix.service.impl;

import cn.pzh.system.web.project.dao.first.entity.fix.FixedDictionaryEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.fix.DictionaryMapper;
import cn.pzh.system.web.project.business.fix.service.DictionaryService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    /***
     * 字典列表信息查询
     * @param fixedDictionaryEntity 查询实体类
     * @return 字典列表信息
     */
    @Override
    public List<FixedDictionaryEntity> listDictionarys(FixedDictionaryEntity fixedDictionaryEntity) {

        if (null == fixedDictionaryEntity.getSortName() ||
                "".equals(fixedDictionaryEntity.getSortName())) {
            fixedDictionaryEntity.setSortName("id");
        }
        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(fixedDictionaryEntity.getPageNumber(), fixedDictionaryEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(fixedDictionaryEntity.getSortName()) + " " + fixedDictionaryEntity.getSortOrder());

        return dictionaryMapper.listDictionarys(fixedDictionaryEntity);
    }

    /***
     * 添加字典信息
     * @param dictionary 字典信息
     * @return 添加字典记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(FixedDictionaryEntity dictionary) {
        CommonFieldUtils.setAdminCommon(dictionary, true);
        return dictionaryMapper.save(dictionary);
    }

    /***
     * 根据字典ID获得字典信息
     * @param id 字典ID
     * @return 字典信息
     */
    @Override
    public FixedDictionaryEntity get(Integer id) {
        return dictionaryMapper.selectDictionaryById(id);
    }

    /***
     * 根据字典编码获得字典信息
     * @param dictionaryCode 字典编码
     * @return 字典信息
     */
    @Override
    public FixedDictionaryEntity get(String dictionaryCode) {
        return dictionaryMapper.selectDictionaryByCode(dictionaryCode);
    }

    /***
     * 修改字典信息
     * @param dictionary 字典信息
     * @return 修改字典记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(FixedDictionaryEntity dictionary) {
        CommonFieldUtils.setAdminCommon(dictionary, false);
        return dictionaryMapper.update(dictionary);
    }

    /***
     * 删除字典--将disFlag变为1
     * @param id 字典ID
     * @return 修改字典记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int delete(Integer id) {

        // 根据字典ID获得字典信息
        FixedDictionaryEntity dictionary = dictionaryMapper.selectDictionaryById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(dictionary);

        // 更新字典信息
        return dictionaryMapper.update(dictionary);
    }


    /***
     * 根据父ID查询数据字典集合
     * @param pid 父ID
     * @return 数据字典集合
     */
    @Override
    public List<FixedDictionaryEntity> getDictionarys(Integer pid) {
        FixedDictionaryEntity fixedDictionary = new FixedDictionaryEntity();
        fixedDictionary.setPid(pid);

        // 根据字典编码查询数据字典集合
        return dictionaryMapper.listDictionarys(fixedDictionary);
    }
}
