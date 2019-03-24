package cn.pzh.system.web.project.dao.first.entity.wx;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class WxUserEntity implements Serializable {
    private Integer id;
    private String accesstoken;
    private String openid;
    private String nickname;
    private String headimg;
    private Date createTime;
    private String provice;
    private String city;
    private Integer money;
    private String appid;
    private String gender;
}