package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.pzh.system.web.project.common.utils.excelUtils.anno.ExcelField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemUserEntity extends CommonEntity implements Serializable {

    private Integer id;

    @ExcelField(name = "用户名", width = 50)
    private String userName;

    private String avatar;

    @ExcelField(name = "真实姓名")
    private String realName;

    private String password;
    private String salt;
    private Integer sex;

    @JsonFormat(pattern = "yyyy年MM月dd日")
    @ExcelField(name = "生日", width = 50)
    private Date birthday;

    private Integer isOnline;
    private String roleId;
    private Integer deptId;

    private String avatarPath;
    private String roleName;
    private String deptName;
    private List<SystemContactEntity> contacts;

    private String createDateStart;
    private String createDateEnd;

}
