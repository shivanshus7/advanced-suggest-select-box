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
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle.Request;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestPossibilitiesCallBack;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestTextBoxWidget;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestTextBoxWidgetImpl;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultSuggestOracle;

/**
 * Implements a simple value holding suggest box, more advanced implementations
 * can have a type safe model to hold and manage items.
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          the class type of the items
 * @param <W>
 *          the {@link EventHandlingValueHolderItem} implementation class
 */
public class DefaultSuggestBox<T, W extends EventHandlingValueHolderItem<T>> extends AbstractSuggestBox<T, W> {

	@SuppressWarnings("rawtypes")
	interface SuggestBoxUiBinder extends UiBinder<Widget, DefaultSuggestBox> {
	}

	private static SuggestBoxUiBinder uiBinder = GWT.create(SuggestBoxUiBinder.class);

	protected @UiField
	SuggestTextBoxWidgetImpl<T, W> textField;

	protected int suggestionMaxCount = 10;
	protected SuggestOracle<T> suggestOracle;

	@SuppressWarnings({ "unused", "rawtypes" })
	private final class CallBackHandler implements SuggestOracle.Callback {
		private SuggestPossibilitiesCallBack<T> innerCallBack;

		@SuppressWarnings("unchecked")
		public void onSuggestionsReady(SuggestOracle.Request request, SuggestOracle.Response response) {
			innerCallBack.setPossibilities(new ArrayList<T>(response.getSuggestions()));
			// display.setMoreSuggestions(response.hasMoreSuggestions(),
			// response.getMoreSuggestionsCount());
			// display.showSuggestions(SuggestBox.this,
			// response.getSuggestions(),
			// oracle.isDisplayStringHTML(),
			// isAutoSelectEnabled(),
			// suggestionCallback);
		}

		public SuggestPossibilitiesCallBack<T> getInnerCallBack() {
			return innerCallBack;
		}

		public void setInnerCallBack(SuggestPossibilitiesCallBack<T> innerCallBack) {
			this.innerCallBack = innerCallBack;
		}

	};

	private CallBackHandler callback = new CallBackHandler();

	public DefaultSuggestBox() {
		this(null);
	}

	public DefaultSuggestBox(String defaultText) {
		this(defaultText, new DefaultSuggestOracle<T>());
	}

	@SuppressWarnings("unchecked")
	public DefaultSuggestBox(String defaultText, SuggestOracle<T> suggestOracle) {
		initWidget(uiBinder.createAndBindUi(this));
		init(defaultText);

		this.suggestOracle = suggestOracle;
		suggestOracle.setSuggestBox((AbstractSuggestBox<T, EventHandlingValueHolderItem<T>>) this);
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

	@Override
	protected void computeFiltredPossibilities(final String text, final SuggestPossibilitiesCallBack<T> suggestPossibilitiesCallBack) {
		Request request = new Request(text, suggestionMaxCount);
		callback.setInnerCallBack(suggestPossibilitiesCallBack);
		suggestOracle.requestSuggestions(request, callback);
	}

	@Override
	protected boolean fillValue(T t, boolean commit) {
		// get the typed text length before updating the field with the selected
		// value
		int startIndex = textField.getText().length();
		// now safely update the value
		if (commit || strictMode) {
			super.fillValue(t, commit);
			if (!commit && !strictMode) {
				textField.setSelectionRange(startIndex, textField.getText().length() - startIndex);
			}
			return true;
		} else {
			// text.setSelectionRange(0, text.getText().length());
			return false;
		}
	}

	@Override
	public SuggestTextBoxWidgetImpl<T, W> getTextField() {
		return textField;
	}

	public void setTextField(SuggestTextBoxWidget<T, W> textField) {
		this.textField = (SuggestTextBoxWidgetImpl<T, W>) textField;
	}

	public SuggestOracle<T> getSuggestOracle() {
		return suggestOracle;
	}

	public void setSuggestOracle(SuggestOracle<T> suggestOracle) {
		this.suggestOracle = suggestOracle;
	}

	public int getPropositionsMaxCount() {
		return suggestionMaxCount;
	}

	public void setPropositionsMaxCount(int propositionsMaxCount) {
		this.suggestionMaxCount = propositionsMaxCount;
	}

}
