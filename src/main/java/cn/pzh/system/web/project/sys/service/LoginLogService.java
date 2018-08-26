package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import javax.servlet.http.HttpServletRequest;

public interface LoginLogService {

    void insertLoginLog(SystemLoginLogEntity loginLogEntity, AjaxJson j, HttpServletRequest request, String userName) ;

}
