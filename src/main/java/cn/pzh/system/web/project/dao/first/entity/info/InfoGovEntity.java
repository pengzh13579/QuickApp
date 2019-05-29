package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
@Data
@EqualsAndHashCode (callSuper = true)
public class InfoGovEntity extends CommonEntity implements Serializable {
    private String govCode;
    private String govName;
    private String govProvince;
    private String govCity;
    private Date scheduleEnd;
    private Integer disFlag;
    private Date createDate;
    private Date updateDate;
    private String createUser;
    private String updateUser;
}