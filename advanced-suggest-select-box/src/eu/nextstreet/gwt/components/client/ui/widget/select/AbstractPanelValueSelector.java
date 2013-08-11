package eu.nextstreet.gwt.components.client.ui.widget.select;

import java.util.*;

import com.google.gwt.event.dom.client.*;

import eu.nextstreet.gwt.components.client.ui.widget.common.*;
import eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle.Callback;
import eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle.Request;
import eu.nextstreet.gwt.components.client.ui.widget.common.SuggestOracle.Response;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory.ListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractBaseWidget;
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

	/**
	 * class for handling events centrally
	 * 
	 * @author Zied Hamdi - http://1vu.fr
	 * 
	 *         REVIEW if possible to use the same code for this class and the suggest box
	 */
	public static class EventsHandler<T, W extends EventHandlingValueHolderItem<T>> implements MouseOverHandler, MouseOutHandler, ClickHandler {
		protected ValueRendererFactory<T, W> valueRendererFactory;
		protected W item;

		public EventsHandler(ValueRendererFactory<T, W> valueRendererFactory, W item) {
			this.item = item;
			this.valueRendererFactory = valueRendererFactory;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			item.hover(true);
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			item.hover(false);
		}

		@Override
		public void onClick(ClickEvent event) {
			valueRendererFactory.getWidgetController().valueSelected(item.getValue());
		}
	};

	protected ValueRendererFactory<T, W> valueRendererFactory;
	protected Map<String, Option<?>> options = new HashMap<String, Option<?>>();
	protected ListRenderer<T, W> listRenderer;
	protected boolean initialized = false;

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
		initialized = true;
	}

	protected abstract void uiSetListPanel(ListRenderer<T, W> listRenderer);

	protected void init(Collection<T> suggestions) {
		for (final T value : suggestions) {
			W valueRenderer = valueRendererFactory.createValueRenderer(value, null, options);
			EventsHandler<T, W> eventsHandler = createEventsHandler(valueRenderer);
			if (eventsHandler != null) {
				// valueRenderer.addMouseOverHandler(eventsHandler);
				// valueRenderer.addMouseOutHandler(eventsHandler);
				valueRenderer.addClickHandler(eventsHandler);
			}
			uiAddPanel(value, valueRenderer);
		}
		uiAddEmptyItem(listRenderer);
		initialized = true;
		uiUpdateSelection();
	}

	protected void uiAddEmptyItem(ListRenderer<T, W> listRenderer) {
		listRenderer.closeList();
	}

	/**
	 * Creates the item mouse over, out and click handlers.
	 * 
	 * @param valueRenderer
	 *          you may return null if you want events to be ignored
	 * @return the events handler for each item
	 */
	protected EventsHandler<T, W> createEventsHandler(W valueRenderer) {
		return new EventsHandler<T, W>(valueRendererFactory, valueRenderer);
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

	public void setSelection(boolean notify, T... values) {
		setSelection(notify, Arrays.asList(values));
	}

	@Override
	public void setSelection(List<T> toSet) {
		// usually this method is called on startup
		setSelection(false, toSet);
	}

	@SuppressWarnings("unchecked")
	public void setSelection(boolean notify, List<T> toSet) {
		super.setSelection(toSet);
		if (notify)
			changeOccured(new PanelSelectedEvent<T>(null, ((ListRenderer<T, EventHandlingValueHolderItem<T>>) listRenderer)));
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
	public boolean removeSelection(Set<T> selectionToRemove) {
		if (super.removeSelection(selectionToRemove)) {
			fireChangeOccured(null);
			uiUpdateSelection();
			return true;
		}
		return false;
	}

	@Override
	public void clearSelection() {
		super.clearSelection();
		uiUpdateSelection();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setMaxSelected(int maxSelected) {
		super.setMaxSelected(maxSelected);
		if (initialized) {
			uiUpdateSelection();
			changeOccured(new PanelSelectedEvent<T>(null, ((ListRenderer<T, EventHandlingValueHolderItem<T>>) listRenderer)));
		}
	}

	/**
	 * Updates the styles relative to selected elements
	 */
	protected abstract void uiUpdateSelection();

}
