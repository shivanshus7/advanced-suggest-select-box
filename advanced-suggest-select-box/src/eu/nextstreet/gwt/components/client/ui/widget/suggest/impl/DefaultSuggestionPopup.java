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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestWidget;

/**
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 */
public class DefaultSuggestionPopup<T> extends PopupPanel implements SuggestWidget<T> {
	public static boolean DEBUG_MODE = false;
	private static final String POPUP_STYLE = "advSugPopup";

	public DefaultSuggestionPopup() {
		this(!DEBUG_MODE, false);
	}

	public DefaultSuggestionPopup(boolean autoHide) {
		this(autoHide, false);
	}

	public DefaultSuggestionPopup(boolean autoHide, boolean modal) {
		super(autoHide, modal);
		setStylePrimaryName(POPUP_STYLE);
	}

	@Override
	public void setWidget(ScrollPanel scrollPanel) {
		super.setWidget(scrollPanel);
	}

	@Override
	public void adjustPosition(int absoluteLeft, int absoluteTop) {
		super.setPopupPosition(absoluteLeft, absoluteTop);
	}

	public void setMinWidth(int pixels) {
		Element elem = getElement();
		elem.getStyle().setPropertyPx("minWidth", pixels);
	}

	@Override
	public void hide() {
		// for comment on debugging
		if (!DEBUG_MODE)
			super.hide();
	}

}
