package cn.pzh.system.web.project.common.dao.first.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SystemFileEntity implements Serializable {

    private Integer id;
    private String fileName;
    private String fileSuffix;
    private String path;
}
