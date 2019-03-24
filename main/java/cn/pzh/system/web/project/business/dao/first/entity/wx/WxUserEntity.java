package cn.pzh.system.web.project.business.dao.first.entity.wx;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.math.BigInteger;
@Data
@EqualsAndHashCode (callSuper = true)
public class WxUserEntity extends CommonEntity implements Serializable {
    private BigInteger id;
    private String accesstoken;
    private String openid;
    private String nickname;
    private String headimg;
    private String createtime;
    private String provice;
    private String city;
    private Integer money;
}