package cn.pzh.system.web.project.common.utils.excelUtils.anno;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelExportByAnno {

    public static <T> void listToExcel(Collection<?> dataSet, Class<?> cls, String sheetName,
                                       int sheetSize, HttpServletResponse response) throws Exception {
        if (sheetSize > 60000 || sheetSize < 1) {
            sheetSize = 60000;
        }
        Field[] fs = cls.getDeclaredFields();

        List<String> headerList = new ArrayList<String>();
        List<Integer> widthList = new ArrayList<Integer>();
        List<Field> fieldList = new ArrayList<Field>();
        for (Field f : fs){
            // 获取字段上加的@ExcelConfig注解
            ExcelField ec = f.getAnnotation(ExcelField.class);
            if (ec == null) continue;
            headerList.add(ec.name());
            widthList.add(ec.width());
            fieldList.add(f);
        }
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 1.计算一共有多少个工作簿
        double sheetNum = Math.ceil(dataSet.size() / new Integer(sheetSize).doubleValue());

        // 2.获得所有数据
        Iterator<?> it = dataSet.iterator();

        int itIndex = 0;

        String sheetShowName = "";

        // 3.创建相应的工作簿，并向其中填充数据
        for (int i = 0; i < sheetNum; i++) {
            // 如果只有一个工作簿的情况
            int lastIndex = (i + 1) * sheetSize > dataSet.size() ? dataSet.size() : (i + 1) * sheetSize;

            // 工作簿名字
            sheetShowName = sheetName + "-" + (i + 1) + "页";

            // 4.生成一个工作簿
            XSSFSheet sheet = workbook.createSheet(sheetShowName);

            // 设置列宽
            for (int w = 0; w < widthList.size(); w++) {
                sheet.setColumnWidth(w, widthList.get(w) * 256);
            }

            // 5.设置标题第一行显示内容
            fillHeadRow(sheet, headerList);

            // 索引、行数
            int index = 1;
            // 6.循环数据内容
            while (it.hasNext()) {

                // 换工作簿
                if ((itIndex) == lastIndex) break;
                itIndex++;
                XSSFRow row = sheet.createRow(index);
                index++;
                Object t = it.next();

                for (short j = 0; j < fieldList.size(); j++) {
                    XSSFCell cell = row.createCell(j);
                    Field field = fieldList.get(j);
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    try {
                        Class tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                        Object value = getMethod.invoke(t, new Object[]{});

                        // 如果是时间类型,按照格式转换
                        String textValue = null;
                        if (value instanceof Date) {
                            Date date = (Date) value;
                            JsonFormat jf = field.getAnnotation(JsonFormat.class);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            if (jf != null) {
                                sdf = new SimpleDateFormat(jf.pattern());
                            }
                            textValue = sdf.format(date);
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            textValue = value.toString();
                        }

                        // 利用正则表达式判断textValue是否全部由数字组成
                        if (textValue != null) {
                            Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                                // 不是数字做普通处理
                                cell.setCellValue(textValue);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 7.设置返回的响应数据
        setResponseHeader(response, sheetShowName + ".xlsx");

        OutputStream os = response.getOutputStream();

        // 8.输出
        workbook.write(os);
        os.flush();
        os.close();

    }
    public static <T> void listToExcel(
            Collection<?> dataSet,
            Class<?> cls,
            String sheetName,
            HttpServletResponse response
    ) throws Exception {
        listToExcel(dataSet, cls, sheetName, 65535, response);
    }

    /***
     * 填充标题头
     * @param sheet
     * @param headerList
     */
    private static void fillHeadRow(XSSFSheet sheet, List<String> headerList) {
        // 创建列首-增加样式-赋值
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headerList.size(); i++) {
            XSSFCell cell = row.createCell(i);
            // cell.setCellStyle(headersStyle);
            XSSFRichTextString text = new XSSFRichTextString(headerList.get(i));
            cell.setCellValue(text);

        }
    }

    /***
     * 设置响应流
     * @param response
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    private static void setResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        fileName = new String(fileName.getBytes(),"ISO8859-1");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }
}
