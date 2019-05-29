package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

@Data
@EqualsAndHashCode (callSuper = true)
public class InfoQuestionItemEntity extends CommonEntity implements Serializable {
    private Integer id;
    private Integer questionId;
    private String itemQuestion;
    private String itemType;
    private String itemSort;
    private String itemMore;
    private List<InfoQuestionOptionEntity> infoQuestionOptionEntities;
}