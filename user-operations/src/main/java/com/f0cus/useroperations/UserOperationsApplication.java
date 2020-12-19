package com.f0cus.useroperations;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="User Operations",
							  description="Perform CRUD operations on User Entity",	
							  contact=@Contact(name= "Aman Thapliyal", 
							  					email="aman.thapliyal791@gmail.com", 
							  					url="http://www.amanthapliyal.com"
							  					)
								)
					)
public class UserOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserOperationsApplication.class, args);
	}

	@Bean
	public LocaleResolver getLocaleResolver() {
		//SessionLocaleResolver locale = new SessionLocaleResolver();
		AcceptHeaderLocaleResolver locale = new AcceptHeaderLocaleResolver();
		locale.setDefaultLocale(Locale.US);
		return locale;
	}
}

