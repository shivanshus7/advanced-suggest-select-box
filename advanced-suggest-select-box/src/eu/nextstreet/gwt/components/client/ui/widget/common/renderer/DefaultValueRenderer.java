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
package eu.nextstreet.gwt.components.client.ui.widget.common.renderer;

import java.util.Map;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;
import eu.nextstreet.gwt.components.client.ui.widget.util.HtmlUtil;

public class DefaultValueRenderer<T> extends HTML implements EventHandlingValueHolderItem<T> {
	private static final String ITEM_HOVER = "hover";
	private static final String MATCHING_STRING = "matchStr";
	public static final String SELECTED = "selected";
	protected T value;
	protected boolean caseSensitive;
	protected ValueRendererFactory<T, ?> valueRendererFactory;
	protected boolean selected, hover;
	protected Map<String, Option<?>> options;

	/**
	 * constructor
	 * 
	 * @param value
	 * @param filterText
	 * @param caseSensitive
	 * @param options
	 * @param valueRendererFactory
	 *          the factory that created this instance
	 */
	public DefaultValueRenderer(final T value, String filterText, boolean caseSensitive, Map<String, Option<?>> options,
			final ValueRendererFactory<T, ?> valueRendererFactory) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		this.valueRendererFactory = valueRendererFactory;
		this.options = options;
		init();
		fillHtml(value, filterText, caseSensitive);
	}

	/**
	 * Override this method to initialize
	 */
	protected void init() {

	}

	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		String html = toHtml(value);
		if (html == null)
			throw new IllegalStateException("toString(T value) cannot return null for " + value + " current renderer : " + valueRendererFactory.getClass());
		html = highlightMatchingSequence(html, filterText, caseSensitive);
		setContent(html);
	}

	/**
	 * Put the HTMl in the current main panel
	 * 
	 * @param html
	 * @see #getMainPanel()
	 */
	protected void setContent(String html) {
		setHTML(html);
	}

	/**
	 * returns the html String representation of value
	 * 
	 * @param value
	 * @return
	 */
	protected String toHtml(T value) {
		return valueRendererFactory.toString(value);
	}

	protected String highlightMatchingSequence(String html, String filterText, boolean caseSensitive) {
		return HtmlUtil.highlightMatchingSequence(html, filterText, caseSensitive, MATCHING_STRING);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public void hover(boolean hover) {
		this.hover = hover;
		handleStyles();
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		handleStyles();
	}

	protected void handleStyles() {
		if (hover)
			getMainPanel().addStyleName(ITEM_HOVER);
		else
			getMainPanel().removeStyleName(ITEM_HOVER);

		if (selected)
			getMainPanel().addStyleName(SELECTED);
		else
			getMainPanel().removeStyleName(SELECTED);
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

	@Override
	public void initWidget() {

	}

	/**
	 * Use this method to control to which panel you want operations to be transferred (ex: selection and hover styling)
	 * 
	 * @return the container panel
	 */
	protected Widget getMainPanel() {
		return this;
	}

	@Override
	public void refresh() {
		// FIXME filterText is not saved
		fillHtml(value, "", caseSensitive);
	}

}
