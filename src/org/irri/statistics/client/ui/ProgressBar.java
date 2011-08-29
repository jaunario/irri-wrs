package org.irri.statistics.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class ProgressBar extends Composite {

	public ProgressBar() {
		
		Grid grid = new Grid(1, 10);
		initWidget(grid);
	}

}
