package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemUserEntity extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255188L;

    private Integer id;
    private String userName;
    private String realName;
    private String password;
    private String salt;
    private Integer isOnline;
    private String email;
    private Integer status;
    private Integer deptId;
    private String deptName;
    private String roleId;
}
