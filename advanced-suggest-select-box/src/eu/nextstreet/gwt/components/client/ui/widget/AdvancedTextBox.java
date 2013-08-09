/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.nextstreet.gwt.components.client.ui.widget;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.common.event.UIHandler;
import eu.nextstreet.gwt.components.shared.ValidationException;
import eu.nextstreet.gwt.components.shared.Validator;

/**
 * A text box that handles additional events like the double click and supports
 * the default text feature
 * 
 * NOTE: You should use {@link #getTextValue()} instead of {@link #getText()} to
 * avoid getting the default text instead of an empty one.
 * 
 * <BR/>
 * This class has the following styles:
 * <ul>
 * <li><b>eu-nextstreet-AdvancedTextBoxDefaultText</b> defines the style of the
 * default text</li>
 * </ul>
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 */
public class AdvancedTextBox extends TextBox implements HasDoubleClickHandlers {
	private static final String DEFAULT_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxDefaultText";
	private static final String MANDATORY_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxMandatoryText";
	private static final String ERROR_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxErrorText";
	private static final String READ_ONLY_TEXT_STYLE = "eu-nextstreet-AdvancedTextBoxReadOnlyText";
	protected Validator<String> validator;
	protected String defaultText;
	protected String defaultTextStyle;
	protected String errorTextStyle;
	protected String mandatoryTextStyle;
	protected String readOnlyTextStyle;
	protected boolean mandatory;
	protected Widget representer;
	protected boolean focused;
	protected UIHandler uiHandler = new UIHandler() {

		@Override
		public void removeStyleName(String style) {
		}

		@Override
		public void removeError() {
			setTitle("");
		}

		@Override
		public void handleTextStyles() {
		}

		@Override
		public void handleError(ValidationException error) {
			setTitle(error.getMessage());
		}

		@Override
		public void handleDefaultText() {
		}

		@Override
		public void addStyleName(String style) {
		}
	};

	public AdvancedTextBox() {
		this(null);
	}

