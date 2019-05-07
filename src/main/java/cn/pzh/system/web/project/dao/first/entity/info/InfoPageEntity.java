package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
@Data
@EqualsAndHashCode (callSuper = true)
public class InfoPageEntity extends CommonEntity implements Serializable {
    private String id;
    private String areaId;
    private String areaName;
    private String areaFlag;
    private String pageTitle;
    private String pageDescribed;
    private String pageContent;
    private Date releaseDate;
    private String industryInfo;

    private String releaseDateStart;
    private String releaseDateEnd;
}