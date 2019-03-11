package cn.pzh.system.web.project.dao.first.entity.fix;

import java.io.Serializable;
import lombok.Data;

@Data
public class FixedCountyEntity implements Serializable {
    private String countyId;
    private String countyName;
    private String fatherProvince;
}