package org.klotski.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ScanEatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScanEatApplication.class, args);
	}

}
