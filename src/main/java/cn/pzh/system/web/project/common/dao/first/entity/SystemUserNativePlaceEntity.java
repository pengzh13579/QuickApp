package cn.pzh.system.web.project.common.dao.first.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemUserNativePlaceEntity extends CommonEntity implements Serializable {

    private Integer id;
    private String userName;
    private String provinceId;
    private String cityId;
    private String areaId;
    private Integer nativeType;
}
