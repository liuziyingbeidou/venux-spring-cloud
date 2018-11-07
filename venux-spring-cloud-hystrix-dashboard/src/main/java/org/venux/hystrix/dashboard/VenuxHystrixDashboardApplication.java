package org.venux.hystrix.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @Title: ZJS 
 * @ClassName: VenuxHystrixDashboardApplication 
 * @Description:  Hystrix Dashboard
 * http://127.0.0.1:9001/hystrix/
 * @author liuzy
 * @date 2018年11月7日 下午3:45:05
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class VenuxHystrixDashboardApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(VenuxHystrixDashboardApplication.class, args);
	}
}