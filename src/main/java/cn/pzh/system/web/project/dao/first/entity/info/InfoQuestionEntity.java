package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode (callSuper = true)
public class InfoQuestionEntity extends CommonEntity implements Serializable {
    private Integer id;
    private String questionCode;
    private String questionName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String questionDescribed;
    private Integer disFlag;
    private Date createDate;
    private Date updateDate;
    private String createUser;
    private String updateUser;
    private String startDateRtn;
    private String endDateRtn;

    private List<InfoQuestionItemEntity> infoQuestionItemEntities;
}