package com.sentiment.newsingestion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsStream {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NewsApiResponse {

        @JsonProperty("status")
        private String status;

        @JsonProperty("totalResults")
        private int totalResults;

        @JsonProperty("articles")
        private List<NewsArticle> articles;

        public String getStatus() {
            return status;
        }

        public List<String> getArticlesLines() {
            List<String> lines = new ArrayList<>();
            for (NewsArticle article : getArticles()) {
                lines.add(article.getTitle());
                lines.addAll(Arrays.asList(article.getContent().split("\n")));
            }
            return lines;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public List<NewsArticle> getArticles() {
            return articles;
        }

        public void setArticles(List<NewsArticle> articles) {
            this.articles = articles;
        }

        public NewsApiResponse(String status, int totalResults, List<NewsArticle> articles) {
            this.status = status;
            this.totalResults = totalResults;
            this.articles = articles;
        }

        public NewsApiResponse() {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NewsArticle {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @JsonProperty("url")
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public NewsArticle(String title, String description, String url) {
            this.title = title;
            this.description = description;
            this.url = url;
        }

        public NewsArticle() {
        }
    }
}


