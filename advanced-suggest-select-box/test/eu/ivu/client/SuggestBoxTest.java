package eu.ivu.client;

import com.google.gwt.core.client.EntryPoint;

import eu.nextstreet.gwt.components.client.ui.widget.suggest.impl.DefaultSuggestionPopup;

public class SuggestBoxTest implements EntryPoint {

	@Override
	public void onModuleLoad() {
		DefaultSuggestionPopup.DEBUG_MODE = true;
		ValidationTest.testWidget();
	}

}
