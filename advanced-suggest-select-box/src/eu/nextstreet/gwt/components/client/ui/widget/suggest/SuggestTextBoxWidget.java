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

	public abstract String getText();

	public abstract void setReadOnly(boolean readOnly);

	public abstract void setReadOnlyTextStyle(String readOnlyTextStyle);

	public abstract String getReadOnlyTextStyle();

	public abstract void setErrorTextStyle(String errorTextStyle);

	public abstract String getErrorTextStyle();

	public abstract boolean isReadOnly();

	public abstract void setDefaultTextStyle(String defaultTextStyle);

	public abstract void setMandatoryTextStyle(String defaultMandatoryTextStyle);

	public abstract String getMandatoryTextStyle();

	public abstract void setMandatory(boolean mandatory);

	public abstract boolean isMandatory();

	public abstract String getDefaultTextStyle();

	public abstract void setValidator(Validator<String> validator);

	public abstract Validator<String> getValidator();

	public abstract void setSelectionRange(int i, int length);

	public abstract String getDefaultText();

	public abstract void setEnabled(boolean enabled);

	public abstract String getTextValue();

	public abstract void setFocus(boolean b);

	public abstract void setDefaultText(String defaultText);

	public abstract void setRepresenter(AbstractSuggestBox<T, W> abstractSuggestBox);

	public abstract AbstractSuggestBox<T, W> getRepresenter();

	public abstract void setText(String value);

	public abstract void setValue(T value);

	public abstract void setStyleName(String suggestField);

	// ------------------- used to position the popup ------------------

	public abstract int getAbsoluteLeft();

	public abstract int getAbsoluteTop();

	public abstract int getOffsetHeight();

	// ------------------- style handling -------------------
	public abstract void addStyleName(String suggestFieldHover);

	public abstract void removeStyleName(String suggestFieldHover);

	void onDoubleClick(DoubleClickEvent event);

}