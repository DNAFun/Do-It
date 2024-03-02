package com.xml.common.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.commons.lang3.RandomUtils;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Objects;

/**
 * Swagger配置项
 *
 * @author XMINGL
 * @since 1.0.0
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (Objects.nonNull(openApi.getTags())) {
                openApi.getTags().forEach(tag -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("x-order", RandomUtils.nextInt(0, 100));
                    tag.setExtensions(map);
                });
            }
            if (Objects.nonNull(openApi.getPaths())) {
                openApi.getPaths().addExtension("x-add", RandomUtils.nextInt(0, 100));
            }
        };
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("DO-It 待办事项系统")
                        .version("1.0.0")
                        .description("因为市面上的TO-DO工具都感觉缺点意思，所以打算自己做一个，希望能有空闲时间将这个项目做完！"));
    }
}
