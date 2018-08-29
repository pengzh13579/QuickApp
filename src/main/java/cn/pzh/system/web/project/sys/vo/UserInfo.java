package cn.pzh.system.web.project.sys.vo;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemUserNativePlaceEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UserInfo implements Serializable {

    private Integer id;
    private String userName;
    private String realName;
    private String avatar;
    private Integer sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String birthdayRtn;
    private List<SystemUserNativePlaceEntity> userNativePlace;
    private List<SystemContactEntity> contacts;
    private Integer isOnline;
    private String deptId;
    private String deptName;
    private String roleId;
    private String roleName;
    private String disFlag;
}
