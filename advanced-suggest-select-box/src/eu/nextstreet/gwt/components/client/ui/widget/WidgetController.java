package eu.nextstreet.gwt.components.client.ui.widget;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.nextstreet.gwt.components.client.ui.widget.common.StringFormulator;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueHolderItem;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.param.Option;

/**
 * Represents a Widget as a context (single check point): notifications as selection or state changes are sent back to this object that is responsible for
 * dispatching it
 * 
 * @author Zied Hamdi - http://1vu.fr
 * 
 * @param <T>
 *          the type of data
 */
public interface WidgetController<T> extends StringFormulator<T> {

	/**
	 * Called when a value is selected from the list, if the value is typed on the keyboard and only one possible element corresponds, this method will be called
	 * immediately only if <code>multipleChangeEvent</code> is true. Otherwise it will wait until a blur event occurs Notice that if
	 * <code>multipleChangeEvent</code> is true, this method will be called also each time the enter key is typed
	 * 
	 * @param value
	 */
	public void valueSelected(T value);

	void toggleSelection(T value);

	boolean removeSelection(T value);

	void clearSelection();

	void setSelection(List<T> toSet);

	List<T> getSelection();

	boolean removeSelection(Set<T> selected);

	Map<String, Option<?>> getOptions();

	Option<?> getOption(String key);

	Option<?> putOption(Option<?> option);

	Option<?> removeOption(Option<?> option);

	<W extends ValueHolderItem<T>> W refresh(T value, W widget);
}
