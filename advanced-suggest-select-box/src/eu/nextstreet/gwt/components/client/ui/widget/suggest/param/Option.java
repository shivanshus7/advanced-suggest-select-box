/*
 * Copyright 2012 Zied Hamdi.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package eu.nextstreet.gwt.components.client.ui.widget.suggest.param;

/**
 * Used to hold an option value, the option is identified by its String key.
 * Options are saved into the suggest box and retrieved by the different actors
 * of the library
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          the value type of the option
 */
public interface Option<T> {

	/**
	 * the key under which the option will be stored
	 * 
	 * @return a dtring representation of the key
	 */
	String getKey();

	/**
	 * the value of the option
	 * 
	 * @return
	 */
	T getValue();
}
