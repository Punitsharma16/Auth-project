package com.example.auth_app_backend.chatbot.service;

import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class PdfService {

    public String extractText(MultipartFile file) {
        try (PDDocument pdDocument = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(pdDocument);

        } catch (Exception e) {
            throw new RuntimeException("Failed to Read Pdf");
        }
    }

    public String websiteText(String websiteUrl) {
        System.out.println("Inside Rag Method");
        WebClient webClient = WebClient.create();
        String webHtml = webClient
                .get()
                .uri(websiteUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return Jsoup.parse(webHtml).text();
    }
}
