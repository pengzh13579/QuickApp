package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemOperationLogEntity;

public interface OperationLogMapper {
    void saveOperationLog(SystemOperationLogEntity operationLogEntity);
}
