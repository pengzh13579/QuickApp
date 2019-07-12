package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionOptionEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoQuestionOptionMapper;
import cn.pzh.system.web.project.business.info.service.InfoQuestionOptionService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoQuestionOptionServiceImpl implements InfoQuestionOptionService {

    @Autowired
    private InfoQuestionOptionMapper questionOptionMapper;

    /***
     * 列表信息查询
     * @param infoQuestionOptionEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoQuestionOptionEntity> listQuestionOptions(InfoQuestionOptionEntity infoQuestionOptionEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoQuestionOptionEntity.getPageNumber(), infoQuestionOptionEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoQuestionOptionEntity.getSortName()) + " " + infoQuestionOptionEntity.getSortOrder());

        return questionOptionMapper.listQuestionOptions(infoQuestionOptionEntity);
    }

    /***
     * 添加信息
     * @param questionOption 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoQuestionOptionEntity questionOption) {
        questionOption.setDisFlag(0);
        Integer maxCd = questionOptionMapper.selectMaxOptionCd(questionOption.getItemId());
        if (maxCd == null) {
            maxCd = 0;
        }
        questionOption.setOptionCd((maxCd + 1));
        return questionOptionMapper.save(questionOption);
    }

    /***
     * 根据ID获得信息
     * @param itemId ID
     * @param optionCd
     * @return 信息
     */
    @Override
    public InfoQuestionOptionEntity get(Integer itemId, Integer optionCd) {
        return questionOptionMapper.selectQuestionOptionById(itemId, optionCd);
    }

    /***
     * 修改信息
     * @param questionOption 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoQuestionOptionEntity questionOption) {
        return questionOptionMapper.update(questionOption);
    }

    /***
     * 删除--将disFlag变为1
     * @param itemId ID
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int delete(Integer itemId, Integer optionCd) {

        // 根据ID获得信息
        InfoQuestionOptionEntity questionOption = questionOptionMapper.selectQuestionOptionById(itemId, optionCd);

        // 将disFlag变为1
        questionOption.setDisFlag(1);

        // 更新信息
        return questionOptionMapper.update(questionOption);
    }
}
