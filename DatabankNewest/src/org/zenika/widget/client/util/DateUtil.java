package org.zenika.widget.client.util;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Utility class which ease the use of Date classes with the DatePicker.
 * 
 * @author Zenika
 * 
 */
public class DateUtil {

	/**
	 * Add days to the Date object.
	 * 
	 * @param date
	 *            The Date to modify
	 * @param days
	 *            Number of day to add
	 * @return The modified Date object
	 */
	public static Date addDays(Date date, int days) {
		return new Date(date.getYear(), date.getMonth(), date.getDate() + days);
	}

	/**
	 * Add months to the Date object.
	 * 
	 * @param date
	 *            The Date to modify
	 * @param months
	 *            Number of month to add
	 * @return The modified Date object
	 */
	public static Date addMonths(Date date, int months) {
		return new Date(date.getYear(), date.getMonth() + months, date
				.getDate());
	}

	/**
	 * Test if two Date objects represent the same day. It tests if the days,
	 * the months and the years are equals.
	 * 
	 * @param date1
	 *            First Date
	 * @param date2
	 *            Second Date
	 * @return true if the days are the same
	 */
	public static boolean areEquals(Date date1, Date date2) {
		return date1.getDate() == date2.getDate()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getYear() == date2.getYear();
	}

	/**
	 * Return a Date object with represents the first day of a month contained
	 * in another Date object.
	 * 
	 * @param date The Date containing the month
	 * @return The first day of the month
	 */
	public static Date getMonthFirstDay(Date date) {
		Date current = date;
		while (current.getDate() != 1) {
			current = new Date(current.getYear(), current.getMonth(), current
					.getDate() - 1);
		}
		return current;
	}

	/**
	 * Returns the place of the day in the week.
	 * Example : sunday = 0, monday = 1 ....
	 * Depends on the locale. 
	 * @param day The day
	 * @return The place of the day
	 */
	public static int getWeekDayIndex(Date day) {
		DateLocale locale = (DateLocale) GWT.create(DateLocale.class);
		int[] daysOrder = locale.getDAY_ORDER();
		int dayIndex = day.getDay();
		for (int i = 0; i < 7; i++) {
			if (dayIndex == daysOrder[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the first day of the current week.
	 * @return Date pointing to the first day 
	 */
	public static Date getWeekFirstDay() {
		return getWeekFirstDay(new Date());
	}

	/**
	 * Returns the first day of the week containing a Date object.
	 * @param date The Date
	 * @return The Date pointing to the first day
	 */
	public static Date getWeekFirstDay(Date date) {
		Date current = date;
		DateLocale local = (DateLocale) GWT.create(DateLocale.class);
		int firstDay = local.getDAY_ORDER()[0];
		while (current.getDay() != firstDay) {
			current = new Date(current.getYear(), current.getMonth(), current
					.getDate() - 1);
		}
		return current;
	}

	/**
	 * Test if a day is a weekend day.
	 * @param day The Date to test
	 * @return true if the Date is a weekend day
	 */
	public static boolean isInWeekEnd(Date day) {
		int dayIndex = day.getDay();
		return (dayIndex == 0 | dayIndex == 6) ? true : false;
	}

	/**
	 * Get the DateTimeFormat corresponding to the locale.
	 * @return DateTimeFormat
	 */
	public static DateTimeFormat getDateTimeFormat() {
		DateLocale locale = (DateLocale) GWT.create(DateLocale.class);
		DateTimeFormat format = locale.getDateTimeFormat();
		return format;
	}
}
