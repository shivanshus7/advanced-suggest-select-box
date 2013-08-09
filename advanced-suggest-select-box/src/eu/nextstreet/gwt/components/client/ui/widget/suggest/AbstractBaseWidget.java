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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultStringFormulator;
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
public abstract class AbstractBaseWidget<T, P, E extends ChangeEvent> extends Composite implements StringFormulator<T> {
	protected Map<String, Option<?>> options = new HashMap<String, Option<?>>();
	protected SuggestOracle<T> suggestOracle;
	protected List<ChangeHandler> changeHandlerList = new ArrayList<ChangeHandler>();

	protected StringFormulator<T> stringFormulator = new DefaultStringFormulator<T>();

	public Option<?> getOption(String key) {
		return options.get(key);
	}

	public Option<?> putOption(Option<?> option) {
		return options.put(option.getKey(), option);
	}

	public Option<?> removeOption(String key) {
		return options.remove(key);
	}

	public Option<?> removeOption(Option<?> option) {
		return removeOption(option.getKey());
	}

	/**
	 * returns the set options
	 * 
	 * @return the set options
	 */
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
		this.stringFormulator = stringFormulator;
	}
}
