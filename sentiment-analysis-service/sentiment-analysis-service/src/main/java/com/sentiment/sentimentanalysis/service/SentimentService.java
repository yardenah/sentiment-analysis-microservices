package com.sentiment.sentimentanalysis.service;

import com.sentiment.sentimentanalysis.nlp.SentimentAnalyzer;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;


@Service
public class SentimentService {

    private final SentimentAnalyzer sentimentAnalyzer;

    private final KafkaReceiver<String, String> kafkaReceiver;

    public SentimentService(KafkaReceiver<String, String> kafkaReceiver,
                            SentimentAnalyzer sentimentAnalyzer) {
        this.sentimentAnalyzer = sentimentAnalyzer;
        this.kafkaReceiver = kafkaReceiver;
    }

    public Flux<String> sentiment(Integer timeWindowSec) {
        var flux = kafkaReceiver.receive().map(message -> message.value());

        return flux.map(x-> new TimeAndMessage(DateTime.now(), x))
                .window(Duration.ofSeconds(timeWindowSec))
                .flatMap(window->toArrayList(window))
                .map(items->{
                    if (items.size() > 10) return "size:" + items.size() + "<br>";
                    System.out.println("size:" + items.size());
                    double avg = items.stream().map(x-> sentimentAnalyzer.analyze(x.message))
                            .mapToDouble(y->y).average().orElse(0.0);
                    if (items.size() == 0) return "EMPTY<br>";
                    return   items.size() + " messages, sentiment = " + avg +  "<br>";

                });
    }

    public static class SentimentRequest {
        private String text;

        public SentimentRequest() {}

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    static class TimeAndMessage {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss, z");
        DateTime curTime;
        String message;

        public TimeAndMessage(DateTime curTime, String message) {
            this.curTime = curTime;
            this.message = message;
        }

        @Override
        public String toString() {
            return "TimeAndMessage{" +
                    "formatter=" + formatter +
                    ", curTime=" + curTime +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public static <T> Mono<ArrayList<T>> toArrayList(Flux<T> source) {
        return  source.reduce(new ArrayList(), (a, b) -> { a.add(b);return a; });
    }

}
