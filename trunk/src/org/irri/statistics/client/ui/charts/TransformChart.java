package org.irri.statistics.client.ui.charts;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;


public class TransformChart extends Composite {
	String[][] basedata;
	public TransformChart() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		MenuBar menuBar = new MenuBar(false);
		menuBar.setAutoOpen(true);
		menuBar.setAnimationEnabled(true);
		verticalPanel.add(menuBar);
		MenuBar menuBar_1 = new MenuBar(true);
		
		MenuItem mntmOptions = new MenuItem("Series", false, menuBar_1);
		MenuBar menuBar_2 = new MenuBar(true);
		
		MenuItem mntmRegionInSeries = new MenuItem("Region", false, menuBar_2);
		menuBar_1.addItem(mntmRegionInSeries);
		MenuBar menuBar_4 = new MenuBar(true);
		
		MenuItem mntmVariableInSeries = new MenuItem("Variable", false, menuBar_4);
		menuBar_1.addItem(mntmVariableInSeries);
		menuBar.addItem(mntmOptions);
		MenuBar menuBar_3 = new MenuBar(true);
		
		MenuItem mntmChartType = new MenuItem("Chart Type", false, menuBar_3);
		menuBar.addItem(mntmChartType);
		
		MenuItem mntmInteractive = new MenuItem("Interactive", false, new Command() {
			public void execute() {
			}
		});
		menuBar.addItem(mntmInteractive);
		
	}

}
