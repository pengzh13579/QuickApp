package cn.pzh.system.web.project.business.wx.service.impl;

import cn.pzh.system.web.project.dao.first.entity.wx.WxUserEntity;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.dao.first.mapper.wx.WxUserMapper;
import cn.pzh.system.web.project.business.wx.service.WxUserService;
import java.util.List;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    /***
     * 列表信息查询
     * @param wxUserEntity 查询实体类
     * @return 列表信息
     */
    @Override
    public List<WxUserEntity> listUsers(WxUserEntity wxUserEntity) {

        // 默认从第pageNum开始，每页pageSize条
//        PageHelper.startPage(wxUserEntity.getPageNumber(), wxUserEntity.getPageSize(),
//                CommonFieldUtils.fieldNameToColumnName(wxUserEntity.getSortName()) + " " + wxUserEntity.getSortOrder());
//
//        return wxUserMapper.listUsers(wxUserEntity);
        return null;
    }

    /***
     * 添加信息
     * @param user 信息
     * @return 添加记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int insert(WxUserEntity user) {
        return wxUserMapper.save(user);
    }

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    @Override
    public WxUserEntity get(Integer id) {
        return wxUserMapper.selectUserById(id);
    }

    /***
     * 修改信息
     * @param user 信息
     * @return 修改记录数
     */
    @Override
    @Transactional (readOnly = false)
    public int update(WxUserEntity user) {
        return wxUserMapper.update(user);
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
        WxUserEntity user = wxUserMapper.selectUserById(id);

        // 更新信息
        return wxUserMapper.update(user);
    }

    /***
     * 根据openid获得微信信息
     * @param openid openid
     * @return 信息
     */
    @Override
    public WxUserEntity getWxUserByOpenId(String openid) {
        return wxUserMapper.getWxUserByOpenId(openid);
    }
}
