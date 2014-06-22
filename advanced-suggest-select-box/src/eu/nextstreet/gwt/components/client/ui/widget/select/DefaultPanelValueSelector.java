package eu.nextstreet.gwt.components.client.ui.widget.select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultSuggestOracle;

public class DefaultPanelValueSelector<T> extends AbstractPanelValueSelector<T, EventHandlingValueHolderItem<T>> {

	public static final String ITEM_AS_EXTERNAL_STYLE = "item";
	public static final String DISABLED_AS_EXTERNAL_STYLE = "disabled";
	public static final String DISABLED_PANEL_AS_EXTERNAL_STYLE = "disabledP";
	public static final String SELECTED_AS_EXTERNAL_STYLE = "selected";

	private static DefaultPanelValueSelectorUiBinder uiBinder = GWT.create(DefaultPanelValueSelectorUiBinder.class);

	public interface Resources extends ClientBundle {
		@Source("eu/nextstreet/gwt/components/client/itemSelectedBackground.png")
		@ImageOptions(repeatStyle = RepeatStyle.Horizontal, flipRtl = true)
		ImageResource itemSelectedBackground();

		@Source(Style.DEFAULT_CSS)
		Style panelStyles();

		@Source("eu/ivu/PanelValueSelector/arrowDotsRight.png")
		@ImageOptions(repeatStyle = RepeatStyle.None, flipRtl = true)
		ImageResource rangeArrow();
	}

	public interface Style extends CssResource {
		/**
		 * The path to the default CSS styles used by this resource.
		 */
		String DEFAULT_CSS = "eu/nextstreet/gwt/components/client/PanelValueSelector.css";

		String selected();

		String hovered();

		String item();

		String panel();

		String disabled();

		String disabledPanel();
	}

	@SuppressWarnings("rawtypes")
	interface DefaultPanelValueSelectorUiBinder extends UiBinder<Widget, DefaultPanelValueSelector> {
	}

	@UiField
	protected HTMLPanel panel;

	protected Resources resources;
	protected Style panelStyles;

	public DefaultPanelValueSelector() {
		this(new DefaultSuggestOracle<T>(), new DefaultValueRendererFactory<T, EventHandlingValueHolderItem<T>>());
	}

	public DefaultPanelValueSelector(Resources resources) {
		this(new DefaultSuggestOracle<T>(), new DefaultValueRendererFactory<T, EventHandlingValueHolderItem<T>>(), resources);
	}

	public DefaultPanelValueSelector(SuggestOracle<T> suggestOracle, ValueRendererFactory<T, EventHandlingValueHolderItem<T>> valueRendererFactory) {
		this(suggestOracle, valueRendererFactory, (Resources) GWT.create(Resources.class));
	}

	public DefaultPanelValueSelector(SuggestOracle<T> suggestOracle, ValueRendererFactory<T, EventHandlingValueHolderItem<T>> valueRendererFactory,
			Resources resources) {
		super(suggestOracle, valueRendererFactory);
		initWidget(uiBinder.createAndBindUi(this));
		setResources(resources);
	}

	public void setResources(Resources resources) {
		this.resources = resources;
		panelStyles = resources.panelStyles();
		panelStyles.ensureInjected();
	}

	@Override
	protected void uiSetListPanel(ListRenderer<T, EventHandlingValueHolderItem<T>> listRenderer) {
		panel.clear();
		panel.add(listRenderer);

		updatePanelStyles();
	}

	public void updatePanelStyles() {
		String styleName = panelStyles.panel();
		// if already set
		panel.removeStyleName(styleName);
		panel.addStyleName(styleName);

		// if already set
		panel.removeStyleName(panelStyles.disabledPanel());
		panel.removeStyleName(DISABLED_PANEL_AS_EXTERNAL_STYLE);
		boolean disabled = !isEnabled();
		if (disabled) {
			panel.addStyleName(panelStyles.disabledPanel());
			panel.addStyleName(DISABLED_PANEL_AS_EXTERNAL_STYLE);
		}
	}

	@Override
	protected void uiAddPanel(T value, EventHandlingValueHolderItem<T> valueRenderer) {
		updateItemStyle(value, valueRenderer);
		super.uiAddPanel(value, valueRenderer);
	}

	@Override
	public <W extends ValueHolderItem<T>> W refresh(T value, W widget) {
		updateItemStyle(value, (EventHandlingValueHolderItem<T>) widget);
		return widget;
	}

	public void updateItemStyle(T value, EventHandlingValueHolderItem<T> valueRenderer) {
		valueRenderer.setStyleName(panelStyles.item());
		boolean disabled = !isEnabled();
		if (disabled) {
			valueRenderer.addStyleName(panelStyles.disabled());
		}
		boolean selected = getSelection().contains(value);
		if (selected)
			valueRenderer.addStyleName(panelStyles.selected());

		// this is done in a separate step so users can apply a style the combines .disabled.selected
		valueRenderer.addStyleName(ITEM_AS_EXTERNAL_STYLE);
		if (disabled)
			valueRenderer.addStyleName(DISABLED_AS_EXTERNAL_STYLE);
		if (selected)
			valueRenderer.addStyleName(SELECTED_AS_EXTERNAL_STYLE);

	}

	@Override
	protected void refresh() {
		updatePanelStyles();
		Map<T, EventHandlingValueHolderItem<T>> valueWidgetMapping = listRenderer.getValueWidgetMapping();
		Set<T> values = valueWidgetMapping.keySet();
		for (T t : values) {
			updateItemStyle(t, valueWidgetMapping.get(t));
		}
		super.refresh();
	}

	@Override
	protected void uiUpdateSelection() {
		if (!initialized)
			throw new IllegalStateException("you must call init() before calling this method");

		refresh();

		List<T> values = listRenderer.getValues();
		for (T t : values) {
			EventHandlingValueHolderItem<T> item = listRenderer.getItem(t);
			boolean selected = selectedItems.contains(t);
			item.setSelected(selected);
			if (selected) {
				item.addStyleName(panelStyles.selected());
				item.addStyleName(SELECTED_AS_EXTERNAL_STYLE);
			} else {
				item.removeStyleName(panelStyles.selected());
				item.removeStyleName(SELECTED_AS_EXTERNAL_STYLE);
			}
		}
	}

	@Override
	protected PanelSelectedEvent<T> changedValue(T item) {
		return new PanelSelectedEvent<T>(item, listRenderer);
	}

}
