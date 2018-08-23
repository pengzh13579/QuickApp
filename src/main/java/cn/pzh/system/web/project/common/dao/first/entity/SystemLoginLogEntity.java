package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SystemLoginLogEntity implements Serializable {

    private Integer id;
    private String logName;
    private String createUser;
    private Date createDate;
    private String succeed;
    private String message;
    private String ip;
}
