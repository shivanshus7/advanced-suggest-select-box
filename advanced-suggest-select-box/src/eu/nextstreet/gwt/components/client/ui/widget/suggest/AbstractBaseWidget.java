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
package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.nextstreet.gwt.components.client.ui.widget.WidgetController;
import eu.nextstreet.gwt.components.client.ui.widget.common.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultOptions;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultStringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

/**
 * The idea behind this handler is to have our own logical events that are handled by a common interface EventHandlerHolder now implemented only for the change
 * event
 * 
 * These events can be used separately from the native ones
 * 
 * This object also contains options since it is a general use concept (should be moved up in next versions)
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 * @param <P>
 *          the type of the parameter specifying the events
 * @param <E>
 *          the concrete type of event
 */
public abstract class AbstractBaseWidget<T, P, E extends ChangeEvent> extends Composite implements StringFormulator<T>, WidgetController<T> {

	/**
	 * don't change the value here directly unless you have a good reason, use {@link #putOption(Option)} instead
	 * 
	 * FIXME use an interface to represent an option instead of a String: enums can implement the interface (strings are not safe)
	 */
	protected Map<String, Option<?>> options = new HashMap<String, Option<?>>();
	protected SuggestOracle<T> suggestOracle;
	protected List<ChangeHandler> changeHandlerList = new ArrayList<ChangeHandler>();

	protected StringFormulator<T> stringFormulator = new DefaultStringFormulator<T>();
	protected List<T> selectedItems = new ArrayList<T>();
	protected int maxSelected = 1;

	protected boolean toggleMode, selectOneOccurenceMode;

	/**
	 * Remembers the removed elements
	 */
	protected List<T> removedValues = new ArrayList<T>();

	@Override
	public Option<?> getOption(String key) {
		return options.get(key);
	}

	@Override
	public Option<?> putOption(Option<?> option) {
		return putOption(option, false);
	}

	/**
	 * 
	 * @param option
	 * @param refresh
	 *          triggers a refresh
	 * @return
	 */
	public Option<?> putOption(Option<?> option, boolean refresh) {
		Option<?> oldOption = options.put(option.getKey(), option);
		if (refresh)
			refresh();
		return oldOption;
	}

	/**
	 * refreshes the data display
	 */
	protected void refresh() {
	}

	public Option<?> removeOption(String key) {
		return options.remove(key);
	}

	@Override
	public Option<?> removeOption(Option<?> option) {
		return removeOption(option.getKey());
	}

	/**
	 * returns the set options
	 * 
	 * @return the set options
	 */
	@Override
	public Map<String, Option<?>> getOptions() {
		return options;
	}

	public void addHandler(ChangeHandler changeHandler) {
		changeHandlerList.add(changeHandler);
	}

	public void removeHandler(ChangeHandler changeHandler) {
		changeHandlerList.remove(changeHandler);
	}

	protected void changeOccured(E changeEvent) {
		for (ChangeHandler changeHandler : changeHandlerList) {
			changeHandler.onChange(changeEvent);
		}
	}

	/**
	 * fires an event of change
	 * 
	 * @param param
	 *          a parameter specifying more details about the change
	 */
	protected void fireChangeOccured(P param) {
		E changeEvent = changedValue(param);
		changeOccured(changeEvent);
	}

	public SuggestOracle<T> getSuggestOracle() {
		return suggestOracle;
	}

	public void setSuggestOracle(SuggestOracle<T> suggestOracle) {
		this.suggestOracle = suggestOracle;
	}

	/**
	 * Leaves the opportunity to construct a specific event
	 * 
	 * @return an event representative of the change.
	 */
	protected abstract E changedValue(P param);

	/**
	 * Returns the string representation of a value, this method is very important since it determines the equality of elements typed by hand with the ones in the
	 * list.
	 * 
	 * @param t
	 *          the value
	 * @return the string representation of a value
	 */
	public String toString(final T t) {
		return stringFormulator.toString(t);
	}

	public StringFormulator<T> getStringFormulator() {
		return stringFormulator;
	}

