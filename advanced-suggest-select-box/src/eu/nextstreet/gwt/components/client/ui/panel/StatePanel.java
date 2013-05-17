package eu.nextstreet.gwt.components.client.ui.panel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;

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
 * @author Zied Hamdi http://1vu.fr
 * 
 */
public class StatePanel<T extends StatePanel.PanelState> extends FocusPanel implements HasValueChangeHandlers<T> {
	public enum EnabledStatus {
		ENABLED, DISABLED
	}

	/**
	 * Implement this interface to define your states
	 * 
	 * @author Zied Hamdi http://1vu.fr
	 * 
	 */
	public static interface PanelState {
		public String name();
	}

	/**
	 * Implement this interface by the object tha manages this panel
	 * 
	 * @author Zied Hamdi http://1vu.fr
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
	 * @author Zied Hamdi http://1vu.fr
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
	protected T panelState;
	protected StatePanelManager<T> panelManager;
	protected boolean enabled;
	protected List<ValueChangeHandler<T>> handlerList;

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
		setPanelState(panelManager.register(this));
		this.panelManager = panelManager;
		initHandlers();
	}

	protected void initHandlers() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				requestStateChange();
			}
		});
	}

	public PanelState getPanelState() {
		return panelState;
	}

	public void setPanelState(T panelState) {
		this.panelState = panelState;
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
		ValueChangeEvent.fire(this, panelState);
	}

	@Override
	public void setStyleName(String style) {
		super.setStyleName(style);
		// since all styles were removed, relaunch style processing
		refreshStyles();
	}

	protected void refreshStyles() {
		setPanelState(panelState);
		setEnabled(enabled);
	}

	/**
	 * asks the manager to get switch state ifaoif {@link #isEnabled()} is true
	 */
	protected void requestStateChange() {
		if (!enabled)
			return;

		setPanelState(panelManager.onStateChangeRequest(this));
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

}
