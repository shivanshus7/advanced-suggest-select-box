package eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;

import eu.nextstreet.gwt.components.client.ui.common.data.ValueRepresentationTransformer;
import eu.nextstreet.gwt.components.client.ui.widget.WidgetController;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.AbstractValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class IconedValueRendererFactory<T, W extends IconedValueHolderItem<T>> extends AbstractValueRendererFactory<T, W> {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ValueRendererFactory<T, ValueHolderItem<T>> textRendererFactory = new DefaultValueRendererFactory();
	/** Gives an image for each value */
	protected ValueRepresentationTransformer<T, Image> iconLinker;

	public IconedValueRendererFactory(StringFormulator<T> stringFormulator, ValueRepresentationTransformer<T, Image> iconLinker,
			ValueRendererFactory<T, ValueHolderItem<T>> textRendererFactory) {
		super(stringFormulator);
		this.iconLinker = iconLinker;
		if (textRendererFactory != null)
			this.textRendererFactory = textRendererFactory;
	}

	public IconedValueRendererFactory(StringFormulator<T> stringFormulator, ValueRepresentationTransformer<T, Image> iconLinker) {
		this(stringFormulator, iconLinker, null);
	}

	public IconedValueRendererFactory(ValueRepresentationTransformer<T, Image> iconLinker) {
		this(null, iconLinker);
	}

	@SuppressWarnings("unchecked")
	@Override
	public W createValueRenderer(T value, String filterText, Map<String, Option<?>> options) {
		Image icon = constructIcon(value);
		EventHandlingValueHolderItem<T> label = (EventHandlingValueHolderItem<T>) textRendererFactory.createValueRenderer(value, filterText, options);
		W toReturn = (W) new IconedValueRenderer<T>(value, icon, label, filterText, BooleanOption.isEnabled(filterText, options), this);
		toReturn.initWidget();
		toReturn.setStyleName(EventHandlingValueHolderItem.ITEM_DEFAULT_STYLE);
		return toReturn;
	}

	/**
	 * Constructs an icon from a value through the transformer
	 * 
	 * @param value
	 *          value
	 * @return icon
	 */
	protected Image constructIcon(T value) {
		if (iconLinker == null)
			return null;

		Image icon = iconLinker.transform(value);
		return icon;
	}

	@Override
	public eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer<T, W> createListRenderer() {
		DefaultListRenderer<T, W> defaultListRenderer = new DefaultListRenderer<T, W>(this);
		return defaultListRenderer;
	}

	public ValueRepresentationTransformer<T, Image> getIconLinker() {
		return iconLinker;
	}

	public void setIconLinker(ValueRepresentationTransformer<T, Image> iconLinker) {
		this.iconLinker = iconLinker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory #toString(T)
	 */
	public String toString(T value) {
		return value.toString();
	}

	@Override
	public void setWidgetController(WidgetController<T> suggestBox) {
		super.setWidgetController(suggestBox);
		textRendererFactory.setWidgetController(suggestBox);
	}

}
