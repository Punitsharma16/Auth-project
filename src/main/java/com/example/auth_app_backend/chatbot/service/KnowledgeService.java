package com.example.auth_app_backend.chatbot.service;

import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

    @Autowired
    VectorStore vectorStore;

    @Autowired
    EmabdingService embeddingService;

    @Autowired
    QdrantService qdrantService;

    public void saveKnowledge(String botId, String content) {
        Document doc = new Document(content);
        doc.getMetadata().put("botId", botId);
        vectorStore.add(List.of(doc));
    }

//    public List<Document> search(String botId, String query) {
//        return vectorStore.similaritySearch(
//                SearchRequest.builder()
//                        .query(query)
//                        .topK(3)
//                        .filterExpression("botId == '" + botId + "'")
//                        .build()
//        );
//    }



    public List<String> search(String botId, String query) {

        try {
            List<Double> embedding = embeddingService.generateEmbedding(query);
            List<Points.ScoredPoint> results = qdrantService.search(
                            "chatbot-collection",
                            embedding,
                            botId
                    );

            return results.stream()
                    .map(point ->
                            point.getPayloadMap()
                                    .get("text")
                                    .getStringValue()
                    )
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveChunks(
            String botId,
            List<String> chunks,
            String fileName
    ) {
        chunks.forEach(chunk -> {
            try {
                List<Double> embedding = embeddingService.generateEmbedding(chunk);
                System.out.println("Generated embedding for chunk: " + embedding);
                qdrantService.saveVector(
                        "chatbot-collection",
                        chunk,
                        botId,
                        fileName,
                        embedding
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
