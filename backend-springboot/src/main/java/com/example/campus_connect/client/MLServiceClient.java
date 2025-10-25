package com.example.campus_connect.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "ml-service", url = "${ml.service.url}")
public interface MLServiceClient {

    @PostMapping("/predict")
    Map<String, Object> predictPerformance(@RequestBody Map<String, Double> data);
}
