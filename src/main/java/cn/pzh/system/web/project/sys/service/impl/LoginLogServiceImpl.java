package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.constant.WebConstants;
import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.model.ShiroUserModel;
import cn.pzh.system.web.project.common.session.LoginUserInfoBean;
import cn.pzh.system.web.project.common.utils.CommonFieldUtils;
import cn.pzh.system.web.project.common.utils.IdUtils;
import cn.pzh.system.web.project.common.utils.IpUtil;
import cn.pzh.system.web.project.common.utils.MD5Util;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.sys.dao.mapper.LoginLogMapper;
import cn.pzh.system.web.project.sys.dao.mapper.UserMapper;
import cn.pzh.system.web.project.sys.service.LoginLogService;
import cn.pzh.system.web.project.sys.service.UserService;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Transactional (readOnly = false)
    public void insertLoginLog(SystemLoginLogEntity loginLogEntity, AjaxJson j, HttpServletRequest request, String userName) {
        loginLogEntity.setLogName("用户登录");
        loginLogEntity.setCreateDate(new Date());
        loginLogEntity.setCreateUser(userName);
        loginLogEntity.setSucceed(String.valueOf(j.isSuccess()));
        loginLogEntity.setIp(IpUtil.getIpAddr(request));
        loginLogEntity.setMessage(j.getMsg());
        loginLogMapper.saveLoginLog(loginLogEntity);
    }
}
