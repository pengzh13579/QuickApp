package cn.pzh.system.web.project.dao.first.entity.sys;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class SystemDictionaryEntity extends CommonEntity implements Serializable {

    private Integer id;
    private Integer num;
    private Integer pid;
    private String name;
    private String tips;
}
