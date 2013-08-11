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
package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class DefaultSuggestOracle<T> extends SuggestOracle<T> {
	protected List<T> possiblilities = new ArrayList<T>();

	@Override
	public void requestSuggestions(eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle.Request request,
			eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle.Callback<T> callback) {
		String text = request.getQuery();
		int limit = request.getLimit();
		List<T> toReturn = new ArrayList<T>();
		for (T t : possiblilities) {
			if (accept(text, t)) {
				toReturn.add(t);
				if (toReturn.size() == limit)
					break;
			}
		}
		callback.onSuggestionsReady(request, new Response<T>(toReturn));

	}

	public void add(T t) {
		possiblilities.add(t);
	}

	public void clear() {
		possiblilities.clear();
	}

	public List<T> getPossiblilities() {
		return possiblilities;
	}

	public void setPossiblilities(List<T> possiblilities) {
		this.possiblilities = possiblilities;
	}

	/**
	 * used to define the filtering strategy, override and check in the inner list if this element should appear
	 * 
	 * @param text
	 * @param t
	 * @return true if the element should be included in the list
	 */
	@SuppressWarnings("unchecked")
	protected boolean accept(String text, T t) {
		if (text == null)
			return true;

		Map<String, Option<?>> options = contextWidget.getOptions();
		boolean caseSensitive = BooleanOption.isEnabled(DefaultOptions.CASE_SENSITIVE.name(), options);
		String stringFormula = contextWidget.toString(t);
		String stringValue = caseSensitive ? stringFormula : stringFormula.toUpperCase();
		String textValue = caseSensitive ? text : text.toUpperCase();
		boolean startsWith = BooleanOption.isEnabled(DefaultOptions.STARTS_WITH.name(), options);
		if (startsWith ? stringValue.startsWith(textValue) : (stringValue.indexOf(textValue) != -1)) {
			return true;
		}
		return false;
	}
}
