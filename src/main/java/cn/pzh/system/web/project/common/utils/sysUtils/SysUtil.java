package cn.pzh.system.web.project.common.utils.sysUtils;

import cn.pzh.system.web.project.common.constant.WebConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 15-9-8.
 */
public class SysUtil {

    public static String SCHEMA_PATH;
    public static final String DEFAULT_PWD = "888888";

    /**
     * 取得系统时间
     * @return
     */
    public static Date getNowDate() {
        Date date = null;
        SimpleDateFormat sd = new SimpleDateFormat(WebConstants.FORMAT_FULL_DATE);
        try {

            date =  sd.parse(sd.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 将日期格式化成yyyy-MM-dd格式的字符串
     *
     * @param d - 日期
     * @return
     */
    public static String date2Str(Date d) {
        if (d==null) return "";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy-MM-dd");
        return df.format(d);
    }

    /**
     * 将日期格式化成yyyy年MM月格式的字符串
     *
     * @param d - 日期
     * @return
     */
    public static String date2YMStr(Date d) {
        if (d==null) return "";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy年MM月");
        return df.format(d);
    }

    /**
     * 将日期格式化成yyyy年MM月dd日格式的字符串
     *
     * @param d - 日期
     * @return
     */
    public static String date2YMDStr(Date d) {
        if (d==null) return "";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy年MM月dd日");
        return df.format(d);
    }

    /**
     * 将日期格式化成yyyy年MM月dd日格式的字符串
     *
     * @param d - 日期
     * @return
     */
    public static String date2YMDHmStr(Date d) {
        if (d==null) return "";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy年MM月dd日hh时mm分");
        return df.format(d);
    }

    /**
     * 将日期格式化成yyyy-MM-dd hh:mm格式的字符串
     *
     * @param d
     * @return
     */
    public static String datetime2Str(Date d) {
        if (d==null) return "";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }

    /**
     * 将日期格式化成MM-dd hh:mm格式的字符串
     *
     * @param d
     * @return
     */
    public static String datetime2MDHM(Date d) {
        if (d==null) return "";
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("MM-dd HH:mm");
        return df.format(d);
    }

    /**
     * 将yyyy-MM-dd格式的字符串转换成日期
     *
     * @param str
     * @return -成功返回Date，失败返回null
     */
    public static Date str2Date(String str) {
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy-MM-dd");
        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd hh:mm格式的字符串转换成日期
     *
     * @param str
     * @return -成功返回Date，失败返回null
     */
    public static Date str2Datetime(String str) {
        if (StringUtils.isBlank(str) || str.equals("null"))
            return null;

        if (str.indexOf("T") > 0) {
            str = str.replaceAll("T", " ");
        }
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(str);
        } catch (ParseException e) {
            df.applyPattern("yyyy-MM-dd HH:mm");
            try {
                return df.parse(str);
            } catch (ParseException e1) { //ankang - 失败后按照yyyy-MM-dd格式尝试再次解析
                df.applyPattern("yyyy-MM-dd");
                try {
                    return df.parse(str);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        }
    }

    /**
     * 将时间字符串格式为：yyyy-MM-dd HH:mm:ss字符串
     * @param str  '2015-11-10 12:00:00.0'
     * @return -成功返回Date，失败返回null
     */
    public static String str2DatetimeStr(String str) {
        Date date = str2Datetime(str);
        if(date == null)
            return "无";
        return datetime2Str(date);
    }

    /**
     * 将时间字符串格式为：yyyy-MM-dd字符串
     * @param str  '2015-11-10 12:00:00.0'
     * @return -成功返回Date，失败返回null
     */
    public static String str2DatetimeStr2(String str) {
        Date date = str2Datetime(str);
        if(date == null)
            return "";
        return date2Str(date);
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayNext(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(5);
        c.set(5, day + 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 获取yyyy-MM-dd格式的当前日期字符串
     *
     * @return
     */
    public static String todayAsStr() {
        return date2Str(new Date());
    }

    /**
     * 检查指定路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean pathExists(String path) {
        File f = new File(path);
        return f.exists();
    }

    /**
     * 获取WEB-INF目录的绝对路径
     *
     * @return
     */
    public static String webInfPath() {
        return new File(SysUtil.class.getResource("/").getPath()).getParent().replaceAll("%20", " ");
    }

    /**
     * 获取网站根目录的绝对路径
     *
     * @return
     */
    public static String webWebPath() {
        return new File(webInfPath()).getParent().replaceAll("%20", " ");
    }

    /**
     * 获取照片保存绝对路径
     * 照片按类名分子文件夹存放
     *
     * @param clsName 类名
     * @return
     */
    public static String photoPath(String clsName) {
        String path = webWebPath() + "\\photo\\" + clsName;
        File f = new File(path);
        if (!f.exists())
            f.mkdirs();
        return path;
    }

    public static String photoUrl(String clsName) {
        return "photo/" + clsName;
    }

    /**
     * 获取软件更新文件目录的绝对路径
     *
     * @return
     */
    public static String appPatchPath() {
        return webWebPath() + "\\update\\app";
    }

    /**
     * 获取数据库更新文件目录的绝对路径
     *
     * @return
     */
    public static String dbPatchPath() {
        return webWebPath() + "\\update\\db";
    }

    public static String tempPath() {
        return webWebPath() + "\\temp";
    }

    /**
     * 复制文件
     *
     * @return
     */
    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 删除指定文件
     *
     * @param filename 要删除的文件
     * @throws IOException
     */
    public static void deleteFile(String filename) throws IOException {
        File f = new File(filename);
        f.delete();
    }

    /**
     * 根据字段名生成BO对象属性名
     *
     * @param columnName
     * @return
     */
    public static String columnName2FieldName(String columnName) {
        return columnName.toLowerCase();
    }

    /**
     * 生成文件CRC16校验码
     *
     * @param filename
     * @return
     */
    public static String fileCRC16(String filename) {
        File f = new File(filename);
        int size = (int) f.length();
        byte buff[] = new byte[size];
        try {
            FileInputStream fs = new FileInputStream(f);
            try {
                fs.read(buff);
                return CRC16Util.evalCRC16(buff);
            } finally {
                fs.close();
            }
        } catch (Exception ex) {
            return "";
        }
    }

    public static boolean isNullOrEmpty(String str) {
        if (null == str) {
            return true;
        }
        return "".equals(str);
    }

    /**
     * 将二进制形式字符串转化为int类型数字 方便做“与”运算
     *
     * @param str
     * @return
     */
    public static int binaryStringToInt(String str) {
        int result = 0;
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if ('0' == str.charAt(i))
                result *= 2;
            else if ('1' == str.charAt(i)) {
                result = result * 2 + 1;
            }
        }
        return result;
    }

    /**
     * 得到一个长度为length 以 1开头后面全为0的二进制形式的字符串
     *
     * @param length
     * @return
     */
    public static String toBinaryStingByLength(int length) {
        StringBuffer resultB = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                resultB.append('1');
            } else {
                resultB.append('0');
            }
        }
        return resultB.toString();
    }

    public static boolean isNull(String str) {
        return (null == str) || ("".equals(str.trim()));
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 判断是否为空，返回字符串
     * @param object
     * @return
     */
    public static String objectToString(Object object){
        if(object == null)
            return "";
        return String.valueOf(object);
    }

    /**
     * 左补齐，不足长度补0
     * @param length 字符串补全后的长度
     * @param number 需要补全的字符串
     * @return
     */
    public static String lPad(int length, String number) {
        String f = "%0" + length + "d";
        return String.format(f, Integer.valueOf(number));
    }

    /**
     * 右补齐，不足长度补0
     * @param str 需要补全的字符串
     * @param lenth 字符串补全后的长度
     * @return
     */
    public static String rPad(String str, int lenth) {
        while (str.length() < lenth) {
            str = str + "0";
        }
        return str;
    }

    /**
     * 桩号转换，如：200.111转K200+111,200转K200
     * @param stake 数字字符串桩号
     * @return
     */
    public static String stakeFormat(String stake){
        String[] start = stake.split("\\.");
        if(start.length==1){
            stake = "K" + start[0];
        }else if(start.length==2){
            stake = "K" + start[0] + "+" + SysUtil.rPad(start[1],3);
        }
        return stake;
    }

    /**
     * 随机生成盐值
     * @param num 盐值得长度（num）
     * @return
     */
    public static String salt(int num) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-@#$%^&*()";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < num; ++j) {
            int number = random.nextInt(73);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
