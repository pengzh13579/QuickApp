package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemRoleEntity extends CommonEntity implements Serializable {

    private Integer id;
    private Integer num;
    private String code;
    private String roleName;
    private String tips;
    private String temp;
}
