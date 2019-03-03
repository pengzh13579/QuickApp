package cn.pzh.system.web.project.common.utils;

import cn.pzh.system.web.project.dao.CommonEntity;
import cn.pzh.system.web.project.common.utils.support.ShiroKit;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 共通字段处理
     *  flag:true 新增
     *      :false 修改
     */
    public static void setDeleteCommon(CommonEntity entity) {

        //系统日期
        Date systemDate = new Date();
        String user = ShiroKit.getUser().getUserName();
        entity.setDisFlag(1);
        entity.setUpdateUser(user);
        entity.setUpdateDate(systemDate);
    }


    /**
     * 将字段转换为列名 realName-->real_name
     */
    public static String fieldNameToColumnName(String field) {

        if (null == field){
            return field;
        }
        Matcher matcher = Pattern.compile("([A-Z]+)").matcher(field);
        while(true){
            if(!matcher.find()){
                return field;
            }
            field = field.replaceAll(matcher.group(0), "_" + matcher.group(0).toLowerCase());
        }
    }
}
