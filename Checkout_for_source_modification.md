#How to check out a valid project

# Introduction #
This page explains how to check out the project source for edition to have a ready to use GWT project.


# Details #
From your eclipse SVN Repository view right click and choose **New > Repository Location...**

In the _Url_ field type **http://advanced-suggest-select-box.googlecode.com/svn/**

Now open the tree hierarchy to the node **trunk/advanced-suggest-select-box**, right click and choose the option **Find/Check out as...**

Select **Checkout as a project configured using the New Project Wizard** then presse **Finish** and select **Google > Web Application Project**

In the project name type : **IntoGwt** , and in the package name type **eu.nextstreet.gwt.components**

Select a gwt version 2.5 or higher. (you can try with lower versions, but you should know we're not testing the project under the 2.5 version)

When prompted if the existing files can be overridden choose yes so that the gwt.xml original file overrides the one created by your project wizard.

_To be sure the "new project wizard" didn't override the files downloaded from svn, redo a svn synchronize on the project and select "override and update" on the project root._

**To be able to see the [demo app](http://into-i.fr/suggestbox/) running, you need to set the boolean field _IntoGwt.test_ value to true, then run the project as a GWT application**

You're done! you can run the gwt project now. Check the boolean variable IntoGWT.test is initialized to 'true': this enables the test procedure.

# Troubleshooting #
## lib/gwt-servlet.jar missing ##
If the project doesn't compile, uncheck the Google Web Application option checkbox (Project properties -> Google -> Web Kit -> Use Google web toolkit). press OK and wait until it compiles. Then go back to that menu and check the option again. Clicking ok should resolve the issue.