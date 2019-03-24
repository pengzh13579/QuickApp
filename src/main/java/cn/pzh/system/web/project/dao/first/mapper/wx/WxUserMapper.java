package cn.pzh.system.web.project.dao.first.mapper.wx;

import cn.pzh.system.web.project.dao.first.entity.wx.WxUserEntity;
import java.util.List;

public interface WxUserMapper {

    /***
     * 列表信息查询语句
     * @param wxUserEntity 查询实体类
     * @return 列表
     */
    List<WxUserEntity> listUsers(WxUserEntity wxUserEntity);

    /***
     * 根据ID查询信息
     * @param id ID
     * @return 信息
     */
    WxUserEntity selectUserById(Integer id);

    /***
     * 批量保存信息
     * @param list 信息
     * @return 保存记录数
     */
    int saveList(List<WxUserEntity> list);

    /***
     * 保存信息
     * @param role 信息
     * @return 保存记录数
     */
    int save(WxUserEntity role);

    /***
     * 更新信息
     * @param role 信息
     * @return 更新记录数
     */
    int update(WxUserEntity role);

    /***
     * 根据openid获得微信信息
     * @param openid openid
     * @return 信息
     */
    WxUserEntity getWxUserByOpenId(String openid);
}
