package eu.nextstreet.gwt.components.client.ui.widget.suggest;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.ui.IsWidget;

import eu.nextstreet.gwt.components.shared.Validator;

/**
 * This interface represents the widget that shows the selected value of the
 * Suggest box.
 * 
 * Since a non strict Suggest box can have values that are not in the list: they
 * don't have a corresponding typed instance value. In theses cases
 * {@link #setValue(Object)} cannot be called, and {@link #setText(String)} is
 * used
 * 
 * TODO : javadoc comment the methods. To understand the rules look at the
 * unique implementation {@link SuggestTextBoxWidgetImpl}
 * 
 * @author Zied Hamdi
 * 
 * @param <T>
 *          the suggest box items type
 * @param <W>
 *          the suggest box items representer type (only for consistency)
 */
public interface SuggestTextBoxWidget<T, W extends EventHandlingValueHolderItem<T>> extends IsWidget, MouseDownHandler, MouseMoveHandler, MouseOutHandler,
		HasKeyUpHandlers, HasKeyDownHandlers, HasDoubleClickHandlers, HasBlurHandlers {

	String getText();

	void setReadOnly(boolean readOnly);

	void setReadOnlyTextStyle(String readOnlyTextStyle);

	String getReadOnlyTextStyle();

	void setErrorTextStyle(String errorTextStyle);

	String getErrorTextStyle();

	boolean isReadOnly();

	void setDefaultTextStyle(String defaultTextStyle);

	void setMandatoryTextStyle(String defaultMandatoryTextStyle);

	String getMandatoryTextStyle();

	void setMandatory(boolean mandatory);

	boolean isMandatory();

	String getDefaultTextStyle();

	void setValidator(Validator<String> validator);

	Validator<String> getValidator();

	void setSelectionRange(int i, int length);

	String getDefaultText();

	void setEnabled(boolean enabled);

	String getTextValue();

	void setFocus(boolean b);

	void setDefaultText(String defaultText);

	void setRepresenter(AbstractSuggestBox<T, W> abstractSuggestBox);

	AbstractSuggestBox<T, W> getRepresenter();

	void setText(String value);

	void setValue(T value);

	void setStyleName(String suggestField);

	// ------------------- used to position the popup ------------------

	int getAbsoluteLeft();

	int getAbsoluteTop();

	int getTextWidgetOffsetWidth();

	int getOffsetWidth();

	int getOffsetHeight();

	// ------------------- style handling -------------------
	void addStyleName(String suggestFieldHover);

	void removeStyleName(String suggestFieldHover);

	void onDoubleClick(DoubleClickEvent event);

}