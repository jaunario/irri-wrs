package org.irri.statistics.client.ui.charts;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class DBLineChart extends Composite {
	private VerticalPanel VisBox = new VerticalPanel();
	public boolean interactive = true;
	public DBLineChart(String[][] data, String title, int width, int height) {
		initWidget(VisBox);
		final AbstractDataTable mydata = createTable(data);
		plot(mydata, createOptions());
		
	}
	
	public DBLineChart(AbstractDataTable datatable, Options options){
		initWidget(VisBox);
		plot(datatable, options);
	}
	
	public void plot(AbstractDataTable datatable, Options options){
		final Options opts = options;
		final AbstractDataTable data = datatable;
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				LineChart mylinechart = new LineChart(data, opts);
				VisBox.add(mylinechart);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
	}
	public static Options createOptions() {
        Options options = Options.create();
        AxisOptions hAxisOptions = AxisOptions.create();
    	AxisOptions vAxisOptions = AxisOptions.create();
    	hAxisOptions.setTitle("Year");
    	//vAxisOptions.setTitle("Year");
    	options.setHAxisOptions(hAxisOptions);
    	options.setVAxisOptions(vAxisOptions);
        options.setWidth(320);
        options.setHeight(300);
        options.setTitle("This is a lince chart");
        options.set("legend", "bottom");
        return options;

	}

	private AbstractDataTable createTable(String[][] qdata) {
        DataTable data = DataTable.create();
        for (int i=0;i<qdata.length;i++){
        	if (i==1) data.addRows(qdata.length-1);
        	for (int j = 1; j < qdata[i].length; j++) {
				if (i==0){
					if (j==1) data.addColumn(AbstractDataTable.ColumnType.STRING, qdata[i][j]);
					else data.addColumn(AbstractDataTable.ColumnType.NUMBER, qdata[i][j]);
				}
				else {
					if (j==1) data.setValue(i-1, j-1, qdata[i][j]);
					else if(qdata[i][j]!=null) data.setValue(i-1, j-1, Double.parseDouble(qdata[i][j]));
				}
			}
        }
        return data;

	}
	
	

}
