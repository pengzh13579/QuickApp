package cn.pzh.system.web.project.common.dao.first.entity;

import lombok.Data;

@Data
public class SystemContactEntity extends CommonEntity {

    private Integer id;
    private Integer userId;
    private Integer typeDetailId;
    private String contact;

}
