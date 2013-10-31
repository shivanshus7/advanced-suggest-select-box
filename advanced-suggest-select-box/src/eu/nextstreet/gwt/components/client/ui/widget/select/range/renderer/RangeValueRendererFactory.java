package eu.nextstreet.gwt.components.client.ui.widget.select.range.renderer;

import java.util.Map;

import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.select.DefaultPanelValueSelector.Resources;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class RangeValueRendererFactory<T> extends DefaultValueRendererFactory<T, RangeValueRenderer<T>> {
	protected Resources resources;
	protected boolean useImage;

	public RangeValueRendererFactory(Resources resources) {
		this(resources, true);
	}

	public RangeValueRendererFactory(Resources resources, boolean useImage) {
		this.resources = resources;
		this.useImage = useImage;
	}

	@Override
	public RangeValueRenderer<T> createValueRenderer(T value, String filterText, Map<String, Option<?>> options) {
		return new RangeValueRenderer<T>(value, filterText, false, this);
	}
}
