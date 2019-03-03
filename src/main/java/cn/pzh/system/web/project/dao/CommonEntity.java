package cn.pzh.system.web.project.dao;

import java.util.Date;

import cn.pzh.system.web.project.common.model.PageInfo;
import lombok.Data;

@Data
public class CommonEntity<T> extends PageInfo {

    private Integer disFlag;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
}
