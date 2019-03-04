package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.monitor.LoginLogEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import javax.servlet.http.HttpServletRequest;

public interface LoginLogService {

    /***
     * 插入登录日志
     * @param loginLogEntity 登录日志实体类
     * @param j ajax信息
     * @param request 请求
     * @param userName 用户名
     */
    void insertLoginLog(LoginLogEntity loginLogEntity, AjaxJson j, HttpServletRequest request, String userName) ;

}
