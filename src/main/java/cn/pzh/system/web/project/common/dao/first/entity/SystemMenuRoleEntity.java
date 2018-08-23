package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class SystemMenuRoleEntity implements Serializable {

    private Integer id;
    private Integer menuId;
    private Integer roleId;
}
