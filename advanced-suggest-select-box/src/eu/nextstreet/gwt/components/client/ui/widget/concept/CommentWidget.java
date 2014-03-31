package eu.nextstreet.gwt.components.client.ui.widget.concept;

import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * switches between two different styled components depending on whether there is data or not inside (somehow as the g+ or fb comment)
 * 
 * @author zied
 * 
 */
public class CommentWidget extends SwitchOnEditWidget<TextBox, TextArea, String> {

	public CommentWidget(String data, String defaultText) {
		this(data, defaultText, "emptyComment", "comment");
	}

	public CommentWidget(String data, String defaultText, String emptyStyle, String dataStyle) {
		super(new TextBox(), new TextArea(), data);
		emptyComp.setText(defaultText);
		setEmptyStyle(emptyStyle);
		setNonEmptyStyle(dataStyle);
	}

	public void setNonEmptyStyle(String dataStyle) {
		dataComp.setStyleName(dataStyle);
	}

	public void setEmptyStyle(String emptyStyle) {
		emptyComp.setStyleName(emptyStyle);
	}

	@Override
	protected boolean isEmptyContent() {
		String data = getData();
		return data == null || data.trim().length() == 0;
	}

	@Override
	protected void setData(String data) {
		dataComp.setText(data);
	}

	@Override
	protected String getData() {
		return dataComp.getText();
	}

	@Override
	protected void gainFocusOnDataComp() {
		dataComp.setFocus(true);
	}

	public void setVisibleLines(int lines) {
		dataComp.setVisibleLines(lines);
	}
}
