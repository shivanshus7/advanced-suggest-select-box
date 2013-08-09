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

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Representation of an item in the suggest list
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 *          value type
 */
public interface ValueHolderItem<T> extends IsWidget {
	public static final String ITEM_DEFAULT_STYLE = "eu-nextstreet-SuggestItem";

	/**
	 * returns the value it represents
	 * 
	 * @return the value it represents
	 */
	public T getValue();

	/**
	 * sets the value to be rendered. This method should handle matching text
	 * highlighting also.
	 * 
	 * @param value
	 */
	public void setValue(T value);

	/**
	 * Highlights the item if it's the one selected
	 * 
	 * @param selected
	 *          true is is selected
	 */
	public void setSelected(boolean selected);

	/**
	 * Highlights the item if it's the one hovered
	 * 
	 * @param hover
	 */
	public void hover(boolean hover);

	/**
	 * sets the style name of the renderer
	 * 
	 * @param item
	 */
	public void setStyleName(String item);

	/**
	 * Returns a {@link UIObject} represented on the screen for this value holder
	 * 
	 * @return a {@link UIObject} represented on the screen for this value holder
	 */
	public UIObject getUiObject();

	/**
	 * Any created instance must be aware of its factory since it can hold context
	 * specific information
	 * 
	 * @return the factory that created this instance
	 */
	ValueRendererFactory<T, ?> getValueRendererFactory();

	/**
	 * Used to separate constructor from initialisation
	 */
	void initWidget();

}
