package com.xml.doit.common.core.configuration;

import com.xml.doit.common.core.context.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系統基礎管理配置類
 *
 * @author XMINGL
 * @since 1.0.0
 */
@Configuration
public class DoItConfiguration {
    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
