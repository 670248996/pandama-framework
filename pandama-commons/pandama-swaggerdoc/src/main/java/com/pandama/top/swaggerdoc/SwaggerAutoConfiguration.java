package com.pandama.top.swaggerdoc;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: Swagger配置类
 * @author: 王强
 * @dateTime: 2022-10-18 13:11:10
 */
@Configuration
@EnableOpenApi
public class SwaggerAutoConfiguration {

    @Bean
    public Docket createRestApi() {
        //swagger3.0使用 OAS_30
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                //此包路径下的类，才生成接口文档
//                .apis(RequestHandlerSelectors.basePackage("com.pandama.top"))
                //加了ApiOperation的类,才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(tokenScheme()))
                .securityContexts(Collections.singletonList(tokenContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("文档标题")
                .description("文档描述")
                .contact(new Contact("作者", "作者地址", "作者邮箱"))
                .version("1.0")
                .build();
    }
    @Bean
    @SuppressWarnings("all")
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

    private HttpAuthenticationScheme tokenScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
    }

    private SecurityContext tokenContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(SecurityReference.builder()
                        .scopes(new AuthorizationScope[0])
                        .reference("Authorization")
                        .build()))
                .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                .build();
    }
}
