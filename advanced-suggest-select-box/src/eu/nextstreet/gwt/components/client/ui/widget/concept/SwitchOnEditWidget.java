package eu.nextstreet.gwt.components.client.ui.widget.concept;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class permit to have two components based on the {@link #isEmptyContent()} method. It also handles the dom events that trigger switching from a state to
 * the other.
 * 
 * @author zied
 * 
 * @param <E>
 *          the component displayed when the content is empty
 * @param <D>
 *          the component used when the item contains data, or while editing
 * @param <T>
 *          the data type
 */
public abstract class SwitchOnEditWidget<E extends Widget, D extends Widget, T> extends SimplePanel {
	protected E emptyComp;
	protected D dataComp;

	protected final class DataCompHandler implements BlurHandler {
		@Override
		public void onBlur(BlurEvent event) {
			if (isEmptyContent())
				setWidget(emptyComp);
		}
	}

	protected class EmptyCompHandler implements ClickHandler, FocusHandler {
		@Override
		public void onClick(ClickEvent event) {
			switchToDataComp();
		}

		@Override
		public void onFocus(FocusEvent event) {
			switchToDataComp();
		}
	}

	public SwitchOnEditWidget(E emptyComp, D dataComp, T data) {
		this.emptyComp = emptyComp;
		this.dataComp = dataComp;
		// init data before deciding on what to display
		setData(data);
		setWidget(isEmptyContent() ? emptyComp : dataComp);

		initDomHandlers();
	}

	protected void initDomHandlers() {
		EmptyCompHandler emptyCompHandler = new EmptyCompHandler();
		emptyComp.addDomHandler(emptyCompHandler, ClickEvent.getType());
		emptyComp.addDomHandler(emptyCompHandler, FocusEvent.getType());
		dataComp.addDomHandler(new DataCompHandler(), BlurEvent.getType());
	}

	/**
	 * this method is called when the user required the focus to edit, and the component switched to the editable one
	 */
	protected void gainFocusOnDataComp() {
	}

	protected abstract boolean isEmptyContent();

	protected abstract void setData(T data);

	protected abstract T getData();

	public void switchToDataComp() {
		setWidget(dataComp);
		gainFocusOnDataComp();
	}
}
