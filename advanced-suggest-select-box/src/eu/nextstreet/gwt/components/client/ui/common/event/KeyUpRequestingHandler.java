package eu.nextstreet.gwt.components.client.ui.common.event;

import com.google.gwt.core.client.Scheduler;

/**
 * A helper class that reduces server requests by sending a request only if a
 * non typing delay is noticed
 * 
 * @author Zied Hamdi http://1vu.fr
 * 
 */
public abstract class KeyUpRequestingHandler {
	/**
	 * Interval in ms between two letters typed to launch a request
	 */
	protected int typingDelayToRequest = 300;
	protected String currentText;
	/**
	 * Minimal charters count to start processing: this is useful in suggest
	 * boxes: eg. no proposals before 3 chars
	 */
	protected int minTextLenth = 0;
	protected boolean isRunningSchedule;
	protected String textBeingRequested;

	/**
	 * Analyzes the typing speed and the characters count then decides whether to
	 * launch a request: avoids launching many requests.
	 * 
	 * @param query
	 */
	public void handleKeyUp(String query) {
		if (query.length() >= minTextLenth) {
			// System.out.println("running shedule: " + isRunningSchedule);
			if (!isRunningSchedule) {
				Scheduler scheduler = Scheduler.get();
				scheduler.scheduleFixedDelay(new Scheduler.RepeatingCommand() {

					@Override
					public boolean execute() {
						String query = getText();
						if (textBeingRequested == null || !textBeingRequested.equals(query)) {
							// System.out.println("started running schedule with " + query +
							// " but text is still being requested " + textBeingRequested);
							textBeingRequested = query;
							isRunningSchedule = true;
							return true;
						}
						// System.out.println("executing with " + query);

						executeRequest(query);
						isRunningSchedule = false;
						return false;
					}
				}, (int) typingDelayToRequest);

			}
		}
	}

	/**
	 * Your implementation of the request launching; You must call
	 * {@link #onRequestExecuted(String)} when the request finishes executing.
	 * 
	 * @param query
	 */
	protected abstract void executeRequest(String query);

	/**
	 * Returns the typed text
	 * 
	 * @return
	 */
	protected abstract String getText();

	/**
	 * This method must be called in the async or sync process launched by
	 * {@link #executeRequest(String)} after the answer has been loaded.
	 * 
	 * @param query
	 */
	protected void onRequestExecuted(String query) {
		textBeingRequested = null;
	}

	public int getTypingDelayToRequest() {
		return typingDelayToRequest;
	}

	public void setTypingDelayToRequest(int typingDelayToRequest) {
		this.typingDelayToRequest = typingDelayToRequest;
	}

	public int getMinTextLenth() {
		return minTextLenth;
	}

	public void setMinTextLenth(int minTextLenth) {
		this.minTextLenth = minTextLenth;
	}

}
