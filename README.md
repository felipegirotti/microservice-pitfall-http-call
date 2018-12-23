# Pitfalls Microservices (SpringBoot)
Into this repo we can see the common pitfall of microservices architecture, the call others services using HTTP REST API.

## Common issues
- Latency
- Timeouts
    - Connection Timeout
    - Read Timeout
- Pull connections
- Circuit Breaker
- Fallback

Into the controller `DemoController` we have 3 different ways of call others services(internal/external)

1. The method `getData`, doesn't have any configuration, just call directly another service and use the default `RestTemplate`.
    Usually, people forgot to set up the basic, timeouts.    
    The issue happens when the called service was slow, your service probably will down. 

2. The method `getDataSlow` is using the `BaseHttpClient` class, there we can define each configuration about `conenction timeout`, `read timeout` and `max conenctions`.  
    That way your service doesn't  get down even the called service was slow. 
    
3. The method `getDataHystrix` is using circuit breaker, the [Hystrix](https://github.com/Netflix/Hystrix). 
    We defined the timeouts, and the fallback.
   
