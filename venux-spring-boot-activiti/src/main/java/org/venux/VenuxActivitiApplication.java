package org.venux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude={org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class VenuxActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenuxActivitiApplication.class, args);
	}

//	@Bean
//	InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {
//
//		return new InitializingBean() {
//			public void afterPropertiesSet() throws Exception {
//
//				Group group = identityService.newGroup("user");
//				group.setName("users");
//				group.setType("security-role");
//				identityService.saveGroup(group);
//
//				User admin = identityService.newUser("admin");
//				admin.setPassword("admin");
//				identityService.saveUser(admin);
//
//			}
//		};
//	}

}