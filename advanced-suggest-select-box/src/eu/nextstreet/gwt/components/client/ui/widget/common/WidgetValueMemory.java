package eu.nextstreet.gwt.components.client.ui.widget.common;

import java.util.*;

import com.google.gwt.user.client.ui.Composite;

import eu.nextstreet.gwt.components.client.ui.widget.WidgetController;
import eu.nextstreet.gwt.components.client.ui.widget.common.ValueRendererFactory.ListRenderer;

/**
 * Remembers the mapping between the widgets and the values they represent
 * 
 * @author Zied Hamdi - http://1vu.fr
 * 
 * @param <T>
 *          the value type
 * @param <W>
 *          the widget type
 */
public abstract class WidgetValueMemory<T, W extends ValueHolderItem<T>> extends Composite implements ListRenderer<T, W> {
	protected Map<T, W> valueWidgetMapping = new HashMap<T, W>();
	protected Map<W, T> valueWidgetInverseMapping = new HashMap<W, T>();
	protected List<T> values = new ArrayList<T>();
	protected WidgetController<T> widgetController;

	protected ValueRendererFactory<T, W> factory;

	public WidgetValueMemory(ValueRendererFactory<T, W> factory) {
		this.factory = factory;
	}

	@Override
	public int getWidgetCount() {
		return valueWidgetMapping.size();
	}

	@Override
	public void clear() {
		valueWidgetMapping.clear();
		values.clear();
	}

	@Override
	public void add(T value, W item) {
		if (value == null || item == null)
			return;

		values.add(value);
		valueWidgetMapping.put(value, item);
		valueWidgetInverseMapping.put(item, value);
	}

	@Override
	public boolean remove(W item) {
		T value = valueWidgetInverseMapping.remove(item);
		if (value != null) {
			valueWidgetMapping.remove(value);
			valueRemoved(value);
			return true;
		}
		return false;
	}

	/**
	 * Don't use this method: it is buggy: the widget created by the factory is retouched by the components and cannot be used directly: it's impossible to
	 * replace the old retouched widget with the new rough one
	 * 
	 * @param index
	 * @param newValue
	 * @return
	 */
	public W setValueAt(int index, T newValue) {
		T oldValue = values.get(index);
		values.set(index, oldValue);
		W widget = valueWidgetMapping.get(oldValue);
		W newWidget = refresh(widget, newValue);
		valueWidgetMapping.remove(oldValue);
		valueWidgetMapping.put(newValue, newWidget);
		valueWidgetInverseMapping.put(newWidget, newValue);
		return newWidget;
	}

	@Override
	public List<T> getValues() {
		return Collections.unmodifiableList(values);
	}

	@Override
	public boolean containsValue(T value) {
		return getValues().contains(value);
	}

	@Override
	public W getItem(T value) {
		return valueWidgetMapping.get(value);
	}

	public void valueRemoved(T value) {
	}

	@Override
	public ValueRendererFactory<T, ?> getFactory() {
		return factory;
	}

	@Override
	public void closeList() {
	}

	@Override
	public void refresh() {
		Set<W> widgetSet = valueWidgetInverseMapping.keySet();
		for (W widget : widgetSet) {
			refresh(widget, valueWidgetInverseMapping.get(widget));
		}
		// int size = values.size();
		// for (int i = 0; i < size; i++) {
		// T value = values.get(i);
		// // setting a 'new' value refreshes the widget
		// setValueAt(i, value);
		// }
	}

	protected W refresh(W widget, T value) {
		return widgetController.refresh(value, factory.refresh(widget, value));
	}

	@Override
	public Map<T, W> getValueWidgetMapping() {
		return Collections.unmodifiableMap(valueWidgetMapping);
	}

	@Override
	public WidgetController<T> getWidgetController() {
		return widgetController;
	}

	@Override
	public void setWidgetController(WidgetController<T> widgetController) {
		this.widgetController = widgetController;
	}
}
