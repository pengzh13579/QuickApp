package cn.pzh.system.web.project.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 *
 * @author fengshuonan
 * @date 2016年12月5日 上午10:26:43
 */
@Data
public class ShiroUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer id;          // 主键ID
    public String userName;      // 账号
    public String realName;         // 姓名
    public Integer deptId;      // 部门id
    public String roleId;       // 角色集
    public String deptName;        // 部门名称
    public List<String> roleNames; // 角色名称集

}
