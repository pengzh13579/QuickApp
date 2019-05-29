package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionAnswerEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoQuestionAnswerMapper;
import cn.pzh.system.web.project.business.info.service.InfoQuestionAnswerService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoQuestionAnswerServiceImpl implements InfoQuestionAnswerService {

    @Autowired
    private InfoQuestionAnswerMapper questionAnswerMapper;

    /***
     * 列表信息查询
     * @param infoQuestionAnswerEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoQuestionAnswerEntity> listQuestionAnswers(InfoQuestionAnswerEntity infoQuestionAnswerEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoQuestionAnswerEntity.getPageNumber(), infoQuestionAnswerEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoQuestionAnswerEntity.getSortName()) + " " + infoQuestionAnswerEntity.getSortOrder());

        return questionAnswerMapper.listQuestionAnswers(infoQuestionAnswerEntity);
    }

    /***
     * 添加信息
     * @param questionAnswer 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoQuestionAnswerEntity questionAnswer) {
        CommonFieldUtils.setAdminCommon(questionAnswer, true);
        return questionAnswerMapper.save(questionAnswer);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoQuestionAnswerEntity get(Integer id) {
        return questionAnswerMapper.selectQuestionAnswerById(id);
    }

    /***
     * 修改信息
     * @param questionAnswer 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoQuestionAnswerEntity questionAnswer) {
        CommonFieldUtils.setAdminCommon(questionAnswer, false);
        return questionAnswerMapper.update(questionAnswer);
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
        InfoQuestionAnswerEntity questionAnswer = questionAnswerMapper.selectQuestionAnswerById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(questionAnswer);

        // 更新信息
        return questionAnswerMapper.update(questionAnswer);
    }
}
