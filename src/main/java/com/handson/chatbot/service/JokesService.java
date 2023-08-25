package com.handson.chatbot.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JokesService {
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Autowired
    ObjectMapper om;
    public String searchJoke(String keyword) throws IOException {
        return getjokeValue(keyword);
    }
    public String getjokeValue(String keyword) throws IOException {


        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/search?query="+keyword)
                .method("GET", null)
                .addHeader("authority", "api.chucknorris.io")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he-IL;q=0.8,he;q=0.7")
                .addHeader("cache-control", "max-age=0")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        String res= response.body().string();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JokesResponse jokes= om.readValue(res,JokesResponse.class);
        //return response.body().string();
        if (jokes.getresult() != null && !jokes.getresult().isEmpty()) {
        JokesResponseObjects jokesResponseObjects= jokes.getresult().get(0);
            return jokes.getresult().get(0).getValue();
        }else {
            return "Not Found!";
        }
    }

    static class JokesResponse {
        //@JsonProperty("data")

       // private List<JokesResponseObjects> data;
        @JsonProperty("result")
        private List<JokesResponseObjects> result;
        private Integer total;

       // public List<JokesResponseObjects> getData() {
          //  return data;
       // }

        public List<JokesResponseObjects> getresult() {
            return result;
        }

        public Integer getTotal() {
            return total;
        }

    }

    private static class JokesResponseObjects {
        @JsonProperty("categories")
        private String [] categories;
        @JsonProperty("created_at")
        private String created_at;
        @JsonProperty("icon_url")
        private String icon_url;
        @JsonProperty("id")
        private String id;
        @JsonProperty("updated_at")
        private String updated_at;
        @JsonProperty("url")
        private String url;
        @JsonProperty("value")
        private String value;

        public String getId() {
            return id;
        }

        public String [] getCategories() {
            return categories;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getUrl() {
            return url;
        }

        public String getValue() {
            return value;
        }
    }

}