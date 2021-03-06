package com.nicolas.redditscraper.tasks;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class HerokuKeepAliveTask {
    @Scheduled(cron = "0 0,15,30,45 8-18 * * 1-5")
    public void keepAlive() {
        try {
            Unirest.get("https://rddt-scraper.herokuapp.com").asString();
            Unirest.get("https://rddt-scraper-api.herokuapp.com").asString();
            System.out.println("Sending keepAlive request to Heroku at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
