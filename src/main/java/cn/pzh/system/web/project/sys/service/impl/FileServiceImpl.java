package cn.pzh.system.web.project.sys.service.impl;

import cn.pzh.system.web.project.common.conf.UploadFileConfig;
import cn.pzh.system.web.project.common.constant.ConfConstants;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;
import cn.pzh.system.web.project.dao.first.entity.comm.CommonFileEntity;
import cn.pzh.system.web.project.dao.first.mapper.comm.FileMapper;
import cn.pzh.system.web.project.sys.service.FileService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
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

    @Autowired
    private FileMapper fileMapper;

    @Override
    @Transactional (readOnly = false)
    public Integer uploadFile(MultipartFile uploadFile, String modelPath) throws IOException {
        return FileSave(uploadFile, modelPath);
    }

    /**
     * 文件上传方法
     * @param uploadFile 上传文件
     * @param modelPath 上传路径
     * @return 是否成功
     * @throws IOException
     */
    private Integer FileSave(MultipartFile uploadFile, String modelPath) throws IOException {

        if (!uploadFile.isEmpty()) {
            //获取文件大小
            Long size = uploadFile.getSize();

            //获取文件的文件名
            String filename = uploadFile.getOriginalFilename();

            //获取文件扩展名
            String fileSuffix = StringUtils.substringAfterLast(filename, ConfConstants.FILE_SPACE);

            //设定一个序列以作为存储文件名
            String fileName = UUID.randomUUID().toString() + ConfConstants.FILE_SPACE + fileSuffix;

            //创建保存文件路径的File对象
            File uploadFoler = new File(uploadFileConfig.getReceiveRoot() + modelPath);

            //创建目的File对象
            File targetFile;

            if (!uploadFoler.exists()) {
                FileUtils.forceMkdir(uploadFoler);
            }
            targetFile = new File(uploadFoler, fileName);

            // 把文件重命名，上传到该目录下面，将上传文件写到服务器上指定的文件。
            uploadFile.transferTo(targetFile);

            //定义附件实体
            CommonFileEntity commonFileEntity = new CommonFileEntity();
            commonFileEntity.setFileName(filename);
            commonFileEntity.setFileSuffix(fileSuffix);

            // /file/model/file.xxx
            commonFileEntity.setPath(File.separator
                    + ConfConstants.WEB_RESOURCE_FILE_PATH
                    + File.separator
                    + modelPath
                    + File.separator
                    + fileName);
            commonFileEntity.setUpdateDate(new Date());
            commonFileEntity.setUpdateUser(ShiroKit.getUser().getUserName());
            fileMapper.saveFile(commonFileEntity);

            return commonFileEntity.getId();
        } else {
            return 0;
        }
    }
}
