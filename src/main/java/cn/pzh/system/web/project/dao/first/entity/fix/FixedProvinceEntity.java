package cn.pzh.system.web.project.dao.first.entity.fix;

import java.io.Serializable;
import lombok.Data;

@Data
public class FixedProvinceEntity implements Serializable {
    private String provinceName;
    private String provinceId;
}