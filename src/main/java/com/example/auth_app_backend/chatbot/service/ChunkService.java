package com.example.auth_app_backend.chatbot.service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    public List<String> chunkText(String text) {
        int chunkSize = 600;
        int overlapSize = 50;
        List<String> chunks = new ArrayList<>();

        for(int start = 0; start < text.length(); start += (chunkSize - overlapSize)) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));
        }
        int numChunks = (int) Math.ceil((double) text.length() / chunkSize);
        return chunks;
    }
}
