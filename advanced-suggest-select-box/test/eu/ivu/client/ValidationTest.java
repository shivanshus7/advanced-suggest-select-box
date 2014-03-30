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
package eu.ivu.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.IntoGwt;
import eu.nextstreet.gwt.components.client.ui.common.data.ValueRepresentationTransformer;
import eu.nextstreet.gwt.components.client.ui.panel.StatePanel;
import eu.nextstreet.gwt.components.client.ui.panel.StatePanel.SimplePanelState;
import eu.nextstreet.gwt.components.client.ui.widget.AdvancedTextBox;
import eu.nextstreet.gwt.components.client.ui.widget.common.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.AbstractValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.concept.CommentWidget;
import eu.nextstreet.gwt.components.client.ui.widget.select.DefaultPanelValueSelector;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractBaseWidget;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestChangeEvent;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl.DefaultIconedSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl.IconedValueRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl.IconedValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultOptions;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultSuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table.SimpleTableRowItemRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.table.SimpleTableValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl.MultiChoiceSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl.MultiChoiceValueHolderLabel;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.multi.impl.MultiChoiceValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.shared.BasicListValidator;
import eu.nextstreet.gwt.components.shared.ValidationException;
import eu.nextstreet.gwt.components.shared.Validator;

public class ValidationTest {

	/**
	 * This is the fictive value class we use in our tests
	 * 
	 * @author Zied Hamdi founder of http://1vu.fr
	 * 
	 */
	static class Value {
		String str;
		String url;

		public Value(String str, String url) {
			this.str = str;
			this.url = url;
		}

		@Override
		public String toString() {
			// toString is unwanted
			return "#(" + str + ")";
		}

	}

	/**
	 * This class aims to give the icon corresponding to each value
	 */
	static final ValueRepresentationTransformer<Value, Image> iconLinker = new ValueRepresentationTransformer<Value, Image>() {

		@Override
		public Image transform(Value value) {
			return new Image("img/chrome_extentions/" + value.str + ".png");
		}
	};

