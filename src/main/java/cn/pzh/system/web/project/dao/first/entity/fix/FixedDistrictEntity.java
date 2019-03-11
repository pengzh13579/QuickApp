package cn.pzh.system.web.project.dao.first.entity.fix;

import java.io.Serializable;
import lombok.Data;

@Data
public class FixedDistrictEntity implements Serializable {
    private String districtId;
    private String districtName;
    private String fatherCounty;
}