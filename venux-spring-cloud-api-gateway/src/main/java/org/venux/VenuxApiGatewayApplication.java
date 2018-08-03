package org.venux;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.venux.filter.VenuxBaseAccessFilter;

@EnableZuulProxy
@SpringCloudApplication
@EnableDiscoveryClient
@EnableHystrix
public class VenuxApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenuxApiGatewayApplication.class, args);
	}
	
	@Bean
	public VenuxBaseAccessFilter venuxBaseOpsAccessFilter() {
		return new VenuxBaseAccessFilter();
	}
	
}
