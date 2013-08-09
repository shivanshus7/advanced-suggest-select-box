package eu.nextstreet.gwt.components.client.ui.widget.select;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.*;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultSuggestOracle;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultValueRendererFactory;

public class DefaultPanelValueSelector<T> extends AbstractPanelValueSelector<T, EventHandlingValueHolderItem<T>> {

	private static DefaultPanelValueSelectorUiBinder uiBinder = GWT.create(DefaultPanelValueSelectorUiBinder.class);

	public interface Resources extends ClientBundle {
		@Source("eu/nextstreet/gwt/components/client/itemSelectedBackground.png")
		@ImageOptions(repeatStyle = RepeatStyle.Horizontal, flipRtl = true)
		ImageResource itemSelectedBackground();

		@Source(Style.DEFAULT_CSS)
		Style panelStyles();
	}

	public interface Style extends CssResource {
		/**
		 * The path to the default CSS styles used by this resource.
		 */
		String DEFAULT_CSS = "eu/nextstreet/gwt/components/client/PanelValueSelector.css";

		String selected();

		String hovered();

		String item();
	}

	@SuppressWarnings("rawtypes")
	interface DefaultPanelValueSelectorUiBinder extends UiBinder<Widget, DefaultPanelValueSelector> {
	}

	@UiField
	protected HTMLPanel panel;

	protected Resources resources = GWT.create(Resources.class);
	protected Style panelStyles = resources.panelStyles();

	public DefaultPanelValueSelector() {
		this(new DefaultSuggestOracle<T>(), new DefaultValueRendererFactory<T, EventHandlingValueHolderItem<T>>());
	}

	public DefaultPanelValueSelector(SuggestOracle<T> suggestOracle, ValueRendererFactory<T, EventHandlingValueHolderItem<T>> valueRendererFactory) {
		this(suggestOracle, valueRendererFactory, (Resources) GWT.create(Resources.class));
	}

	public DefaultPanelValueSelector(SuggestOracle<T> suggestOracle, ValueRendererFactory<T, EventHandlingValueHolderItem<T>> valueRendererFactory,
			Resources resources) {
		super(suggestOracle, valueRendererFactory);
		initWidget(uiBinder.createAndBindUi(this));
		this.resources = resources;
		panelStyles = resources.panelStyles();
		panelStyles.ensureInjected();
	}

	@Override
	protected void uiSetListPanel(ListRenderer<T, EventHandlingValueHolderItem<T>> listRenderer) {
		panel.add(listRenderer);
	}

	@Override
	protected void uiAddPanel(T value, EventHandlingValueHolderItem<T> valueRenderer) {
		valueRenderer.setStyleName(panelStyles.item());
		super.uiAddPanel(value, valueRenderer);
	}

	@Override
	protected void uiUpdateSelection() {
		Set<T> values = listRenderer.getValues();
		for (T t : values) {
			EventHandlingValueHolderItem<T> item = listRenderer.getItem(t);
			if (selected.contains(t)) {
				item.addStyleName(panelStyles.selected());
			} else {
				item.removeStyleName(panelStyles.selected());
			}
		}
	}

	@Override
	protected PanelSelectedEvent<T> changedValue(T item) {
		return new PanelSelectedEvent<T>(item);
	}

}
