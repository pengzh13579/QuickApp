package cn.pzh.system.web.project.sys.service;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import cn.pzh.system.web.project.sys.vo.DepartmentTreeInfo;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface DepartmentService {

    List<DepartmentTreeInfo> listDepartmentTree(String code);

    Boolean insert(SystemDepartmentEntity info);

    SystemDepartmentEntity get(Integer id);

    Boolean update(SystemDepartmentEntity info);

    void delete(Integer id);

    List<SystemDepartmentEntity> listDepartmentUsers(String code);

    SystemDepartmentEntity getDepartmentByCode(String code);
}
