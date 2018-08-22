package cn.pzh.system.web.project.common.utils;

import cn.pzh.system.web.project.common.dao.first.entity.CommonEntity;
import java.util.Date;

public class CommonFieldUtils {

    /**
     * 共通字段处理
     */
    public static void setAdminCommon(CommonEntity entity, String user) {

        //系统日期
        Date systemDate = new Date();

        entity.setDisFlag(0);
        entity.setCreateUser(user);
        entity.setUpdateUser(user);
        entity.setCreateDate(systemDate);
        entity.setUpdateDate(systemDate);
    }

    /**
     * 共通字段处理
     */
    public static void setAdminCommon(CommonEntity entity, Boolean flag) {

        //系统日期
        Date systemDate = new Date();
        entity.setDisFlag(0);
        entity.setUpdateUser("admin");
        entity.setUpdateDate(systemDate);
        if (flag) {
            entity.setCreateUser("admin");
            entity.setCreateDate(systemDate);
        }
    }
}
