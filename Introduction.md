#This page shows the simplest way to use the suggest box with its default implementations.

# Introduction #

After this fast tutorial you will be able to create a suggest box or a select box the way you are used to with other widgets. At every extension point you will have the possibility to see how to get a more advanced look or behavior by following the proposed link. We recommend you read this document entirely before you start following links since  the overall architecture is explained here.


# Details #

The simplest test case of this library is the following:
```
	public void onModuleLoad() {
		class Value {
			String str;

			public Value(String str) {
				this.str = str;
			}

			@Override
			public String toString() {
				return str;
			}

		}

		DefaultSuggestBox<Value, ValueHolderLabel<Value>> box = new DefaultSuggestBox<Value, ValueHolderLabel<Value>>();
		box.add(new Value("01 - ABCD"));
		box.add(new Value("02 - CDEF"));
		box.add(new Value("03 - GHIJ"));
		RootPanel.get("suggestBoxContainer").add(box);
	}

```

By running this example, you will see the default behavior and look of the component.

An extension of the suggest box to support icons is _DefaultIconedSuggestBox_, it handles explicitly the left part of the _DefaultSuggestBox_ to add it an icon (I recommend you take a look at the source code to see how direct is the process of extending the Suggest Box features).

```
	public void onModuleLoad() {
		class Value {
			String str;

			public Value(String str) {
				this.str = str;
			}

			@Override
			public String toString() {
				return str;
			}

		}
		DefaultIconedSuggestBox box = new DefaultIconedSuggestBox<Value, IconedValueHolderLabel<Value>>(
				"select or type value");
		box.add(new Value("01 - ABCD"));
		box.add(new Value("02 - CDEF"));
		box.add(new Value("03 - GHIJ"));

		box.setIconLinker(iconLinker);

		RootPanel.get("suggestBoxContainer").add(box);
	}
```

You must wonder what was put in the call `box.setIconLinker(iconLinker)`!?. The icon linker gives us what icon to put on a given value.
In our online example we have a pretty simple implementation that supposes there are images with the same name as tha values:
```
	/**
	 * This class aims to give the icon corresponding to each value
	 */
	static final ValueRepresentationTransformer<Value, Image> iconLinker = new ValueRepresentationTransformer<Value, Image>() {

		@Override
		public Image transform(Value value) {
			return new Image("img/chrome_extentions/" + value.str + ".png");
		}
	};
```

As you surely understood at this point `ValueRepresentationTransformer<T,R>` is a general purpose interface designed to translate a value into one of its representations. The representation can be an icon, but can also be a complex widget. The coming versions of this project will show other uses of this interface.

Similarly, you can create a multivalued select box with only some lines of code:
```
		final MultiChoiceSuggestBox<Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>> multiBox = new MultiChoiceSuggestBox<ValidationTest.Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>>(
				"select or type value",
				DockPanel.NORTH,
				new MultiChoiceValueRendererFactory<Value, MultiChoiceValueHolderLabel<Value>>(
						iconLinker));
		multiBox.setStartsWith(false);
		multiBox.getTextField().getTop()
				.setStyleName("eu-nextstreet-MultiChoiceSelection");
		fillData(multiBox);
		multiBox.setIconLinker(iconLinker);
		RootPanel.get("suggestBoxMultiValueContainer").add(multiBox);
```
`MultiChoiceValueRendererFactory` is the class that handles the creation of the selected items and their container. It is constructed with the same `iconLinker` to have the same icons on the selected values as the one in the list (you could choose to have smaller icons or no icons for that section for example by providing a different Icon Linker)

All these examples can be found in the class `eu.nextstreet.gwt.components.client.ValidationTest` that is also used internally to test the demo application.

These examples of use show some limitations of the standard components:
## Filtering limitations ##
In many cases, list values have a business code and a label (at least). Users wish to have the possibility to filter by typing either the code or the label. For example, the user could wish to type AB and see the element **01 - ABCD** selected.
To accomplish this task you have two possible ways:
### Simple way ###
Extend _DefaultSuggestBox_ (or any of the available implementations) and override the method _accept(String value, T item)_ which tells if _item_ has to be in the list of the elements when the user typed the string _value_
For example to let the user type any of the code or the label as explained in the preceding chapter, create your own class as follows:
```
public class MySuggestBox extends DefaultSuggestBox {
	protected boolean accept(String text, T t) {
		if (t.toString().toUpperCase().contains(text.toUpperCase())) {
			return true;
		}
		return false;
	}
}
```

### Extended way ###
`DefaultSuggestBox` has a simple implementation of the base class's abstract methods `AbstractSuggestBox<T,W>`.

`AbstractSuggestBox<T,W>` doesn't hold the list of items neither it defines how to hold them. Instead it only declares an abstract method `protected abstract List<T> getFiltredPossibilities(String text);` that it calls each time it needs to show (or update) the list. This way it delegates the responsibility of the list management to a user defined object: this allows to get the list from the server for example only when needed, or to generate values from what the user is typing etc...
Therefore by extending `AbstractSuggestBox<T,W>` you will have to hold you list values alone. You can also decide for example not to show the list unless the user typed at least 3 letters.
Of course, you can extend any subclass of `AbstractSuggestBox<T,W>` and override only the needed methods instead of implementing all methods.

To control the list that is displayed when the user types a text, return the list of items by overriding the method `getFiltredPossibilities(String text)`.

## Widget design ##

