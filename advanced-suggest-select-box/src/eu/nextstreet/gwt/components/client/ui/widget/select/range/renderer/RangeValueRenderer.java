package eu.nextstreet.gwt.components.client.ui.widget.select.range.renderer;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.DefaultValueRenderer;
import eu.nextstreet.gwt.components.client.ui.widget.select.DefaultPanelValueSelector.Resources;

public class RangeValueRenderer<T> extends DefaultValueRenderer<T> {

	public interface RangeTemplate extends SafeHtmlTemplates {
		@Template("<div class='label'>{0}</div><img class='image' src='{1}'/><div class='selectionIndicator'>{2}</div>")
		public SafeHtml labeledDiv(String label, SafeUri imageUri, String value);
	}

	protected SimplePanel mainPanel;
	protected RangeTemplate rangeTemplate;

	public RangeValueRenderer(T value, String filterText, boolean caseSensitive, ValueRendererFactory<T, ?> valueRendererFactory) {
		super(value, filterText, caseSensitive, valueRendererFactory);
	}

	@Override
	protected void init() {
		rangeTemplate = GWT.create(RangeTemplate.class);
		mainPanel = new SimplePanel();
		rangeTemplate = GWT.create(RangeTemplate.class);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected String toHtml(T value) {
		Resources resources = ((RangeValueRendererFactory<T>) valueRendererFactory).resources;
		return rangeTemplate.labeledDiv(valueRendererFactory.toString(value), resources.rangeArrow().getSafeUri(), "").asString();
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

}
