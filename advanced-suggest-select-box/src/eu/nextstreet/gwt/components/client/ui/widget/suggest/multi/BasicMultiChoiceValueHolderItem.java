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

package eu.nextstreet.gwt.components.client.ui.widget.suggest.multi;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;

import eu.nextstreet.gwt.components.client.ui.common.data.ValueRepresentationTransformer;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;

/**
 * Has no concrete widget for the displayed item, but prepares the space for it
 * and appends a functional 'remove' button on the right
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 * 
 * @param <T>
 *          item value type
 * @param <R>
 *          concrete widget type
 * @param <C>
 *          concrete ValueHolderItem
 */
public abstract class BasicMultiChoiceValueHolderItem<T, R extends IsWidget, C extends MultiChoiceValueHolderItem<T, C>> extends
		AbstractMultiChoiceValueHolderItem<T, C> implements ValueRepresentationTransformer<T, R> {

	/** Css style for multi item */
	private static final String LINE = "line";
	private static final String LINE_BREAK = "lineBreak";
	private static final String STYLE = "eu-nextstreet-MultiItem";
	/** Css style for selected */
	private static final String SELECTED = "eu-nextstreet-MultiItemSelected";
	/** Css style for hover */
	private static final String ITEM_HOVER = "eu-nextstreet-MultiItemHover";
	private static final String ITEM = "item";
	private static final String REMOVE_BUTTON = "remove";

	protected FlowPanel containerPanel = new FlowPanel();
	protected FlowPanel panel = new FlowPanel();
	protected R concreteWidget;
	protected IsWidget removeButton;
	protected SimplePanel endingPanel = new SimplePanel();
	protected ValueRendererFactory<T, ? extends ValueHolderItem<T>> valueRendererFactory;

	/**
	 * Decorates the concrete widget with functional 'remove' button
	 * 
	 * @param value
	 *          item value
	 * @param valueRendererFactory
	 *          the factory that created this instance
	 */
	public BasicMultiChoiceValueHolderItem(T value, ValueRendererFactory<T, ? extends ValueHolderItem<T>> valueRendererFactory) {
		super(value);
		this.valueRendererFactory = valueRendererFactory;
	}

	@Override
	public void initWidget() {
		concreteWidget = transform(value);
		panel.setStyleName(ITEM);
		setStyleName(STYLE);
		createRemoveButton();
		panel.add(concreteWidget);
		panel.add(removeButton);

		containerPanel.setStyleName(LINE);
		containerPanel.add(panel);
		endingPanel.setStyleName(LINE_BREAK);
		containerPanel.add(endingPanel);
		setWidget(containerPanel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel#
	 * setSelected(boolean)
	 */
	@Override
	public void setSelected(boolean selected) {
		// label.setSelected(selected);
		if (selected)
			addStyleName(SELECTED);
		else
			removeStyleName(SELECTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel#
	 * hover(boolean)
	 */
	@Override
	public void hover(boolean hover) {
		// label.hover(hover);
		if (hover)
			addStyleName(ITEM_HOVER);
		else
			removeStyleName(ITEM_HOVER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel#
	 * getUiObject()
	 */
	@Override
	public UIObject getUiObject() {
		return this;
	}

	/**
	 * You can override this method to have a different remove button
	 */
	protected void createRemoveButton() {
		removeButton = new Image("img/multi/remove.png");
		((Image) removeButton).setStyleName(REMOVE_BUTTON);
		((HasClickHandlers) removeButton).addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				remove();
			}
		});

	}

	public ValueRendererFactory<T, ? extends ValueHolderItem<T>> getValueRendererFactory() {
		return valueRendererFactory;
	}

}
