package eu.nextstreet.gwt.components.client.ui.widget.select;

import com.google.gwt.event.dom.client.ChangeEvent;

import eu.nextstreet.gwt.components.client.ui.widget.common.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory.ListRenderer;

public class PanelSelectedEvent<T> extends ChangeEvent {
	protected T selected;
	protected ListRenderer<T, EventHandlingValueHolderItem<T>> listRenderer;

	public PanelSelectedEvent(T item, ListRenderer<T, EventHandlingValueHolderItem<T>> listRenderer) {
		this.selected = item;
		setSource(listRenderer.getFactory().getWidgetController());
	}

	public T getSelected() {
		return selected;
	}

}
