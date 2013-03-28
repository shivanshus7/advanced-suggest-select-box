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

import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;

/**
 * The default list renderer has all needed methods implemented by its parent
 * class
 * 
 * @author Zied Hamdi founder of http://into-i.fr
 * 
 * @param <T>
 */
public class DefaultListRenderer<T, W extends EventHandlingValueHolderItem<T>>
		extends VerticalPanel implements ListRenderer<T, W> {

	@Override
	public void add(W item) {
		super.add((Widget) item);
	}

	@SuppressWarnings("unchecked")
	public W getAt(int index) {
		return (W) super.getWidget(index);
	}

	@Override
	public boolean remove(W item) {
		return super.remove(item);
	}
}
