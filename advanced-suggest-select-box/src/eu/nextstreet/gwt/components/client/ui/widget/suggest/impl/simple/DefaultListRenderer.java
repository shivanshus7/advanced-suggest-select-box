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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.common.WidgetValueMemory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;

/**
 * The default list renderer has all needed methods implemented by its parent class
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 */
public class DefaultListRenderer<T, W extends EventHandlingValueHolderItem<T>> extends WidgetValueMemory<T, W> {

	protected VerticalPanel panel = new VerticalPanel();

	public DefaultListRenderer() {
		initWidget(panel);
		panel.setSpacing(0);
	}

	@Override
	public void add(T value, W item) {
		super.add(value, item);
		panel.add((Widget) item);
	}

	@Override
	public void clear() {
		super.clear();
		panel.clear();
	}

	@SuppressWarnings("unchecked")
	public W getAt(int index) {
		return (W) panel.getWidget(index);
	}

}
