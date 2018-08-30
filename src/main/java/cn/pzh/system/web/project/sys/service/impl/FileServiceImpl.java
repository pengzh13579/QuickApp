package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.conf.UploadFileConfig;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.common.utils.IpUtil;
import cn.pzh.system.web.project.sys.dao.mapper.LoginLogMapper;
import cn.pzh.system.web.project.sys.service.FileService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional (propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @Override
    @Transactional (readOnly = false)
    public String uploadFile(MultipartFile uploadFile, String localPath) throws IOException {

        return String.valueOf(FileSave(uploadFile,localPath));
    }

    /**
     * 文件上传方法
     * @param uploadFile 上传文件
     * @param ContextPath 上传路径
     * @return 是否成功
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public Integer FileSave(MultipartFile uploadFile,
            String ContextPath) throws IOException {

        if (!uploadFile.isEmpty()) {
            //获取文件大小
            Long size = uploadFile.getSize();


            //获取文件的文件名
            String filename = uploadFile.getOriginalFilename();

            //获取文件扩展名
            String fileSuffix = StringUtils.substringAfterLast(filename, ".");

            //设定一个序列以作为存储文件名
            String fileName = UUID.randomUUID().toString() + "." + fileSuffix;

            //创建保存文件路径的File对象
            File uploadFoler = new File(ContextPath);

            //创建目的File对象
            File targetFile;

            if (!uploadFoler.exists()) {
                FileUtils.forceMkdir(uploadFoler);
            }
            targetFile = new File(uploadFoler, fileName);

            // 把文件重命名，上传到该目录下面，将上传文件写到服务器上指定的文件。
            uploadFile.transferTo(targetFile);

            //定义附件实体
//            SystemFileEntity sysFileEntity = new SystemFileEntity();
//            sysFileEntity.setFileName(filename);
//            sysFileEntity.setFilePath(targetFile.getPath().split("uploadFile")[1]);
//            Integer fileSEQ = fileMapper.save(sysFileEntity);

            return 1;
        } else {
            return 0;
        }
    }
}
