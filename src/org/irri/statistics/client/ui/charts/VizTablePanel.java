package org.irri.statistics.client.ui.charts;


import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;


public class VizTablePanel extends Composite {
	private VerticalPanel TablePanel = new VerticalPanel();
	private Table viztab;
	/**
	 * @wbp.parser.constructor
	 */
	public VizTablePanel(){
		Runnable onLoadCallBack = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				viztab = new Table();
				TablePanel.add(viztab);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallBack, Table.PACKAGE);
		initWidget(TablePanel);
	}
	
    public VizTablePanel(String query, String title, int[] numcols){
    	final int[] ncols = numcols;
    	final AsyncCallback<String[][]> DBDataTable = new AsyncCallback<String[][]>() {

    		public void onFailure(Throwable caught) {
    			throw new UnsupportedOperationException("Not supported yet.");
    		}

    		public void onSuccess(String[][] result) {
    			final String[][] out = result;
    			Runnable onLoadCallback = new Runnable() {
    				
    				public void run() {    					
    					viztab = new Table(ChartDataTable.create(out, ncols), createOptions());    					
    					TablePanel.add(viztab);
    				}
    			};
    			VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    		}
    	};

    	RPCUtils.getService("mysqlservice").RunSELECT(query, DBDataTable);
    	initWidget(TablePanel);
    }

    public VizTablePanel(String[][] data, int[] numcols){
    	final int[] ncols = numcols;
		final String[][] out = data;
		Runnable onLoadCallback = new Runnable() {
			
			public void run() {    					
				viztab = new Table(ChartDataTable.create(out, ncols), createOptions());    					
				TablePanel.add(viztab);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    	initWidget(TablePanel);
    }

    public Options createOptions() {
    	Options options = Options.create();
    	options.setHeight("80%");
    	options.setWidth("80%");
    	return options;
    }
    
    public void show(AbstractDataTable datatable, Options options){
    	if (this.viztab != null) {
    		viztab.draw(datatable, options);
    	}
    }

//    private AbstractDataTable createTable(String[][] qdata){
//    	DataTable data = DataTable.create();    	
//    	for (int i=0;i<qdata.length;i++){
//    		if (i==1) data.addRows(qdata.length-1);
//    		for (int j=0;j<qdata[i].length;j++){
//    			if (i==0) {
//    				if (j==0) data.addColumn(AbstractDataTable.ColumnType.STRING, qdata[0][j]);
//    				else data.addColumn(AbstractDataTable.ColumnType.NUMBER, qdata[0][j]);
//    			} else {
//    				if (j==0) data.setValue(i-1, j, qdata[i][j]);
//    				else data.setValue(i-1, j, Double.parseDouble(qdata[i][j]));
//    			}
//    		}
//    	}
//    	return data;
//    }
}
