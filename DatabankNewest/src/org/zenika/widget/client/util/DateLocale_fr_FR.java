package org.zenika.widget.client.util;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * DateLocale implementation for France.
 * @author Loïc Bertholet - Zenika
 *
 */
public class DateLocale_fr_FR implements DateLocale {
	
	public int[] DAYS_ORDER = { 1, 2, 3, 4, 5, 6, 0 };

	public int[] getDAY_ORDER() {
		return DAYS_ORDER;
	}
	
	public DateTimeFormat getDateTimeFormat() {
		DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
		return format;
	}
	
}
