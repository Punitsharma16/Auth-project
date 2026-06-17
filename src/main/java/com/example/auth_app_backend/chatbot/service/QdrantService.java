package com.example.auth_app_backend.chatbot.service;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static io.qdrant.client.PointIdFactory.id;
import static io.qdrant.client.ValueFactory.value;

@Service
@RequiredArgsConstructor
public class QdrantService {

    private final QdrantClient qdrantClient;

    public void saveVector(
            String collectionName,
            String chunk,
            String botId,
            String fileName,
            List<Double> embedding
    ) throws Exception {

        List<Float> vector = embedding.stream()
                .map(Double::floatValue)
                .toList();

        Points.PointStruct point = Points.PointStruct.newBuilder()
                .setId(id(Math.abs(new Random().nextLong())))
                .setVectors(
                        Points.Vectors.newBuilder()
                                .setVector(
                                        Points.Vector.newBuilder()
                                                .addAllData(vector)
                                                .build()
                                )
                                .build()
                )
                .putPayload("text", value(chunk))
                .putPayload("botId", value(botId))
                .putPayload("fileName", value(fileName))
                .build();

        qdrantClient.upsertAsync(collectionName, List.of(point)).get();
    }


    public List<Points.ScoredPoint> search(
            String collectionName,
            List<Double> embedding,
            String botId
    ) throws Exception {

        List<Float> vector = embedding.stream()
                .map(Double::floatValue)
                .toList();

        Points.Filter filter = Points.Filter.newBuilder()
                .addMust(
                        Points.Condition.newBuilder()
                                .setField(
                                        Points.FieldCondition.newBuilder()
                                                .setKey("botId")
                                                .setMatch(
                                                        Points.Match.newBuilder()
                                                                .setKeyword(botId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        return qdrantClient.searchAsync(
                Points.SearchPoints.newBuilder()
                        .setCollectionName(collectionName)
                        .addAllVector(vector)
                        .setLimit(3)
                        .setFilter(filter)
                        .setWithPayload(
                                Points.WithPayloadSelector.newBuilder()
                                        .setEnable(true)
                                        .build()
                        )
                        .build()
        ).get();
    }
}
