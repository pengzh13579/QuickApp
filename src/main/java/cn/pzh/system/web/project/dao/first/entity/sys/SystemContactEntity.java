package cn.pzh.system.web.project.dao.first.entity.sys;

import lombok.Data;

@Data
public class SystemContactEntity {

    private Integer id;
    private String userName;
    private Integer contactType;
    private String contactInfo;
}
