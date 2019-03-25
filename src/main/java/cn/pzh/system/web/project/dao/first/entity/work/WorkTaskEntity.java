package cn.pzh.system.web.project.dao.first.entity.work;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class WorkTaskEntity extends CommonEntity implements Serializable {
    private Integer id;
    private String taskName;
    private String taskContent;
    private Integer taskStatus;
}