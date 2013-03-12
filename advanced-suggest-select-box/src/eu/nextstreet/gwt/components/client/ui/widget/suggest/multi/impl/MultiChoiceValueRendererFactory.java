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

package eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;

import eu.nextstreet.gwt.components.client.ui.common.data.ValueRepresentationTransformer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.AbstractValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.MultiChoiceListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.MultiChoiceValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

/**
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          item value type
 * @param <C>
 *          concrete ValueHolderItem
 */
public class MultiChoiceValueRendererFactory<T, C extends MultiChoiceValueHolderItem<T, C>>
		extends AbstractValueRendererFactory<T, C> {

	/** transforms a value into its icon representation */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ValueRendererFactory<T, ValueHolderItem<T>> textRendererFactory = new DefaultValueRendererFactory();
	protected ValueRepresentationTransformer<T, Image> iconLinker;

	/**
	 * @param iconLinker
	 *          the value transformer into icons
	 */
	public MultiChoiceValueRendererFactory(
			ValueRepresentationTransformer<T, Image> iconLinker,
			ValueRendererFactory<T, ValueHolderItem<T>> textRendererFactory) {
		this.iconLinker = iconLinker;
		if (textRendererFactory != null)
			this.textRendererFactory = textRendererFactory;
	}

	public MultiChoiceValueRendererFactory(
			ValueRepresentationTransformer<T, Image> iconLinker) {
		this(iconLinker, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * #createValueRenderer(java.lang.Object, java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public C createValueRenderer(T value, String filterText,
			Map<String, Option<?>> options) {
		C toReturn = (C) new MultiChoiceValueHolderLabel<T>(value, this,
				textRendererFactory);
		toReturn.initWidget();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * #createListRenderer()
	 */
	@Override
	public eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer<T, C> createListRenderer() {
		return new MultiChoiceListRenderer<T, C>();
	}

	/**
	 * returns the icon linker to values
	 * 
	 * @return the icon linker to values
	 */
	public ValueRepresentationTransformer<T, Image> getIconLinker() {
		return iconLinker;
	}

	@Override
	public void setSuggestBox(
			AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox) {
		super.setSuggestBox(suggestBox);
		textRendererFactory.setSuggestBox(suggestBox);
	}

}
