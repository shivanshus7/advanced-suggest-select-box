package eu.nextstreet.gwt.components.client.ui.panel;

import eu.nextstreet.gwt.components.client.ui.panel.StatePanel.PanelState;
import eu.nextstreet.gwt.components.client.ui.panel.StatePanel.StatePanelManager;

/**
 * Default implementation of a {@link StatePanelManager} that simply loops on
 * the possible states
 * 
 * @author Zied Hamdi http://1vu.fr
 * 
 */
public class DefaultStatePanelManager<T extends StatePanel.PanelState> implements StatePanelManager<T> {

	protected StatePanel<T> statePanel;
	protected int index = -1;

	@Override
	public T onStateChangeRequest(StatePanel<T> panel) {
		return nextState(panel.getState());
	}

	protected T nextState(PanelState panelState) {
		index = (++index % statePanel.getStateList().length);
		return statePanel.getStateList()[index];
	}

	@Override
	public T register(StatePanel<T> statePanel) {
		this.statePanel = statePanel;
		return nextState(null);
	}

}
