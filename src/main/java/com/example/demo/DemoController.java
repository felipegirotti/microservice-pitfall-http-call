package com.example.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RestOperations client;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getData(@PathVariable long id) {
        return restOperations.getForObject("http://localhost:8999/rest/" + id, Map.class);
    }

    @GetMapping(value = "/slow", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getDataSlow() {
        return  client.getForObject("http://localhost:8999/rest/2",  Map.class);
    }

    @GetMapping(value = "/circuit-breaker", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getDataHystrix() {

        HystrixCommand.Setter config = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("slowService"));
        // timeout and fallback
        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter();
        commandProperties.withExecutionTimeoutInMilliseconds(2000);
        commandProperties.withCircuitBreakerRequestVolumeThreshold(1);
        commandProperties.withFallbackEnabled(true);
        config.andCommandPropertiesDefaults(commandProperties);

        return new RequestCommand(config, restOperations).execute();
    }
}
