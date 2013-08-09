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

import com.google.gwt.event.dom.client.ChangeEvent;

/**
 * Represents a suggest box specific event change specifying if the value was
 * typed of selected and giving the type save selected value
 * 
 * @author Zied Hamdi founder of http://1vu.fr founder of http://into-i.fr
 * 
 * @param <T>
 */
public class SuggestChangeEvent<T, W extends EventHandlingValueHolderItem<T>> extends ChangeEvent {
	protected AbstractSuggestBox<T, W> source;
	protected T selection;
	protected boolean selected;
	protected boolean removed;
	protected String text;

	public SuggestChangeEvent(AbstractSuggestBox<T, W> suggestBox, T selection, boolean removed) {
		this.source = suggestBox;
		this.selection = selection;
		this.selected = true;
		this.removed = removed;
	}

	public SuggestChangeEvent(AbstractSuggestBox<T, W> suggestBox, T selection) {
		this(suggestBox, selection, false);
	}

	public SuggestChangeEvent(AbstractSuggestBox<T, W> suggestBox, String text) {
		this.source = suggestBox;
		this.text = text;
		this.selected = false;
	}

	public T getSelection() {
		return selection;
	}

	public void setSelection(T selection) {
		this.selection = selection;
	}

	public AbstractSuggestBox<T, W> getSource() {
		return source;
	}

	public void setSource(AbstractSuggestBox<T, W> source) {
		this.source = source;
	}

	/**
	 * Specifies if the value was selected to distinguish between a null selected
	 * value and a "non selected value"
	 * 
	 * @return true ifaoif the value was selected
	 */
	public boolean isSelected() {
		return selected;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
