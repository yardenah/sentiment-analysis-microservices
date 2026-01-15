package com.sentiment.sentimentanalysis.controller;

import com.sentiment.sentimentanalysis.service.SentimentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class SentimentController {

    private final SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("OK");
    }

    @GetMapping("/sentiment")
    public Flux<String> sentiment(
            @RequestParam(defaultValue = "3") Integer timeWindowSec) {
        return sentimentService.sentiment(timeWindowSec);
    }

}

