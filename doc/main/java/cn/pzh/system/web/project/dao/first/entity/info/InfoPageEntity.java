package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
@Data
@EqualsAndHashCode (callSuper = true)
public class InfoPageEntity extends CommonEntity implements Serializable {
    private Integer ID;
    private String AREAId;
    private String PAGETitle;
    private String PAGEContent;
    private Date RELEASEDate;
    private String INDUSTRYInfo;
}