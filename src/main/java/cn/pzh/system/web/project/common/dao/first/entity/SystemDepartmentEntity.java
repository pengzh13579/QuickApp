package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemDepartmentEntity extends CommonEntity implements Serializable {

    private Integer id;
    private Integer num;
    private Integer pid;
    private String pids;
    private String simpleName;
    private String fullName;
    private String tips;
}
