package cn.pzh.system.web.project.common.utils.excelUtils.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface ExcelField {

    // 列名
    String name() default "";

    // 宽度
    int width() default 20;

    // 默认值
    String defaultValue() default "";

    // 执行方法
    String doMethod() default "";
}
