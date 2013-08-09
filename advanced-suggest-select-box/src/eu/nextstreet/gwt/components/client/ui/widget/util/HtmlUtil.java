package eu.nextstreet.gwt.components.client.ui.widget.util;

public class HtmlUtil {
	public static String highlightMatchingSequence(String html, String filterText, boolean caseSensitive, String matchingStringStyle) {
		if (filterText == null)
			return html;

		if (caseSensitive) {
			html = html.replace(filterText, "<span class='" + matchingStringStyle + "'>" + filterText + "</span>");

		} else {
			String startSequence = "###start###";
			String endSequence = "###end###";
			String temp = html.toLowerCase().replace(filterText.toLowerCase(), startSequence + filterText + endSequence);
			int firstIndex = temp.indexOf(startSequence);
			int lastIndex = temp.indexOf(endSequence) - startSequence.length();
			if (firstIndex > -1) {
				html = html.substring(0, firstIndex) + "<span class='" + matchingStringStyle + "'>" + html.substring(firstIndex, lastIndex) + "</span>"
						+ html.substring(firstIndex + filterText.length());
			}

		}
		return html;
	}
}
