package cn.pzh.system.web.project.common.dao.first.model;

import lombok.Data;

@Data
public class SystemContactEntity extends CommonEntity {

    private Long iId;
    private Long userId;
    private Long typeDetailId;
    private String contact;

}
