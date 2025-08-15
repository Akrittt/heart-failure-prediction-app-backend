package com.health.care.Service;

import com.health.care.Entity.PredictionEntity;
import com.health.care.Entity.modelResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PredictionModelService {

    private final WebClient webClient;

    @Value("${model.api.baseurl}")
    private String modelApiBaseUrl;

    @Autowired
    public PredictionModelService(WebClient.Builder webClientBuilder, @Value("${model.api.baseurl}") String modelApiBaseUrl) {
        this.modelApiBaseUrl = modelApiBaseUrl;
        this.webClient = webClientBuilder.baseUrl(modelApiBaseUrl).build();
    }

    public Mono<modelResponseEntity> getPredictionFromModel(PredictionEntity patientData) {
        String predictEndpoint = "/predict";
        return webClient.post()
                .uri(predictEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patientData) // Send the PatientData object directly as JSON
                .retrieve()
                .bodyToMono(modelResponseEntity.class)
                .onErrorResume(e -> {
                    System.err.println("Error calling your model API: " + e.getMessage());
                    return Mono.error(new RuntimeException("Failed to get prediction from your model.", e));
                });// Expecting ModelApiResponse


    }
}