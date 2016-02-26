**Ce projet est fondé par Zied Hamdi consultant expert en technologies open source, fondateur de [1VU (One View)](http://1vu.fr), société éditeur et SSII off-shore de consulting en nouvelles technologies (GWT, Android, Google, JEE, etc...), n'hésitez pas à nous contacter pour intervenir sur vos projets partout en France.**

### Follow this [link](http://1vu-widgets.appspot.com/IntoGwt.html) to see the widget demo. ###

| **Icon Handling Suggest Box** | **Personalized Html Table Suggest Box** | **Multiple Value Suggest Box** |
|:------------------------------|:----------------------------------------|:-------------------------------|
|<a href='http://into-i.fr/suggestbox/'><img src='http://into-i.fr/suggestbox/img/suggestBoxCapture.PNG' /></a> |  <a href='http://into-i.fr/suggestbox/'><img src='http://into-i.fr/suggestbox/img/suggestBoxTableCapture.PNG' /></a> | <a href='http://into-i.fr/suggestbox/'><img src='http://into-i.fr/suggestbox/img/multiValuedSuggestBoxCapture.PNG' /></a> |


# Standard select and suggest boxes have these issues: #
  * The select box style can not be changed much, in addition its appearance differs between browsers
  * On a select box or a suggest box we are obliged to establish the complete list of possibilities before we could display the widget, this can slow down performance.
  * We can't build complex items (eg. with images and advanced widgets) in the list of possible values (nor in the select box it self),
  * Filtering when you type in a suggest box is fixed to a "starts with" algorithm that doesn't answer to all project requirements.
  * we cannot react on events like triggering a help toolbox on each value on a mouse over

This project answers to all these problems with many pretty and direct extension points.  In addition, advanced default implementations allow you to answer to the major cases by implementing none or one method only.

# With this lib you'll be able to: #
  * Have a **multiple value selection** suggest box (like facebook's mail contact selection)
  * Use the **Icon form** of the suggest box with many possibilities of display: additional information can be displayed on top of the suggest box (to explain a value or a state), the choice list can be a HTML table with many columns, etc...
  * Benefit of the many implemented features like **double click, keyboard events handling** etc...
  * Control the way **items are filtered** (by implementing just one "accept()" method)
  * Control the style of the component by providing you own **css elements for many individually controlled parts**.
  * Provide a different way to choose from possible values: the default way is a list that pops up under a text box, but you can imagine other ways like a circle, a panel or any presentation. In that case you'll have to implement only the visual part, the rest is taken care of by the component.
  * Use the features here as a basis library for your evolved needs: **Many extension points** allow you in a non intrusive way, to take advantage of atomic features of the library
  * **Optimized performance** for select boxes with many items: items are not charged at page load but at first query and the list size can be limited

A complete documentation is available in the wiki section.

**Please feel free to suggest features. Be sure that all of them will be deeply analysed before deciding to adopt or not.
Also star the project if you think it answers to your needs and that it should evolve at higher speed.**

_The project version 2.0.3 is used in an R&D project of the 1Vu company, we are making changes to fufill the needs of the project and open sourcing stuff that is general enough to be useful to the community, new versions may still come out in the near future since the proprietary project is still under development_

_The version 1.0.3-beta is in production in a big Frensh webapp since may 2010. The customer didn't complain about any bugs so we didn't need to produce a 1.0.4-stable :-), The other versions : 1.5.0 and 1.6.0 are not in production but were tested extensively before releasing so don't be afraid of the beta mention. I will be happy to correct any reported bugs, things should go fast because the project architecture was designed to separate rules thoroughly. You can also refine the code as needed: the Apache Licence allows it.
Today I'm busy on other projects, but if I see a real demand on this project, I will add the GWT Cell technology support and the many other improvements I have in mind_