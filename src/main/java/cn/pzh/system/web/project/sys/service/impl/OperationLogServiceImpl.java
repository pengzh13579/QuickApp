package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.dao.first.entity.monitor.OperationLogEntity;
import cn.pzh.system.web.project.dao.first.mapper.monitor.OperationLogMapper;
import cn.pzh.system.web.project.sys.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    @Transactional (readOnly = false)
    public void insertOperationLog(OperationLogEntity operationLogEntity) {
        operationLogMapper.saveOperationLog(operationLogEntity);
    }
}
