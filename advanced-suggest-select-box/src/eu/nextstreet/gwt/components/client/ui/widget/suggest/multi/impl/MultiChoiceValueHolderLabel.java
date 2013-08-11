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

package eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl.IconedValueRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.BasicMultiChoiceValueHolderItem;

/**
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * @param <T>
 *          item value type
 */
public class MultiChoiceValueHolderLabel<T> extends BasicMultiChoiceValueHolderItem<T, IconedValueRenderer<T>, MultiChoiceValueHolderLabel<T>> {
	private static final String MULTI_CHOICE_ITEM = "eu-nextstreet-MultiChoiceItem";
	protected ValueRendererFactory<T, ? extends ValueHolderItem<T>> textRendererFactory;

	/**
	 * 
	 * @param value
	 *          value
	 * @param imageRendererFactory
	 *          the factory the created this instance
	 */
	public MultiChoiceValueHolderLabel(T value, ValueRendererFactory<T, ? extends ValueHolderItem<T>> imageRendererFactory,
			ValueRendererFactory<T, ? extends ValueHolderItem<T>> textRendererFactory) {
		super(value, imageRendererFactory);
		this.textRendererFactory = textRendererFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.nextstreet.gwt.components.client.ui.common.data.
	 * ValueRepresentationTransformer#transform(java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IconedValueRenderer<T> transform(T value) {
		Image image = (Image) ((MultiChoiceValueRendererFactory) valueRendererFactory).getIconLinker().transform(value);
		image.setTitle(toString(value));

		EventHandlingValueHolderItem<T> label = (EventHandlingValueHolderItem<T>) textRendererFactory.createValueRenderer(value, "", null);

		IconedValueRenderer<T> toReturn = new IconedValueRenderer<T>(value, image, label, "", false, valueRendererFactory);
		toReturn.initWidget();

		return toReturn;
	}

	@Override
	public void initWidget() {
		super.initWidget();
		setStyleName(MULTI_CHOICE_ITEM);
	}

	protected String toString(T value) {
		return valueRendererFactory.toString(value);
	}

	// -------------------- these are unused for now ------------------

	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return concreteWidget.addMouseDownHandler(handler);
	}

	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return concreteWidget.addMouseUpHandler(handler);
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return concreteWidget.addMouseOutHandler(handler);
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return concreteWidget.addMouseOverHandler(handler);
	}

	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return concreteWidget.addMouseMoveHandler(handler);
	}

	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return concreteWidget.addMouseWheelHandler(handler);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return concreteWidget.addClickHandler(handler);
	}

	public ValueRendererFactory<T, ? extends ValueHolderItem<T>> getTextRendererFactory() {
		return textRendererFactory;
	}

	public void setTextRendererFactory(ValueRendererFactory<T, ? extends ValueHolderItem<T>> textRendererFactory) {
		this.textRendererFactory = textRendererFactory;
	}

	@Override
	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return concreteWidget.addDoubleClickHandler(handler);
	}

}
