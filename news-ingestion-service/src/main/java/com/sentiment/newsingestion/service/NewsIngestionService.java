package com.sentiment.newsingestion.service;

import com.sentiment.newsingestion.client.NewsApiClient;
import com.sentiment.newsingestion.config.AppKafkaSender;
import com.sentiment.newsingestion.config.KafkaProducerConfig;
import com.sentiment.newsingestion.model.NewsStream;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.Duration;
import java.util.UUID;

@Service
public class NewsIngestionService {

    private static final String TOPIC = "news.sentences";

    private final NewsApiClient newsApiClient;

    @Autowired
    AppKafkaSender kafkaSender;

    public NewsIngestionService(NewsApiClient newsApiClient,
                                AppKafkaSender kafkaSender) {
        this.newsApiClient = newsApiClient;
        this.kafkaSender = kafkaSender;
    }

    public Mono<Void> ingestNews(String keyword) {
        return newsApiClient.fetchNews(keyword).map((x) -> kafkaSender.send(x, TOPIC)).then();
    }
}


