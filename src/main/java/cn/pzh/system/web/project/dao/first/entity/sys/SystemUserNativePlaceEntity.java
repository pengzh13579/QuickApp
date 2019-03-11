package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemUserNativePlaceEntity extends CommonEntity implements Serializable {

    private String userName;
    private String provinceId;
    private String cityId;
    private String areaId;
    private Integer nativeType;
}
