package cn.pzh.system.web.project.sys.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class MenuInfoVO implements Serializable {
    private Integer id;
    private String code;
    private Integer pId;
    private String name;
    private String icon;
    private String url;
    private Integer num;
    private Integer menuFlag;
    private Integer openFlag;
}
