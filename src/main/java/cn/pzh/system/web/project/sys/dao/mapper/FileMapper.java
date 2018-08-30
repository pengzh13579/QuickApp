package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemFileEntity;
import cn.pzh.system.web.project.common.dao.first.entity.SystemLoginLogEntity;

public interface FileMapper {
    Integer saveFile(SystemFileEntity systemFileEntity);
}
