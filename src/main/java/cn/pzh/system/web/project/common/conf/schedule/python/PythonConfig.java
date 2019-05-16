package cn.pzh.system.web.project.common.conf.schedule.python;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class PythonConfig {

    @Value("${python.localtion}")
    private String localtion;
}
