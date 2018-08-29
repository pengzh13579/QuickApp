package cn.pzh.system.web.project.sys.vo;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class MenuInfo implements Serializable {
    private Integer id;
    private String code;
    private String pId;
    private String name;
    private String icon;
    private String url;
    private Integer num;
    private Integer isMenu;
    private Integer isOpen;
}
