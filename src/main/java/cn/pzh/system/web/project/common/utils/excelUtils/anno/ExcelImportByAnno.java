package cn.pzh.system.web.project.common.utils.excelUtils.anno;

import org.apache.poi.xssf.usermodel.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ExcelImportByAnno {

    public static <T> List<T> excelToList(Class<?> clazz) throws Exception {
        List<T> list = new ArrayList<T>();
        T entity = (T) clazz.newInstance();
        // 取得类型,并根据对象类型设置值.
        //Field field = fieldsMap.get(index);
        Field field = null;
        String c = "";
        Class<?> fieldType = field.getType();
        if (fieldType == null) {
            //continue;
        }
        if (String.class == fieldType) {
            field.set(entity, String.valueOf(c));
        } else if (BigDecimal.class == fieldType) {
            c = c.indexOf("%") != -1 ? c.replace("%", "") : c;
            field.set(entity, BigDecimal.valueOf(Double.valueOf(c)));
        } else if (Date.class == fieldType) {
            //field.set(entity, DateUtils.parseDate(c));
        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            field.set(entity, Integer.parseInt(c));
        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            field.set(entity, Long.valueOf(c));
        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            field.set(entity, Float.valueOf(c));
        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
            field.set(entity, Short.valueOf(c));
        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            field.set(entity, Double.valueOf(c));
        } else if (Character.TYPE == fieldType) {
            if ((c != null) && (c.length() > 0)) {
                field.set(entity, Character.valueOf(c.charAt(0)));
            }
        }
        list.add(entity);
        return list;
    }
}
