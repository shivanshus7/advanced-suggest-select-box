package eu.nextstreet.gwt.components.client.ui.widget.common;

/**
 * Isolates the representation of a value in its String representation : the
 * value which the user types to retrieve an element or the one displayed when
 * an element is selected
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 * @param <T>
 */
public interface StringFormulator<T> {

	/**
	 * transforms the value T into a human readable String expression
	 * 
	 * @param t
	 *          value
	 * @return the string readable formulation
	 */
	String toString(T t);
}
