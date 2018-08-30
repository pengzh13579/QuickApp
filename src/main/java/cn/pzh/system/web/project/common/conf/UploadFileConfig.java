package cn.pzh.system.web.project.common.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class UploadFileConfig {

    //接收文件目录
    @Value ("${upload.receive-root}")
    private String receiveRoot;

}
