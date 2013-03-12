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

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

/**
 * Isolates the creation of suggested items rendering widgets. An instance of
 * this calss's implementation is considered as a context. It is therefore
 * accessible from {@link ValueHolderItem#getValueRendererFactory()}
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 *          the represented value type
 * @param <W>
 *          the returned widget type
 */
public interface ValueRendererFactory<T, W extends ValueHolderItem<T>> {
	/**
	 * Holds the items of the suggest box
	 * 
	 * @author Zied Hamdi founder of http://into-i.fr
	 * 
	 * @param <T>
	 *          the type of the value
	 */
	public static interface ListRenderer<T, W> extends IsWidget {

		/**
		 * elements in list
		 * 
		 * @return count
		 */
		int getWidgetCount();

		/**
		 * Empties the list (for recomputing)
		 */
		void clear();

		/**
		 * adds an item to the list
		 * 
		 * @param item
		 *          item
		 */
		void add(W item);

		/**
		 * returns the item at position index
		 * 
		 * @param index
		 *          position
		 * @return item
		 */
		W getAt(int index);

	}

	/**
	 * returns the widget representing an item in the suggest list
	 * 
	 * @param value
	 *          the item value
	 * @param filterText
	 *          the text that was typed in the suggest box
	 * @param options
	 *          you can set options on the {@link AbstractSuggestBox} that you
	 *          will find here
	 * @return the widget
	 */
	W createValueRenderer(T value, String filterText,
			Map<String, Option<?>> options);

	/**
	 * Creates the widget responsible for displaying the list of possible items
	 * 
	 * @return the widget
	 */
	ListRenderer<T, W> createListRenderer();

	/**
	 * Returns the {@link AbstractSuggestBox} implementation that handles the
	 * current renderer
	 * 
	 * @return
	 */
	AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> getSuggestBox();

	/**
	 * This method is called by the framework, you should never use it manually
	 */
	void setSuggestBox(
			AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox);

	public String toString(T value);

}
