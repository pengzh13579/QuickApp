package cn.pzh.system.web.project.dao.first.entity.monitor;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class LoginLogEntity implements Serializable {

    private Integer id;
    private String logName;
    private String createUser;
    private Date createDate;
    private String succeed;
    private String message;
    private String ip;
}
