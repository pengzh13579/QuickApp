package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoQuestionEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoQuestionMapper;
import cn.pzh.system.web.project.business.info.service.InfoQuestionService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoQuestionServiceImpl implements InfoQuestionService {

    @Autowired
    private InfoQuestionMapper questionMapper;

    /***
     * 列表信息查询
     * @param infoQuestionEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoQuestionEntity> listQuestions(InfoQuestionEntity infoQuestionEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoQuestionEntity.getPageNumber(), infoQuestionEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoQuestionEntity.getSortName()) + " " + infoQuestionEntity.getSortOrder());

        return questionMapper.listQuestions(infoQuestionEntity);
    }

    /***
     * 添加信息
     * @param question 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoQuestionEntity question) {
        CommonFieldUtils.setAdminCommon(question, true);
        return questionMapper.save(question);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoQuestionEntity get(Integer id) {
        return questionMapper.selectQuestionById(id);
    }

    /***
     * 修改信息
     * @param question 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoQuestionEntity question) {
        CommonFieldUtils.setAdminCommon(question, false);
        return questionMapper.update(question);
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
        InfoQuestionEntity question = questionMapper.selectQuestionById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(question);

        // 更新信息
        return questionMapper.update(question);
    }

    @Override
    public InfoQuestionEntity getFullQuestion(InfoQuestionEntity infoQuestionEntity) {
        return questionMapper.getFullQuestion(infoQuestionEntity);
    }
}
