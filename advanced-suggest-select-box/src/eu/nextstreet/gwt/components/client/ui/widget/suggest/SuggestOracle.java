/*
 * Copyright 2008 Google Inc.
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
package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import java.util.Collection;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

@SuppressWarnings("rawtypes")
public abstract class SuggestOracle<T> {
	// private Response<T> emptyResponse = new Response<T>(new ArrayList<T>());
	protected AbstractBaseWidget contextWidget;

	/**
	 * Callback for {@link com.google.gwt.user.client.ui.SuggestOracle}. Every {@link Request} should be associated with a callback that should be called after a
	 * {@link Response} is generated.
	 */
	public interface Callback<T> {
		/**
		 * Consume the suggestions created by a {@link com.google.gwt.user.client.ui.SuggestOracle} in response to a {@link Request}.
		 * 
		 * @param request
		 *          the request
		 * @param response
		 *          the response
		 */
		void onSuggestionsReady(Request request, Response<T> response);
	}

	/**
	 * A {@link com.google.gwt.user.client.ui.SuggestOracle} request.
	 */
	public static class Request implements IsSerializable {
		private int limit = 20;
		private String query;

		/**
		 * Constructor for {@link Request}.
		 */
		public Request() {
		}

		/**
		 * Constructor for {@link Request}.
		 * 
		 * @param query
		 *          the query string
		 */
		public Request(String query) {
			setQuery(query);
		}

		/**
		 * Constructor for {@link Request}.
		 * 
		 * @param query
		 *          the query string
		 * @param limit
		 *          limit on the number of suggestions that should be created for this query
		 */
		public Request(String query, int limit) {
			setQuery(query);
			setLimit(limit);
		}

		/**
		 * Gets the limit on the number of suggestions that should be created.
		 * 
		 * @return the limit
		 */
		public int getLimit() {
			return limit;
		}

		/**
		 * Gets the query string.
		 * 
		 * @return the query string
		 */
		public String getQuery() {
			return query;
		}

		/**
		 * Sets the limit on the number of suggestions that should be created.
		 * 
		 * @param limit
		 *          the limit
		 */
		public void setLimit(int limit) {
			this.limit = limit;
		}

		/**
		 * Sets the query string used for this request.
		 * 
		 * @param query
		 *          the query string
		 */
		public void setQuery(String query) {
			this.query = query;
		}
	}

	/**
	 * {@link com.google.gwt.user.client.ui.SuggestOracle} response.
	 * 
	 * <p>
	 * Can optionally have truncation information provided. To indicate that there are more results but the number is not known, use:
	 * 
	 * <p>
	 * <code>response.setMoreSuggestions(true);</code>
	 * 
	 * <p>
	 * Or to indicate more results with an exact number, use:
	 * 
	 * <p>
	 * <code>response.setMoreSuggestionsCount(102);</code>
	 */
	public static class Response<T> implements IsSerializable {
		private Collection<T> suggestions;

		/**
		 * The response is considered to have "more suggestions" when the number of matching suggestions exceeds {@link Request#getLimit}, so the response
		 * suggestion list is truncated.
		 */
		private boolean moreSuggestions = false;

		/**
		 * Number of truncated suggestions.
		 */
		private int numMoreSuggestions = 0;

		/**
		 * Constructor for {@link Response}.
		 */
		public Response() {
		}

		/**
		 * Constructor for {@link Response}.
		 * 
		 * @param suggestions
		 *          each element of suggestions must implement the {@link Suggestion} interface
		 */
		public Response(Collection<T> suggestions) {
			setSuggestions(suggestions);
		}

		/**
		 * Gets how many more suggestions there are.
		 * 
		 * @return the count. if there no more suggestions or the number of more suggestions is unknown, returns 0.
		 */
		public int getMoreSuggestionsCount() {
			return this.numMoreSuggestions;
		}

		/**
		 * Gets the collection of suggestions. Each suggestion must implement the {@link Suggestion} interface.
		 * 
		 * @return the collection of suggestions
		 */
		public Collection<T> getSuggestions() {
			return this.suggestions;
		}

		/**
		 * Gets whether or not the suggestion list was truncated due to the {@link Request#getLimit}.
		 */
		public boolean hasMoreSuggestions() {
			return this.moreSuggestions;
		}

		/**
		 * Sets whether or not the suggestion list was truncated due to the {@link Request#getLimit}.
		 */
		public void setMoreSuggestions(boolean moreSuggestions) {
			this.moreSuggestions = moreSuggestions;
		}

		/**
		 * Sets whether or not the suggestion list was truncated due to the {@link Request#getLimit}, by providing an exact count of remaining suggestions.
		 * 
		 * @param count
		 *          number of truncated suggestions. Pass 0 to indicate there are no other suggestions, which is equivalent to {@link #setMoreSuggestions(boolean)
		 *          setMoreSuggestions(false)}.
		 */
		public void setMoreSuggestionsCount(int count) {
			this.numMoreSuggestions = count;
			this.moreSuggestions = (count > 0);
		}

		/**
		 * Sets the suggestions for this response. Each suggestion must implement the {@link Suggestion} interface.
		 * 
		 * @param suggestions
		 *          the suggestions
		 */
		public void setSuggestions(Collection<T> suggestions) {
			this.suggestions = suggestions;
		}
	}

	public SuggestOracle() {
	}

	/**
	 * Should {@link Suggestion} display strings be treated as HTML? If true, this all suggestions' display strings will be interpreted as HTML, otherwise as
	 * text.
	 * 
	 * @return by default, returns false
	 */
	public boolean isDisplayStringHTML() {
		return false;
	}

	/**
	 * Generate a {@link Response} based on a default request. The request query must be null as it represents the results the oracle should return based on no
	 * query string.
	 * <p>
	 * After the {@link Response} is created, it is passed into
	 * {@link Callback#onSuggestionsReady(com.google.gwt.user.client.ui.SuggestOracle.Request, com.google.gwt.user.client.ui.SuggestOracle.Response)} .
	 * </p>
	 * 
	 * @param request
	 *          the request
	 * @param callback
	 *          the callback to use for the response
	 */
	public void requestDefaultSuggestions(Request request, Callback<T> callback) {
		assert (request.query == null);
		requestSuggestions(request, callback);
	}

	/**
	 * Generate a {@link Response} based on a specific {@link Request}. After the {@link Response} is created, it is passed into
	 * {@link Callback#onSuggestionsReady(com.google.gwt.user.client.ui.SuggestOracle.Request, com.google.gwt.user.client.ui.SuggestOracle.Response)} .
	 * 
	 * @param request
	 *          the request
	 * @param callback
	 *          the callback to use for the response
	 */
	public abstract void requestSuggestions(Request request, Callback<T> callback);

	/**
	 * FIXME hide by an interface to allow to apply it on other widgets
	 * 
	 * @return
	 */
	public AbstractBaseWidget getContextWidget() {
		return contextWidget;
	}

	public void setContextWidget(AbstractBaseWidget suggestBox) {
		this.contextWidget = suggestBox;
	}
}
