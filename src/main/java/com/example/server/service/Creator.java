package com.example.server.service;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

public class Creator {
    /** API Key */
    private static final String KEY = "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";
    /** url */
    private static final String URL = "https://api.openai.com/v1/chat/completions";

    public static String askQuestion(String question) throws JsonProcessingException {
        // ObjectMapper to parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        SendText sendText = new SendText();

        sendText.setModel("gpt-3.5-turbo");
        // less temperature -> generated text more reliable
        // higher temperature -> generated text more creativity, less reliable. range: 0.0 - 1.0
        sendText.setTemperature(0.7);
        sendText.setMessages(Collections.singletonList(new SendText.MessagesBean("user", question)));

        // create OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS) // connection time out 10s
                .readTimeout(100, TimeUnit.SECONDS)    // read time out 30s
                .build();

        // create requestBody with Json
        RequestBody requestBody = RequestBody.create(objectMapper.writeValueAsString(sendText),
                MediaType.parse("application/json; charset=utf-8"));

        // create request
        Request request =
                new Request.Builder().url(URL).addHeader("Authorization", "Bearer ".concat(KEY)).post(requestBody).build();

        // send request & handle response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // parse Json to get result
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            String answer = jsonNode.get("choices").get(0).get("message").get("content").asText();

            return answer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No response!";
    }

    public static void main(String[] args) throws JsonProcessingException{
        System.out.println(askQuestion("hello"));
    }
}

