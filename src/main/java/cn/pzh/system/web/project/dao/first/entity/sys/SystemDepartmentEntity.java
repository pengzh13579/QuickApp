package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemDepartmentEntity extends CommonEntity implements Serializable {
    private Integer id;
    private String code;
    private String departCode;
    private String pcode;
    private String departSimpleName;
    private String departFullName;
    private String departLeader;
    private String departTel;
    private Integer openFlag;
    private String tips;
    private String temp;
    private Integer num;
}
