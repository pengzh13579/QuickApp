package cn.pzh.system.web.project.business.sys.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile uploadFile, String modelPath) throws IOException;

}
