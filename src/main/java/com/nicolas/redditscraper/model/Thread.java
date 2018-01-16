package com.nicolas.redditscraper.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Thread {
    private String title;
    private Integer upvotes;
    private String permalink;
    private String link;
    private String subreddit;

    public Thread(String title, Integer upvotes, String permalink, String link, String subreddit) {
        this.title = title;
        this.upvotes = upvotes;
        this.permalink = permalink;
        this.link = link;
        this.subreddit = subreddit;
    }
}
