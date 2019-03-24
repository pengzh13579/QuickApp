package cn.pzh.system.web.project.business.wx.service;

import cn.pzh.system.web.project.dao.first.entity.sys.WxUserEntity;
import java.util.List;

public interface UserService {

    /***
     * 列表信息查询
     * @param wxUserEntity 查询实体类
     * @return 列表信息
     */
    List<WxUserEntity> listUsers(WxUserEntity wxUserEntity);

    /***
     * 添加信息
     * @param info 信息
     * @return 添加记录数
     */
    int insert(WxUserEntity info);

    /***
     * 根据ID获得信息
     * @param id ID
     * @return 信息
     */
    WxUserEntity get(Integer id);

    /***
     * 修改信息
     * @param info 信息
     * @return 修改记录数
     */
    int update(WxUserEntity info);

    /***
     * 删除--将disFlag变为1
     * @param id ID
     * @return 删除记录数
     */
    int delete(Integer id);
}
