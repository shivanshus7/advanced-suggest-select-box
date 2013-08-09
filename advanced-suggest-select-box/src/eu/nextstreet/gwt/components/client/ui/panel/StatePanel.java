package eu.nextstreet.gwt.components.client.ui.panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;

import eu.nextstreet.gwt.components.client.ui.widget.util.WidgetUtil;

/**
 * This class is a panel with possible {@link PanelState} each expressed through
 * its css style defined by {@link PanelState#name()}. It handles click events
 * to notify its manager that a state change request was fired.
 * 
 * two {@link PanelState} implementations are provided :
 * 
 * DefaultPanelState : states are defined as an Enum with two values
 * {@link DefaultPanelState#PRESSED} and {@link DefaultPanelState#UNPRESSED}
 * 
 * SimplePanelState accepts a String in its constructor: that string will be
 * used as the css style
 * 
 * In addition to the states, each state of the StatePanel can be in an enabled
 * or disabled status
 * 
 * @author Zied Hamdi founder of http://1vu.fr
 * 
 */
public class StatePanel<T extends StatePanel.PanelState> extends SimplePanel implements HasValueChangeHandlers<T> {
	public enum EnabledStatus {
		ENABLED, DISABLED
	}

	/**
	 * Implement this interface to define your states
	 * 
	 * @author Zied Hamdi founder of http://1vu.fr
	 * 
	 */
	public static interface PanelState {
		public String name();
	}

	/**
	 * Implement this interface by the object tha manages this panel
	 * 
	 * @author Zied Hamdi founder of http://1vu.fr
	 * 
	 */
	public static interface StatePanelManager<T extends StatePanel.PanelState> {
		/**
		 * Called when the panel detected a state change request
		 * 
		 * @param panel
		 *          the source panel
		 * @return the state that must be applied to panel
		 */
		public T onStateChangeRequest(StatePanel<T> panel);

		public T register(StatePanel<T> statePanel);
	}

	/**
	 * 
	 * @author Zied Hamdi founder of http://1vu.fr
	 * 
	 */
	public static class SimplePanelState implements PanelState {
		protected final String state;

		public SimplePanelState(String state) {
			this.state = state;
		}

		@Override
		public String name() {
			return state;
		}

		@SuppressWarnings("unchecked")
		public static <T extends StatePanel.PanelState> T[] construct(String... names) {
			ArrayList<SimplePanelState> toReturn = new ArrayList<StatePanel.SimplePanelState>();
			for (String name : names) {
				toReturn.add(new SimplePanelState(name));
			}

			return ((T[]) toReturn.toArray(new SimplePanelState[toReturn.size()]));
		}
	}

	protected T[] stateList;
	protected T state;
	protected StatePanelManager<T> panelManager;
	protected boolean enabled = true;
	protected List<ValueChangeHandler<T>> handlerList;
	protected List<UIObject> skipElements = new ArrayList<UIObject>();

	/**
	 * Constructs the panel with two default values ("UNPRESSED", "PRESSED") and
	 * {@link DefaultStatePanelManager}. this panel toggles between the two states
	 */
	public StatePanel() {
		this(new DefaultStatePanelManager<T>());
	}

	public StatePanel(StatePanelManager<T> panelManager) {
		this(panelManager, "UNPRESSED", "PRESSED");
	}

	public StatePanel(T[] stateList) {
		this(stateList, new DefaultStatePanelManager<T>());
	}

	/**
	 * Constructs the panel with {@link SimplePanelState} values and
	 * {@link DefaultStatePanelManager}. this panel toggles between the string
	 * states
	 */
	@SuppressWarnings("unchecked")
	public StatePanel(String... stateList) {
		this((T[]) SimplePanelState.construct(stateList), new DefaultStatePanelManager<T>());
	}

	/**
	 * Constructs the panel with {@link SimplePanelState} values. This panel is
	 * handled by panelManager
	 */
	@SuppressWarnings("unchecked")
	public StatePanel(StatePanelManager<T> panelManager, String... stateList) {
		this((T[]) SimplePanelState.construct(stateList), panelManager);
	}

	public StatePanel(T[] stateList, StatePanelManager<T> panelManager) {
		this.stateList = stateList;
		setState(panelManager.register(this));
		this.panelManager = panelManager;
		initHandlers();
	}

	protected void initHandlers() {
		addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!skip(event))
					requestStateChange();
			}
		}, ClickEvent.getType());
	}

	public PanelState getState() {
		return state;
	}

	public void setState(T panelState) {
		this.state = panelState;
		for (PanelState state : stateList) {
			String stateCssName = state.name();
			if (state.equals(panelState)) {
				addStyleName(stateCssName);
			} else {
				removeStyleName(stateCssName);
			}
		}
		fireStateChangeOccured();
	}

	protected void fireStateChangeOccured() {
		ValueChangeEvent.fire(this, state);
	}

	@Override
	public void setStyleName(String style) {
		super.setStyleName(style);
		// since all styles were removed, relaunch style processing
		refreshStyles();
	}

	protected void refreshStyles() {
		setState(state);
		setEnabled(enabled);
	}

	/**
	 * asks the manager to get switch state ifaoif {@link #isEnabled()} is true
	 */
	protected void requestStateChange() {
		if (!enabled)
			return;

		setState(panelManager.onStateChangeRequest(this));
	}

	public T[] getStateList() {
		return stateList;
	}

	public void setStateList(T[] stateList) {
		this.stateList = stateList;
	}

	public StatePanelManager<T> getPanelManager() {
		return panelManager;
	}

	public void setPanelManager(StatePanelManager<T> panelManager) {
		this.panelManager = panelManager;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		String enabledStyle = EnabledStatus.ENABLED.name();
		String disbledStyle = EnabledStatus.DISABLED.name();
		addStyleName(enabled ? enabledStyle : disbledStyle);
		removeStyleName(enabled ? disbledStyle : enabledStyle);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return super.addHandler(handler, ValueChangeEvent.getType());
	}

	/**
	 * Utility method used to skip some children from mouse events
	 * 
	 * @param x
	 * @param y
	 * @return true if the x, y absolute window coordinates are in one of
	 *         {@link #skipElements} {@link UIObject}s
	 */
	protected boolean skip(int x, int y) {
		for (UIObject toSkip : skipElements) {
			if (WidgetUtil.inBounds(toSkip, x, y))
				return true;
		}
		return false;
	}

	/**
	 * Utility method used to skip some children from mouse events
	 * 
	 * @param event
	 * @return true if the mouse event is in one of {@link #skipElements}
	 *         {@link UIObject}s
	 */
	@SuppressWarnings("rawtypes")
	protected boolean skip(MouseEvent event) {
		return skip(event.getClientX(), event.getClientY() + Window.getScrollTop());
	}

	public void setSkipElements(UIObject... elements) {
		skipElements.clear();
		addSkipElements(elements);
	}

	public void addSkipElements(UIObject... elements) {
		skipElements.addAll(Arrays.asList(elements));
	}

	public void removeSkipElements(UIObject... elements) {
		skipElements.removeAll(Arrays.asList(elements));
	}

}
