package com.nicolas.redditscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedditScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditScraperApplication.class, args);
	}
}
