package com.sentiment.newsingestion.service;

import com.sentiment.newsingestion.client.NewsApiClient;
import com.sentiment.newsingestion.config.AppKafkaSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NewsIngestionService {

    private static final String TOPIC = "news.sentences";
    private final NewsApiClient newsApiClient;
    private final AppKafkaSender kafkaSender;
    private volatile boolean stopped = false;

    public NewsIngestionService(NewsApiClient newsApiClient,
                                AppKafkaSender kafkaSender) {
        this.newsApiClient = newsApiClient;
        this.kafkaSender = kafkaSender;
    }

    public Mono<Void> ingestNews(String keyword) {
        stopped = false;

        return newsApiClient.fetchNews(keyword)
                .takeWhile(article -> !stopped)
                .doOnNext(article -> kafkaSender.send(article, TOPIC))
                .then();
    }

        public void stopIngestion() {
            this.stopped = true;
        }
}


