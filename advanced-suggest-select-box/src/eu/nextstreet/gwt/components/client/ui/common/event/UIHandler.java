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
package eu.nextstreet.gwt.components.client.ui.common.event;

import eu.nextstreet.gwt.components.shared.ValidationException;

/**
 * Common UI use cases are treated by this interface that is implemented as a listener. Usually components will have
 * their internal behaviour that will be extensible by an external {@link UIHandler}. Notice that {@link UIHandler} can
 * be implemented as a set or list of {@link UIHandler}s
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 */
public interface UIHandler {
	/**
	 * Called when a validation error occurs
	 * 
	 * @param error
	 */
	void handleError(ValidationException error);

	/**
	 * Called when the validation occurs without errors
	 */
	void removeError();

	/**
	 * Called to decide whether to include the default text or not, usually calls {@link #handleTextStyles()} when
	 * finished
	 */
	void handleDefaultText();

	/**
	 * Handles the style depending on the current state: mandaory, errors, read only, default style. Can be extended for
	 * other notions
	 */
	void handleTextStyles();

	/**
	 * called each time a style is removed from the component
	 * 
	 * @param style
	 *            the css style to be applied FIXME should pass it an enum rather than the css
	 */
	void removeStyleName(String style);

	/**
	 * called each time a style is added to the component
	 * 
	 * @param style
	 *            the css style to be applied FIXME should pass it an enum rather than the css
	 */
	void addStyleName(String style);
}