	public void setStringFormulator(StringFormulator<T> stringFormulator) {
		if (stringFormulator == this)
			throw new IllegalArgumentException();
		this.stringFormulator = stringFormulator;
	}

	/**
	 * Called when a value is selected from the list, if the value is typed on the keyboard and only one possible element corresponds, this method will be called
	 * immediately only if <code>multipleChangeEvent</code> is true. Otherwise it will wait until a blur event occurs Notice that if
	 * <code>multipleChangeEvent</code> is true, this method will be called also each time the enter key is typed
	 * 
	 * @param value
	 */
	@Override
	public void valueSelected(T value) {
		// OPTIMIZE may be optimized using a hashed list or map
		boolean alreadySelected = selectedItems.contains(value);
		if (toggleMode && alreadySelected) {
			removeSelection(value, false);
			return;
		}
		if (!(selectOneOccurenceMode && alreadySelected))
			selectedItems.add(value);
		if (maxSelected > 0) {
			if (selectedItems.size() > maxSelected)
				selectedItems.remove(0);
		}
	}

	@Override
	public void setSelection(List<T> toSet) {
		clearSelection();
		removedValues.clear();
		selectedItems.addAll(toSet);
	}

	public void setSelection(T... values) {
		setSelection(Arrays.asList(values));
	}

	public void toggleSelection(T value) {
		if (!removeSelection(value))
			valueSelected(value);
	}

	@Override
	public boolean removeSelection(T value, boolean fireEvent) {
		removedValues.add(value);
		return selectedItems.remove(value);
	}

	@Override
	public boolean removeSelection(T value) {
		return removeSelection(value, true);
	}

	@Override
	public boolean removeSelection(Set<T> selectionToRemove) {
		removedValues.addAll(selectionToRemove);

		boolean toReturn = false;

		// not using removeAll since it removes completely and not only once (if the element is more than once)
		for (T selected : selectionToRemove) {
			int removeIndex = selectedItems.indexOf(selected);
			if (removeIndex != -1) {
				selectedItems.remove(removeIndex);
				toReturn = true;
			}
		}
		return toReturn;
	}

	@Override
	public void clearSelection() {
		selectedItems.clear();
	}

	public int getMaxSelected() {
		return maxSelected;
	}

	/**
	 * sets the maximum number of simultaneous selections. -1 means no limit. Default is 1
	 * 
	 * @param maxSelected
	 */
	public void setMaxSelected(int maxSelected) {
		this.maxSelected = maxSelected;
		if (maxSelected == -1)
			return;

		int selCount = selectedItems.size() - maxSelected;
		if (selCount > 0) {
			for (int i = 0; i < selCount; i++) {
				selectedItems.remove(0);
			}
		}
	}

	public List<T> getRemovedValues() {
		removedValues.removeAll(selectedItems);
		return removedValues;
	}

	@Override
	public List<T> getSelection() {
		return selectedItems;
	}

	public boolean isToggleMode() {
		return toggleMode;
	}

	public void setToggleMode(boolean toggleMode) {
		this.toggleMode = toggleMode;
	}

	public boolean isSelectOneOccurenceMode() {
		return selectOneOccurenceMode;
	}

	public void setSelectOneOccurenceMode(boolean selectOneOccurenceMode) {
		this.selectOneOccurenceMode = selectOneOccurenceMode;
		Set<T> uniques = new HashSet<T>();
		Set<T> duplicates = new HashSet<T>();
		for (T selected : selectedItems) {
			if (uniques.contains(selected))
				duplicates.add(selected);
			else
				uniques.add(selected);
		}
		removeSelection(duplicates);
	}

	/**
	 * FIXME Should merge this method with {@link AbstractSuggestBox#setReadOnly(boolean)}
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		putOption(new BooleanOption(DefaultOptions.DISABLED.name(), !enabled), true);
	}

	public boolean isEnabled() {
		// defaults to enabled (that's why when the option is not preset, we consider it's enabled)
		return !BooleanOption.isEnabled(DefaultOptions.DISABLED.name(), options);
	}

	@Override
	public <W extends ValueHolderItem<T>> W refresh(T value, W widget) {
		return widget;
	}

}
