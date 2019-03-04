package cn.pzh.system.web.project.sys.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class ChangePasswordVO implements Serializable {

    private String oldPwd;
    private String newPwd;
    private String againPwd;

}
