package cn.pzh.system.web.project.common.conf.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 配置redis数据源 Java Lib For OIS, Powered By xiaour.
 *
 * @author Zhang.Tao
 * @version V2.0.0
 * @ClassName RedisConfig
 * @Date 2017年4月24日 下午5:25:30
 */

@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
