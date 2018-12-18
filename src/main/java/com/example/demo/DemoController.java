package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class DemoController {

    RestOperations restOperations = new RestTemplate();


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getData(@PathVariable long id) {
        return restOperations.getForObject("http://localhost:8999/rest/" + id, Map.class);
    }

}
