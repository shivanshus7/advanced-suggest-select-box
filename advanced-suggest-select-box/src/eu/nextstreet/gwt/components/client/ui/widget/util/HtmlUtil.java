package eu.nextstreet.gwt.components.client.ui.widget.util;

public class HtmlUtil {
	protected String matchingStringStyle;
	protected boolean caseSensitive;

	public String highlightMatchingSequence(String html, String filterText) {
		return highlightMatchingSequence(html, filterText, caseSensitive, filterText);
	}

	public static String highlightMatchingSequence(String html, String filterText, boolean caseSensitive, String matchingStringStyle) {
		if (StringUtil.isEmptyOrNull(filterText))
			return html;

		String prefix = "<span class='" + matchingStringStyle + "'>";
		String suffix = "</span>";

		return StringUtil.replaceBySurrounding(html, filterText, prefix, suffix, caseSensitive).toString();
	}

}
