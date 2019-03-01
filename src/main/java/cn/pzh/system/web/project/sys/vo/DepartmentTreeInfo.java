package cn.pzh.system.web.project.sys.vo;

import java.util.List;
import lombok.Data;

@Data
public class DepartmentTreeInfo {

    private Integer id;
    private String code;
    private String departSimpleName;
    private Integer openFlag;
    private List<DepartmentTreeInfo> departmentChildren;
}
