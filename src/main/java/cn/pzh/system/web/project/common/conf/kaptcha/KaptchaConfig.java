package cn.pzh.system.web.project.common.conf.kaptcha;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Component
public class KaptchaConfig {

    private static Properties props = new Properties();

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 读取配置文件
        try {
            props.load(KaptchaConfig.class.getClassLoader()
                    .getResourceAsStream("kaptcha.properties"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        Config config = new Config(props);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }

}
