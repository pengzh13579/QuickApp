package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoPayEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.InfoPayMapper;
import cn.pzh.system.web.project.business.info.service.InfoPayService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoPayServiceImpl implements InfoPayService {

    @Autowired
    private InfoPayMapper payMapper;

    /***
     * 列表信息查询
     * @param infoPayEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoPayEntity> listPays(InfoPayEntity infoPayEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoPayEntity.getPageNumber(), infoPayEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoPayEntity.getSortName()) + " " + infoPayEntity.getSortOrder());

        return payMapper.listPays(infoPayEntity);
    }

    /***
     * 添加信息
     * @param pay 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoPayEntity pay) {
        CommonFieldUtils.setAdminCommon(pay, true);
        return payMapper.save(pay);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoPayEntity get(Integer id) {
        return payMapper.selectPayById(id);
    }

    /***
     * 修改信息
     * @param pay 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoPayEntity pay) {
        CommonFieldUtils.setAdminCommon(pay, false);
        return payMapper.update(pay);
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
        InfoPayEntity pay = payMapper.selectPayById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(pay);

        // 更新信息
        return payMapper.update(pay);
    }
}
