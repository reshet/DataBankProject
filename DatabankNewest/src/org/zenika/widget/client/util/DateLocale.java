package org.zenika.widget.client.util;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.Localizable;

/**
 * This interface is used to extend the localization property
 * of the DateTimeFormat of GWT. It adds the support of the days order 
 * which is not the same in all countries. 
 * It uses the Localizable interface. See GWT documentation for more detail.
 * @author Zenika
 *
 */
public interface DateLocale extends Localizable{
	
	/**
	 * Returns an array containing the order of days in the week.
	 * For a week starting by the monday, it'll return {1,2,3,4,5,6,0}.
	 * @return Array of days index
	 */
	public int[] getDAY_ORDER();
	
	/**
	 * Return the DateTimeFormat corresponding to the date pattern used in
	 * the country. For example : "dd/MM/yyyy" in France and "MM/dd/yyyy" in the US.
	 * @return DateTimeFormat
	 */
	public DateTimeFormat getDateTimeFormat();
	
}
