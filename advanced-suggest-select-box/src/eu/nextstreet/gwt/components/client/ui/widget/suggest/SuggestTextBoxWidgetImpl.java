package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.GestureChangeHandler;
import com.google.gwt.event.dom.client.GestureEndHandler;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

import eu.nextstreet.gwt.components.client.ui.widget.AdvancedTextBox;
import eu.nextstreet.gwt.components.shared.Validator;

/**
 * This class is the ui representer of the "inactive" suggest box: the text box
 * handles all events and is surrounded by panels which you can fill with you
 * own representation of the selected item
 * 
 * @param <T>
 *          the type of items
 * 
 * @author Zied Hamdi
 * 
 * 
 */
public class SuggestTextBoxWidgetImpl<T, W extends EventHandlingValueHolderItem<T>>
		extends Composite implements SuggestTextBoxWidget<T, W> {
	/** the text field style name */
	private static final String SUGGEST_FIELD = "eu-nextstreet-SuggestField";
	private static final String SUGGEST_FIELD_TOP = "eu-nextstreet-SuggestField-top";
	private static final String SUGGEST_FIELD_BOTTOM = "eu-nextstreet-SuggestField-bottom";
	private static final String SUGGEST_FIELD_LEFT = "eu-nextstreet-SuggestField-left";
	private static final String SUGGEST_FIELD_RIGHT = "eu-nextstreet-SuggestField-right";

	/** the main panel */
	protected DockPanel panel = new DockPanel();
	/** the main component */
	protected AdvancedTextBox advancedTextBox = new AdvancedTextBox();

	/** the suggest box that contains this widget */
	protected AbstractSuggestBox<T, W> representer;

	/**
	 * the button is a background image so that the focus is not lost when it is
	 * clicked, this value corresponds to the image width
	 */
	protected int buttonWidth = 16;

	/** any value change is notified to this list of listeners */
	protected ChangeEventHandlerHolder<T, ChangeEvent> valueChangeEventHandlerHolder = new ChangeEventHandlerHolder<T, ChangeEvent>() {

		@Override
		protected ChangeEvent changedValue(T param) {
			return new SuggestChangeEvent<T, W>(representer, param);
		}

	};

	/** any value change is notified to this list of listeners */
	protected ChangeEventHandlerHolder<String, ChangeEvent> textChangeEventHandlerHolder = new ChangeEventHandlerHolder<String, ChangeEvent>() {

		@Override
		protected ChangeEvent changedValue(String param) {
			return new SuggestChangeEvent<T, W>(representer, param);
		}

	};

	/** left of the text box */
	protected SimplePanel left = new SimplePanel();
	/** right of the text box */
	protected SimplePanel right = new SimplePanel();
	/** top of the text box */
	protected SimplePanel top = new SimplePanel();
	/** bottom of the text box */
	protected SimplePanel bottom = new SimplePanel();

	public SuggestTextBoxWidgetImpl() {
		initWidget(panel);
		initPanels();
		addMouseDownHandler(this);
		addMouseMoveHandler(this);
		addMouseOutHandler(this);
	}

	/**
	 * Sets the current value and notifies listeners
	 * 
	 * @param value
	 *          the new value
	 */
	@Override
	public void setValue(T value) {
		advancedTextBox.setText(representer.toString(value));
		valueChangeEventHandlerHolder.fireChangeOccured(value);
	}

	/**
	 * Sets the current value as a string and notifies listeners
	 * 
	 * @param value
	 *          the new value
	 */
	@Override
	public void setText(String value) {
		advancedTextBox.setText(value);
		textChangeEventHandlerHolder.fireChangeOccured(value);
	}

	private void initPanels() {
		panel.add(advancedTextBox, DockPanel.CENTER);
		panel.add(top, DockPanel.NORTH);
		top.setStyleName(SUGGEST_FIELD_TOP);
		panel.add(bottom, DockPanel.SOUTH);
		bottom.setStyleName(SUGGEST_FIELD_BOTTOM);
		advancedTextBox.setStyleName(SUGGEST_FIELD);
		left.setStyleName(SUGGEST_FIELD_LEFT);
		panel.add(left, DockPanel.WEST);
		right.setStyleName(SUGGEST_FIELD_RIGHT);
		panel.add(right, DockPanel.EAST);
	}

	/**
	 * Sets the widget on the left part of the text box
	 * 
	 * @param left
	 *          a widget
	 */
	public void setLeftWidget(IsWidget left) {
		this.left.setWidget(left);
	}

	/**
	 * Sets the widget on the right part of the text box
	 * 
	 * @param right
	 *          a widget
	 */
	public void setRightWidget(IsWidget right) {
		this.right.setWidget(right);
	}

	/**
	 * Sets the widget on top of the text box
	 * 
	 * @param top
	 *          a widget
	 */
	public void setTopWidget(IsWidget top) {
		this.top.setWidget(top);
	}

	/**
	 * Sets the widget under the text box
	 * 
	 * @param bottom
	 *          a widget
	 */
	public void setBottomWidget(IsWidget bottom) {
		this.bottom.setWidget(bottom);
	}

	@Override
	public AbstractSuggestBox<T, W> getRepresenter() {
		return representer;
	}

	@Override
	public void setRepresenter(AbstractSuggestBox<T, W> abstractSuggestBox) {
		this.representer = abstractSuggestBox;
	}

	/** Sets the default text */
	@Override
	public void setDefaultText(String defaultText) {
		advancedTextBox.setDefaultText(defaultText);
	}

	/** delegates the focus to the text field */
	@Override
	public void setFocus(boolean b) {
		advancedTextBox.setFocus(b);
	}

	/** gets the text value */
	@Override
	public String getTextValue() {
		return advancedTextBox.getTextValue();
	}

	/** Enables or disables the edition */
	@Override
	public void setEnabled(boolean enabled) {
		advancedTextBox.setEnabled(enabled);
	}

	/** returns the default text */
	@Override
	public String getDefaultText() {
		return advancedTextBox.getDefaultText();
	}

	/** delegates to the text box */
	@Override
	public void setSelectionRange(int i, int length) {
		advancedTextBox.setSelectionRange(i, length);
	}

	@Override
	public Validator<String> getValidator() {
		return advancedTextBox.getValidator();
	}

	@Override
	public void setValidator(Validator<String> validator) {
		advancedTextBox.setValidator(validator);
	}

	@Override
	public String getDefaultTextStyle() {
		return advancedTextBox.getDefaultTextStyle();
	}

	@Override
	public boolean isMandatory() {
		return advancedTextBox.isMandatory();
	}

	@Override
	public void setMandatory(boolean mandatory) {
		advancedTextBox.setMandatory(mandatory);
	}

	@Override
	public String getMandatoryTextStyle() {
		return advancedTextBox.getMandatoryTextStyle();
	}

	@Override
	public void setMandatoryTextStyle(String defaultMandatoryTextStyle) {
		advancedTextBox.setMandatoryTextStyle(defaultMandatoryTextStyle);
	}

	@Override
	public void setDefaultTextStyle(String defaultTextStyle) {
		advancedTextBox.setDefaultTextStyle(defaultTextStyle);
	}

	@Override
	public boolean isReadOnly() {
		return advancedTextBox.isReadOnly();
	}

	@Override
	public String getErrorTextStyle() {
		return advancedTextBox.getErrorTextStyle();
	}

	@Override
	public void setErrorTextStyle(String errorTextStyle) {
		advancedTextBox.setErrorTextStyle(errorTextStyle);
	}

	@Override
	public String getReadOnlyTextStyle() {
		return advancedTextBox.getReadOnlyTextStyle();
	}

	@Override
	public void setReadOnlyTextStyle(String readOnlyTextStyle) {
		advancedTextBox.setReadOnlyTextStyle(readOnlyTextStyle);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		advancedTextBox.setReadOnly(readOnly);
	}

	@Override
	public String getText() {
		return advancedTextBox.getText();
	}

	// -------------------------- delegate events -----------------------
	public HandlerRegistration addAttachHandler(Handler handler) {
		return advancedTextBox.addAttachHandler(handler);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return advancedTextBox.addChangeHandler(handler);
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return advancedTextBox.addValueChangeHandler(handler);
	}

	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return advancedTextBox.addDoubleClickHandler(handler);
	}

	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return advancedTextBox.addBlurHandler(handler);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return advancedTextBox.addClickHandler(handler);
	}

	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return advancedTextBox.addFocusHandler(handler);
	}

	public HandlerRegistration addGestureChangeHandler(
			GestureChangeHandler handler) {
		return advancedTextBox.addGestureChangeHandler(handler);
	}

	public HandlerRegistration addGestureEndHandler(GestureEndHandler handler) {
		return advancedTextBox.addGestureEndHandler(handler);
	}

	public HandlerRegistration addGestureStartHandler(GestureStartHandler handler) {
		return advancedTextBox.addGestureStartHandler(handler);
	}

	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return advancedTextBox.addKeyDownHandler(handler);
	}

	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return advancedTextBox.addKeyPressHandler(handler);
	}

	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return advancedTextBox.addKeyUpHandler(handler);
	}

	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return advancedTextBox.addMouseDownHandler(handler);
	}

	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return advancedTextBox.addMouseMoveHandler(handler);
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return advancedTextBox.addMouseOutHandler(handler);
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return advancedTextBox.addMouseOverHandler(handler);
	}

	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return advancedTextBox.addMouseUpHandler(handler);
	}

	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return advancedTextBox.addMouseWheelHandler(handler);
	}

	// --------------------- other features ----------------------

	public int getButtonWidth() {
		return buttonWidth;
	}

	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	// -------------------- self handling ---------------------
	/**
	 * On a click on the button, we recompute the possibilities list and we show
	 * them
	 */
	@UiHandler("textField")
	public void onMouseDown(MouseDownEvent event) {
		int interval = advancedTextBox.getAbsoluteLeft()
				+ advancedTextBox.getOffsetWidth() - event.getClientX();
		if (interval < buttonWidth) {
			if (representer.isShowingSuggestList()) {
				representer.hideSuggestList(false);
			} else {
				representer.recomputePopupContent(KeyCodes.KEY_DOWN);
				representer.highlightSelectedValue();
			}
		}
	}

	/**
	 * The style adding and removing is handled by {@link AbstractSuggestBox} here
	 * we just analyse our own state
	 * 
	 * @param event
	 *          mouse event
	 */
	@UiHandler("textField")
	public void onMouseMove(MouseMoveEvent event) {
		int mousePosition = event.getX();
		representer
				.mouseOnButton(mousePosition > (advancedTextBox.getOffsetWidth() - buttonWidth));
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		representer.mouseOnButton(false);
	}

	// --------------------- layout widgets accessors -------------------------
	public DockPanel getPanel() {
		return panel;
	}

	public void setPanel(DockPanel panel) {
		this.panel = panel;
	}

	public AdvancedTextBox getAdvancedTextBox() {
		return advancedTextBox;
	}

	public void setAdvancedTextBox(AdvancedTextBox advancedTextBox) {
		this.advancedTextBox = advancedTextBox;
	}

	public SimplePanel getLeft() {
		return left;
	}

	public void setLeft(SimplePanel left) {
		this.left = left;
	}

	public SimplePanel getRight() {
		return right;
	}

	public void setRight(SimplePanel right) {
		this.right = right;
	}

	public SimplePanel getTop() {
		return top;
	}

	public void setTop(SimplePanel top) {
		this.top = top;
	}

	public SimplePanel getBottom() {
		return bottom;
	}

	public void setBottom(SimplePanel bottom) {
		this.bottom = bottom;
	}

	public void setFieldId(String id) {
		advancedTextBox.getElement().setId(id + "suggest");
	}

	// ------------------------------------------------------- end.
}
