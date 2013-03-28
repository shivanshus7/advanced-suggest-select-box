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

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Handles the non visual aspects of this pattern. It extends
 * {@link SimplePanel} so concrete implementations can extends them (as Widgets)
 * 
 * @param <T>
 *          item value type
 * @param <C>
 *          concrete ValueHolderItem
 * @author Zied Hamdi
 * 
 */
public abstract class AbstractMultiChoiceValueHolderItem<T, C extends MultiChoiceValueHolderItem<T, C>>
		extends SimplePanel implements MultiChoiceValueHolderItem<T, C> {

	/** item's value */
	protected T value;

	/** list containing this item */
	protected MultiChoiceListRenderer<T, C> associatedList;

	public AbstractMultiChoiceValueHolderItem(T value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel#
	 * getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderLabel#
	 * setValue(java.lang.Object)
	 */
	@Override
	public void setValue(T value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.
	 * MultiChoiceValueHolderItem
	 * #addedTo(eu.nextstreet.gwt.components.client.ui.widget
	 * .suggest.multi.MultiChoiceListRenderer)
	 */
	@Override
	public void addedTo(MultiChoiceListRenderer<T, C> list)
			throws IllegalStateException {
		if (associatedList != null)
			throw new IllegalStateException("This item " + value
					+ " is already bound to a list");
		associatedList = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.
	 * MultiChoiceValueHolderItem#remove()
	 */
	@Override
	public void remove() {
		associatedList.remove((C) this);
	}

}
