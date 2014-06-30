package com.kudrevatykh.integration.bash;

import java.util.TimeZone;

import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@ComponentScan(basePackageClasses=Main.class)
@EnableAutoConfiguration
@EnableIntegration
@ImportResource("classpath:/bash-context.xml")
public class Main {

	public static void main(String... args) {
		ApplicationContext ctx = SpringApplication.run(Main.class, args);
	}
	
	@Bean
	public DateTimeFormatter htmlFormatter() {
		DateTimeFormatterFactory factory = new DateTimeFormatterFactory();
		factory.setPattern("yyyy-MM-dd HH:mm"); 
		factory.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
		return factory.createDateTimeFormatter();
	}

}
