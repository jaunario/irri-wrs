package org.irri.statistics.client.ui.charts;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.MotionChart.Options;

public class DBMotionChart extends Composite {
	private ScrollPanel scrollPanel = new ScrollPanel();
	public DBMotionChart(AbstractDataTable datatable, Options options) {
		
		initWidget(scrollPanel);
	}
	
	public void plot(AbstractDataTable datatable, Options options){
		final Options opts = options;
		final AbstractDataTable data = datatable;

		Runnable onLoadCallback = new Runnable() {
			public void run() {
				MotionChart mychart = new MotionChart(data, opts);
				scrollPanel.add(mychart);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, MotionChart.PACKAGE);
	}
	
	public Options createOptions(){
		Options mcoptions = Options.create();
		mcoptions.setShowChartButtons(true);
		return mcoptions;
	}
}
