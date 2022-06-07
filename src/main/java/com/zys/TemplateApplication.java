package com.zys;

import com.alibaba.druid.support.http.StatViewServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
//                                ElasticsearchDataAutoConfiguration.class,
//                                KafkaAutoConfiguration.class,
//                                RedisAutoConfiguration.class}) //忽略数据库链接启动
@MapperScan("com.zys.dao") //开始mapper扫描就不用再每一个dao上加@mapper注解了
@EnableAsync //开启异步支持
@EnableScheduling //开启对定时任务的支持
@SpringBootApplication
public class TemplateApplication {

    //springboot启动默认扫描主程序（启动类）同级以及子级包，
    // 所以不用配置包扫描路径，
    // 如果例如controller类所在包和主程序不在同一级目录、子目录下，
    // 那么启动就扫描不到controller层
    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }

    /**
     * druid 监控页面配置账户密码
     * http://127.0.0.1:8080/druid/sql.html
     * 此配置项也可在配置文件中配置
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("allow", "127.0.0.1");// IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("deny", "");// IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("loginUsername", "root");
        registrationBean.addInitParameter("loginPassword", "1234");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }
}
