package com.sherlock.spring.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sherlock.spring.ratelimit.RateLimiterRepository;
import com.sherlock.spring.ratelimit.data.RateTrackerBean;
import com.sherlock.spring.utils.DateUtils;

@RestController
public class RateLimitControllers {

	@Autowired
	RateLimiterRepository rateLimitValidator;

	private StringBuilder requests = new StringBuilder();

	@RequestMapping(value = "/testapi", method = RequestMethod.GET)
	public String testapi(@RequestParam(value = "tenant") String tenant, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// validation
		RateTrackerBean validationResponse = rateLimitValidator.validate(tenant);
		if (validationResponse.isLimitExceeded()) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

			return HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase() + ", last request at "
					+ DateUtils.parseDate(validationResponse.getLastRequest());
		}

		String previousRequests = requests.toString();

		String currentDateAsString = DateUtils.getCurrentDateAsString();
		requests.append(" || ");
		requests.append(tenant);
		requests.append(" : ");
		requests.append(currentDateAsString);

		return "OK. Current request at " + currentDateAsString + ", Previous requests were " + previousRequests;

	}

	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public String state() throws Exception {
		return rateLimitValidator.getCurrentState();
	}

}
