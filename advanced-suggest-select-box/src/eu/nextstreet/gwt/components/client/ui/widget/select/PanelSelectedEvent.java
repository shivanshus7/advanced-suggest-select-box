package eu.nextstreet.gwt.components.client.ui.widget.select;

import com.google.gwt.event.dom.client.ChangeEvent;

public class PanelSelectedEvent<T> extends ChangeEvent {
	protected T selected;

	public PanelSelectedEvent(T selected) {
		this.selected = selected;
	}

	public T getSelected() {
		return selected;
	}

}
