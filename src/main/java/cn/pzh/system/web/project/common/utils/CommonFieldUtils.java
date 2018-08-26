package cn.pzh.system.web.project.common.utils;

import cn.pzh.system.web.project.common.dao.first.entity.CommonEntity;
import cn.pzh.system.web.project.common.model.ShiroUserModel;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;

import java.util.Date;

public class CommonFieldUtils {

    /**
     * 共通字段处理
     */
    public static void setAdminCommon(CommonEntity entity) {

        //系统日期
        Date systemDate = new Date();

        String user = ShiroKit.getUser().getUserName();
        entity.setDisFlag(0);
        entity.setCreateUser(user);
        entity.setUpdateUser(user);
        entity.setCreateDate(systemDate);
        entity.setUpdateDate(systemDate);
    }

    /**
     * 共通字段处理
     *  flag:true 新增
     *      :false 修改
     */
    public static void setAdminCommon(CommonEntity entity, Boolean flag) {

        //系统日期
        Date systemDate = new Date();
        String user = ShiroKit.getUser().getUserName();
        entity.setDisFlag(0);
        entity.setUpdateUser(user);
        entity.setUpdateDate(systemDate);
        if (flag) {
            entity.setCreateUser(user);
            entity.setCreateDate(systemDate);
        }
    }
}
