package cn.pzh.system.web.project.common.model;

import lombok.Data;

/**
 * jquery ztree 插件的节点
 */
@Data
public class CityModel {

    private Integer id;

    private String countyId;

    private String countyName;

    private String fatherProvince;

}


