package cn.pzh.system.web.project.sys.vo;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemContactEntity;
import cn.pzh.system.web.project.dao.first.entity.sys.SystemUserNativePlaceEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UserInfoVO implements Serializable {

    private Integer id;
    private String userName;
    private String realName;
    private String avatarPath;
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
