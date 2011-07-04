package org.irri.statistics.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class Wrs_Browser extends Composite {

	public Wrs_Browser() {
		
		DockLayoutPanel BrowserWrapper = new DockLayoutPanel(Unit.PX);
		initWidget(BrowserWrapper);
		
		RPFiltering FilterPanel = new RPFiltering();
		BrowserWrapper.add(FilterPanel);
	}

}
