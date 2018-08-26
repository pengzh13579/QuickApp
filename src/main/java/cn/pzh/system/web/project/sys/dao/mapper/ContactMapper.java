package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;
import java.util.List;

public interface ContactMapper {

    List<SystemContactEntity> selectContactByUserName(String userName);

    void saveContact(List<SystemContactEntity> systemContactEntitys);
}
