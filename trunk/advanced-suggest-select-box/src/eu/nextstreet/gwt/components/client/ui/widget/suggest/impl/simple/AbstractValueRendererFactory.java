package eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple;

import eu.nextstreet.gwt.components.client.ui.widget.WidgetController;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;

public abstract class AbstractValueRendererFactory<T, W extends EventHandlingValueHolderItem<T>> implements ValueRendererFactory<T, W> {
	protected WidgetController<T> widgetController;
	protected StringFormulator<T> stringFormulator;

	public AbstractValueRendererFactory() {
		super();
	}

	public AbstractValueRendererFactory(StringFormulator<T> stringFormulator) {
		this.stringFormulator = stringFormulator;
	}

	@Override
	public WidgetController<T> getWidgetController() {
		return widgetController;
	}

	/**
	 * You should never call this method manually
	 * 
	 * @param controller
	 */
	public void setWidgetController(WidgetController<T> controller) {
		this.widgetController = controller;
	}

	@Override
	public String toString(T value) {
		if (stringFormulator == null)
			return ((StringFormulator<T>) widgetController).toString(value);

		return stringFormulator.toString(value);
	}

	public StringFormulator<T> getStringFormulator() {
		return stringFormulator;
	}

	public void setStringFormulator(StringFormulator<T> stringFormulator) {
		this.stringFormulator = stringFormulator;
	}
}
