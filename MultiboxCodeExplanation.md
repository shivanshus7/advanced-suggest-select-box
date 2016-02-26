This page will explain the test code of the multibox available from line 164 in [the ValidationTest example](https://code.google.com/p/advanced-suggest-select-box/source/browse/trunk/advanced-suggest-select-box/test/eu/nextstreet/gwt/components/client/ValidationTest.java#164)


# Introduction #
The MultiChoiceSuggestBox is a suggest box that allows to select multiple values (including multiple times the same value). It was first democratized for the facebook mail to add multiple contacts to your choice.

An example is available in the demo.

Here's an explanation of how to code it.

# Details #
In MultiChoiceSuggestBox there are three generic arguments
```
MultiChoiceSuggestBox<Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>>
```
  1. The class type of the values contained in the suggest box
  1. The representer of a value while in the suggested values list
  1. The representer of a selected value while in the selected items list

In addition, it receives three parameters in its constructor:
  1. The "no value" text that helps to understand what the field is for
  1. The position of the panel that will hold the selected values list (note that you have the possibility to put the panel completely out of the box)
  1. The factory for the items of the "selected values panel" (the type of the items was mentioned as the third generic argument)

With the following lines, we decided to make the selected values show only icons and no text.
Any Widget can be returned by the factory to represent selected items in the suggest box, for that, you need to implement **ValueRendererFactory**. By default we provided a mechanism that should fulfill the need of the majority of users : it displays text or an icon + text. This mechanism uses the **StringFormulator** that transforms an Object value to its String human readable representation. The mechanism of creating the text value through StringFormulator is expressed in AbstractValueRendererFactory. So we must cast our factory to AbstractValueRendererFactory to get access to the wanted method: **setStringFormulator**. From that instant, the method **toString(V value)** will be used to display the text value of an item
```
((AbstractValueRendererFactory) choiceItemsRendererFactory.getTextRendererFactory()).setStringFormulator(new StringFormulator<Value>() {

  @Override
  public String toString(Value t) {
    return "";
  }
});
```

With the following line, we say to the suggest box which text should be displayed for the elements in the suggestion list (while still not selected)
```
multiBox.setStringFormulator(stringFormulator);
```

We also tell the suggest box to suggest only elements that start with the typed text (as opposed to "contains" the typed text)

**Note that the options mecanism can allow you to extend the suggest box and pass it your own options through this same mechanism**
```
multiBox.putOption(new BooleanOption(DefaultOptions.STARTS_WITH.name(), true));
```

The next line sets the interpreter of the items for the icon zone (which is displayed in the suggestion list) :
```
 multiBox.setIconLinker(iconLinker);
```

Now you can set the values with which the suggest box is initialized
```
List<Value> multiSelection = new ArrayList<ValidationTest.Value>();
multiSelection.add(blogger);
multiSelection.add(chromeToolBox);
multiBox.setValues(multiSelection);
```

Congratulations! you're done. You can now react on events.

See also the [Introduction](Introduction.md) section for further details

The complete example is below

```java

final MultiChoiceSuggestBox<Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>> multiBox = new MultiChoiceSuggestBox<Value, IconedValueRenderer<Value>, MultiChoiceValueHolderLabel<Value>>(
"select or type value", DockPanel.NORTH, choiceItemsRendererFactory);


((AbstractValueRendererFactory) choiceItemsRendererFactory.getTextRendererFactory()).setStringFormulator(new StringFormulator<Value>() {

@Override
public String toString(Value t) {
return "";
}
});

multiBox.setStringFormulator(stringFormulator);
multiBox.putOption(new BooleanOption(DefaultOptions.STARTS_WITH.name(), true));
multiBox.getTextField().getTop().setStyleName("eu-nextstreet-MultiChoiceSelection");
fillData(multiBox);
multiBox.setIconLinker(iconLinker);

List<Value> multiSelection = new ArrayList<ValidationTest.Value>();
multiSelection.add(blogger);
multiSelection.add(chromeToolBox);
multiBox.setValues(multiSelection);

multiBox.addHandler(new ChangeHandler() {

@Override
public void onChange(ChangeEvent event) {
//.. do your stuff
}
});

```