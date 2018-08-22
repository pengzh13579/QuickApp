package cn.pzh.system.web.project.common.utils;

import java.io.Closeable;

public class CloseIOUtil {

    /**
     * 关闭IO流
     */
    public static void closeAll(Closeable... cls) {

        if (cls != null) {
            for (Closeable cl : cls) {
                try {
                    if (cl != null) {
                        cl.close();
                    }
                } catch (Exception e) {

                } finally {
                    cl = null;
                }
            }
        }
    }
}

