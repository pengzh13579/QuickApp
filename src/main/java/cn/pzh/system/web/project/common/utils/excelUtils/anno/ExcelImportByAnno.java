package cn.pzh.system.web.project.common.utils.excelUtils.anno;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class ExcelImportByAnno {

    public static <T> List<T> excelToList(MultipartFile file, Class<?> clazz) throws Exception {
        XSSFWorkbook xb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = xb.getSheetAt(0);

        //获取sheet中第一行行号
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();

        XSSFRow headRow = sheet.getRow(0);
        List<T> list = new ArrayList<T>();
        for(int i = firstRowNum + 1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            Iterator cells = row.cellIterator();
            int cellIndex = 0;
            T entity = (T) clazz.newInstance();
            while(cells.hasNext()){
                XSSFCell cell = (XSSFCell) cells.next();
                setEntityField(clazz, headRow.getCell(cellIndex).getStringCellValue(), entity, cell);
                cellIndex += 1;
                //logger.debug(cell.getStringCellValue());
            }
            list.add(entity);


        }
        // 取得类型,并根据对象类型设置值.
        //Field field = fieldsMap.get(index);
        return list;
    }

    private static <T> void setEntityField(Class<?> clazz, String fieldName, T entity, XSSFCell cell) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field field = clazz.getDeclaredField(fieldName);
        String c = getCellValueByCell(cell);
        Class<?> fieldType = field.getType();
        if (fieldType == null) {
            //continue;
        }
        String getMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Class tCls = entity.getClass();
        if (String.class == fieldType) {
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{String.class});
            getMethod.invoke(entity, new Object[]{String.valueOf(c)});
        } else if (BigDecimal.class == fieldType) {
            c = c.indexOf("%") != -1 ? c.replace("%", "") : c;
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{BigDecimal.class});
            getMethod.invoke(entity, new Object[]{BigDecimal.valueOf(Double.valueOf(c))});
        } else if (Date.class == fieldType) {
            //field.set(entity, DateUtils.parseDate(c));
        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{Integer.class});
            getMethod.invoke(entity, new Object[]{Integer.parseInt(c)});
        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{Long.class});
            getMethod.invoke(entity, new Object[]{Long.valueOf(c)});
        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{Float.class});
            getMethod.invoke(entity, new Object[]{Float.valueOf(c)});
        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{Short.class});
            getMethod.invoke(entity, new Object[]{Short.valueOf(c)});
        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            Method getMethod = tCls.getMethod(getMethodName, new Class[]{Double.class});
            getMethod.invoke(entity, new Object[]{Double.valueOf(c)});
        } else if (Character.TYPE == fieldType) {
            if ((c != null) && (c.length() > 0)) {
                Method getMethod = tCls.getMethod(getMethodName, new Class[]{Character.class});
                getMethod.invoke(entity, new Object[]{Character.valueOf(c.charAt(0))});
            }
        }
    }
    //获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();

        switch (cellType) {
            case Cell.CELL_TYPE_STRING: //字符串类型
                cellValue = cell.getStringCellValue().trim();
                cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
                break;
            case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC: //数值类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    //cellValue = DateUtil.formatDateByFormat(cell.getDateCellValue(), "yyyy-MM-dd");
                } else {  //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                break;
            default: //其它类型，取空串吧
                cellValue = "";
                break;
        }
        return cellValue;
    }
}
