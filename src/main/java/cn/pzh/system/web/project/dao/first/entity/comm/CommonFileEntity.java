package cn.pzh.system.web.project.dao.first.entity.comm;

import java.util.Date;
import lombok.Data;
import java.io.Serializable;

@Data
public class CommonFileEntity implements Serializable {

    private Integer id;
    private String fileName;
    private String fileSuffix;
    private String path;
    private String updateUser;
    private Date updateDate;
}
