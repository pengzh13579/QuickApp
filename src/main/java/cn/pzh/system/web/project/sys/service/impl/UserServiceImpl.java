package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.constant.WebConstants;
import cn.pzh.system.web.project.common.dao.first.model.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.model.SystemUserEntity;
import cn.pzh.system.web.project.common.session.LoginUserInfoBean;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.common.utils.IdUtils;
import cn.pzh.system.web.project.common.utils.MD5Util;
import cn.pzh.system.web.project.sys.dao.mapper.UserMapper;
import cn.pzh.system.web.project.sys.service.UserService;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

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
        systemContactEntity.setContact(userEntity.getEmail());
        systemContactEntity.setUserId(userEntity.getIId());
        systemContactEntity.setTypeDetailId(new Long(1));
        CommonFieldUtils.setAdminCommon(systemContactEntity, "registration");

        userMapper.saveContact(systemContactEntity);

        return true;
    }

    @Override
    public String loginCheck(String userName, String password, Boolean rememberFlag)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        SystemUserEntity user = setSession(userName);
        if (null != user) {
            String loginPass = MD5Util.EncoderStringByMd5(password + user.getSalt());
            if (loginPass.equals(user.getPassword())) {
                if (1 != user.getIsOnline()) {
                    //获取当前登陆者
                    Subject userSub = SecurityUtils.getSubject();

                    //创建令牌
                    UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), loginPass);

                    //前台记住我checkbox是否打勾
                    token.setRememberMe(rememberFlag);

                    //登录验证
                    userSub.login(token);
                    user.setIsOnline(1);
                    //user.setLoginTime(new Date());
                    //userMapper.update(user);
                    return WebConstants.LOGIN_SUCCESS;
                }
                return WebConstants.IS_ONLINE;
            }
        }
        return WebConstants.LOGIN_ERROR;
    }

    @Override
    public void updateOnlineStatus(String userName) {

    }

    /**
     * 将Cookie添加进Session
     */
    private SystemUserEntity setSession(String userName) {
        SystemUserEntity user = userMapper.getUserNameByName(userName);
        Session session = SecurityUtils.getSubject().getSession();
        LoginUserInfoBean loginUserInfoBean = new LoginUserInfoBean();
        loginUserInfoBean.setUserName(user.getUserName());
        loginUserInfoBean.setRealName(user.getRealName());
        loginUserInfoBean.setEmail(user.getEmail());
        loginUserInfoBean.setLoginTime(new Date());
        session.setAttribute("userInfo", loginUserInfoBean);
        return user;
    }
}
