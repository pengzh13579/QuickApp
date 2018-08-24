package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.constant.WebConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.session.LoginUserInfoBean;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.common.utils.IdUtils;
import cn.pzh.system.web.project.common.utils.MD5Util;
import cn.pzh.system.web.project.sys.dao.mapper.ContactMapper;
import cn.pzh.system.web.project.sys.dao.mapper.UserMapper;
import cn.pzh.system.web.project.sys.service.UserService;
import cn.pzh.system.web.project.sys.vo.UserInfo;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
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
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public List<SystemUserEntity> getAll() {
        return userMapper.getAll();
    }

    @Override
    @Transactional (readOnly = false)
    public Boolean registration(SystemUserEntity userEntity)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        //用户信息
        userEntity.setSalt(IdUtils.salt(WebConstants.SALT_SIZE));
        userEntity.setPassword(MD5Util.EncoderStringByMd5(userEntity.getPassword() + userEntity.getSalt()));
        CommonFieldUtils.setAdminCommon(userEntity, "registration");
        userMapper.saveUser(userEntity);

        //联系方式，注册只有邮箱
        SystemContactEntity systemContactEntity = new SystemContactEntity();
        //TODO
//        systemContactEntity.setContact(userEntity.getEmail());
//        systemContactEntity.setUserId(userEntity.getId());
//        systemContactEntity.setTypeDetailId(1);

        contactMapper.saveContact(systemContactEntity);

        return true;
    }

    @Override
    public UserInfo userLogin(String userName, String password, Boolean rememberFlag)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        //if (1 != user.getIsOnline()) {
        //获取当前登陆者
        Subject userSub = SecurityUtils.getSubject();

        //创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        //前台记住我checkbox是否打勾
        token.setRememberMe(rememberFlag);
        try {
            //登录验证
            userSub.login(token);
        }catch(Exception e){
            return null;
        }
        SystemUserEntity user = setSession(userName);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
        //}
        //return WebConstants.IS_ONLINE;
    }

    @Override
    public void updateOnlineStatus(String userName, Integer isOnline) {

    }

    /**
     * 将Cookie添加进Session
     */
    private SystemUserEntity setSession(String userName) {
        SystemUserEntity user = userMapper.getUserByUserName(userName);
        Session session = SecurityUtils.getSubject().getSession();
        LoginUserInfoBean loginUserInfoBean = new LoginUserInfoBean();
        loginUserInfoBean.setUserName(user.getUserName());
        loginUserInfoBean.setRealName(user.getRealName());
        //TODO
        //loginUserInfoBean.setEmail(user.getEmail());
        loginUserInfoBean.setLoginTime(new Date());
        session.setAttribute("userInfo", loginUserInfoBean);
        return user;
    }
}
