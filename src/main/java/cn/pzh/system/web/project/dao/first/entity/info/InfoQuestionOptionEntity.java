package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class InfoQuestionOptionEntity extends CommonEntity implements Serializable {
    private Integer itemId;
    private String itemName;
    private String optionName;
    private Integer optionCd;
}