package eu.nextstreet.gwt.components.client.ui.widget.select.range.renderer;

import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;

import eu.nextstreet.gwt.components.client.ui.widget.common.renderer.DefaultValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.select.DefaultPanelValueSelector.Resources;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

public class RangeValueRendererFactory<T> extends DefaultValueRendererFactory<T, RangeValueRenderer<T>> {
	public interface RangeTemplate extends SafeHtmlTemplates {
		@Template("<div class='{3}'><div class='label'>{0}</div><img class='image' src='{1}'/><div class='selectionIndicator'>{2}</div></div>")
		public SafeHtml imageLabeledDiv(SafeHtml label, SafeUri imageUri, SafeHtml value, String stlyeName);

		@Template("<div class='{2}'><div class='label'>{0}</div><div class='selectionIndicator'>{1}</div></div>")
		public SafeHtml labeledDiv(SafeHtml label, SafeHtml value, String stlyeName);
	}

	protected RangeTemplate rangeTemplate;
	protected Resources resources;
	protected boolean useImage;

	public RangeValueRendererFactory(Resources resources) {
		this(resources, true);
	}

	public RangeValueRendererFactory(Resources resources, boolean useImage) {
		this.resources = resources;
		this.useImage = useImage;
		rangeTemplate = GWT.create(RangeValueRendererFactory.RangeTemplate.class);
	}

	@Override
	public RangeValueRenderer<T> createValueRenderer(T value, String filterText, Map<String, Option<?>> options) {
		return new RangeValueRenderer<T>(value, filterText, false, options, this);
	}

	public boolean isUseImage() {
		return useImage;
	}

	public void setUseImage(boolean useImage) {
		this.useImage = useImage;
	}

	public String toStyleName(T value, boolean enabled) {
		return enabled ? "" : "disabled";
	}

	public SafeUri toImageUri(T value, boolean enabled) {
		return resources.rangeArrow().getSafeUri();
	}

	public SafeHtml toValueString(T value, boolean enabled) {
		return SafeHtmlUtils.fromSafeConstant("");
	}

	public SafeHtml toLabelString(T value, boolean enabled) {
		return SafeHtmlUtils.fromString(value == null ? "" : value.toString());
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public RangeTemplate getRangeTemplate() {
		return rangeTemplate;
	}

	public void setRangeTemplate(RangeTemplate rangeTemplate) {
		this.rangeTemplate = rangeTemplate;
	}
}
