package com.sherlock.spring.ratelimit.data;

import java.util.Date;

public class RateTrackerBean {

	private int requestCount;
	private Date lastRequest;
	private boolean limitExceeded;

	public RateTrackerBean() {
		this(0, new Date());
	}

	public RateTrackerBean(int requestCount, Date lastRequest) {
		super();
		this.requestCount = requestCount;
		this.lastRequest = lastRequest;
		this.limitExceeded = false;
	}

	public void incrementRequestCount() {
		this.requestCount++;
		this.lastRequest = new Date();
	}

	public int getRequestCount() {
		return requestCount;
	}

	public Date getLastRequest() {
		return lastRequest;
	}

	public void setLastRequest(Date lastRequest) {
		this.lastRequest = lastRequest;
	}

	public boolean isLimitExceeded() {
		return limitExceeded;
	}

	public void setLimitExceeded(boolean limitExceeded) {
		this.limitExceeded = limitExceeded;
	}

}
