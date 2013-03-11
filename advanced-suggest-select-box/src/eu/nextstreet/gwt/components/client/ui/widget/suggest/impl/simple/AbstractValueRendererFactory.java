package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.AbstractSuggestBox;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;

public abstract class AbstractValueRendererFactory<T, W extends EventHandlingValueHolderItem<T>>
		implements ValueRendererFactory<T, W> {
	protected AbstractSuggestBox<T, EventHandlingValueHolderItem<T>> suggestBox;

	public AbstractValueRendererFactory() {
		super();
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
}
