package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.monitor.LoginLogEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import javax.servlet.http.HttpServletRequest;

public interface LoginLogService {

    void insertLoginLog(LoginLogEntity loginLogEntity, AjaxJson j, HttpServletRequest request, String userName) ;

}
