package eu.nextstreet.gwt.components.client.ui.widget.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory;
import eu.nextstreet.gwt.components.client.ui.widget.suggest.ValueRendererFactory.ListRenderer;

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
public abstract class WidgetValueMemory<T, W> extends Composite implements ListRenderer<T, W> {
	protected Map<T, W> valueWidgetMapping = new HashMap<T, W>();
	protected Map<W, T> valueWidgetInverseMapping = new HashMap<W, T>();

	protected ValueRendererFactory<T, ?> factory;

	public WidgetValueMemory(ValueRendererFactory<T, ?> factory) {
		this.factory = factory;
	}

	@Override
	public int getWidgetCount() {
		return valueWidgetMapping.size();
	}

	@Override
	public void clear() {
		valueWidgetMapping.clear();
	}

	@Override
	public void add(T value, W item) {
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

	@Override
	public Set<T> getValues() {
		return valueWidgetMapping.keySet();
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
}
