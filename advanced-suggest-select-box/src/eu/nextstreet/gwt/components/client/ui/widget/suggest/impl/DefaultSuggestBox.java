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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestPossibilitiesCallBack;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestTextBoxWidget;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestTextBoxWidgetImpl;

/**
 * Implements a simple value holding suggest box, more advanced implementations
 * can have a type safe model to hold and manage items.
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *            the class type of the items
 * @param <W>
 *            the {@link EventHandlingValueHolderItem} implementation class
 */
public class DefaultSuggestBox<T, W extends EventHandlingValueHolderItem<T>>
		extends AbstractSuggestBox<T, W> {

	@SuppressWarnings("rawtypes")
	interface SuggestBoxUiBinder extends UiBinder<Widget, DefaultSuggestBox> {
	}

	private static SuggestBoxUiBinder uiBinder = GWT
			.create(SuggestBoxUiBinder.class);

	protected @UiField
	SuggestTextBoxWidgetImpl<T, W> textField;

	protected List<T> possiblilities;
	protected boolean startsWith;

	public DefaultSuggestBox() {
		this(false, true);
	}

	public DefaultSuggestBox(boolean caseSensitive, boolean startsWith) {
		this(null, new ArrayList<T>(), caseSensitive, startsWith);
	}

	public DefaultSuggestBox(String defaultText) {
		this(defaultText, false, true);
	}

	public DefaultSuggestBox(String defaultText, boolean caseSensitive,
			boolean startsWith) {
		this(defaultText, new ArrayList<T>(), caseSensitive, startsWith);
	}

	public DefaultSuggestBox(String defaultText, List<T> possiblilities,
			boolean caseSensitive, boolean startsWith) {
		initWidget(uiBinder.createAndBindUi(this));
		init(defaultText);

		this.possiblilities = possiblilities;
		this.startsWith = startsWith;
		super.setCaseSensitive(caseSensitive);
	}

	// ------------------ default event handling -----------------------
	@UiHandler("textField")
	public void onKeyUp(KeyUpEvent keyUpEvent) {
		super.onKeyUp(keyUpEvent);
	}

	@UiHandler("textField")
	public void onBlur(BlurEvent event) {
		super.onBlur(event);
	}

	@UiHandler("textField")
	public void onDoubleClick(DoubleClickEvent event) {
		super.onDoubleClick(event);
	}

	// -------------------------- end.

	public void add(T t) {
		possiblilities.add(t);
	}

	public void clear() {
		possiblilities.clear();
	}

	@Override
	protected void computeFiltredPossibilities(final String text,
			final SuggestPossibilitiesCallBack<T> suggestPossibilitiesCallBack) {
		List<T> toReturn = new ArrayList<T>();
		for (T t : possiblilities) {
			if (accept(text, t))
				toReturn.add(t);
		}
		suggestPossibilitiesCallBack.setPossibilities(toReturn);
	}

	/**
	 * used to define the filtering strategy, override and check in the inner
	 * list if this element should appear
	 * 
	 * @param text
	 * @param t
	 * @return true if the element should be included in the list
	 */
	protected boolean accept(String text, T t) {
		String stringValue = caseSensitive ? toString(t) : toString(t)
				.toUpperCase();
		String textValue = caseSensitive ? text : text.toUpperCase();
		if (startsWith ? stringValue.startsWith(textValue) : (stringValue
				.indexOf(textValue) != -1)) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean fillValue(T t, boolean commit) {
		// get the typed text length before updating the field with the selected
		// value
		int startIndex = textField.getText().length();
		// now safely update the value
		if (startsWith || commit || strictMode) {
			super.fillValue(t, commit);
			if (!commit && !strictMode) {
				textField.setSelectionRange(startIndex, textField.getText()
						.length() - startIndex);
			}
			return true;
		} else {
			// text.setSelectionRange(0, text.getText().length());
			return false;
		}
	}

	public List<T> getPossiblilities() {
		return possiblilities;
	}

	public void setPossiblilities(List<T> possiblilities) {
		this.possiblilities = possiblilities;
	}

	public boolean isStartsWith() {
		return startsWith;
	}

	public void setStartsWith(boolean startsWith) {
		this.startsWith = startsWith;
	}

	@Override
	public SuggestTextBoxWidgetImpl<T, W> getTextField() {
		return textField;
	}

	public void setTextField(SuggestTextBoxWidget<T, W> textField) {
		this.textField = (SuggestTextBoxWidgetImpl<T, W>) textField;
	}

}
