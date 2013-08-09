package eu.nextstreet.gwt.components.client.ui.common.data;

/**
 * Used to transform a value to one of its representations: icon, text, widget
 * etc...
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 * @param <T>
 *          the value Type
 * @param <R>
 *          the representation type
 */
public interface ValueRepresentationTransformer<T, R> {

	/**
	 * The null unconditional return value transformer
	 */
	@SuppressWarnings("rawtypes")
	public static final ValueRepresentationTransformer NULL_TRANSFORMER = new ValueRepresentationTransformer() {

		@Override
		public Object transform(Object value) {
			return null;
		}
	};

	/**
	 * Returns the representation of the value in its <R> form
	 * 
	 * @param value
	 *          value
	 * @return the <R> form representation of <code>value</code>
	 */
	R transform(T value);
}
