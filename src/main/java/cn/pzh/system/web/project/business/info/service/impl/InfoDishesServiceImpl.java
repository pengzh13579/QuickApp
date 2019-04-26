package cn.pzh.system.web.project.business.info.service.impl;

import cn.pzh.system.web.project.dao.first.entity.info.InfoDishesEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.info.DishesMapper;
import cn.pzh.system.web.project.business.info.service.InfoDishesService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class InfoDishesServiceImpl implements InfoDishesService {

    @Autowired
    private DishesMapper dishesMapper;

    /***
     * 列表信息查询
     * @param infoDishesEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<InfoDishesEntity> listDishess(InfoDishesEntity infoDishesEntity) {

        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(infoDishesEntity.getPageNumber(), infoDishesEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(infoDishesEntity.getSortName()) + " " + infoDishesEntity.getSortOrder());

        return dishesMapper.listDishess(infoDishesEntity);
    }

    /***
     * 添加信息
     * @param dishes 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(InfoDishesEntity dishes) {
        CommonFieldUtils.setAdminCommon(dishes, true);
        return dishesMapper.save(dishes);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public InfoDishesEntity get(Integer id) {
        return dishesMapper.selectDishesById(id);
    }

    /***
     * 修改信息
     * @param dishes 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(InfoDishesEntity dishes) {
        CommonFieldUtils.setAdminCommon(dishes, false);
        return dishesMapper.update(dishes);
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
        InfoDishesEntity dishes = dishesMapper.selectDishesById(id);

        // 将disFlag变为1
        CommonFieldUtils.setDeleteCommon(dishes);

        // 更新信息
        return dishesMapper.update(dishes);
    }
}
