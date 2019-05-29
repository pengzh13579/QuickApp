package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
@Data
@EqualsAndHashCode (callSuper = true)
public class InfoQuestionAnswerEntity extends CommonEntity implements Serializable {
    private Integer itemId;
    private String answerInfo;
    private Integer disFlag;
    private Date createDate;
    private Date updateDate;
    private String createUser;
    private String updateUser;
}