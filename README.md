# sherlock-rate-limiting

## About

This app is a POC for rate limiting REST APIs, based on configured inputs.

* `sherlock.ratelimiter.requests`: To specify the number of requests to the API.

* `sherlock.ratelimiter.seconds`: To specify the time interval - in seconds.

If you have specified `sherlock.ratelimiter.seconds` as 10 and `sherlock.ratelimiter.requests` as 2, the framework would limit the 2 API calls for a 10 second interval time. 

### Important Classes:
```
RateLimiterRepository - Spring component class, that would be responsible for loading config and keeping the track of api calls per client.
RateLimitControllers - Spring Rest Controller class, that would have this validator enabled for "/testapi?tenant=" path.
RateTrackerBean - Skelton class, that be used to store the track the rate limits.
```

## Deployment

* The app is deployed on Heroku Cloud here:* https://sherlock-ratelimiting.herokuapp.com/

**API for which rate limit is enabled:**  https://sherlock-ratelimiting.herokuapp.com/testapi?tenant=abc

**Configurations:** sherlock.ratelimiter.requests=2, sherlock.ratelimiter.requests=10 ( here [application.properties](src/main/resources/application.properties) )

**Check state of the Rate limiter:** https://sherlock-ratelimiting.herokuapp.com/state

**Exposed APIs (springdoc):** https://sherlock-ratelimiting.herokuapp.com/swagger-ui.html
