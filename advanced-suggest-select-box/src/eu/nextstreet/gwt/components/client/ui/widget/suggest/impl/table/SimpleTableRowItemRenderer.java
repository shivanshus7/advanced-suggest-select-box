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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.util.HtmlUtil;

/**
 * represents a row in the suggest box when using the factory
 * {@link SimpleTableValueRendererFactory}
 * 
 * Typically the methods {@link #explodeValueInColumns(Object, String, boolean)}
 * and probably {@link #createWidget(String, boolean, int, String)} will be
 * overridden to answer to your needs. By default
 * {@link #createWidget(String, boolean, int, String)} will create {@link HTML}
 * widgets with the String content you returned in
 * {@link #explodeValueInColumns(Object, String, boolean)}
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 *          the value to be represented in this row's type
 */
public class SimpleTableRowItemRenderer<T> extends BasicWidgetListHolder
		implements EventHandlingValueHolderItem<T> {
	private static final String MATCHING_STRING = "eu-nextstreet-SuggestTableMatchingString";
	public static final String SELECTED = "selected";
	private static final long serialVersionUID = 1L;
	protected T value;
	protected boolean caseSensitive;
	protected String styleName;
	protected ValueRendererFactory<T, ? extends ValueHolderItem<T>> valueRendererFactory;

	public SimpleTableRowItemRenderer(T value, String filterText,
			boolean caseSensitive,
			ValueRendererFactory<T, ? extends ValueHolderItem<T>> valueRendererFactory) {
		this.value = value;
		this.caseSensitive = caseSensitive;
		fillHtml(value, filterText, caseSensitive);
		this.valueRendererFactory = valueRendererFactory;
	}

	/**
	 * This method synchronizes all the method calls, you shouldn't override or if
	 * you do, you should call it in your implementation to have the other method
	 * calls done for you
	 * 
	 * @param value
	 *          the row value
	 * @param filterText
	 *          the text written in the suggest box (to be highlighted for
	 *          example)
	 * @param caseSensitive
	 *          the case sensitive option of the suggest box
	 */
	protected void fillHtml(T value, String filterText, boolean caseSensitive) {
		String[] valueInColumns = explodeValueInColumns(value, filterText,
				caseSensitive);
		for (int i = 0; i < valueInColumns.length; i++) {
			String colText = valueInColumns[i];
			add(createWidget(filterText, caseSensitive, i, colText));
		}
	}

	/**
	 * Constructs the widget corresponding to the parameters information. This is
	 * not typed by generics because widgets can be different fo each column
	 * 
	 * @param filterText
	 *          the text that was typed in the suggest box (to do the matching
	 *          sequence highlighting)
	 * @param caseSensitive
	 *          true if the case sensitive option is enabled
	 * @param col
	 *          the column index
	 * @param colText
	 *          the column text (can be an image url or any text)
	 * @return teh widgets to display
	 * @see #explodeValueInColumns(Object, String, boolean)
	 */
	protected Widget createWidget(String filterText, boolean caseSensitive,
			int col, String colText) {
		return new HTML(highlightColumnText(col) ? highlightMatchingSequence(
				colText, filterText, caseSensitive) : colText);
	}

	/**
	 * Returns an html with tags highlighting the matching string
	 * 
	 * 
	 * @param filterText
	 *          the text that was typed in the suggest box (to do the matching
	 *          sequence highlighting)
	 * @param caseSensitive
	 *          true if the case sensitive option is enabled
	 * @param colText
	 *          the column text (can be an image url or any text) * @return
	 */
	protected String highlightMatchingSequence(String colText, String filterText,
			boolean caseSensitive) {
		return HtmlUtil.highlightMatchingSequence(colText, filterText,
				caseSensitive, MATCHING_STRING);
	}

	/**
	 * Specifies whether you want the highlight function to be called on teh
	 * column with index <code>i</code>
	 * 
	 * @param col
	 *          the column index starting from 0
	 * @return
	 */
	protected boolean highlightColumnText(int col) {
		return true;
	}

	/**
	 * Decomposes the current value in the different columns text value, this text
	 * will be used after highlighting
	 * {@link #highlightMatchingSequence(String, String, boolean)}, if enabled
	 * {@link #highlightColumnText(int)}) to construct widgets
	 * {@link #createWidget(String, boolean, int, String)}
	 * 
	 * you can return urls or any string value you will need after to create the
	 * corresponding widget
	 * 
	 * 
	 * @param filterText
	 *          the text that was typed in the suggest box (to do the matching
	 *          sequence highlighting)
	 * @param caseSensitive
	 *          true if the case sensitive option is enabled
	 * @return the row columns text representations
	 */
	protected String[] explodeValueInColumns(T value, String filterText,
			boolean caseSensitive) {
		return new String[] { value.toString() };
	}

	// interfaces

	@Override
	public void hover(boolean hover) {
		String styleName = getStyleName() + "-hover";
		for (Widget widget : this) {
			if (hover)
				widget.addStyleName(styleName);
			else
				widget.removeStyleName(styleName);
		}
	}

	@Override
	public void setSelected(boolean selected) {
		if (selected) {
			addStyleDependentName(SELECTED);
		} else {
			removeStyleDependentName(SELECTED);
		}
	}

	public void addStyleDependentName(String styleSuffix) {
		for (Widget widget : this) {
			widget.addStyleDependentName(styleSuffix);
		}
	}

	public void removeStyleDependentName(String styleSuffix) {
		for (Widget widget : this) {
			widget.removeStyleDependentName(styleSuffix);
		}
	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasMouseDownHandlers)
				toReturn.add(((HasMouseDownHandlers) widget)
						.addMouseDownHandler(handler));
		}
		return toReturn;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		for (Widget widget : this) {
			// FIXME maybe should optimize
			widget.fireEvent(event);
		}
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasMouseUpHandlers)
				toReturn.add(((HasMouseUpHandlers) widget).addMouseUpHandler(handler));
		}
		return toReturn;
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasMouseOutHandlers)
				toReturn
						.add(((HasMouseOutHandlers) widget).addMouseOutHandler(handler));
		}
		return toReturn;
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasMouseOverHandlers)
				toReturn.add(((HasMouseOverHandlers) widget)
						.addMouseOverHandler(handler));
		}
		return toReturn;
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasMouseMoveHandlers)
				toReturn.add(((HasMouseMoveHandlers) widget)
						.addMouseMoveHandler(handler));
		}
		return toReturn;
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasMouseWheelHandlers)
				toReturn.add(((HasMouseWheelHandlers) widget)
						.addMouseWheelHandler(handler));
		}
		return toReturn;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		MultipleHandlerRegistration toReturn = new MultipleHandlerRegistration();
		for (Widget widget : this) {
			if (widget instanceof HasClickHandlers)
				toReturn.add(((HasClickHandlers) widget).addClickHandler(handler));
		}
		return toReturn;
	}

	@Override
	public UIObject getUiObject() {
		if (size() > 0)
			return get(0);
		return null;
	}

	public String getStyleName() {
		return styleName;
	}

	@Override
	public void setStyleName(String style) {
		this.styleName = style;
		for (Widget widget : this) {
			widget.setStyleName(styleName);
		}

	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
	 */
	@Override
	public Widget asWidget() {
		throw new UnsupportedOperationException(
				"There's no element in GWT to represent a row, this is a special case that shouldn't be called");
	}

	public ValueRendererFactory<T, ? extends ValueHolderItem<T>> getValueRendererFactory() {
		return valueRendererFactory;
	}

	@Override
	public void initWidget() {

	}
}
