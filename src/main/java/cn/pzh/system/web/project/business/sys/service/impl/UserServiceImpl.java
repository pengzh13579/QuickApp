package cn.pzh.system.web.project.business.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemContactEntity;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserEntity;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserNativePlaceEntity;
import cn.pzh.system.web.project.common.session.LoginUserInfoBean;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.common.utils.DateUtil;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.dao.first.mapper.sys.ContactMapper;
import cn.pzh.system.web.project.dao.first.mapper.sys.UserLoginNumberMapper;
import cn.pzh.system.web.project.dao.first.mapper.sys.UserMapper;
import cn.pzh.system.web.project.dao.first.mapper.sys.UserNativePlaceMapper;
import cn.pzh.system.web.project.business.sys.service.UserService;
import cn.pzh.system.web.project.business.sys.vo.UserInfoVO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private UserNativePlaceMapper userNativePlaceMapper;

    @Autowired
    private UserLoginNumberMapper userLoginNumberMapper;

    /***
     * 用户列表信息查询
     * @param systemUserEntity 查询实体类
     * @return 用户列表信息
     */
    @Override
    public List<SystemUserEntity> listUsers(SystemUserEntity systemUserEntity) {

        if (null == systemUserEntity.getSortName() ||
                "".equals(systemUserEntity.getSortName())) {
            systemUserEntity.setSortName("user_name");
        }
        // 默认从第pageNum开始，每页pageSize条
        PageHelper.startPage(systemUserEntity.getPageNumber(), systemUserEntity.getPageSize(),
                CommonFieldUtils.fieldNameToColumnName(systemUserEntity.getSortName()) + " " + systemUserEntity.getSortOrder());

        return userMapper.listUsers(systemUserEntity);

    }

    /***
     * 添加用户信息
     * @param userInfo 用户信息
     * @param avatarId 用户头像
     * @return 添加用户结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean registration(UserInfoVO userInfo, String avatarId) {

        SystemUserEntity userEntity = new SystemUserEntity();

        // 将用户头像所对应的ID存入
        userEntity.setAvatar(avatarId);
        BeanUtils.copyProperties(userInfo, userEntity);

        // 用户信息盐+密码
        userEntity.setSalt(ShiroKit.getRandomSalt(5));
        userEntity.setPassword(ShiroKit.md5("111111", userEntity.getSalt()));

        CommonFieldUtils.setAdminCommon(userEntity, true);

        // 保存用户信息
        userMapper.saveUser(userEntity);

        // 联系方式非空check
        if(null != userInfo.getContacts()){

            // 联系方式，注册只有邮箱
            List<SystemContactEntity> contacts = userInfo.getContacts().stream().filter(item -> item != null)
                    .collect(Collectors.toList());
            contacts.forEach(contact -> contact.setUserName(userEntity.getUserName()));

            // 保存联系方式
            contactMapper.saveContact(contacts);
        }

        // 籍贯地址等信息保存处理
        List<SystemUserNativePlaceEntity> userNativePlace = userInfo.getUserNativePlace();

        // 籍贯地址等信息非空check
        if(null != userNativePlace) {
            userNativePlace.forEach(nativePlace -> nativePlace.setUserName(userInfo.getUserName()));
            userNativePlaceMapper.saveNativePlace(userNativePlace);
        }
        return true;
    }

    /***
     * 用户登录
     * @param userName 用户名
     * @param password 密码
     * @param rememberFlag 记住我
     * @return 用户信息
     */
    @Override
    @Transactional (readOnly = false)
    public UserInfoVO userLogin(String userName, String password,
            Boolean rememberFlag) {

        // 获取当前登陆者
        Subject userSub = SecurityUtils.getSubject();

        // 创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        // 前台记住我checkbox是否打勾
        token.setRememberMe(rememberFlag);

        // 登录验证
        userSub.login(token);

        // 将用户加入到session中
        SystemUserEntity user = setSession(userName);

        // 返回用户信息
        UserInfoVO userInfo = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    /***
     * 更新在线状态
     * @param isOnline 在线值
     * @param UserName 用户名
     */
    @Override
    @Transactional (readOnly = false)
    public void updateOnlineStatus(Integer isOnline, String UserName) {
        updateOnlineStatusByUserName(isOnline, UserName);
    }

    /***
     * 根据用户名获得用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    @Override
    public SystemUserEntity getUserEntity(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    /***
     * 根据用户名获得用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    @Override
    public UserInfoVO getUser(String userName) {
        SystemUserEntity systemUserEntity = userMapper.getUserByUserName(userName);
        UserInfoVO userInfo = new UserInfoVO();
        BeanUtils.copyProperties(systemUserEntity, userInfo);
        userInfo.setBirthdayRtn(DateUtil.formatDate(userInfo.getBirthday(), "yyyy-MM-dd"));
        userInfo.setContacts(contactMapper.selectContactByUserName(userInfo.getUserName()));
        userInfo.setUserNativePlace(userNativePlaceMapper.selectNativePlaceByUserName(userInfo.getUserName()));
        return userInfo;
    }

    /***
     * 根据id获得用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfoVO getUser(Integer id) {
        SystemUserEntity systemUserEntity = userMapper.getUserById(id);
        UserInfoVO userInfo = new UserInfoVO();
        BeanUtils.copyProperties(systemUserEntity, userInfo);
        return userInfo;
    }

    /***
     * 修改密码
     * @param newPwd 新密码
     */
    @Override
    @Transactional (readOnly = false)
    public void changePassword(String newPwd) {

        SystemUserEntity systemUserEntity = new SystemUserEntity();

        // 当前登录用户名
        systemUserEntity.setUserName(ShiroKit.getUser().getUserName());

        // 盐
        systemUserEntity.setSalt(ShiroKit.getRandomSalt(5));

        // 密码
        systemUserEntity.setPassword(ShiroKit.md5(newPwd, systemUserEntity.getSalt()));

        // 共通字段设置
        CommonFieldUtils.setAdminCommon(systemUserEntity, true);
        userMapper.updateUser(systemUserEntity);
    }

    /***
     * 更新用户信息
     * @param userInfo 用户信息
     * @return 添加用户结果
     */
    @Override
    @Transactional (readOnly = false)
    public Boolean updateUserInfo(UserInfoVO userInfo, String avatarId) {

        SystemUserEntity userEntity = new SystemUserEntity();
        BeanUtils.copyProperties(userInfo, userEntity);
        userEntity.setAvatar(avatarId);

        // 根据用户名获得用户信息
        SystemUserEntity user = userMapper.getUserByUserName(userInfo.getUserName());

        // 用户非空check
        if (null == user) {
            return false;
        }

        // 共通字段设置
        CommonFieldUtils.setAdminCommon(userEntity, false);

        // 更新用户信息
        userMapper.updateUser(userEntity);

        // 通过用户名删除联系方式
        contactMapper.deleteContactByUserName(userInfo.getUserName());

        //联系方式，注册只有邮箱
        // 联系方式非空check
        if(null != userInfo.getContacts()) {

            // 联系方式保存处理，先删后加
            contactMapper.deleteContactByUserName(userInfo.getUserName());
            List<SystemContactEntity> contacts = userInfo.getContacts().stream().filter(item -> item != null).collect(Collectors.toList());
            contacts.forEach(contact -> contact.setUserName(userInfo.getUserName()));
            contactMapper.saveContact(contacts);
        }
        // 籍贯地址等信息非空check
        List<SystemUserNativePlaceEntity> userNativePlace = userInfo.getUserNativePlace();
        if(null != userNativePlace) {

            // 籍贯地址等信息保存处理，先删后加
            userNativePlaceMapper.deleteNativePlaceByUserName(userInfo.getUserName());
            userNativePlace.forEach(nativePlace -> nativePlace.setUserName(userInfo.getUserName()));
            userNativePlaceMapper.saveNativePlace(userNativePlace);
        }
        return true;
    }

    /***
     * 更新用户状态
     * @param userName 用户名
     * @param disFlag 状态值
     */
    @Override
    @Transactional (readOnly = false)
    public void changeUserStatus(String userName, Integer disFlag) {

        SystemUserEntity userEntity = new SystemUserEntity();
        userEntity.setUserName(userName);

        // 共通字段设置
        CommonFieldUtils.setAdminCommon(userEntity, false);
        userEntity.setDisFlag(disFlag);

        // 更新用户信息
        userMapper.updateUser(userEntity);
    }

    /***
     * 用户名重复性check
     * @param userName 用户名
     * @return false：不重复<br/>
     *          true：重复
     */
    @Override
    public Boolean checkRepeatUserName(String userName) {
        return null == userMapper.getUserByUserName(userName) ? false : true;
    }

    /***
     * 更新用户关联角色
     * @param roleIds 关联角色ID
     * @param userName 用户名
     */
    @Override
    @Transactional (readOnly = false)
    public void updateUserRoleId(String roleIds, String userName) {
        SystemUserEntity userEntity = new SystemUserEntity();
        userEntity.setRoleId(roleIds);
        userEntity.setUserName(userName);
        CommonFieldUtils.setAdminCommon(userEntity, false);
        userMapper.updateUser(userEntity);
    }

    /***
     * 部门下关联用户列表信息查询
     * @param code 页面选择的部门编码
     * @return 用户列表信息
     */
    @Override
    public List<SystemUserEntity> listDepartmentUsers(String code) {
        return userMapper.listDepartmentUsers(code);
    }

    /***
     * 修改用户表登录次数，主要判断是否超过3次
     * @param success 是否成功
     * @param userName 用户名
     * @return 登录次数
     */
    @Override
    @Transactional (readOnly = false)
    public int updateUserLoginNumber(boolean success, String userName) {

        // 获取登录次数
        Integer loginNumber = userLoginNumberMapper.selectUserLoginNumber(userName);

        // 判断登录次数是不是空，如果为空，则代表用户登录次数没有数据，初始化登录次数
        if (loginNumber == null){

            loginNumber = 0;
            userLoginNumberMapper.insertUserLoginNumber(userName, loginNumber);
        }

        // 判断用户登录是否成功
        if (success) {
            loginNumber = 0;
        } else {
            loginNumber += 1;
        }

        // 插入或更新登录次数
        userLoginNumberMapper.updateUserLoginNumber(userName, loginNumber);
        return loginNumber;
    }

    /***
     * 将用户信息设置到session中
     * @param userName 用户名
     * @return 用户信息
     */
    private SystemUserEntity setSession(String userName) {

        // 根据用户名获得用户信息
        SystemUserEntity user = userMapper.getUserByUserName(userName);

        // 获得session
        Session session = SecurityUtils.getSubject().getSession();

        // 登录用户信息
        LoginUserInfoBean loginUserInfoBean = new LoginUserInfoBean();
        loginUserInfoBean.setUserName(user.getUserName());
        loginUserInfoBean.setRealName(user.getRealName());
        loginUserInfoBean.setLoginTime(new Date());

        // 将登录用户信息加入session
        session.setAttribute("userInfo", loginUserInfoBean);
        return user;
    }

    /***
     * 根据用户名更新在线状态
     * @param isOnline 在线状态值
     * @param UserName 用户名
     */
    private void updateOnlineStatusByUserName(Integer isOnline, String UserName) {
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        systemUserEntity.setUserName(UserName);
        systemUserEntity.setIsOnline(isOnline);
        CommonFieldUtils.setAdminCommon(systemUserEntity, false);
        userMapper.updateUser(systemUserEntity);
    }
}
