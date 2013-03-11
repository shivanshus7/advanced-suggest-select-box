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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.util.HtmlUtil;

public class DefaultValueRenderer<T> extends HTML implements
		EventHandlingValueHolderItem<T> {
	private static final String ITEM_HOVER = "eu-nextstreet-SuggestItemHover";
	private static final String MATCHING_STRING = "eu-nextstreet-SuggestMatchingString";
	public static final String SELECTED = "eu-nextstreet-SuggestItemSelected";
	protected T value;
	protected boolean caseSensitive;
	protected ValueRendererFactory<T, ?> valueRendererFactory;

	/**
	 * constructor
	 * 
	 * @param value
	 * @param filterText
	 * @param caseSensitive
	 * @param valueRendererFactory
	 *          the factory that created this instance
	 */
	public DefaultValueRenderer(T value, String filterText,
			boolean caseSensitive, ValueRendererFactory<T, ?> valueRendererFactory) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		this.valueRendererFactory = valueRendererFactory;
		fillHtml(value, filterText, caseSensitive);
	}

	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		String html = toHtml(value);
		if (html == null)
			throw new IllegalStateException(
					"toString(T value) cannot return null for " + value
							+ " current renderer : " + valueRendererFactory.getClass());
		html = highlightMatchingSequence(html, filterText, caseSensitive);
		setHTML(html);
	}

	/**
	 * returns the html String representation of value
	 * 
	 * @param value
	 * @return
	 */
	protected String toHtml(T value) {
		return valueRendererFactory.getSuggestBox().toString(value);
	}

	// /**
	// * Override this method to have a specific string representation of the
	// value
	// *
	// * @param value
	// * value
	// * @return the string value
	// */
	// public String toString(T value) {
	// return valueRendererFactory.toString(value);
	// }

	protected String highlightMatchingSequence(String html, String filterText,
			boolean caseSensitive) {
		return HtmlUtil.highlightMatchingSequence(html, filterText, caseSensitive,
				MATCHING_STRING);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public void hover(boolean hover) {
		if (hover)
			addStyleName(ITEM_HOVER);
		else
			removeStyleName(ITEM_HOVER);
	}

	@Override
	public void setSelected(boolean focused) {
		if (focused)
			addStyleName(SELECTED);
		else
			removeStyleName(SELECTED);
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	@Override
	public UIObject getUiObject() {
		return this;
	}

	@Override
	public ValueRendererFactory<T, ?> getValueRendererFactory() {
		return valueRendererFactory;
	}
}
