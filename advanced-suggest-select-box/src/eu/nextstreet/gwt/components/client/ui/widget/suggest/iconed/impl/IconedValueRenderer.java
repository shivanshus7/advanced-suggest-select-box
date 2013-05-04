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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.UIObject;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderItem;

/**
 * Displays an iconed item
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          the value class type
 */
public class IconedValueRenderer<T> extends Composite implements IconedValueHolderItem<T> {
	private static final String ITEM_HOVER = "eu-nextstreet-SuggestItemHover";
	public static final String SELECTED = "eu-nextstreet-SuggestItemSelected";

	protected HorizontalPanel horizontalPanel = new HorizontalPanel();
	protected Image icon;
	protected EventHandlingValueHolderItem<T> label;
	protected ValueRendererFactory<T, ? extends ValueHolderItem<T>> valueRendererFactory;

	public IconedValueRenderer(T value, Image icon, EventHandlingValueHolderItem<T> label, String filterText, boolean caseSensitive,
			ValueRendererFactory<T, ? extends ValueHolderItem<T>> valueRendererFactory) {
		initWidget(horizontalPanel);
		this.label = label;
		if (icon == null) {
			icon = new Image();
		}
		this.icon = icon;
	}

	@Override
	public T getValue() {
		return label.getValue();
	}

	@Override
	public void setValue(T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSelected(boolean selected) {
		// label.setSelected(selected);
		if (selected)
			addStyleName(SELECTED);
		else
			removeStyleName(SELECTED);
	}

	@Override
	public void hover(boolean hover) {
		// label.hover(hover);
		if (hover)
			addStyleName(ITEM_HOVER);
		else
			removeStyleName(ITEM_HOVER);

	}

	@Override
	public UIObject getUiObject() {
		return horizontalPanel;
	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return addDomHandler(handler, MouseUpEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return addDomHandler(handler, MouseWheelEvent.getType());
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public Image getIcon() {
		return icon;
	}

	@Override
	public ValueRendererFactory<T, ? extends ValueHolderItem<T>> getValueRendererFactory() {
		return valueRendererFactory;
	}

	@Override
	public void initWidget() {
		horizontalPanel.setSpacing(5);
		horizontalPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		horizontalPanel.add(icon);
		horizontalPanel.setCellWidth(icon, "20px");
		horizontalPanel.add(label);
	}

}
