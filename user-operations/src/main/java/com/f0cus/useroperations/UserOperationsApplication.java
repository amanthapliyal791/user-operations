package com.f0cus.useroperations;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@SpringBootApplication
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
