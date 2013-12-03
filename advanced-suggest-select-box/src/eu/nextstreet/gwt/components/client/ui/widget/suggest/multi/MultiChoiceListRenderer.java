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

import com.google.gwt.user.client.ui.FlowPanel;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.common.WidgetValueMemory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;

/**
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 */
public class MultiChoiceListRenderer<T, C extends MultiChoiceValueHolderItem<T, C>> extends WidgetValueMemory<T, C> {

	protected AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox;
	protected FlowPanel panel = new FlowPanel();

	public MultiChoiceListRenderer(AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox, ValueRendererFactory<T, C> factory) {
		super(factory);
		initWidget(panel);
		this.suggestBox = suggestBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory .ListRenderer#add
	 */
	@Override
	public void add(T value, C item) {
		super.add(value, item);
		panel.add(item);
		item.addedTo(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory .ListRenderer#getRow(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public C getAt(int index) {
		return (C) panel.getWidget(index);
	}

	@Override
	public boolean remove(C item) {
		super.remove(item);
		suggestBox.removeSelection(item.getValue());
		return panel.remove(item);
	}

	@Override
	public void clear() {
		super.clear();
		panel.clear();
	}

}
