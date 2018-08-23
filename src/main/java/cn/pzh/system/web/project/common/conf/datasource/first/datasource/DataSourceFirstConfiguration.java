package cn.pzh.system.web.project.common.conf.datasource.first.datasource;

import cn.pzh.system.web.project.common.conf.datasource.first.annotation.DatasourceFirstMapper;
import cn.pzh.system.web.project.common.constant.ConfConstants;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan (basePackages = ConfConstants.MAPPER_SCAN_PACKAGE, annotationClass = DatasourceFirstMapper.class, sqlSessionTemplateRef = ConfConstants.FIRST_SQL_SESSION_ID)
public class DataSourceFirstConfiguration {

    @Bean (name = ConfConstants.FIRST_DATASOURCE_ID)
    @ConfigurationProperties (prefix = ConfConstants.FIRST_DATASOURCE_YML_CONFIGURATION)
    @Primary
    public DataSource testDataSource() {
        return new DruidDataSource();
    }

    @Bean (name = ConfConstants.FIRST_SQL_SESSION_FACTORY_ID)
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier (ConfConstants.FIRST_DATASOURCE_ID) DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Interceptor[] plugins =  new Interceptor[]{firstPageHelper()};
        bean.setPlugins(plugins);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/cn/pzh/system/web/project/**/dao/first/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean (name = ConfConstants.FIRST_DATASOURCE_MBEAN)
    @Primary
    public DataSourceTransactionManager testTransactionManager(
            @Qualifier (ConfConstants.FIRST_DATASOURCE_ID) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean (name = ConfConstants.FIRST_SQL_SESSION_ID)
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier (ConfConstants.FIRST_SQL_SESSION_FACTORY_ID) SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PageHelper firstPageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
