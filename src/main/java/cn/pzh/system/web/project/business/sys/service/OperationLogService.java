package cn.pzh.system.web.project.business.sys.service;

import cn.pzh.system.web.project.dao.first.entity.monitor.OperationLogEntity;

public interface OperationLogService {

    /***
     * 插入操作日志
     * @param operationLogEntity 操作日志实体类
     */
    void insertOperationLog(OperationLogEntity operationLogEntity );

}
