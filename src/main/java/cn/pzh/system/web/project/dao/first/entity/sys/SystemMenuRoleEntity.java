package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;

@Data
public class SystemMenuRoleEntity implements Serializable {

    private Integer id;
    private Integer menuId;
    private Integer roleId;
}
