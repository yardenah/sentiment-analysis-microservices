package com.sentiment.newsingestion.client;

import com.sentiment.newsingestion.model.NewsStream;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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
                .flatMapIterable(NewsStream.NewsApiResponse::getArticlesLines);
    }
}
