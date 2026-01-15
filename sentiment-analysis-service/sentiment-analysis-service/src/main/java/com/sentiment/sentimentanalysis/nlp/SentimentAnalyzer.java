package com.sentiment.sentimentanalysis.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentClass;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class SentimentAnalyzer {
    private final StanfordCoreNLP nlp;
    private final Map<String, Double> sentimentValues = new HashMap<>();

    public SentimentAnalyzer() {
        Properties p = new Properties();
        p.put("annotators", "tokenize, ssplit, parse, sentiment");
        this.nlp = new StanfordCoreNLP(p);

        sentimentValues.put("Very negative", 1d);
        sentimentValues.put("Negative", 2d);
        sentimentValues.put("Neutral", 3d);
        sentimentValues.put("Positive", 4d);
        sentimentValues.put("Very positive", 5d);
    }

    public double analyze(String text) {
        var ann = nlp.process(text);
        var sentences = ann.get(CoreAnnotations.SentencesAnnotation.class);
        return sentences.stream()
                .map(s -> s.get(SentimentCoreAnnotations.SentimentClass.class))
                .mapToDouble(sentimentValues::get)
                .average()
                .orElse(0.0);
    }
}

