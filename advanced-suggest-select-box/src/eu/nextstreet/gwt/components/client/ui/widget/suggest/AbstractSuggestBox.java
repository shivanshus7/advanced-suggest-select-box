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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestionPopup;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultStringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;
import eu.nextstreet.gwt.components.shared.Validator;

/**
 * Suggest box (or select box) with many possibilities either in behavior and in
 * presentation.
 * 
 * The default event handling behavior must be connected explicitly by adding
 * the corresponding @UiHandler("textField") on your implementation: this
 * approach makes the default behavior accessible but non intrusive
 * 
 * @see DefaultSuggestBox for an implementation exemple
 * @author Zied Hamdi
 * 
 *         bugs: when a selection is directly replaced by characters, the enter
 *         button doesn't fire the event (it's postponed to the blur event).
 * 
 * @param <T>
 */
public abstract class AbstractSuggestBox<T, W extends EventHandlingValueHolderItem<T>>
		extends ChangeEventHandlerHolder<Boolean, SuggestChangeEvent<T, W>>
		implements StringFormulator<T> {

	private static final String SUGGEST_FIELD_COMP = "eu-nextstreet-SuggestFieldComp";
	private static final String SUGGEST_FIELD = "eu-nextstreet-SuggestFieldDetail";
	private static final String SUGGEST_FIELD_HOVER = "eu-nextstreet-SuggestFieldHover";
	private static final String SUGGEST_BOX_LOADING = "eu-nextstreet-AdvancedTextBoxDefaultText-loading";

	// private static SuggestBoxUiBinder uiBinder = GWT
	// .create(SuggestBoxUiBinder.class);
	protected T selected;
	protected String typed;
	protected Map<String, Option<?>> options = new HashMap<String, Option<?>>();
	protected SuggestWidget<T> suggestWidget = new DefaultSuggestionPopup<T>();
	protected ScrollPanel scrollPanel = new ScrollPanel();
	protected ListRenderer<T, W> listRenderer;
	protected boolean strictMode;
	protected StringFormulator<T> stringFormulator = new DefaultStringFormulator<T>();

	protected int selectedIndex = -1;

	private boolean recomputePopupContent = true;
	/**
	 * Specifies if enter is hit multiple times with same value, whether it
	 * generates a change event for each
	 */
	private boolean multipleChangeEvent;
	private boolean fireChangeOnBlur;

	protected ValueRendererFactory<T, W> valueRendererFactory;
	protected List<T> currentPossibilities;

	// @SuppressWarnings("rawtypes")
	// interface SuggestBoxUiBinder extends UiBinder<Widget, AbstractSuggestBox> {
	// }

	public AbstractSuggestBox() {
		this(null);
	}

	public AbstractSuggestBox(String defaultText) {
	}

	/**
	 * This method must be called in the implementation's constructor
	 * 
	 * @param defaultText
	 *          the defalt text. Can be null
	 */
	protected void init(String defaultText) {
		setStyleName(SUGGEST_FIELD_COMP);
		getTextField().setRepresenter(this);
		getTextField().setStyleName(SUGGEST_FIELD);
		getTextField().setDefaultText(defaultText);
		suggestWidget.setWidget(scrollPanel);
		setValueRendererFactory(new DefaultValueRendererFactory<T, W>(this));
	}

	// unused
	// @UiHandler("text")
	// public void onMouseOver(MouseOverEvent event) {
	// }
	//
	// @UiHandler("text")
	// public void onMouseOut(MouseOutEvent event) {
	// }

	/**
	 * Default double click handling
	 */
	// @UiHandler("textField")
	public void onDoubleClick(DoubleClickEvent event) {
		doubleClicked(event);
	}

	/**
	 * Default blur event handling
	 * 
	 * @param event
	 */
	// @UiHandler("textField")
	public void onBlur(BlurEvent event) {
		new Timer() {
			@Override
			public void run() {
				String currentText = getText();
				if (typed == null || !typed.equals(currentText)) {
					if (strictMode) {
						clearSelection();
					} else {
						valueTyped(currentText);
					}
				} else if (fireChangeOnBlur) {
					valueSelected(selected);
					fireChangeOnBlur = false;
				}
				if (currentText.trim().length() == 0)
					setText("");
			}
		}.schedule(200);
		if (isShowingSuggestList()) {
			new Timer() {
				@Override
				public void run() {
					hideSuggestList(false);
				}
			}.schedule(300);
		}
	}

	/**
	 * Default keyboard events handling
	 * 
	 * @param keyUpEvent
	 */
	// @UiHandler("textField")
	public void onKeyUp(KeyUpEvent keyUpEvent) {
		final int keyCode = keyUpEvent.getNativeKeyCode();

		if (keyCode == KeyCodes.KEY_TAB || keyCode == KeyCodes.KEY_ALT
				|| keyCode == KeyCodes.KEY_CTRL || keyCode == KeyCodes.KEY_SHIFT
				|| keyCode == KeyCodes.KEY_HOME || keyCode == KeyCodes.KEY_END) {
			return;
		} else if (keyCode == KeyCodes.KEY_UP || keyCode == KeyCodes.KEY_DOWN
				|| keyCode == KeyCodes.KEY_LEFT || keyCode == KeyCodes.KEY_RIGHT) {
			// don't recompute if only navigating
			handleKeyNavigation(keyCode);
			recomputePopupContent = false;
		} else if (keyCode == KeyCodes.KEY_DELETE
				|| keyCode == KeyCodes.KEY_BACKSPACE) {
			recomputePopupContent = true;

		} else if (keyCode == KeyCodes.KEY_DELETE
				|| keyCode == KeyCodes.KEY_BACKSPACE) {
			selectedIndex = -1;
		} else {
			recomputePopupContent = true;
		}
		if (recomputePopupContent) {
			SuggestPossibilitiesCallBack<T> callBack = new SuggestPossibilitiesCallBack<T>() {

				@Override
				public void setPossibilities(List<T> possibilities) {
					// the value was already set by the previous handler
					if (possibilities.size() == 1)
						return;

					EventHandlingValueHolderItem<T> popupWidget = getItemAt(selectedIndex);
					if (popupWidget != null && selectedIndex != -1)
						popupWidget.setSelected(false);
					int widgetCount = listRenderer.getWidgetCount();

					if (widgetCount == 0)
						return;

					if (keyCode == KeyCodes.KEY_ENTER) {
						if (isShowingSuggestList()) {
							if (popupWidget != null) {
								T t = popupWidget.getValue();
								fillValue(t, true);
							} else {
								// value not in list, this enter means OK.
								valueTyped(getText());
							}
							hideSuggestList();
						} else {
							if (multipleChangeEvent) {
								// popup is not visible, this enter means OK (check if the
								// value was entered from the list or from text).
								if (selected == null)
									valueTyped(getText());
								else
									fillValue(selected, multipleChangeEvent);
							}
						}
					} else if (keyCode == KeyCodes.KEY_ESCAPE) {
						hideSuggestList();
					} else if (keyCode == KeyCodes.KEY_TAB) {

					} else {
						recomputePopupContent = true;
						if (strictMode) {
							final StringBuffer reducingText = new StringBuffer(getText());
							recomputePopupContent(keyCode,
									new SuggestPossibilitiesCallBack<T>() {

										@Override
										public void setPossibilities(List<T> possibilities) {
											if (reducingText.length() > 1 && possibilities.size() < 1) {
												// FIXME should optimize by remembering the last valid
												// entry
												// (that has at least one possibility)
												reducingText.setLength(reducingText.length() - 1);
												setText(reducingText.toString());
												recomputePopupContent(keyCode, this);
											}
										}
									});
						}
					}
				}
			};
			recomputePopupContent(keyCode, callBack);
		}

	}

	/**
	 * Handles key navigation or triggers the loading of possibilities (if no data
	 * is available)
	 * 
	 * @param keyCode
	 *          the typed key
	 */
	protected void handleKeyNavigation(int keyCode) {
		if (currentPossibilities == null || !isShowingSuggestList()) {
			recomputePopupContent(keyCode);
		} else {
			int widgetCount = currentPossibilities.size();
			if (widgetCount == 0)
				return;
			int newSelectedIndex;
			if (keyCode == KeyCodes.KEY_DOWN) {
				newSelectedIndex = (selectedIndex + 1) % widgetCount;
				highlightSelectedValue(selectedIndex, newSelectedIndex);
			} else if (keyCode == KeyCodes.KEY_UP) {
				newSelectedIndex = (selectedIndex - 1) % widgetCount;
				if (newSelectedIndex < 0)
					newSelectedIndex += widgetCount;

				highlightSelectedValue(selectedIndex, newSelectedIndex);
			}
		}
	}

	/**
	 * returns true if the suggest list is visible
	 * 
	 * @return true if the suggest list is visible
	 */
	protected boolean isShowingSuggestList() {
		return suggestWidget.isShowing();
	}

	/**
	 * 
	 */
	protected void highlightSelectedValue(int oldSelectedIndex,
			int newSelectedIndex) {
		if (oldSelectedIndex != -1) {
			EventHandlingValueHolderItem<T> oldPopupWidget = getItemAt(oldSelectedIndex);
			oldPopupWidget.setSelected(false);
		}
		EventHandlingValueHolderItem<T> newPopupWidget = getItemAt(newSelectedIndex);

		if (newPopupWidget != null) {
			newPopupWidget.setSelected(true);
			UIObject uiObject = newPopupWidget.getUiObject();
			assert (uiObject != null);
			scrollPanel.ensureVisible(uiObject);
		}
		selectedIndex = newSelectedIndex;
	}

	/**
	 * Orders the suggest widget to be hidden
	 */
	protected void hideSuggestList(boolean resteSelectedIndex) {
		suggestWidget.hide();
		if (resteSelectedIndex)
			selectedIndex = -1;
	}

	private void hideSuggestList() {
		hideSuggestList(true);
	}

	/**
	 * Recomputes the content of the popup. Returns true to show there's no need
	 * to do more processing. Special cases are when one of the keys
	 * {@link KeyCodes#KEY_DOWN}, {@link KeyCodes#KEY_UP} is pressed: all possible
	 * values are presented
	 * 
	 * @param keyCode
	 */
	protected void recomputePopupContent(final int keyCode) {
		recomputePopupContent(keyCode, null);
	}

	protected void recomputePopupContent(final int keyCode,
			final SuggestPossibilitiesCallBack<T> callBack) {
		if (isReadOnly())
			return;

		String textValue = getText();
		SuggestPossibilitiesCallBack<T> suggestPossibilitiesCallBack = new SuggestPossibilitiesCallBack<T>() {

			@Override
			public void setPossibilities(List<T> possibilities) {
				getTextField().removeStyleName(SUGGEST_BOX_LOADING);

				AbstractSuggestBox.this.setPossibilities(keyCode, possibilities);
				if (callBack != null)
					callBack.setPossibilities(possibilities);
			}
		};
		getTextField().addStyleName(SUGGEST_BOX_LOADING);

		computeFiltredPossibilities(textValue, suggestPossibilitiesCallBack);
	}

	protected void setPossibilities(int keyCode, List<T> possibilities) {
		this.currentPossibilities = possibilities;
		if (possibilities.size() > 0) {
			listRenderer.clear();
			if (possibilities.size() == 1) {
				// laisse l'utilisateur effacer les valeurs
				if (keyCode != KeyCodes.KEY_BACKSPACE && keyCode != KeyCodes.KEY_LEFT
						&& keyCode != KeyCodes.KEY_RIGHT) {
					fillValue(possibilities.get(0), false);
				}
			}

			constructPopupContent(possibilities);

			showSuggestList();
		} else {
			hideSuggestList();
		}
	}

	protected void showSuggestList() {
		suggestWidget.adjustPosition(getTextField().getAbsoluteLeft(),
				getTextField().getAbsoluteTop() + getTextField().getOffsetHeight());
		highlightSelectedValue(-1, selectedIndex);
		suggestWidget.show();
	}

	protected void constructPopupContent(List<T> possibilities) {
		int size = possibilities.size();
		for (int i = 0; i < size; i++) {
			final T t = possibilities.get(i);
			String currentText = getText();
			if (checkSelected(t, currentText))
				selectedIndex = i;

			final W currentLabel = createValueRenderer(t, currentText);
			listRenderer.add(currentLabel);
			currentLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent clickEvent) {
					itemClicked(t);
				}
			});

			class MouseHoverhandler implements MouseOverHandler, MouseOutHandler {

				@Override
				public void onMouseOver(MouseOverEvent event) {
					currentLabel.hover(true);
				}

				@Override
				public void onMouseOut(MouseOutEvent event) {
					currentLabel.hover(false);
				}
			}

			MouseHoverhandler hoverhandler = new MouseHoverhandler();
			currentLabel.addMouseOverHandler(hoverhandler);
			currentLabel.addMouseOutHandler(hoverhandler);
		}
	}

	/**
	 * Override to use another strategy to determine if an item is selected
	 * 
	 * @param item
	 *          item to test
	 * @param currentText
	 *          the current field text
	 * @return true ifaoif <code>currentText</code> matches the item
	 *         <code>t</code>
	 */
	protected boolean checkSelected(final T item, String currentText) {
		String value = toString(item);
		boolean found = value.equals(currentText);
		return found;
	}

	private W createValueRenderer(final T t, String value) {
		final W currentLabel = valueRendererFactory.createValueRenderer(t, value,
				getOptions());
		return currentLabel;
	}

	/**
	 * returns the set options
	 * 
	 * @return the set options
	 */
	public Map<String, Option<?>> getOptions() {
		return options;
	}

	/**
	 * Returns the string representation of a value, this method is very important
	 * since it determines the equality of elements typed by hand with the ones in
	 * the list.
	 * 
	 * @param t
	 *          the value
	 * @return the string representation of a value
	 */
	public String toString(final T t) {
		return stringFormulator.toString(t);
	}

	private EventHandlingValueHolderItem<T> getItemAt(int index) {
		if (index != -1 && listRenderer.getWidgetCount() > index)
			return (EventHandlingValueHolderItem<T>) listRenderer.getAt(index);
		return null;
	}

	/**
	 * Fills a "type safe" value (one of the available values in the list).
	 * override to check existing values change
	 * 
	 * @param t
	 *          the selected value
	 * @param commit
	 *          consider a value changed only if commit is true (otherwise you can
	 *          have duplicate events)
	 * @return true if the suggest box has to be hidden. Subclasses can override
	 *         this method and return false to force the suggest widget to remain
	 *         open even if there's only one element remaining in the list of
	 *         choices
	 */
	protected boolean fillValue(final T t, boolean commit) {
		String textValue = toString(t);
		getTextField().setText(textValue);
		hideSuggestList();
		getTextField().setFocus(true);
		selected = t;
		typed = textValue;
		if (commit) {
			valueSelected(t);
		} else {
			// the change is not notified immediately since we just suggest the
			// value t. We keep a flag to know there was no notification
			fireChangeOnBlur = true;
		}
		return true;
	}

	/**
	 * Called when a value is selected from the list, if the value is typed on the
	 * keyboard and only one possible element corresponds, this method will be
	 * called immediately only if <code>multipleChangeEvent</code> is true.
	 * Otherwise it will wait until a blur event occurs Notice that if
	 * <code>multipleChangeEvent</code> is true, this method will be called also
	 * each time the enter key is typed
	 * 
	 * @param value
	 */
	public void valueSelected(T value) {
		fireChangeOccured(true);
	}

	/**
	 * Called when a typed value is confirmed whether by pressing the enter key,
	 * or on blur. Notice that this method behavior also can be changed thanks to
	 * the property {@link #multipleChangeEvent} which specifies if the method has
	 * to be called on each enter key press or only on the first one.
	 * 
	 * @param value
	 */
	public void valueTyped(String value) {
		selected = null;
		// if (defautText != null && defautText.equals(value))
		// value = "";
		typed = value;
		fireChangeOccured(false);
	}

	/**
	 * returns the value selected from list, if a text was typed then returns null
	 * 
	 * @return the value selected from list, if a text was typed then returns null
	 */
	public T getSelected() {
		return selected;
	}

	/**
	 * 
	 * @return the typed value, this can also be the selected one from list: to
	 *         check if the value belongs to the list check if
	 *         {@link #getSelected()} returns null
	 */
	public String getTyped() {
		return typed;
	}

	@Override
	protected SuggestChangeEvent<T, W> changedValue(Boolean selected) {
		if (selected)
			return new SuggestChangeEvent<T, W>(this, getSelected());
		return new SuggestChangeEvent<T, W>(this, getText());
	}

	/**
	 * Returns the text currently in the text field, this method can have
	 * different results before and after the call of
	 * {@link #recomputePopupContent(int)} which auto completes the text
	 * automatically if only one result remains.
	 * 
	 * @return the text fiel
	 */
	public String getText() {
		return getTextField().getTextValue();
	}

	public void setText(String str) {
		getTextField().setText(str);
		typed = str;
	}

	/**
	 * Not used in the the library: utility method to get the typed item
	 * corresponding to a given text
	 * 
	 * <b>Warning!</b> this methods calls
	 * {@link #computeFiltredPossibilities(String, SuggestPossibilitiesCallBack)}
	 * that could make a call to the server, you should avoid calling it.
	 * 
	 * @param text
	 *          the typed text
	 * @return the item that corresponds to text or null if none
	 */
	public void computeSelected(String text) {
		computeFiltredPossibilities(text, new SuggestPossibilitiesCallBack<T>() {

			@Override
			public void setPossibilities(List<T> possibilities) {
				if (possibilities.size() == 1) {
					setSelectedValue(possibilities.get(0));
				} else {
					setSelectedValue(null);
				}
			}
		});
	}

	protected void setSelectedValue(T selected) {
		this.selected = selected;
	}

	public boolean isEmpty() {
		return getText().length() == 0;
	}

	public void setEnabled(boolean enabled) {
		getTextField().setEnabled(enabled);
	}

	public void setFocus(boolean focus) {
		getTextField().setFocus(focus);
	}

	protected abstract void computeFiltredPossibilities(String text,
			SuggestPossibilitiesCallBack<T> suggestPossibilitiesCallBack);

	/**
	 * returns the curent suggest renderer factory
	 * 
	 * @return the curent suggest box renderer factory
	 */
	public ValueRendererFactory<T, ? extends EventHandlingValueHolderItem<T>> getValueRendererFactory() {
		return valueRendererFactory;
	}

	/**
	 * Sets the items renderer factory: you can define your own item factory to
	 * control the way items are shown in the suggest list
	 * 
	 * @param valueRendererFactory
	 */
	public void setValueRendererFactory(
			ValueRendererFactory<T, W> valueRendererFactory) {
		this.valueRendererFactory = valueRendererFactory;
		valueRendererFactory
				.setSuggestBox((AbstractSuggestBox<T, EventHandlingValueHolderItem<T>>) this);
		if (listRenderer != null) {
			listRenderer.clear();
			scrollPanel.clear();
		}
		listRenderer = valueRendererFactory.createListRenderer();
		scrollPanel.add((Widget) listRenderer);
		System.out.println();
	}

	public SuggestWidget<T> getSuggestWidget() {
		return suggestWidget;
	}

	/**
	 * Sets the suggesting list holder widget
	 * 
	 * @param suggestWidget
	 */
	public void setSuggestWidget(SuggestWidget<T> suggestWidget) {
		this.suggestWidget = suggestWidget;
	}

	public Object getOption(String key) {
		return options.get(key);
	}

	public Option<?> putOption(Option<?> option) {
		return options.put(option.getKey(), option);
	}

	public Option<?> removeOption(String key) {
		return options.remove(key);
	}

	public Option<?> removeOption(Option<?> option) {
		return removeOption(option.getKey());
	}

	/**
	 * Controls whether the method {@link #valueSelected(Object)} expressing a
	 * change event will be called each time a value is selected or if it has to
	 * wait until a blur occurs.
	 * 
	 * @return true if the event is immediate
	 */
	public boolean isMultipleChangeEvent() {
		return multipleChangeEvent;
	}

	public void setMultipleChangeEvent(boolean multipleChangeEvent) {
		this.multipleChangeEvent = multipleChangeEvent;
	}

	public boolean isStrictMode() {
		return strictMode;
	}

	public void setStrictMode(boolean strictMode) {
		this.strictMode = strictMode;
	}

	public String getDefaultText() {
		return getTextField().getDefaultText();
	}

	public void setDefaultText(String text) {
		this.getTextField().setDefaultText(text);
	}

	/**
	 * action when the mouse hovers the arrow button
	 * 
	 * @param onButton
	 */
	protected void mouseOnButton(boolean onButton) {
		if (onButton && !isReadOnly())
			getTextField().addStyleName(SUGGEST_FIELD_HOVER);
		else
			getTextField().removeStyleName(SUGGEST_FIELD_HOVER);
	}

	/**
	 * action when the suggest box is double clicked
	 * 
	 * @param event
	 */
	protected void doubleClicked(DoubleClickEvent event) {
		this.getTextField().setSelectionRange(0, getText().length());
		recomputePopupContent(KeyCodes.KEY_RIGHT);
	}

	/**
	 * action on an item click
	 * 
	 * @param t
	 */
	protected void itemClicked(T t) {
		fillValue(t, true);
	}

	public Validator<String> getValidator() {
		return getTextField().getValidator();
	}

	public void setValidator(Validator<String> validator) {
		getTextField().setValidator(validator);
	}

	public String getErrorTextStyle() {
		return getTextField().getErrorTextStyle();
	}

	public String getMandatoryTextStyle() {
		return getTextField().getMandatoryTextStyle();
	}

	public boolean isMandatory() {
		return getTextField().isMandatory();
	}

	public void setDefaultTextStyle(String defaultTextStyle) {
		getTextField().setDefaultTextStyle(defaultTextStyle);
	}

	public void setErrorTextStyle(String errorTextStyle) {
		getTextField().setErrorTextStyle(errorTextStyle);
	}

	public void setMandatory(boolean mandatory) {
		getTextField().setMandatory(mandatory);
	}

	public void setMandatoryTextStyle(String defaultMandatoryTextStyle) {
		getTextField().setMandatoryTextStyle(defaultMandatoryTextStyle);
	}

	public String getDefaultTextStyle() {
		return getTextField().getDefaultTextStyle();
	}

	public String getReadOnlyTextStyle() {
		return getTextField().getReadOnlyTextStyle();
	}

	public boolean isReadOnly() {
		return getTextField().isReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		getTextField().setReadOnly(readOnly);
	}

	public void setReadOnlyTextStyle(String readOnlyTextStyle) {
		getTextField().setReadOnlyTextStyle(readOnlyTextStyle);
	}

	public void setSelected(T selected) {
		this.selected = selected;
		setText(toString(selected));
		valueSelected(selected);
	}

	public abstract SuggestTextBoxWidget<T, W> getTextField();

	public void highlightSelectedValue() {
		highlightSelectedValue(-1, selectedIndex);
	}

	public StringFormulator<T> getStringFormulator() {
		return stringFormulator;
	}

	public void setStringFormulator(StringFormulator<T> stringFormulator) {
		this.stringFormulator = stringFormulator;
	}

	public void clearSelection() {
		setText("");
		valueTyped("");
	}

}