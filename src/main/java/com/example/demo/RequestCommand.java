package com.example.demo;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

public class RequestCommand extends HystrixCommand<Map> {

    RestOperations restOperations;

    RequestCommand(Setter config, RestOperations restOperations) {
        super(config);
        this.restOperations = restOperations;
    }

    @Override
    protected Map run() throws Exception {
        return restOperations.getForObject("http://localhost:8999/rest/2", Map.class);
    }

    @Override
    protected Map getFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("error", "please try again more later");

        return response;
    }
}
