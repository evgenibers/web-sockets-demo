package net.evgenibers.wsd.utils;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date utilities.
 *
 * @author Evgeni Bokhanov
 */
@Log4j2
public class DateUtils {
	public static final String DEFAULT_DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss.SSS";
	public static final String DEFAULT_DATE_TIME_ERROR_STRING = "Wrong date-time pattern!";

	/**
	 * Prevent instantiation.
	 */
	private DateUtils() {
	}

	/**
	 * Get current date and time as string using default template.
	 *
	 * @return Date string
	 */
	public static String currentDateAndTime() {
		return currentDateAndTime(DEFAULT_DATE_TIME_PATTERN);
	}

	/**
	 * Ger current date and time as string.
	 *
	 * @param pattern Date-time pattern
	 * @return Date string
	 */
	public static String currentDateAndTime(String pattern) {
		try {
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
		} catch (Exception e) {
			log.error("currentDateAndTime", e);
			return DEFAULT_DATE_TIME_ERROR_STRING;
		}
	}
}
