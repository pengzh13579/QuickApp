package cn.pzh.system.web.project.business.sys.vo;

import java.util.List;
import lombok.Data;

@Data
public class DepartmentTreeVO {

    private Integer id;
    private String code;
    private String departSimpleName;
    private Integer openFlag;
    private List<DepartmentTreeVO> departmentChildren;
}
