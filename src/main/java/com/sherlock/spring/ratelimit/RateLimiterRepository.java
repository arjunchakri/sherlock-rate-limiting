package com.sherlock.spring.ratelimit;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.sherlock.spring.ratelimit.data.RateTrackerBean;

@Component
public class RateLimiterRepository {

	@Value("${sherlock.ratelimiter.seconds}")
	private int rateLimiterSeconds;

	@Value("${sherlock.ratelimiter.requests}")
	private int rateLimiterRequestsCount;

	private LoadingCache<String, RateTrackerBean> rateTracker;
	private Gson gson;

	@PostConstruct
	public void initialize() throws IOException {
		gson = new Gson();
		rateTracker = CacheBuilder.newBuilder()
				.expireAfterWrite(rateLimiterSeconds, TimeUnit.SECONDS)
				.build(new CacheLoader<String, RateTrackerBean>() {
					public RateTrackerBean load(String key) {
						return new RateTrackerBean();
					}
				});
	}

	public RateTrackerBean validate(String tenantId) {
		RateTrackerBean tenantRequest = null;
		try {
			tenantRequest = rateTracker.get(tenantId);
		}
		catch (ExecutionException e) {
			tenantRequest = new RateTrackerBean();
		}
		if (tenantRequest.isLimitExceeded()) {
			return tenantRequest;
		}

		tenantRequest.incrementRequestCount();
		if (tenantRequest.getRequestCount() > rateLimiterRequestsCount) {
			tenantRequest.setLimitExceeded(true);
		}
		rateTracker.put(tenantId, tenantRequest);
		return tenantRequest;
	}

	public String getCurrentState() {
		return gson.toJson(rateTracker);
	}

}
