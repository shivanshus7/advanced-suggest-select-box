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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.IsWidget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl.DefaultIconedSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl.IconedValueRendererFactory;

/**
 * 
 * Suggest box for multiple choice values
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          the item value type
 * @param <W>
 *          the item list representer
 * @param <C>
 *          the selected items representer
 */
public class MultiChoiceSuggestBox<T, W extends IconedValueHolderItem<T>, C extends ValueHolderItem<T>>
		extends DefaultIconedSuggestBox<T, W> {

	/**
	 * Contains the list of widgets for the selected values in their addition
	 * order
	 */
	protected List<C> selectedValues = new ArrayList<C>();
	/**
	 * Contains the list of widgets for the selected values in their addition
	 * order
	 */
	protected ListRenderer<T, C> selectedValuesPanel;

	protected ValueRendererFactory<T, C> choiceItemsRendererFactory;

	protected DockPanel.DockLayoutConstant position;

	/**
	 * @param defaultText
	 */
	public MultiChoiceSuggestBox(String defaultText,
			DockPanel.DockLayoutConstant position,
			ValueRendererFactory<T, C> choiceItemsRendererFactory) {
		super(defaultText);

		this.position = position;
		setChoiceItemsRendererFactory(choiceItemsRendererFactory);
		setChoicesPanel();
	}

	@Override
	protected void init(String defaultText) {
		super.init(defaultText);
		IconedValueRendererFactory<T, W> unifiedValueRendererFactory = new IconedValueRendererFactory<T, W>(
				iconLinker);
		setValueRendererFactory(unifiedValueRendererFactory);
	}

	/**
	 * Sets the choices panel at a given position
	 * 
	 */
	protected void setChoicesPanel() {
		setPanelAt(position, selectedValuesPanel);
	}

	public void valueSelected(T value) {
		selectedValuesPanel.add(choiceItemsRendererFactory.createValueRenderer(
				value, DEBUG_ID_PREFIX, getOptions()));
		super.valueSelected(value);
		setText("");
		emptyIcon();
	}

	protected ValueRendererFactory<T, C> getChoiceItemsRendererFactory() {
		return choiceItemsRendererFactory;
	}

	@SuppressWarnings("unchecked")
	protected void setChoiceItemsRendererFactory(
			ValueRendererFactory<T, C> choiceItemsRendererFactory) {
		this.choiceItemsRendererFactory = choiceItemsRendererFactory;
		choiceItemsRendererFactory
				.setSuggestBox((AbstractSuggestBox<T, EventHandlingValueHolderItem<T>>) this);
		selectedValuesPanel = choiceItemsRendererFactory.createListRenderer();
		setChoicesPanel();
	}

	public List<T> getValues() {
		int widgetCount = selectedValuesPanel.getWidgetCount();
		List<T> toReturn = new ArrayList<T>();
		for (int i = 0; i < widgetCount; i++) {
			C selected = selectedValuesPanel.getAt(i);
			toReturn.add(selected.getValue());
		}
		return toReturn;
	}

	public void setValues(List<T> toSet) {
		clearSelection();
		for (T value : toSet) {
			selectedValuesPanel.add(choiceItemsRendererFactory.createValueRenderer(
					value, DEBUG_ID_PREFIX, getOptions()));
		}
	}

	public void clearSelection() {
		selectedValuesPanel.clear();
		super.clearSelection();
	}

	protected DockPanel.DockLayoutConstant getPosition() {
		return position;
	}

	protected void setPosition(DockPanel.DockLayoutConstant position) {
		// remove the old panel
		setPanelAt(position, null);
		this.position = position;
		// put it in its new position
		setChoicesPanel();
	}

	/**
	 * Sets the widget at position
	 * 
	 * @param position
	 *          position
	 * @param widget
	 *          widget maybe null to remove widget
	 */
	protected void setPanelAt(DockPanel.DockLayoutConstant position,
			IsWidget widget) {
		if (position == DockPanel.WEST)
			textField.setLeftWidget(widget);
		else if (position == DockPanel.EAST)
			textField.setRightWidget(widget);
		else if (position == DockPanel.NORTH)
			textField.setTopWidget(widget);
		else if (position == DockPanel.SOUTH)
			textField.setBottomWidget(widget);
	}

}
