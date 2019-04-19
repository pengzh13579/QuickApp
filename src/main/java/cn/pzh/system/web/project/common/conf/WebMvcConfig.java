package cn.pzh.system.web.project.common.conf;

import cn.pzh.system.web.project.common.conf.wx.WxRequestInterceptor;
import cn.pzh.system.web.project.common.constant.ConfConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author pengzh
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    //图片存放根目录下的子目录
    @Value ("${upload.receive-root}")
    private String uploadFile;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/" + ConfConstants.WEB_RESOURCE_FILE_PATH +"/**").addResourceLocations("file:" + uploadFile);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public WxRequestInterceptor wxRequestInterceptor() {
        return new WxRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(wxRequestInterceptor()).addPathPatterns("/wxController/**");

        super.addInterceptors(registry);
    }
}