`DefaultSuggestBox` defines through a ui.xml file the look it has. There are many possibilities to alter this look by appending elements all around it through the methods available in its field **textField** of type `SuggestTextBoxWidgetImpl<T, W>`. The Iconed version uses `setLeftWidget(IsWidget left)` to put an icon to the left of the widget. Similar methods `setXXXWidget` are available for the 4 sides.

If the available settings aren't sufficient for your needs you can  build a new design by extending `AbstractSuggestBox<T,W>` and creating your own implementation of `SuggestTextBoxWidget<T, W>`. The method 'AbstractSuggestBox.getTextField()` is your connection to the API. In addition, you can keep the behavior of the default event handling by calling from your implementation the methods `onBlur`, `onKeyUp`, `onDoubleClick` and more. If you decided to start your own component, we recommend you look at the code of `DefaultSuggestBox` that is pretty direct.

## Displaying the list differently ##
### CSS styling ###
You can do pretty much on the way the list is displayed through the different css styles available for each subcomponent:

// add details here

### Defining your own items ###
Items are defined through the interface `ValueHolderLabel<T>`. You should implement this interface if you want to change the display of each item in the list. For example if the items should display an image, a table row or any other display by item.

To have your own items, you will have to create two implementations:
  1. the item it self :  implement the `interface ValueHolderLabel<T>`
  1. the item factory : implement the `interface ValueRendererFactory<T, W extends ValueHolderLabel<T>>`
where
  1. `<T>` is the type of your data (the class repesenting an item)
  1. `<W>` is your implementation of `ValueHolderLabel<T>`

Finally inform the suggest box that you want to use your own item implementation by calling the method
```
AbstractSuggestBox.setValueRendererFactory(
			ValueRendererFactory<T, ? extends ValueHolderLabel<T>> valueRendererFactory) 
```

passing it the factory you implemented to create your own items.

Here's the code of the default implementations that is pretty direct:
```
public class DefaultValueRenderer<T> extends HTML implements
		ValueHolderLabel<T> {
	private static final String ITEM_HOVER = "eu-nextstreet-SuggestItemHover";
	private static final String MATCHING_STRING = "eu-nextstreet-SuggestMatchingString";
	protected T value;

	public DefaultValueRenderer(T value, String filterText) {
		this.value = value;
		String html = value.toString();
		setHTML(html.replace(filterText, "<span class='" + MATCHING_STRING
				+ "'>" + filterText + "</span>"));
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public void hover(boolean hover) {
		if (hover)
			addStyleName(ITEM_HOVER);
		else
			removeStyleName(ITEM_HOVER);
	}

	public static final String SELECTED = "eu-nextstreet-SuggestItemSelected";

	@Override
	public void setFocused(boolean focused) {
		if (focused)
			addStyleName(SELECTED);
		else
			removeStyleName(SELECTED);
	}

}
```

```

public class DefaultValueRendererFactory<T> implements
		ValueRendererFactory<T, DefaultValueRenderer<T>> {

	@Override
	public DefaultValueRenderer<T> createValueRenderer(T value,
			String filterText) {
		return new DefaultValueRenderer<T>(value, filterText);
	}

}
```

As you see, when writing your own implementations you will have to handle the mouse over and focus events your own way (which is the asked feature).

Notice that you can change the items when the application is already running without trouble, just change the current renderer.

### Providing your own list container ###
You can also decide not to show the list under the suggest box but above it, or inside another container (for example a drop down panel).

In that case you will have to provide your own implementation of the `interface SuggestWidget<T>` then call

```
AbstractSuggestBox.setSuggestWidget(SuggestWidget<T> SuggestWidget)
```
on your suggest box to provide your instance.

Notice that you can change the list box at runtime, however you will have to handle the cleaning of the old box your self.

# Listening to events #
To listen to value change events you have two choices:
  * Get a notification the simple way by adding a `ChangeHandler`
  * Get a more evolved notification that tells you whether a value was selected or typed. Keeping a type safe linking when a value was selected.

## Simple notification ##
You can listen to the change events by adding an instance of the standard gwt class `ChangeHandler` to your SuggestBox
```
		box.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
//			((TextBox) event.getSource()).getText();
// a coming version will add type safety handling also in this option
			}
		});
```

You will receive the event at the moment you expect it:
  1. If an item is clicked in the list (immediately)
  1. If an item is selected in the list with the keyboard (immediately)
  1. When the user types a text and validates it with enter (immediately) including for a text that is not in the list
  1. When a value is selected automatically because it's the only matching value (on blur)

**Note:** when a user types a value that's text matches exactly the text of a value in the list, the value is considered as selected (in opposition to typed). See the method `protected String toString(final T t)` for more details about this section.

## Advanced notification ##
You can get into more details about whether the user selected a value or typed one that is not in the list by extending `AbstractSuggestBox<T>` or one of its descendants and overriding the methods:
  1. `public void valueSelected(T value)`
  1. `public void valueTyped(String value)`
As you can see, the method `valueSelected()` receives directly the element that was selected in the list instead of a String value. This method is called each time a value is selected from the list in any of the situations 1, 2, 4 described in the section above.

In opposition the method `valueTyped()` is called only when a value is typed manually.

**in both cases don't forget to call the super implementation to accomplish the standard handling**

If you want to catch the event even earlier, consider overriding the method `protected boolean fillValue(final T t, boolean commit)` which handles the SuggestList closing, the text filling of the value inside the text box (via the method `protected String toString(final T t)`) and the decision of firing an event immediately or on blur.