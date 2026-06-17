package com.example.auth_app_backend.chatbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmabdingService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    public List<Double> generateEmbedding(String text) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        String requestBody = """
        {
          "inputs": "%s"
        }
        """.formatted(
                text
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", " ")
        );
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://router.huggingface.co/hf-inference/models/BAAI/bge-small-en-v1.5"
                ))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response =
                client.send(
                        (java.net.http.HttpRequest) request,
                        HttpResponse.BodyHandlers.ofString()
                );

        System.out.println("HF RESPONSE -> " + response.body());
        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "HF API ERROR : " + response.body()
            );
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                response.body(),
                new TypeReference<List<Double>>() {}
        );
    }
}
