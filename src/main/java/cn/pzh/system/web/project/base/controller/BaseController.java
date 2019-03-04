package cn.pzh.system.web.project.base.controller;

import cn.pzh.system.web.project.common.utils.sysUtils.SysUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 异常处理方法
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler
    public String handleException(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Exception e) {

        // json格式的ajax请求
        if (request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {

            response.setStatus(500);
            response.setContentType("application/json;charset=utf-8");

            try {
                PrintWriter writer = response.getWriter();
                if (e instanceof RuntimeException) {// 运行时异常
                    writer.write("系统内部异常！");
                } else {// 非运行时异常
                    writer.write(e.getMessage());// 此处待细化异常处理给提示 ？？？
                }
                writer.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
                printException(e1);
            }
            return null;

        } else {// URL普通请求
            if (e instanceof RuntimeException) {// 运行时异常
                request.setAttribute("exceptionMessage", "系统内部异常！");
                printException(e);
            } else {
                request.setAttribute("exceptionMessage", e.getMessage());// 此处待细化异常处理给显示
                printException(e);
            }
            try {// 跳转统一异常处理界面
                request.getRequestDispatcher("/error").forward(request, response);
            } catch (ServletException e1) {
                e1.printStackTrace();
                printException(e1);
            } catch (IOException e2) {
                e2.printStackTrace();
                printException(e2);
            } catch (Exception e3) {
                e3.printStackTrace();
                printException(e3);
            }
            return null;
        }

    }

    /**
     * 打印异常
     * @param e 异常
     */
    public void printException(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        logger.error(new Date() + ":" + sw.toString());
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                //logger.debug("================================");
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                //logger.debug("********************************");
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                //setValue(SysUtil.str2Datetime(text));
                if (text == null || text.length() == 0) {
                    setValue(null);
                } else {
                    try {
                        if (text.length() == 10) {
                            setValue(SysUtil.str2Date(text));
                        } else if (text.length() == 13) {
                            setValue(new Date(Long.parseLong(text)));
                        } else if (text.length() == 16) {
                            setValue(SysUtil.str2Datetime(text));
                        } else if (text.length() == 19) {
                            setValue(SysUtil.str2Datetime(text));
                        } else {
                            logger.error("转换日期失败: 日期长度不符合要求!");
                            throw new IllegalArgumentException("转换日期失败: 日期长度不符合要求!");
                        }
                    } catch (Exception ex) {
                        logger.error("转换日期失败: " + ex.getMessage());
                        throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
                    }
                }
            }
        });

    }


}
