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

import java.util.Map;

public class BooleanOption extends BasicOption<Boolean> {

	public BooleanOption() {
	}

	public BooleanOption(String key, Boolean value) {
		super(key, value);
	}

	public BooleanOption(String key) {
		super(key);
	}

	public static boolean isEnabled(String key, Map<String, Option<?>> options) {
		if (!options.containsKey(key))
			return false;

		Option<?> option = options.get(key);
		if (option instanceof BooleanOption)
			return (Boolean) option.getValue();
		else
			throw new IllegalArgumentException("Options under key '" + key + "' is not a boolean option : " + option.getClass());

	}
}
