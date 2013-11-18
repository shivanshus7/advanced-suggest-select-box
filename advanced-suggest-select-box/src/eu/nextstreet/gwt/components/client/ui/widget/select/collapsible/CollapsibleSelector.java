package eu.nextstreet.gwt.components.client.ui.widget.select.collapsible;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * This is like a toggle panel with some more control :
 * <ul>
 * <li>* add click action
 * <li>* add mouse over for a delay action
 * <li>
 * 
 * @author Zied Hamdi - http://1vu.fr
 * 
 */
public class CollapsibleSelector<L extends Widget, C extends Widget> extends Composite {

	private static final String COLLAPSED = "collapsed";

	private static final String EXPANDED = "expanded";

	private static CollapsibleSelectorUiBinder uiBinder = GWT.create(CollapsibleSelectorUiBinder.class);

	@SuppressWarnings("rawtypes")
	interface CollapsibleSelectorUiBinder extends UiBinder<Widget, CollapsibleSelector> {
	}

	public interface CollapseHandler extends EventHandler {

		void onStateChange(CollapseEvent collapseEvent);

	}

	public static class CollapseEvent extends GwtEvent<CollapseHandler> {
		public static final GwtEvent.Type<CollapseHandler> TYPE = new Type<CollapseHandler>();

		protected final boolean expanded;

		public CollapseEvent(boolean expand) {
			this.expanded = expand;
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<CollapseHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(CollapseHandler handler) {
			handler.onStateChange(this);
		}

		public boolean isExpanded() {
			return expanded;
		}

	}

	@UiField
	protected HTMLPanel collapsibleSelector;

	@UiField
	protected SimplePanel expanderPanel;

	@UiField
	protected SimplePanel collapsiblePanel;

	protected boolean collapseOnFocusLost;

	protected HandlerManager handlerManager;

	public CollapsibleSelector() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CollapsibleSelector(boolean startExpanded) {
		this();
		handlerManager = new HandlerManager(this);
		// this is done just because at startup updatetuylesNames will this the style is already set
		collapsibleSelector.addStyleName(EXPANDED);

		setExpanded(startExpanded);
	}

	public CollapsibleSelector(String label, boolean startExpanded) {
		this(startExpanded);
		Widget expanderWidget = createDefaultLabel(label);
		expanderPanel.add(expanderWidget);
	}

	protected Widget createDefaultLabel(String label) {
		Label expander = new Label(label);
		addExpandClickHandler(expander);
		return expander;
	}

	protected void addFinishDetection(HasAllFocusHandlers expander) {
		// TODO Auto-generated method stub

	}

	protected void addMouseOverHandler(HasMouseOverHandlers expander) {
		expander.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				toggleExpanded();
			}
		});
	}

	protected void addExpandClickHandler(HasClickHandlers expander) {
		expander.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				toggleExpanded();
			}
		});
	}

	public void toggleExpanded() {
		setExpanded(!isExpanded());
	}

	public void setExpanded(boolean expand) {
		boolean stateChanged = updateStyles(expand);
		if (stateChanged) {
			collapsiblePanel.setVisible(expand);
			CollapseEvent event = new CollapseEvent(expand);
			handlerManager.fireEvent(event);
		}
	}

	public boolean updateStyles(boolean expand) {
		String styleNameToAdd = expand ? EXPANDED : COLLAPSED;
		String styleNameToRemove = expand ? COLLAPSED : EXPANDED;
		boolean currentState = isExpanded();
		if (currentState == expand)
			return false;

		collapsibleSelector.addStyleName(styleNameToAdd);
		collapsibleSelector.removeStyleName(styleNameToRemove);
		return true;
	}

	public boolean isExpanded() {
		return collapsiblePanel.isVisible();
	}

	public void setCollapsibleWidget(C widget) {
		// setCollapseOnFocusLost(collapseOnFocusLost);

		collapsiblePanel.setWidget(widget);
	}

	@SuppressWarnings("unchecked")
	public C getCollapsibleWidget() {
		return (C) collapsiblePanel.getWidget();
	}

	public void setExpanderWidget(L widget) {
		// setCollapseOnFocusLost(collapseOnFocusLost);

		expanderPanel.setWidget(widget);
	}

	@SuppressWarnings("unchecked")
	public L getExpanderWidget() {
		return (L) expanderPanel.getWidget();
	}

	public boolean isCollapseOnFocusLost() {
		return collapseOnFocusLost;
	}

	// doesn't work (report it to later)
	// public void setCollapseOnFocusLost(boolean collapseOnFocusLost) {
	// this.collapseOnFocusLost = collapseOnFocusLost;
	// Widget collapsibleWidget = collapsiblePanel.getWidget();
	// if (collapsibleWidget == null)
	// return;
	//
	// if (collapsibleWidget instanceof HasFocusHandlers)
	// addFinishDetection((HasAllFocusHandlers) collapsibleWidget);
	// else
	// throw new UnsupportedOperationException("Cannot call this method if your collapsible widget is not a HasFocusHandlers : " + collapsibleWidget.getClass());
	// }

	public HandlerRegistration addCollapseHandler(CollapseHandler handler) {
		return handlerManager.addHandler(CollapseEvent.TYPE, handler);
	}

}
