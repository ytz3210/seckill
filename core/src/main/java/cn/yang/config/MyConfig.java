package cn.yang.config;

import cn.yang.interceptor.MybatisInterceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Connection;

//@Configuration
//public class MyConfig implements WebMvcConfigurer {
//    @Bean
//    public SqlSessionFactory getSqlSessionFactory() throws Exception {
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBean().getObject();
//        sqlSessionFactory.getConfiguration().addInterceptor(new MybatisInterceptor());
//        return sqlSessionFactory;
//    }
//}
