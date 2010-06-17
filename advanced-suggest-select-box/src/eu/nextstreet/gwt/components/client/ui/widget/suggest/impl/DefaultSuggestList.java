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

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestWidget;

public class DefaultSuggestList<T> extends PopupPanel implements
		SuggestWidget<T> {
	private static final String POPUP_STYLE = "eu-nextstreet-SuggestPopup";

	public DefaultSuggestList() {
		this(true, false);
	}

	public DefaultSuggestList(boolean autoHide) {
		this(autoHide, false);
	}

	public DefaultSuggestList(boolean autoHide, boolean modal) {
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

}