	static Value blogger = new Value("Blogger", "https://chrome.google.com/webstore/detail/mmoheajlpfaigefceljflpohdehkjbli");
	static Value chromeToolBox = new Value("Chrome Toolbox", "https://chrome.google.com/webstore/detail/fjccknnhdnkbanjilpjddjhmkghmachn");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void testWidget() {
		StringFormulator<ValidationTest.Value> stringFormulator = new StringFormulator<ValidationTest.Value>() {

			@Override
			public String toString(Value t) {
				return t.str;
			}
		};

		RootPanel suggestBoxContainer = RootPanel.get("suggestBoxContainer");
		if (suggestBoxContainer == null) {
			System.err
					.println("Library Test code is enabled while it seems it shouldn't. Please inform the author of the library to turn off the test or set eu.nextstreet.gwt.components.client.IntoGwt.test=false;");
			return;
		}
		// Can't use generics in this example since we switch between a table
		// and a label view so the second argument is ? extends
		// ValueHolderLabel<Value>
		// final DefaultSuggestBox<Value,ValueHolderLabel<Value>> box = new
		// DefaultSuggestBox<Value,
		// ValueHolderLabel<Value>>("select or type value");
		final DefaultIconedSuggestBox box = new DefaultIconedSuggestBox<Value, IconedValueHolderItem<Value>>("select or type value");
		box.setStringFormulator(stringFormulator);
		box.putOption(new BooleanOption(DefaultOptions.STARTS_WITH.name(), false));
		fillData(box);

		box.setIconLinker(iconLinker);

		suggestBoxContainer.add(box);
		// // box.setText("Chrome Toolbox");
		// // box.computeSelected(box.getText());
		// //
		// // Object selected = box.getSelected();
		// // if (selected != chromeToolBox)
		// // throw new IllegalStateException("Chrome Toolbox should be selected,"
		// // + selected + " found instead");
		//
		// System.out.println("selected : " + selected);

		box.setSelected(blogger);

		VerticalPanel options = fillOptions(box);

		RootPanel.get("options").add(options);

		addLogInfoPanel(box);

		initPanelSelector();

		initStatePanel();

		CommentWidget commentWidget = new CommentWidget(null, "click to comment");
		RootPanel.get("commentWidget").add(commentWidget);

		AdvancedTextBox advancedTextBox = new AdvancedTextBox("Please type a value");
		advancedTextBox.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				Window.alert("Double clicked");
			}
		});
		RootPanel.get("advancedTextBox").add(advancedTextBox);

		MultiChoiceValueRendererFactory<Value, MultiChoiceValueHolderLabel<Value>> choiceItemsRendererFactory = new MultiChoiceValueRendererFactory<Value, MultiChoiceValueHolderLabel<Value>>(
				iconLinker);
		final MultiChoiceSuggestBox<Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>> multiBox = new MultiChoiceSuggestBox<ValidationTest.Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>>(
				"select or type value", DockPanel.NORTH, choiceItemsRendererFactory);

		// FIXME some default behavior like: only icons or only text should be
		// possible through options
		((AbstractValueRendererFactory) choiceItemsRendererFactory.getTextRendererFactory()).setStringFormulator(new StringFormulator<Value>() {

			@Override
			public String toString(Value t) {
				return "";
			}
		});

		multiBox.setStringFormulator(stringFormulator);
		multiBox.putOption(new BooleanOption(DefaultOptions.STARTS_WITH.name(), true));
		multiBox.setStrictMode(true);
		multiBox.getTextField().getTop().setStyleName("eu-nextstreet-MultiChoiceSelection");
		fillData(multiBox);
		multiBox.setIconLinker(iconLinker);

		List<Value> multiSelection = new ArrayList<ValidationTest.Value>();
		multiSelection.add(blogger);
		multiSelection.add(chromeToolBox);
		multiBox.setSelection(multiSelection);

		multiBox.addHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				VerticalPanel logPanel = new VerticalPanel();
				SuggestChangeEvent suggestEvent = (SuggestChangeEvent) event;
				MultiChoiceSuggestBox<Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>> source = (MultiChoiceSuggestBox) suggestEvent.getSource();
				Value selected = (Value) suggestEvent.getSelection();
				if (selected != null) {
					logPanel.add(new HTML("selection changed: <i><b>'" + selected.str + "'</b></i> was " + (suggestEvent.isRemoved() ? "removed" : "added")));
				} else {
					logPanel.add(new HTML("text <i><b>'" + suggestEvent.getText() + "'</b></i> was typed"));
				}
				logPanel.add(new HTML("current selection : "));
				logPanel.add(new HTML("<span style='color: gray'>" + source.getSelection() + "</span>"));
				logPanel.add(new HTML("currently removed values : "));
				logPanel.add(new HTML("<span style='color: gray'>" + source.getRemovedValues() + "</span>"));
				RootPanel suggestBoxMultiValueLog = RootPanel.get("suggestBoxMultiValueLog");
				suggestBoxMultiValueLog.clear();
				suggestBoxMultiValueLog.add(logPanel);
			}
		});

		RootPanel.get("suggestBoxMultiValueContainer").add(multiBox);

	}

	protected static void initPanelSelector() {
		DefaultPanelValueSelector<Value> panelSelector = new DefaultPanelValueSelector<ValidationTest.Value>();
		fillData(panelSelector);
		panelSelector.init();
		RootPanel selectionPanel = RootPanel.get("selectionPanel");
		selectionPanel.add(panelSelector);
	}

	protected static void initStatePanel() {
		StatePanel<SimplePanelState> statePanel = new StatePanel<SimplePanelState>("UNPRESSED", "PRESSED", "flat", "green");
		statePanel.setStyleName("sPanel");

		FlowPanel innerPanels = new FlowPanel();
		statePanel.setWidget(innerPanels);

		final HTML stateListenerLabel = new HTML("Click anywhere on this panel to change its parent's state. <br/>");
		innerPanels.add(stateListenerLabel);

		TextBox textBox = new TextBox();
		textBox.setText("Clicking in the text box is safe");
		textBox.setWidth("300px");
		innerPanels.add(textBox);

		final HTML stateListenerLabel2 = new HTML("Current state: " + statePanel.getState().name());
		innerPanels.add(stateListenerLabel2);

		statePanel.setSkipElements(textBox);

		stateListenerLabel.getElement().setAttribute("style", "color:gray;");
		statePanel.addValueChangeHandler(new ValueChangeHandler<SimplePanelState>() {

			@Override
			public void onValueChange(ValueChangeEvent<SimplePanelState> event) {
				stateListenerLabel2.setHTML("Current state: " + event.getValue().name());
			}
		});
		RootPanel statePanelHolder = RootPanel.get("statePanel");
		statePanelHolder.add(statePanel);
	}

	@SuppressWarnings("rawtypes")
	protected static void addLogInfoPanel(final DefaultIconedSuggestBox box) {
		final VerticalPanel infoContainer = new VerticalPanel();
		RootPanel.get("infoContainer").add(infoContainer);
		final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("HH:mm:ss");
		box.addHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (infoContainer.getWidgetCount() > 3)
					infoContainer.remove(3);
				infoContainer.insert(new Label("At " + dateTimeFormat.format(new Date()) + " you "
						+ (((SuggestChangeEvent) event).isSelected() ? "selected " : "typed ") + ((AbstractSuggestBox) event.getSource()).getText()), 0);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	protected static VerticalPanel fillOptions(final DefaultIconedSuggestBox box) {
		CheckBox startsWith = startsWithOption(box);

		CheckBox caseSensitive = caseSensitiveOption(box);

		// final CheckBox style = roundedCornersOption(box);

		final CheckBox strict = strictModeOption(box);

		final CheckBox mandatory = mandaoryValueOption(box);
		final CheckBox readOnly = readOnlyOption(box);

		final CheckBox tableRenderer = tableRendererOption(box);

		CheckBox validationOption = validationOption(box);

		VerticalPanel options = new VerticalPanel();
		options.add(tableRenderer);
		options.add(validationOption);
		options.add(strict);
		options.add(mandatory);
		options.add(readOnly);
		options.add(new Label("---- filtering examples ----"));
		options.add(startsWith);
		options.add(caseSensitive);
		// options.add(style);
		return options;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void fillData(final AbstractBaseWidget box) {
		DefaultSuggestOracle<Value> suggestOracle = (DefaultSuggestOracle<Value>) box.getSuggestOracle();
		suggestOracle.add(blogger);
		suggestOracle.add(new Value("Calendar", "https://chrome.google.com/webstore/detail/ookhcbgokankfmjafalglpofmolfopek"));
		suggestOracle.add(new Value("Chrome For a Cause", "https://chrome.google.com/webstore/detail/bbfammmagchhaohncbhghoohcfoeckdi"));
		suggestOracle.add(chromeToolBox);
		suggestOracle.add(new Value("Google Documents", "https://chrome.google.com/webstore/detail/nnbmlagghjjcbdhgmkedmbmedengocbn"));
		suggestOracle.add(new Value("GWT Developer Plugin", "http://code.google.com/webtoolkit/"));
		suggestOracle.add(new Value("Screen Capture", "https://chrome.google.com/webstore/detail/cpngackimfmofbokmjmljamhdncknpmg"));
		suggestOracle.add(new Value("Send From Gmail", "https://chrome.google.com/webstore/detail/pgphcomnlaojlmmcjmiddhdapjpbgeoc"));
		suggestOracle.add(new Value("Similar Pages", "https://chrome.google.com/webstore/detail/pjnfggphgdjblhfjaphkjhfpiiekbbej"));
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox validationOption(final DefaultIconedSuggestBox box) {
		final CheckBox validationOption = new CheckBox("Validate (min: 3 chars)");
		final BasicListValidator<String> listValidator = new BasicListValidator<String>();
		listValidator.add(new Validator<String>() {

			@Override
			public void validate(String value) throws ValidationException {
				if (value.length() < 3)
					throw new ValidationException("Text is too short");

			}
		});

		validationOption.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setValidator(event.getValue() ? listValidator : null);
			}

		});
		return validationOption;

	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox tableRendererOption(final DefaultIconedSuggestBox box) {
		final CheckBox multiColumn = new CheckBox("Html Table Mode");
		multiColumn.setValue(box.isReadOnly());
		multiColumn.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				SimpleTableValueRendererFactory<Value, SimpleTableRowItemRenderer<Value>> tableRendererFactory = new SimpleTableValueRendererFactory<Value, SimpleTableRowItemRenderer<Value>>() {

					@SuppressWarnings("serial")
					@Override
					protected SimpleTableRowItemRenderer<Value> newInstance(Value value, String filterText, boolean caseSensitive) {
						SimpleTableRowItemRenderer<Value> simpleTableRowItemRenderer = new SimpleTableRowItemRenderer<Value>(value, filterText, caseSensitive, this) {

							@Override
							protected String[] explodeValueInColumns(Value value, String filterText, boolean caseSensitive) {
								if (value == null)
									return new String[] { "", "", "", "", "", "" };

								return new String[] {"img/chrome_extentions/" + value.str + ".png", value.str, "<a href='" + value.url + "' target='_blank'>View</a>",
										//@formatter:off
										"<span style='color:green; padding-left: 5px; border-letf:1px solid;'>col3</span>",
										"<span style='color:green; padding-left: 5px;'>col4</span>",
										"<span style='color:green; padding-left: 5px;'>etc...</span>",
										//@formatter:on
								};
							}

							@Override
							protected Widget createWidget(String filterText, boolean caseSensitive, int col, String colText) {
								if (col == 0 && colText != null)
									return new Image(colText);
								return super.createWidget(filterText, caseSensitive, col, colText);
							}

							@Override
							protected boolean highlightColumnText(int col) {
								return col == 1;
							}
						};

						simpleTableRowItemRenderer.initWidget();
						return simpleTableRowItemRenderer;
					}

				};
				// box.setValueRendererFactory(tableRendererFactory)
				box.setValueRendererFactory((event.getValue() ? tableRendererFactory : new IconedValueRendererFactory<Value, IconedValueHolderItem<Value>>(iconLinker)));
			}

		});
		return multiColumn;
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox readOnlyOption(final DefaultIconedSuggestBox box) {
		final CheckBox readOnly = new CheckBox("Read Only");
		readOnly.setValue(box.isReadOnly());
		readOnly.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setReadOnly(event.getValue());
			}

		});
		return readOnly;
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox mandaoryValueOption(final DefaultIconedSuggestBox box) {
		final CheckBox mandatory = new CheckBox("Mandatory");
		mandatory.setValue(box.isMandatory());
		mandatory.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setMandatory(event.getValue());
			}

		});
		return mandatory;
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox strictModeOption(final DefaultIconedSuggestBox box) {
		final CheckBox strict = new CheckBox("Strict mode");
		strict.setValue(false);
		strict.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.setStrictMode(event.getValue());
			}

		});
		return strict;
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox roundedCornersOption(final DefaultIconedSuggestBox box) {
		final CheckBox style = new CheckBox("Rounded");
		style.setValue(BooleanOption.isEnabled(DefaultOptions.STARTS_WITH.name(), box.getOptions()));
		style.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				IntoGwt.changeCss(style.getValue() ? "IntoGwt.css" : "IntoGwtClassic.css");
			}

		});
		return style;
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox caseSensitiveOption(final DefaultIconedSuggestBox box) {
		CheckBox caseSensitive = new CheckBox("Case Sensitive");
		caseSensitive.setValue(BooleanOption.isEnabled(DefaultOptions.CASE_SENSITIVE.name(), box.getOptions()));
		caseSensitive.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.putOption(new BooleanOption(DefaultOptions.CASE_SENSITIVE.name(), event.getValue()));
			}
		});
		return caseSensitive;
	}

	@SuppressWarnings("rawtypes")
	protected static CheckBox startsWithOption(final DefaultIconedSuggestBox box) {
		CheckBox startsWith = new CheckBox("Starts With");
		startsWith.setValue(BooleanOption.isEnabled(DefaultOptions.STARTS_WITH.name(), box.getOptions()));
		startsWith.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				box.putOption(new BooleanOption(DefaultOptions.STARTS_WITH.name(), event.getValue()));
			}
		});
		return startsWith;
	}

}
