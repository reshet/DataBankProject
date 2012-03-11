package org.zenika.widget.client.util;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * DateLocale implementation for Swiss German.
 * 
 * @author Oliver Grass
 * 
 */
public class DateLocale_de_CH implements DateLocale {

  public int[] DAYS_ORDER = {1, 2, 3, 4, 5, 6, 0};

  public int[] getDAY_ORDER() {
    return DAYS_ORDER;
  }

  public DateTimeFormat getDateTimeFormat() {
    DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
    return format;
  }

}
