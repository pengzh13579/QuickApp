package cn.pzh.system.web.project.common.conf.db.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DruidConfig {

    @Value("${druid.url-mappings}")
    private String druidUrlMappings;

    @Value("${druid.url-patterns}")
    private String druidUrlPatterns;

    @Value("${druid.username}")
    private String druidUsername;

    @Value("${druid.password}")
    private String druidPassword;

    @Value("${druid.allow}")
    private String druidAllow;

    @Value("${druid.deny}")
    private String druidDeny;

    @Value("${druid.resetEnable}")
    private String druidResetEnable;


    @Value("${druid.exclusions}")
    private String druidExclusions;


    @Bean
    public ServletRegistrationBean druidServlet() {

        // 主要实现WEB监控的配置处理
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new StatViewServlet(), druidUrlMappings);

        //白名单
        servletRegistrationBean.addInitParameter("allow", druidAllow);
        // 黑名单
        servletRegistrationBean.addInitParameter("deny", druidDeny);
        // 用户名
        servletRegistrationBean.addInitParameter("loginUsername", druidUsername);
        // 密码
        servletRegistrationBean.addInitParameter("loginPassword", druidPassword);
        // 是否可以重置数据源
        servletRegistrationBean.addInitParameter("resetEnable", druidResetEnable);
        return servletRegistrationBean ;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        // 添加过滤规则,所有请求进行监控处理
        filterRegistrationBean.addUrlPatterns(druidUrlPatterns);
        // 添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", druidExclusions);
        return filterRegistrationBean ;
    }
}
