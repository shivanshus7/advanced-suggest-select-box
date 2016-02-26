This page is dedicated to the css styling of the suggest boxes

# Introduction #

The library doesn't include a Resources client bundle since there was no time allocated for that task. However, its css integration is pretty direct: just copy the included css file content to your own project to start working on your design.

# Hints #

To be able to work with the popup content, directly from your browser using FireBug or a silmilar tool, there's a possibility I use to keep the popup open event if I click outside of it, or if I select an element.

To do this, add this line to you onModuleLoad() method:
```
  DefaultSuggestionPopup.DEBUG_MODE = true;
```