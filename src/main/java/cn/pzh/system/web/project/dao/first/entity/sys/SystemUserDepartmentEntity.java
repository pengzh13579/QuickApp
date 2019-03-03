package cn.pzh.system.web.project.dao.first.entity.sys;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemUserDepartmentEntity implements Serializable {

    private Integer id;
    private Integer userId;
    private Integer departmentId;
}
