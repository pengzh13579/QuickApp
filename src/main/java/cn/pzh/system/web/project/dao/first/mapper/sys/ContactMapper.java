package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemContactEntity;
import java.util.List;

public interface ContactMapper {

    List<SystemContactEntity> selectContactByUserName(String userName);

    void saveContact(List<SystemContactEntity> systemContactEntitys);

    void deleteContactByUserName(String userName);

}