	public AdvancedTextBox(final String defautText) {
		this.defaultText = defautText;
		addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				focused = true;
				String text = AdvancedTextBox.this.getText();
				if (AdvancedTextBox.this.defaultText == null
						|| !AdvancedTextBox.this.defaultText.equals(text)) {
					setSelectionRange(0, text.length());
				} else {
					AdvancedTextBox.super.setText("");
				}
			}
		});

		addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				focused = false;
				handleDefaultText();
			}
		});

		addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				handleDefaultText();
			}
		});

		addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				handleTextStyles();
			}
		});
	}

	@Override
	public HandlerRegistration addDoubleClickHandler(
			final DoubleClickHandler handler) {
		return addDomHandler(handler, DoubleClickEvent.getType());
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		handleDefaultText();
	}

	/**
	 * If the box text is empty fills it with the default value
	 * 
	 */
	protected void handleDefaultText() {
		// does nothing if the element is focused
		if (hasFocus())
			return;

		if (defaultText != null && defaultText.length() > 0) {
			boolean empty = isEmptyTextField();
			if (empty && !isReadOnly()) {
				super.setText(defaultText);
			}
		}
		if (uiHandler != null)
			uiHandler.handleDefaultText();

		handleTextStyles();
	}

	public boolean hasFocus() {
		return focused;
	}

	/**
	 * true if and only if the value is empty after trimming (if the value is the
	 * default text, returns false)
	 * 
	 * @return true if and only if the value is empty after trimming (if the value
	 *         is the default text, returns false)
	 * @see #isEmpty() to integrate the test of having the default text in.
	 */
	protected boolean isEmptyTextField() {
		String text = getText();
		return text == null || text.trim().length() == 0;
	}

	/**
	 * returns true if the current text is empty or equal to the default text
	 * 
	 * @return true if the current text is empty or equal to the default text
	 */
	public boolean isEmpty() {
		return isEmptyTextField()
				|| (defaultText == null ? true : defaultText.equals(getText()));
	}

	/**
	 * Adds or removes the default text style depending on the value
	 * 
	 */
	protected void handleTextStyles() {
		if (isReadOnly()) {
			addStyleName(getReadOnlyTextStyle());
		} else {
			String text = getTextValue();
			ValidationException error = null;
			if (validator != null) {
				try {
					validator.validate(text);
				} catch (ValidationException ex) {
					error = ex;
				}
			}
			if (error == null) {
				if (isEmptyTextField() || text.trim().length() == 0) {
					addStyleName(getTextStyle());
				} else {
					removeStyleName(getTextStyle());
				}
				removeStyleName(getErrorTextStyle());
				removeError();
			} else {
				removeStyleName(getTextStyle());
				addStyleName(getErrorTextStyle());
				handleError(error);
			}
		}
		if (uiHandler != null)
			uiHandler.handleTextStyles();

	}

	/**
	 * By default sets the title of the field to the message of the validator
	 * exception
	 * 
	 * @param error
	 */
	protected void handleError(ValidationException error) {
		if (uiHandler != null)
			uiHandler.handleError(error);
	}

	/**
	 * By default removes the title of the field (in case it was previously set by
	 * {@link #handleError(ValidationException)})
	 * 
	 */
	protected void removeError() {
		if (uiHandler != null)
			uiHandler.removeError();
	}

	protected String getTextStyle() {
		return mandatory ? getMandatoryTextStyle() : getDefaultTextStyle();
	}

	public String getDefaultTextStyle() {
		if (defaultTextStyle == null)
			return DEFAULT_TEXT_STYLE;
		return defaultTextStyle;
	}

	/**
	 * Returns the current text or an empty text if default value
	 * 
	 * <BR/>
	 * <b>Note:</b> {@link #getText()} cannot be overriden since many other parent
	 * functions use it (like {@link #setSelectionRange(int, int)}).
	 * 
	 * 
	 * @return an empty string if the current text is the default value or the
	 *         contained text
	 */
	public String getTextValue() {
		String text = super.getText();
		if (text.trim().equals(defaultText))
			return "";
		return text;
	}

	public void setDefaultText(String text) {
		defaultText = text;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		if (defaultText != null && isEmptyTextField()) {
			setText(defaultText);
		}
	}

	public String getDefaultText() {
		return defaultText;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		removeStyleName(getTextStyle());
		this.mandatory = mandatory;
		handleTextStyles();
	}

	public String getMandatoryTextStyle() {
		if (mandatoryTextStyle == null)
			return MANDATORY_TEXT_STYLE;
		return mandatoryTextStyle;
	}

	public void setMandatoryTextStyle(String defaultMandatoryTextStyle) {
		this.mandatoryTextStyle = defaultMandatoryTextStyle;
	}

	public void setDefaultTextStyle(String defaultTextStyle) {
		removeStyleName(getTextStyle());
		this.defaultTextStyle = defaultTextStyle;
		handleTextStyles();
	}

	public String getErrorTextStyle() {
		if (errorTextStyle == null)
			return ERROR_TEXT_STYLE;
		return errorTextStyle;
	}

	public void setErrorTextStyle(String errorTextStyle) {
		removeStyleName(getErrorTextStyle());
		this.errorTextStyle = errorTextStyle;
		handleTextStyles();
	}

	public Validator<String> getValidator() {
		return validator;
	}

	public void setValidator(Validator<String> validator) {
		this.validator = validator;
		handleTextStyles();
	}

	public String getReadOnlyTextStyle() {
		if (readOnlyTextStyle == null)
			return READ_ONLY_TEXT_STYLE;
		return readOnlyTextStyle;
	}

	public void setReadOnlyTextStyle(String readOnlyTextStyle) {
		this.readOnlyTextStyle = readOnlyTextStyle;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		removeStyleName(getReadOnlyTextStyle());
		super.setReadOnly(readOnly);
		handleTextStyles();
		if (readOnly) {
			String text = getText();
			if (defaultText != null && text != null && defaultText.equals(text))
				setText("");
		} else {
			handleDefaultText();
		}
	}

	@Override
	public void removeStyleName(String style) {
		super.removeStyleName(style);
		if (uiHandler != null)
			uiHandler.removeStyleName(style);
	}

	@Override
	public void addStyleName(String style) {
		super.addStyleName(style);
		if (uiHandler != null)
			uiHandler.addStyleName(style);
	}

	public UIHandler getUiHandler() {
		return uiHandler;
	}

	public void setUiHandler(UIHandler uiHandler) {
		this.uiHandler = uiHandler;
	}

	/**
	 * Returns the component that is holding this text box (for example the
	 * SuggestBox)
	 * 
	 * @return the component that is holding this text box
	 */
	public Widget getRepresenter() {
		return representer;
	}

	public void setRepresenter(Widget representer) {
		this.representer = representer;
	}

}
