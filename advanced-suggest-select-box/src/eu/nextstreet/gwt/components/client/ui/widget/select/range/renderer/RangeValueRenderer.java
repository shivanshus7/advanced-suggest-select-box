package eu.nextstreet.gwt.components.client.ui.widget.select.range.renderer;

import java.util.Map;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.DefaultValueRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.select.range.renderer.RangeValueRendererFactory.RangeTemplate;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.simple.DefaultOptions;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.BooleanOption;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class RangeValueRenderer<T> extends DefaultValueRenderer<T> {

	protected SimplePanel mainPanel;
	protected RangeTemplate rangeTemplate;

	/**
	 * We initialize the template with getValueRendererFactory().getRangeTemplate(), but you can decide to change the template for a given renderer after creation
	 * 
	 * @param value
	 * @param filterText
	 * @param caseSensitive
	 * @param options
	 * @param valueRendererFactory
	 */
	public RangeValueRenderer(T value, String filterText, boolean caseSensitive, Map<String, Option<?>> options, RangeValueRendererFactory<T> valueRendererFactory) {
		super(value, filterText, caseSensitive, options, valueRendererFactory);
	}

	@Override
	protected void init() {
		mainPanel = new SimplePanel();
		rangeTemplate = getValueRendererFactory().getRangeTemplate();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected String toHtml(T value) {
		RangeValueRendererFactory<T> factory = (RangeValueRendererFactory<T>) valueRendererFactory;
		boolean enabled = !BooleanOption.isEnabled(DefaultOptions.DISABLED.name(), options);
		if (factory.isUseImage()) {
			return rangeTemplate.imageLabeledDiv(toLabel(value, enabled), getImageUri(value, enabled), toValueString(value, enabled), toStyleName(value, enabled))
					.asString();
		} else {
			return rangeTemplate.labeledDiv(toLabel(value, enabled), toValueString(value, enabled), toStyleName(value, enabled)).asString();
		}
	}

	public String toStyleName(T value, boolean enabled) {
		return getValueRendererFactory().toStyleName(value, enabled);
	}

	protected SafeHtml toValueString(T value, boolean enabled) {
		return getValueRendererFactory().toValueString(value, enabled);
	}

	protected SafeHtml toLabel(T value, boolean enabled) {
		return getValueRendererFactory().toLabelString(value, enabled);
	}

	protected SafeUri getImageUri(T value, boolean enabled) {
		return getValueRendererFactory().toImageUri(value, enabled);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RangeValueRendererFactory<T> getValueRendererFactory() {
		return (RangeValueRendererFactory<T>) super.valueRendererFactory;
	}

	@Override
	protected Widget getMainPanel() {
		return mainPanel;
	}

	@Override
	protected void setContent(String html) {
		mainPanel.setWidget(new HTML(html));
		display();
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		display();
	}

	@Override
	public void hover(boolean hover) {
		super.hover(hover);
		display();
	}

	protected void display() {
		setHTML(mainPanel.toString());
	}

	public RangeValueRendererFactory.RangeTemplate getRangeTemplate() {
		return rangeTemplate;
	}

	public void setRangeTemplate(RangeValueRendererFactory.RangeTemplate rangeTemplate) {
		this.rangeTemplate = rangeTemplate;
	}

}
