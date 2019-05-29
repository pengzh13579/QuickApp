package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoGovEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoGovMapper;
import cn.pzh.system.web.project.business.info.service.InfoGovService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoGovServiceImpl implements InfoGovService {

    @Autowired
    private InfoGovMapper govMapper;

    /***
     * 列表信息查询
     * @param infoGovEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoGovEntity> listGovs(InfoGovEntity infoGovEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoGovEntity.getPageNumber(), infoGovEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoGovEntity.getSortName()) + " " + infoGovEntity.getSortOrder());

        return govMapper.listGovs(infoGovEntity);
    }

    /***
     * 添加信息
     * @param gov 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoGovEntity gov) {
        CommonFieldUtils.setAdminCommon(gov, true);
        return govMapper.save(gov);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoGovEntity get(Integer id) {
        return govMapper.selectGovById(id);
    }

    /***
     * 修改信息
     * @param gov 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoGovEntity gov) {
        CommonFieldUtils.setAdminCommon(gov, false);
        return govMapper.update(gov);
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
        InfoGovEntity gov = govMapper.selectGovById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(gov);

        // 更新信息
        return govMapper.update(gov);
    }
}
