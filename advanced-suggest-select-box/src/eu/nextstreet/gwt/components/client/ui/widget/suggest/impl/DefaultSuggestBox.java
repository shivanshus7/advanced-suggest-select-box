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

import eu.nextstreet.gwt.components.client.ui.common.event.KeyUpRequestingHandler;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.*;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle.Request;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultSuggestOracle;

/**
 * Implements a simple value holding suggest box, more advanced implementations can have a type safe model to hold and manage items.
 * 
 * @author Zied Hamdi founder of http://1vu.fr
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

	protected int propositionsMaxCount = 10;

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

	protected class KeyEventManager extends KeyUpRequestingHandler {
		int keyCode;

		@Override
		protected void executeRequest(String query) {
			onKeyUp(keyCode);
		}

		@Override
		protected String getText() {
			return textField.getText();
		}

		public void setLastEvent(int keyCode) {
			this.keyCode = keyCode;
		}

	}

	protected KeyEventManager keyEventManager = new KeyEventManager();

	private CallBackHandler callback = new CallBackHandler();

	public DefaultSuggestBox() {
		this(null);
	}

	public DefaultSuggestBox(String defaultText) {
		this(defaultText, new DefaultSuggestOracle<T>());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DefaultSuggestBox(String defaultText, SuggestOracle<T> suggestOracle) {
		initWidget(uiBinder.createAndBindUi(this));
		init(defaultText);

		this.suggestOracle = suggestOracle;
		suggestOracle.setContextWidget((AbstractBaseWidget) this);
	}

	// ------------------ default event handling -----------------------
	@UiHandler("textField")
	public void keyUp(KeyUpEvent keyUpEvent) {
		keyEventManager.setLastEvent(keyUpEvent.getNativeKeyCode());
		keyEventManager.handleKeyUp(textField.getText());
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
		Request request = new Request(text, propositionsMaxCount);
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

	public int getPropositionsMaxCount() {
		return propositionsMaxCount;
	}

	public void setPropositionsMaxCount(int propositionsMaxCount) {
		this.propositionsMaxCount = propositionsMaxCount;
	}

	public KeyEventManager getKeyEventManager() {
		return keyEventManager;
	}

	public void setKeyEventManager(KeyEventManager keyEventManager) {
		this.keyEventManager = keyEventManager;
	}

}
