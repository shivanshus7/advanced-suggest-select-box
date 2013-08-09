package eu.nextstreet.gwt.components.client.ui.widget.select;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.*;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle.Callback;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle.Request;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.SuggestOracle.Response;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

/**
 * Ergonomic widget that permits selecting a value by clicking on it as it is in radio boxes but with a different visual
 * 
 * @author Zied Hamdi - http://1vu.fr
 * 
 * @param <T>
 *          the item value type (maybe as simple as String, or an object of your choice)
 * @param <W>
 *          the item list representer: the widget that displays the value in the list
 */
public abstract class AbstractPanelValueSelector<T, W extends EventHandlingValueHolderItem<T>> extends AbstractBaseWidget<T, T, PanelSelectedEvent<T>> {
	public enum LabelDiplayOptions {
		INSIDE, RANGE, ABOVE;
	}

	protected ValueRendererFactory<T, W> valueRendererFactory;
	protected Map<String, Option<?>> options = new HashMap<String, Option<?>>();
	protected ListRenderer<T, W> listRenderer;

	public AbstractPanelValueSelector(SuggestOracle<T> suggestOracle, ValueRendererFactory<T, W> valueRendererFactory) {
		this.suggestOracle = suggestOracle;
		suggestOracle.setContextWidget(this);
		setValueRendererFactory(valueRendererFactory);
	}

	public void init() {
		listRenderer = valueRendererFactory.createListRenderer();
		uiSetListPanel(listRenderer);

		suggestOracle.requestSuggestions(new Request(null), new Callback<T>() {
			@Override
			public void onSuggestionsReady(Request request, Response<T> response) {
				Collection<T> suggestions = response.getSuggestions();
				init(suggestions);
			}
		});
	}

	protected abstract void uiSetListPanel(ListRenderer<T, W> listRenderer);

	protected void init(Collection<T> suggestions) {
		for (final T value : suggestions) {
			W valueRenderer = valueRendererFactory.createValueRenderer(value, null, options);
			valueRenderer.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					valueRendererFactory.getWidgetController().valueSelected(value);
				}
			});
			uiAddPanel(value, valueRenderer);
		}
		uiUpdateSelection();
	}

	protected void uiAddPanel(T value, W valueRenderer) {
		listRenderer.add(value, valueRenderer);
	}

	public void setOptions(Map<String, Option<?>> options) {
		this.options = options;
	}

	public ValueRendererFactory<T, W> getValueRendererFactory() {
		return valueRendererFactory;
	}

	public void setValueRendererFactory(ValueRendererFactory<T, W> valueRendererFactory) {
		this.valueRendererFactory = valueRendererFactory;
		valueRendererFactory.setWidgetController(this);
	}

	@Override
	public void valueSelected(T value) {
		super.valueSelected(value);
		fireChangeOccured(value);
		uiUpdateSelection();
	}

	@Override
	public void setSelection(List<T> toSet) {
		super.setSelection(toSet);
		uiUpdateSelection();
	}

	@Override
	public void toggleSelection(T value) {
		super.toggleSelection(value);
		uiUpdateSelection();
	}

	@Override
	public boolean removeSelection(T value) {
		boolean removed = super.removeSelection(value);
		fireChangeOccured(value);
		uiUpdateSelection();
		return removed;
	}

	@Override
	public void clearSelection() {
		super.clearSelection();
		uiUpdateSelection();
	}

	/**
	 * Updates the styles relative to selected elements
	 */
	protected abstract void uiUpdateSelection();

}
