package com.rekrutacja.SecondService.clients;

import com.rekrutacja.SecondService.Models.JsonModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "json-service",url = "${application.config.json-url}")
public interface firstServiceClient {

    @GetMapping("/generate/json/1")
    List<String> fetchJsonsFromFirstService();


}
