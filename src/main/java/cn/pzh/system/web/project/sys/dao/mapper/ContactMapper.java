package cn.pzh.system.web.project.sys.dao.mapper;

import cn.pzh.system.web.project.common.dao.first.entity.SystemContactEntity;

public interface ContactMapper {

    SystemContactEntity selectContactByUserName(String userName);

    void saveContact(SystemContactEntity systemContactEntity);
}
