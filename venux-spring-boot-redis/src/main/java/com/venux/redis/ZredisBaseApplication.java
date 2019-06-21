package com.venux.redis;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

import com.google.common.base.Predicate;
import com.venux.redis.filter.SecurityFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableHystrix
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages="com.venux")
@EnableDiscoveryClient
@EnableRetry
public class ZredisBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZredisBaseApplication.class, args);
    }
    
	@Bean
	public Docket basedocApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("zredis base service")
				.apiInfo(apiInfo())
				.select()
				.paths(basedocPaths())
				.build();

	}
	
	@Bean
    public FilterRegistrationBean securityFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SecurityFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("基础数据/权限/缓存/分布式锁服务API")
				.description("基础数据/权限/缓存/分布式锁服务API")
				.build();
	}
	@SuppressWarnings("unchecked")
	private Predicate<String> basedocPaths() {
		return or(regex("/zredis/base.*"),regex("/zredis/authority.*"),regex("/zredis/distlock.*"),regex("/zredis/cache.*"),regex("/zredis/pubsub.*"));
	}

}