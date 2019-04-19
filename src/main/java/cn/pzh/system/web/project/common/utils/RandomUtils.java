package cn.pzh.system.web.project.common.utils;

import java.util.Random;

public class RandomUtils {

    /***
     * 获取随机的num位字符串
     * @param num
     * @return
     */
    public static String getRandomStr(int num)
    {
        String originStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i <= num; i++)
        {
            int randomNumber = new Random().nextInt(46);
            sb.append(originStr.charAt(randomNumber));
        }
        return sb.toString();
    }
}
