package com.sentiment.newsingestion.controller;

import com.sentiment.newsingestion.client.NewsApiClient;
import com.sentiment.newsingestion.service.NewsIngestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NewsIngestionController {

    private final NewsIngestionService service;

    public NewsIngestionController(NewsIngestionService service) {
        this.service = service;
    }

    @GetMapping("/api/news/ingest")
    public Mono<Void> ingest(@RequestParam String keyword) {
        return service.ingestNews(keyword);
    }
}

