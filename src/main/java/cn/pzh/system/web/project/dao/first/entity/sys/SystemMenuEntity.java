package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
public class SystemMenuEntity extends CommonEntity implements Serializable {

    private Integer id;
    private String code;
    private Integer pid;
    private String menuRealName;
    private String icon;
    private String url;
    private Integer num;
    private Integer levels;
    private Integer menuFlag;
    private String tips;
    private Integer openFlag;

}
