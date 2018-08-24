package cn.pzh.system.web.project.sys.vo;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserInfo implements Serializable {

    private String userName;
    private String realName;
    private String avatar;
    private Integer sex;
    private Date birthday;
    private List<SystemContactEntity> contacts;
    private Integer isOnline;
    private String deptId;
    private String deptName;
    private String roleId;
}
