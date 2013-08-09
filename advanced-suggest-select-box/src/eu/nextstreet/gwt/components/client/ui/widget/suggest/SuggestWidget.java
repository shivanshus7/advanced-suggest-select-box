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
package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Widget representing the list of available suggestions (typicallay a popup)
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 */
public interface SuggestWidget<T> {

	void setWidget(ScrollPanel scrollPanel);

	boolean isShowing();

	void hide();

	void show();

	/**
	 * the left upper corner and right lower corner are set here to inform the
	 * list holder of the position of the text box
	 * 
	 * @param absoluteLeft
	 * @param absoluteTop
	 */
	void adjustPosition(int absoluteLeft, int absoluteTop);

	/**
	 * sets the style min-width attribute
	 * 
	 * @param pixels
	 */
	void setMinWidth(int pixels);

}
