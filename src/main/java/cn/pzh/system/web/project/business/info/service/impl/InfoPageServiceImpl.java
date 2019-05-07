package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoPageEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoPageMapper;
import cn.pzh.system.web.project.business.info.service.InfoPageService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoPageServiceImpl implements InfoPageService {

    @Autowired
    private InfoPageMapper pageMapper;

    /***
     * 列表信息查询
     * @param infoPageEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoPageEntity> listPages(InfoPageEntity infoPageEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoPageEntity.getPageNumber(), infoPageEntity.getPageSize(),
                        CommonFieldUtils.fieldNameToColumnName(infoPageEntity.getSortName()) + " " + infoPageEntity.getSortOrder());

        return pageMapper.listPages(infoPageEntity);
    }

    /***
     * 添加信息
     * @param page 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoPageEntity page) {
        CommonFieldUtils.setAdminCommon(page, true);
        return pageMapper.save(page);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoPageEntity get(String id) {
        return pageMapper.selectPageById(id);
    }

    /***
     * 修改信息
     * @param page 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoPageEntity page) {
        CommonFieldUtils.setAdminCommon(page, false);
        return pageMapper.update(page);
    }

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int delete(String id) {

        // 根据ID获得信息
        InfoPageEntity page = pageMapper.selectPageById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(page);

        // 更新信息
        return pageMapper.update(page);
    }
}
