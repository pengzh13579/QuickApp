package cn.pzh.system.web.project.sys.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Integer uploadFile(MultipartFile uploadFile, String localPath) throws IOException;

}
