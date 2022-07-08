package org.hgc.cityRepository.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
    Spring Boot配置druid数据源参考：https://blog.csdn.net/weixin_44943959/article/details/116278903
 */
@Configuration
@RefreshScope
public class DataSourceConfig {

//    @ConfigurationProperties("spring.datasource")
//    @Bean
//    DataSource dataSource() {
//        return new DruidDataSource();
//    }

    /**Druid 监视器配置===>访问http://localhost:8080/druid登录即可开始监控
     * Druid的servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123");
        initParams.put("allow","127.0.0.1");
        bean.setInitParameters(initParams);
        bean.setUrlMappings(Arrays.asList("/druid/*"));
        return bean;
    }
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>(new WebStatFilter());
        HashMap<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "/css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
