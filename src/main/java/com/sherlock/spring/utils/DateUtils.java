package com.sherlock.spring.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private DateUtils() {
	}

	private static final String INTERNAL_STANDARD_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	public static String getCurrentDateAsString() {
		return parseDate(Calendar.getInstance()
				.getTime());
	}

	public static String parseDate(Date date) {
		return new SimpleDateFormat(INTERNAL_STANDARD_DATE_FORMAT).format(date);
	}

}
