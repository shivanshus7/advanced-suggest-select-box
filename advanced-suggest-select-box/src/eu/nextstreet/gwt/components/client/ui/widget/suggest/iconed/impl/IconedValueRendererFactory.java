package eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.impl;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;

import eu.nextstreet.gwt.components.client.ui.common.data.ValueRepresentationTransformer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.EventHandlingValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.iconed.IconedValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultListRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class IconedValueRendererFactory<T, W extends IconedValueHolderItem<T>> implements ValueRendererFactory<T, W> {

	/** Gives an image for each value */
	protected ValueRepresentationTransformer<T, Image>	iconLinker;

	public IconedValueRendererFactory(ValueRepresentationTransformer<T, Image> transformer) {
		this.iconLinker = transformer;
	}

	@Override
	public W createValueRenderer(T value, String filterText, Map<String, Option<?>> options) {
		Image icon = constructIcon(value);
		@SuppressWarnings("unchecked")
		W toReturn = (W) new IconedValueRenderer<T>(value, icon, filterText, BooleanOption.isEnabled(filterText, options), this);
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
		DefaultListRenderer<T, W> defaultListRenderer = new DefaultListRenderer<T, W>();
		defaultListRenderer.setSpacing(0);
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
	 * @see
	 * eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory
	 * #toString(T)
	 */
	public String toString(T value) {
		return null;
	}

}
