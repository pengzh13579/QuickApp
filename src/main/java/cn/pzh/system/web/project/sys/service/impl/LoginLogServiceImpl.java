package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.monitor.LoginLogEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.utils.IpUtil;
import cn.pzh.system.web.project.dao.first.mapper.monitor.LoginLogMapper;
import cn.pzh.system.web.project.sys.service.LoginLogService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
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
    public void insertLoginLog(LoginLogEntity loginLogEntity, AjaxJson j, HttpServletRequest request, String userName) {
        loginLogEntity.setLogName("用户登录");
        loginLogEntity.setCreateDate(new Date());
        loginLogEntity.setCreateUser(userName);
        loginLogEntity.setSucceed(String.valueOf(j.isSuccess()));
        loginLogEntity.setIp(IpUtil.getIpAddr(request));
        loginLogEntity.setMessage(j.getMsg());
        loginLogMapper.saveLoginLog(loginLogEntity);
    }
}
