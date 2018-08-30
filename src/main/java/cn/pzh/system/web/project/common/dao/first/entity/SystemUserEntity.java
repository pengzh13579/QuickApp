package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemUserEntity extends CommonEntity implements Serializable {

    private Integer id;
    private String userName;
    private Integer avatar;
    private String avatarImg;
    private String realName;
    private String password;
    private String salt;
    private Integer sex;
    private Date birthday;
    private List<SystemContactEntity> contacts;
    private Integer isOnline;
    private String roleId;
    private String roleName;
    private Integer deptId;
    private String deptName;
}
