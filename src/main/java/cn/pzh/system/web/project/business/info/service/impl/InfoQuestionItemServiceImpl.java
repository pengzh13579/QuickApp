package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionItemEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoQuestionItemMapper;
import cn.pzh.system.web.project.business.info.service.InfoQuestionItemService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoQuestionItemServiceImpl implements InfoQuestionItemService {

    @Autowired
    private InfoQuestionItemMapper questionItemMapper;

    /***
     * 列表信息查询
     * @param infoQuestionItemEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoQuestionItemEntity> listQuestionItems(InfoQuestionItemEntity infoQuestionItemEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoQuestionItemEntity.getPageNumber(), infoQuestionItemEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoQuestionItemEntity.getSortName()) + " " + infoQuestionItemEntity.getSortOrder());

        return questionItemMapper.listQuestionItems(infoQuestionItemEntity);
    }

    /***
     * 添加信息
     * @param questionItem 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoQuestionItemEntity questionItem) {
        CommonFieldUtils.setAdminCommon(questionItem, true);
        return questionItemMapper.save(questionItem);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoQuestionItemEntity get(Integer id) {
        return questionItemMapper.selectQuestionItemById(id);
    }

    /***
     * 修改信息
     * @param questionItem 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoQuestionItemEntity questionItem) {
        CommonFieldUtils.setAdminCommon(questionItem, false);
        return questionItemMapper.update(questionItem);
    }

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int delete(Integer id) {

        // 根据ID获得信息
        InfoQuestionItemEntity questionItem = questionItemMapper.selectQuestionItemById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(questionItem);

        // 更新信息
        return questionItemMapper.update(questionItem);
    }
}
