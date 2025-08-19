package br.com.project.portfolio.mock.member.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "br.com.project.portfolio.mock.member.api")
public class MockMembersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockMembersApiApplication.class, args);
	}

}
