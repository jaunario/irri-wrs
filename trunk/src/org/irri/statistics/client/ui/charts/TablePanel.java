package org.irri.statistics.client.ui.charts;


import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
//import com.google.gwt.visualization.client.visualizations.Toolbar;
//import com.google.gwt.visualization.client.visualizations.Toolbar.Component;


public class TablePanel extends Composite {
	private DockLayoutPanel TablePanel = new DockLayoutPanel(Unit.PCT);
	private Table viztab;
//	private Toolbar TBTable;
	/**
	 * @wbp.parser.constructor
	 */
	public TablePanel(){
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
	
    public TablePanel(String query, String title, int[] numcols, String width, String height){
    	final int[] ncols = numcols;
    	final String h = height;
    	final String w = width;
    	final AsyncCallback<String[][]> DBDataTable = new AsyncCallback<String[][]>() {

    		public void onFailure(Throwable caught) {
    			throw new UnsupportedOperationException("Not supported yet.");
    		}

    		public void onSuccess(String[][] result) {
    			final String[][] out = result;
//    			drawToolBar();
    			Runnable onLoadCallback = new Runnable() {
    				
    				public void run() {
    					viztab = new Table(ChartDataTable.create(out, ncols), setTableSize(w, h));    					
    					TablePanel.add(viztab);
    				}
    			};
    			VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    		}
    	};

    	RPCUtils.getService("mysqlservice").RunSELECT(query, DBDataTable);
    	initWidget(TablePanel);
    }

    public TablePanel(String[][] data, int[] numcols, String width, String height){
    	final int[] ncols = numcols;
    	final String h = height;
    	final String w = width;
    	final String[][] out = data;
//    	drawToolBar();
		Runnable onLoadCallback = new Runnable() {
			
			public void run() {
				
				viztab = new Table(ChartDataTable.create(out, ncols), setTableSize(w, h));    					
				TablePanel.add(viztab);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    	initWidget(TablePanel);
    }

    public Options setTableSize(String w, String h) {
    	Options options = Options.create();
    	options.setHeight(h);
    	options.setWidth(w);
    	return options;
    }
    
    public void show(AbstractDataTable datatable, Options options){
    	if (this.viztab != null) {
    		viztab.draw(datatable, options);
    	}
    }
    
//    public void drawToolBar(){
//    	Runnable onLoadCallback = new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				TBTable = new Toolbar(); 
//				Component cmp = Component.create();
//				cmp.setType(Toolbar.Type.CSV);
//				TBTable.addComponent(cmp);
//				TablePanel.add(TBTable);
//			}
//		}; 
//		VisualizationUtils.loadVisualizationApi(onLoadCallback, Toolbar.PACKAGE);
//    }
//    
//    public AbstractDataTable getData(){
//    	
//    }

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
