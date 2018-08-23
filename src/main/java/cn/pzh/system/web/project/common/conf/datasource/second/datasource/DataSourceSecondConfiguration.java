package cn.pzh.system.web.project.common.conf.datasource.second.datasource;

import cn.pzh.system.web.project.common.conf.datasource.second.annotation.DatasourceSecondMapper;
import cn.pzh.system.web.project.common.constant.ConfConstants;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan (basePackages = ConfConstants.MAPPER_SCAN_PACKAGE, annotationClass = DatasourceSecondMapper.class, sqlSessionTemplateRef = ConfConstants.SECOND_SQL_SESSION_ID)
public class DataSourceSecondConfiguration {

    @Bean (name = ConfConstants.SECOND_DATASOURCE_ID)
    @ConfigurationProperties (prefix = ConfConstants.SECOND_DATASOURCE_YML_CONFIGURATION)
    public DataSource testDataSource() {
        return new DruidDataSource();
    }

    @Bean (name = ConfConstants.SECOND_SQL_SESSION_FACTORY_ID)
    public SqlSessionFactory testSqlSessionFactory(@Qualifier (ConfConstants.SECOND_DATASOURCE_ID) DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/cn/pzh/system/web/project/**/dao/second/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean (name = ConfConstants.SECOND_DATASOURCE_MBEAN)
    public DataSourceTransactionManager testTransactionManager(
            @Qualifier (ConfConstants.SECOND_DATASOURCE_ID) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean (name = ConfConstants.SECOND_SQL_SESSION_ID)
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier (ConfConstants.SECOND_SQL_SESSION_FACTORY_ID) SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
