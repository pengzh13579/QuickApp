package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemRoleEntity extends CommonEntity implements Serializable {

    private Integer id;
    private Integer num;
    private Integer pid;
    private String name;
    private Integer deptId;
    private String tips;
}
