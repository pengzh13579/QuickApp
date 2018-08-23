package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class SystemMenuEntity extends CommonEntity implements Serializable {

    private Integer id;
    private String code;
    private String pcode;
    private String pcodes;
    private String name;
    private String icon;
    private String url;
    private Integer num;
    private Integer levels;
    private Integer isMenu;
    private String tips;
    private Integer isOpen;

}
