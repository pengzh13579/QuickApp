package cn.pzh.system.web.project.dao.first.mapper.sys;

import cn.pzh.system.web.project.dao.first.entity.sys.SystemDepartmentEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {

    List<SystemDepartmentEntity> getDepartmentList();

    SystemDepartmentEntity selectDepartmentById(Integer id);

    void saveList(List<SystemDepartmentEntity> list);

    Boolean save(SystemDepartmentEntity role);

    Boolean update(SystemDepartmentEntity role);

    SystemDepartmentEntity selectDepartmentByCode(String code);

    Boolean insertOrUpdateRelatedUsers(@Param("id") Integer id, @Param("userIds") List<Integer> userIds);

    Boolean deleteRelatedUsers(@Param("id") Integer id, @Param("userIds") List<Integer> userIds);
}
