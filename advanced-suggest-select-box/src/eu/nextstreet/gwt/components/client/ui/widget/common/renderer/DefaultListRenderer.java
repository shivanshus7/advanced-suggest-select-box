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
package eu.nextstreet.gwt.components.client.ui.widget.common.renderer;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.common.WidgetValueMemory;

/**
 * The default list renderer has all needed methods implemented by its parent class
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 */
public class DefaultListRenderer<T, W extends EventHandlingValueHolderItem<T>> extends WidgetValueMemory<T, W> {

	protected FlowPanel panel = new FlowPanel();

	public DefaultListRenderer(ValueRendererFactory<T, ?> factory) {
		super(factory);
		initWidget(panel);
		panel.setStyleName("defaultListRenderer");
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

	@Override
	public void closeList() {
		super.closeList();
		SimplePanel closing = new SimplePanel();
		closing.setStyleName("last");
		panel.add(closing);
	}

}
