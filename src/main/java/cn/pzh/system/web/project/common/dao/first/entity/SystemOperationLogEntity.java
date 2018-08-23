package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class SystemOperationLogEntity implements Serializable {

    private Integer id;
    private String logType;
    private String logName;
    private String createUser;
    private Date createDate;
    private String succeed;
    private String message;
    private String method;
    private String className;
}
