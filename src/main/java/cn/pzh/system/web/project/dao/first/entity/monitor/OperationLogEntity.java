package cn.pzh.system.web.project.dao.first.entity.monitor;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class OperationLogEntity implements Serializable {

    private String logType;
    private String logName;
    private String createUser;
    private Date createDate;
    private String succeed;
    private String message;
    private String method;
    private String className;
}
