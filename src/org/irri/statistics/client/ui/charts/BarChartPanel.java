package org.irri.statistics.client.ui.charts;

import org.irri.statistics.client.utils.NumberUtils;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ImageBarChart;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class BarChartPanel extends Composite {
	private VerticalPanel VisBox = new VerticalPanel();
	public String[][] mydata;
	public boolean interactive = true; 
	public BarChartPanel(String query, String title, int w, int h) {
		
		final String ChartTitle = title;		
    	final int width = w;
    	final int height = h;
    	final AsyncCallback<String[][]> DBDataTable = new AsyncCallback<String[][]>() {
        	
        	public void onSuccess(String[][] result) {
                final String[][] out = result;
                final int[] numcols = NumberUtils.createIntSeries(1, out[0].length-1, 1);
                Runnable onLoadCallback = new Runnable() {

                    public void run() {
                    	mydata = out;
                    	plotImage(ChartDataTable.create(out, numcols), ChartTitle,width,height);                        
                    }
                };
                VisualizationUtils.loadVisualizationApi(onLoadCallback, BarChart.PACKAGE);
            }

            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }  
        };
        RPCUtils.getService("mysqlservice").RunSELECT(query, DBDataTable);
        initWidget(VisBox);
        
		
	}
	
	public BarChartPanel(AbstractDataTable data, String title, int w, int h, boolean isinteractive){
		interactive =  isinteractive;
		if (interactive){
			plotImage(data, title, w, h);
		} else {
			plotInteractive(data, title, w, h);
		}
		initWidget(VisBox);
	}
	
	public void plotInteractive(AbstractDataTable datatable, String title, int w, int h){
		final AbstractDataTable data = datatable;
		final Options opts = createOptions(title,w,h);
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BarChart bar = new BarChart(data, opts);
                VisBox.add(bar);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, BarChart.PACKAGE);
		
	}
	
	public void plotImage(AbstractDataTable datatable, String title, int w, int h){
		final AbstractDataTable data = datatable;
		final String Title = title;
		final int width = w;
		final int height = h;
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ImageBarChart.Options opts = ImageBarChart.Options.create();
				opts.setTitle(Title);
				opts.setWidth(width);
				opts.setHeight(height);
				opts.set("legend", "bottom");
				ImageBarChart bar = new ImageBarChart(data, opts);
                VisBox.add(bar);
				
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, ImageBarChart.PACKAGE);
	}
	
	public Options createOptions(String title, int w, int h) {
        Options options = Options.create();
        AxisOptions hAxisOptions = AxisOptions.create();
    	AxisOptions vAxisOptions = AxisOptions.create();
    	options.setHAxisOptions(hAxisOptions);
    	options.setVAxisOptions(vAxisOptions);
        options.setWidth(w);
        options.setHeight(h);
        options.setTitle(title);
        options.set("legend", "bottom");
        return options;
    }

}
