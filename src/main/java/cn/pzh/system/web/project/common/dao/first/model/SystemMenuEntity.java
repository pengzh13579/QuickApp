package cn.pzh.system.web.project.common.dao.first.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class SystemMenuEntity extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1435515995276255180L;

    private Long iId;
    private String menuCode;
    private String parentMenuCode;
    private String menuName;
    private String menuUrl;

}
