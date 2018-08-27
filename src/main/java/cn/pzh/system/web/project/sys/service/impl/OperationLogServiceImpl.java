package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemOperationLogEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.utils.IpUtil;
import cn.pzh.system.web.project.sys.dao.mapper.LoginLogMapper;
import cn.pzh.system.web.project.sys.dao.mapper.OperationLogMapper;
import cn.pzh.system.web.project.sys.service.OperationLogService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
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
    public void insertOperationLog(SystemOperationLogEntity operationLogEntity) {
        operationLogMapper.saveOperationLog(operationLogEntity);
    }
}
