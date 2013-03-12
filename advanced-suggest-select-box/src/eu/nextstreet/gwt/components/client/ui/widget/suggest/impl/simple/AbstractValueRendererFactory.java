package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;

public abstract class AbstractValueRendererFactory<T, W extends EventHandlingValueHolderItem<T>>
		implements ValueRendererFactory<T, W> {
	protected AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox;
	protected StringFormulator<T> stringFormulator;

	public AbstractValueRendererFactory() {
		super();
	}

	public AbstractValueRendererFactory(StringFormulator<T> stringFormulator) {
		this.stringFormulator = stringFormulator;
	}

	@Override
	public AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> getSuggestBox() {
		return suggestBox;
	}

	/**
	 * You should never call this method manually
	 * 
	 * @param suggestBox
	 */
	public void setSuggestBox(
			AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox) {
		this.suggestBox = suggestBox;
	}

	@Override
	public String toString(T value) {
		if (stringFormulator == null)
			return suggestBox.toString(value);

		return stringFormulator.toString(value);
	}

	public StringFormulator<T> getStringFormulator() {
		return stringFormulator;
	}

	public void setStringFormulator(StringFormulator<T> stringFormulator) {
		this.stringFormulator = stringFormulator;
	}
}
