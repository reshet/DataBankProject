package org.zenika.widget.client.datePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zenika.widget.client.util.DateUtil;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Main class of the DatePicker. It extends the TextBox widget and manages a
 * Date object. When it is clicked, it opens a PopupCalendar on which we can
 * select a new date. <br>
 * Example of use : <br>
 * <code>
 * DatePicker datePicker = new DatePicker();<br>
 * RootPanel.get().add(datePicker);<br>
 * </code> You can specify a theme (see the CSS file DatePickerStyle.css) and
 * the date to initialize the date picker. Enjoy xD
 * 
 * @author Zenika
 * @author Loïc Bertholet - Zenika
 * @author James Heggs (jheggs@axonbirch.com)
 * 
 */
public class DatePicker extends TextBox implements ClickHandler, ChangeHandler,
		KeyPressHandler {

	private PopupCalendar popup;
	private Date selectedDate;
	// the oldest date that can be selected
	private Date oldestDate;
	// the youngest date that can be selected
	private Date youngestDate;
	private DateTimeFormat dateFormatter;

	private List<ChangeHandler> changeHandlers;

	{
		dateFormatter = DateUtil.getDateTimeFormat();
		popup = new PopupCalendar(this);
		changeHandlers = new ArrayList<ChangeHandler>();
	}

	/**
	 * Default constructor. It creates a DatePicker which shows the current
	 * month.
	 */
	public DatePicker() {
		super();
		setText("");
		// sinkEvents(Event.ONCHANGE | Event.ONKEYPRESS);
		addClickHandler(this);
		addChangeHandler(this);
		addKeyPressHandler(this);
	}

	/**
	 * Create a DatePicker which show a specific Date.
	 * 
	 * @param selectedDate
	 *            Date to show
	 */
	public DatePicker(Date selectedDate) {
		this();
		this.selectedDate = selectedDate;
		synchronizeFromDate();
	}

	/**
	 * Create a DatePicker which uses a specific theme.
	 * 
	 * @param theme
	 *            Theme name
	 */
	public DatePicker(String theme) {
		this();
		setTheme(theme);
	}

	/**
	 * Create a DatePicker which specifics date and theme.
	 * 
	 * @param selectedDate
	 *            Date to show
	 * @param theme
	 *            Theme name
	 */
	public DatePicker(Date selectedDate, String theme) {
		this(selectedDate);
		setTheme(theme);
	}

	/**
	 * Return the Date contained in the DatePicker.
	 * 
	 * @return The Date
	 */
	public Date getSelectedDate() {
		return selectedDate;
	}

	/**
	 * Set the Date of the datePicker and synchronize it with the display.
	 * 
	 * @param value
	 */
	public void setSelectedDate(Date value) {
		this.selectedDate = value;

		synchronizeFromDate();

		fireChange();
	}

	/**
	 * Return the theme name.
	 * 
	 * @return Theme name
	 */
	public String getTheme() {
		return popup.getTheme();
	}

	/**
	 * Set the theme name.
	 * 
	 * @param theme
	 *            Theme name
	 */
	public void setTheme(String theme) {
		popup.setTheme(theme);
	}

	/**
	 * @see com.google.gwt.user.client.ui.TextBoxBase#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event) {
		switch (DOM.eventGetType(event)) {
		case Event.ONBLUR:
			popup.hidePopupCalendar();
			break;
		default:
			break;

		}
		super.onBrowserEvent(event);
	}

	/**
	 * @see ClickHandler#onClick(ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		showPopup();
	}

	/**
	 * @see ChangeHandler#onChange(ChangeEvent)
	 */
	public void onChange(ChangeEvent event) {
		parseDate();
	}

	/**
	 * @see KeyPressHandler#onKeyPress(KeyPressEvent)
	 */
	public void onKeyPress(KeyPressEvent event) {
		int keyCode = event.getNativeEvent().getKeyCode();
		switch (keyCode) {
		case KeyCodes.KEY_ENTER:
			parseDate();
			showPopup();
			break;
		case KeyCodes.KEY_ESCAPE:
			if (popup.isVisible())
				popup.hidePopupCalendar();
			break;
		default:
			break;
		}

	}

	/**
	 * Display the date in the DatePicker.
	 */
	public void synchronizeFromDate() {
		if (this.selectedDate != null) {
			this.setText(dateFormatter.format(this.selectedDate));
		} else {
			this.setText("");
		}
	}

	/**
	 * Display the PopupCalendar.
	 */
	private void showPopup() {
		if (this.selectedDate != null) {
			popup.setDisplayedMonth(this.selectedDate);
		}
		popup.setPopupPosition(this.getAbsoluteLeft() + 150, this
				.getAbsoluteTop());
		popup.displayMonth();
		doAfterShowPopup(popup);
	}

	/**
	 * Call when the calendar pop-up is shown. Can be overwritten.
	 * 
	 * @param popup
	 */
	protected void doAfterShowPopup(PopupCalendar popup) {
		// nothing special to do

		/*
		 * popup position can be updated so that it is displayed "above" the
		 * TextBox
		 */
		// popup.setPopupPosition(popup.getPopupLeft(), popup.getPopupTop() -
		// 150);

		/*
		 * there is also a known bug if the DatePicker is in a modal MyGWT
		 * Dialog - it may be deprecated
		 */
		// DOM.setIntStyleAttribute(popup.getElement(), "zIndex",
		// MyDOM.getZIndex());
		// Element p = DOM.getParent(popup.getElement());
		// int index = DOM.getChildIndex(p, popup.getElement());
		// p = DOM.getChild(p, --index);
		// DOM.setIntStyleAttribute(p, "zIndex", MyDOM.getZIndex() - 2);
	}

	/**
	 * Parse the date entered in the DatePicker.
	 */
	private void parseDate() {
		if (getText() == null || getText().length() == 0) {
			selectedDate = null;
		} else {
			try {
				Date parsedDate = dateFormatter.parse(getText());
				if (canBeSelected(parsedDate))
					selectedDate = parsedDate;
			} catch (IllegalArgumentException e) {
				// Do something ?
			}
		}
		synchronizeFromDate();
	}

	/**
	 * Return true if the selectedDay is between datepicker's interval dates.
	 * 
	 * @param selectedDay
	 * @return boolean
	 */
	public boolean canBeSelected(Date selectedDay) {
		if (this.getOldestDate() != null
				&& selectedDay.after(this.getOldestDate()))
			return false;

		if (this.getYoungestDate() != null
				&& !DateUtil.addDays(selectedDay, 1).after(
						this.getYoungestDate()))
			return false;

		return true;
	}

	public Date getOldestDate() {
		return oldestDate;
	}

	public void setOldestDate(Date oldestDate) {
		this.oldestDate = oldestDate;
	}

	public Date getYoungestDate() {
		return youngestDate;
	}

	public void setYoungestDate(Date youngestDate) {
		this.youngestDate = youngestDate;
	}

	/**
	 * @see com.google.gwt.user.client.ui.TextBoxBase#addChangeHandler(ChangeHandler)
	 */
	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		HandlerRegistration handlerRegistration = super
				.addChangeHandler(handler);
		if (changeHandlers == null) {
			changeHandlers = new ArrayList<ChangeHandler>();
		}
		changeHandlers.add(handler);
		return handlerRegistration;
	}

	/**
	 * Warns {@link ChangeHandler} from {@link #changeHandlers} that a change
	 * happened without giving a {@link ChangeEvent}.
	 */
	protected void fireChange() {
		for (ChangeHandler changeHandler : changeHandlers) {
			changeHandler.onChange(null);
		}
	}

}
