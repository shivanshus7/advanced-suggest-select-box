#This page describes the steps to integrate the current version in your project

# Introduction #
The component is a very standard GWT component, used GWT users will not need to read this document.
Here we describe how to integrate the jar in an eclipse development environment.

# Details #
  1. Download the current [jar](http://code.google.com/p/advanced-suggest-select-box/downloads/list) file
  1. Put it in your library folder (next to your other jars: a good practice is to put it inside the project so it can be referenced through a relative url that will avoid additional configuration when getting versions from your source repository eg: svn)
  1. Add the jar in your classpath : right click projet > Properties > Java Build Path > Libraries > Ad JARs
  1. In your project's _**.gwt.xml** file add the line
```
	<inherits name='eu.nextstreet.gwt.components.IntoGwt' />
```_

You're done. You can now either use directly the DefaultSuggestBox class, extend it or extend one of the many available extension points.

Have Fun :-)