package com.sentiment.newsingestion.client;

import com.sentiment.newsingestion.model.NewsStream;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NewsApiClient {

    private final WebClient webClient;

    public NewsApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<String> fetchNews(String keyword) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/everything")
                        .queryParam("q", keyword)
                        .queryParam("pageSize", 100)
                        .queryParam("page", 1)
                        .queryParam("apiKey", "5b38541e2ee7427bb8840ea681ea5ccd")
                        .build())
                .retrieve()
                .bodyToMono(NewsStream.NewsApiResponse.class)
                .flatMapIterable(NewsStream.NewsApiResponse::getArticlesLines)
                .delayElements(Duration.ofMillis(300));
    }
}
