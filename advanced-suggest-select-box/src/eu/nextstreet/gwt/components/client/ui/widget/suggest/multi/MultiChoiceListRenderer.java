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

import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl.MultiChoiceSuggestBox;

/**
 * @author Zied Hamdi
 * 
 */
public class MultiChoiceListRenderer<T, C extends MultiChoiceValueHolderItem<T, C>> extends FlowPanel implements ListRenderer<T, C> {

	protected AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox;

	public MultiChoiceListRenderer() {
		super();
	}

	public MultiChoiceListRenderer(AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox) {
		this.suggestBox = suggestBox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * .ListRenderer#add(java.lang.Object)
	 */
	@Override
	public void add(C item) {
		item.addedTo(this);
		super.add(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * .ListRenderer#getRow(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public C getAt(int index) {
		return (C) super.getWidget(index);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean remove(C item) {
		boolean removed = super.remove(item);
		((MultiChoiceSuggestBox) suggestBox).valueRemoved(item.getValue());
		return removed;
	}

}
