package cn.pzh.system.web.project.sys.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class ChangePasswordInfo implements Serializable {

    private String oldPwd;
    private String newPwd;
    private String againPwd;

}
