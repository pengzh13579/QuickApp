package cn.pzh.system.web.project.common.model;

import lombok.Data;

/**
 * jquery ztree 插件的节点
 *
 * @author fengshuonan
 * @date 2017年2月17日 下午8:25:14
 */
@Data
public class CityModel {

    private Integer id;

    private String cityId;

    private String city;

    private String father;

}

