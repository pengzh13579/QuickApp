package cn.pzh.system.web.project.dao.first.entity.fix;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class FixedDictionaryEntity extends CommonEntity implements Serializable {
    private Integer id;
    private Integer pid;
    private String dictionartyCode;
    private String dictionartyName;
    private Integer dictionartyValue;
    private Integer sort;
    private String described;
}
