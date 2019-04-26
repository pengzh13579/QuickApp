package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
@Data
@EqualsAndHashCode (callSuper = true)
public class SystemScheduleEntity extends CommonEntity implements Serializable {
    private Integer id;
    private String scheduleName;
    private String scheduleNameCn;
    private String scheduleCron;
    private String scheduleCode;
    private String scheduleParam;
    private Integer disFlag;
    private Date createDate;
    private Date updateDate;
    private String createUser;
    private String updateUser;
}