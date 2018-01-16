package com.nicolas.redditscraper.service;

import com.nicolas.redditscraper.model.Thread;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    private String redditEndpoint = "https://www.reddit.com";

    public List<Thread> getHotThreads(List<String> subreddits) throws IOException {
        List<Thread> hotThreads = new ArrayList<>();
        List<Element> hotThreadElements = new ArrayList<>();

        for (String subreddit : subreddits) {
            hotThreadElements.addAll(getHotThreadElements(getThreadElements(subreddit)));
            for (Element hotThreadElement : hotThreadElements) {
                hotThreads.add(new Thread(
                        getThreadTitleFromElement(hotThreadElement),
                        getNumberOfUpvotesFromElement(hotThreadElement),
                        getPermalinkFromElement(hotThreadElement),
                        getThreadLinkFromElement(hotThreadElement),
                        subreddit
                ));
            }
            hotThreadElements.clear();
        }

        return hotThreads;
    }

    private String getThreadTitleFromElement(Element threadElement) {
        return getTitleAndLinkElement(threadElement).text();
    }

    private Element getTitleAndLinkElement(Element threadElement) {
        return threadElement.selectFirst("p.title a.title");
    }

    private int getNumberOfUpvotesFromElement(Element threadElement) {
        String upvotes = threadElement.children().select("div[class=score unvoted]").get(0).attr("title");
        if (upvotes == null || upvotes.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(upvotes);
    }

    private String getPermalinkFromElement(Element threadElement) {
        return redditEndpoint + threadElement.attr("data-permalink");
    }

    private String getThreadLinkFromElement(Element threadElement) {
        return formatThreadLink(getTitleAndLinkElement(threadElement).attr("href"));
    }

    private List<Element> getHotThreadElements(List<Element> threadElements) {
        List<Element> hotThreadElements = new ArrayList<>();
        for (Element threadElement : threadElements) {
            int upvotes = getNumberOfUpvotesFromElement(threadElement);
            if (upvotes > 5000) {
                hotThreadElements.add(threadElement);
            } else {
                break;
            }
        }
        return hotThreadElements;
    }

    private List<Element> getThreadElements(String subreddit) throws IOException {
        Document document = Jsoup.connect(redditEndpoint + "/r/" + subreddit + "/top/?sort=top&t=day&limit=100").get();
        return document.getElementById("siteTable").children().select("div[class*=thing]");
    }

    private String formatThreadLink(String link) {
        if (!link.startsWith("http")) {
            return redditEndpoint + link;
        }
        return link;
    }
}
