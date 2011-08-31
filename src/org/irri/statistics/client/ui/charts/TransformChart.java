package org.irri.statistics.client.ui.charts;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.visualization.client.AbstractDataTable;


public class TransformChart extends Composite {
	private VerticalPanel vpChartWrapper = new VerticalPanel();
	String[][] basedata;
	public int charttype = 1;
	public int[] numerics;
	public int plotcol = 0;
	public TransformChart(String[][] data,int[] numcols, int type, boolean interactive) {
		initmenu();	
		basedata = data;
		numerics = numcols;
		AbstractDataTable datatable = ChartDataTable.create(basedata, numerics);
		switch (type) {
		case 0:
			BarChartPanel barchar = new BarChartPanel(datatable, "Your barchart", 300, 300, true);
			vpChartWrapper.add(barchar);
			break;
		case 1:
			break;
		default:
			break;
		}
		initWidget(vpChartWrapper);
	}
	
	private void initmenu(){
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
		
		MenuItem mntmLine = new MenuItem("Line", false, (Command) null);
		menuBar_3.addItem(mntmLine);
		
		MenuItem mntmBar = new MenuItem("Bar", false, (Command) null);
		menuBar_3.addItem(mntmBar);
		
		MenuItem mntmScatter = new MenuItem("Scatter", false, (Command) null);
		menuBar_3.addItem(mntmScatter);
		menuBar.addItem(mntmChartType);
		
		MenuItem mntmInteractive = new MenuItem("Interactive", false, new Command() {
			public void execute() {
			}
		});
		menuBar.addItem(mntmInteractive);
		
	}
	
	public void setInteractive(boolean isinteractive){
		
	}
		

}
