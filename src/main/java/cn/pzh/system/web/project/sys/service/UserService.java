package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import cn.pzh.system.web.project.sys.vo.UserInfoVO;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    /***
     * 用户列表信息查询
     * @param systemUserEntity 查询实体类
     * @return 用户列表信息
     */
    List<SystemUserEntity> listUsers(SystemUserEntity systemUserEntity);

    /***
     * 添加用户信息
     * @param userInfo 用户信息
     * @param avatarId 用户头像
     * @return 添加用户结果
     */
    Boolean registration(UserInfoVO userInfo, Integer avatarId) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /***
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param rememberFlag 记住我
     * @return 用户信息
     */
    UserInfoVO userLogin(String userName, String password, Boolean rememberFlag)
            throws Exception;

    /***
     * 更新在线状态
     * @param isOnline 在线值
     * @param UserName 用户名
     */
    @Transactional (readOnly = false)
    void updateOnlineStatus(Integer isOnline, String UserName);

    /***
     * 根据用户名获得用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    SystemUserEntity getUserEntity(String userName);

    /***
     * 根据用户名获得用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    UserInfoVO getUser(String userName);

    /***
     * 根据id获得用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfoVO getUser(Integer id);

    /***
     * 修改密码
     * @param newPwd 新密码
     */
    @Transactional (readOnly = false)
    void changePassword(String newPwd);

    /***
     * 更新用户信息
     * @param userInfo 用户信息
     * @return 添加用户结果
     */
    @Transactional (readOnly = false)
    Boolean updateUserInfo(UserInfoVO userInfo)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /***
     * 更新用户状态
     * @param userName 用户名
     * @param disFlag 状态值
     */
    @Transactional (readOnly = false)
    void changeUserStatus(String userName, Integer disFlag);

    /***
     * 用户名重复性check
     * @param userName 用户名
     * @return false：不重复<br/>
     *          true：重复
     */
    Boolean checkRepeatUserName(String userName);

    /***
     * 更新用户关联角色
     * @param roleIds 关联角色ID
     * @param userName 用户名
     */
    void updateUserRoleId(String roleIds, String userName);

    /***
     * 部门下关联用户列表信息查询
     * @param code 页面选择的部门编码
     * @return 用户列表信息
     */
    List<SystemUserEntity> listDepartmentUsers(String code);
}
