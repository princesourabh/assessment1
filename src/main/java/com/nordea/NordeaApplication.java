package com.nordea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NordeaApplication {

	private static Logger LOG = LoggerFactory.getLogger(NordeaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NordeaApplication.class, args);
		LOG.info("Nordea Application Exit");
	}

}
