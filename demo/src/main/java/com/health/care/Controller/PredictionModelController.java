package com.health.care.Controller;

import com.health.care.Entity.PredictionEntity;
import com.health.care.Entity.modelResponseEntity;
import com.health.care.Service.PredictionModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("http://localhost:5173/")
@RequestMapping("/api/predict")
public class PredictionModelController {

    private final PredictionModelService PredictionModelService;

    @Autowired
    public PredictionModelController(PredictionModelService PredictionModelService) {
        this.PredictionModelService = PredictionModelService;
    }

    @PostMapping
    public Mono<ResponseEntity<modelResponseEntity>> getPrediction(@RequestBody PredictionEntity patientData) {
        System.out.println("Received patient data: " + patientData.toString());

        return PredictionModelService.getPredictionFromModel(patientData)
                .map(modelResponseEntity -> {
                    // Abnormal case handling
                    if (modelResponseEntity.getProbability() > 0.70) { // High risk threshold
                        modelResponseEntity.setVerdict("High Risk: Immediate medical attention recommended.");
                    } else if (modelResponseEntity.getProbability() < 0.15) { // Very low risk
                        modelResponseEntity.setVerdict("Low Risk: Unlikely to be readmitted soon.");
                    } else {
                        modelResponseEntity.setVerdict("Moderate Risk: Monitor patient's condition closely.");
                    }
                    return ResponseEntity.ok(modelResponseEntity);
                }) // Return the full response object
                .onErrorResume(e -> {
                    System.err.println("Error processing prediction request: " + e.getMessage());

                    //  returning a generic 500 with a message.
                    modelResponseEntity errorResponse = new modelResponseEntity();
                    errorResponse.setMessage("Error: " + e.getMessage());
                    errorResponse.setPrediction(-1);
                    errorResponse.setProbability(0.0);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }

}
