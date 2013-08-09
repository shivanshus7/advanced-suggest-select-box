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

import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;

/**
 * Items in a multi-valued suggest box have to be aware of their container for
 * eventual removal.
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * @param <T>
 *          item value type
 * @param <C>
 *          concrete ValueHolderItem *
 */
public interface MultiChoiceValueHolderItem<T, C extends MultiChoiceValueHolderItem<T, C>>
		extends EventHandlingValueHolderItem<T> {

	/**
	 * Must be called when this item is added to a list to keep a reference on it
	 * for an eventual removal
	 * 
	 * @throws IllegalStateException
	 *           when the item is already associated to a list
	 */
	void addedTo(MultiChoiceListRenderer<T, C> list) throws IllegalStateException;

	/**
	 * Removes this item from the list it was associated to
	 */
	void remove();
}
