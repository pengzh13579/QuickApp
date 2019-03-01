package cn.pzh.system.web.project.dao;

import java.util.Date;
import lombok.Data;

@Data
public class CommonEntity {

    private Integer disFlag;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
}
