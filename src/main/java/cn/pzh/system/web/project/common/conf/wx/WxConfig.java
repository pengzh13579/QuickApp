package cn.pzh.system.web.project.common.conf.wx;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WxConfig {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;
}
