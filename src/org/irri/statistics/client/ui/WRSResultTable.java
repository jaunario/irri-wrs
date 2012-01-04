package org.irri.statistics.client.ui;

import java.util.Comparator;

import org.irri.statistics.client.WRS_DataClasses.CountryStat;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;


public class WRSResultTable extends Composite {
	private ListDataProvider<CountryStat> queryresult = new ListDataProvider<CountryStat>();
	CellTable<CountryStat> ctResult = new CellTable<CountryStat>();
	ListHandler<CountryStat> ResultHandler;
	
	public WRSResultTable() {
		// Main Wrapper
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(verticalPanel);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		ctResult.setPageSize(20);
		scrollPanel.setWidget(ctResult);
		verticalPanel.add(scrollPanel);
		
		ResultHandler = new ListHandler<CountryStat>(queryresult.getList());
		ctResult.addColumnSortHandler(ResultHandler);
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
				
		queryresult.addDataDisplay(ctResult);
		
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(ctResult);
		verticalPanel.add(pager);
		
	}
	
	public void clearResultTable(){
		for (int i = ctResult.getColumnCount()-1; i >= 0 ; i--) {
			ctResult.removeColumn(i);
		}
	}
	
	public void sqlPopulateTable(String query){
		final AsyncCallback<String[][]> callback = new AsyncCallback<String[][]>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(String[][] result) {
				populateResultTable(result);
			}
		};
		RPCUtils.getService("mysqlservice").RunSELECT(query, callback);
	}
	
	public void populateResultTable(String[][] data){
		queryresult.getList().clear();
		clearResultTable();
		
		for (int i = 1; i < data.length; i++) {
			float[] vals = new float[data[i].length-2];					
			for (int j = 0; j < vals.length; j++) {
				if (data[i][j+2]!=null){
					vals[j] = Float.parseFloat(data[i][j+2]);
				}
			}
			queryresult.getList().add(new CountryStat(data[i][0], Integer.parseInt(data[i][1]), vals));
		}
		
		for (int i = 0; i < data[0].length; i++) {
			if (i==0){
				Column<CountryStat, String> thisColumn = new Column<CountryStat, String>(new TextCell()) {
					@Override
					public String getValue(CountryStat cstat) {
						return cstat.getCountry();
					}
				};
				thisColumn.setSortable(true);
				thisColumn.setFieldUpdater(new FieldUpdater<CountryStat, String>() {
					
					@Override
					public void update(int index, CountryStat object, String value) {
						object.setCountry(value);
						queryresult.refresh();
					}
				});
				ResultHandler.setComparator(thisColumn, new Comparator<CountryStat>() {
					
					@Override
					public int compare(CountryStat arg0, CountryStat arg1) {
						return arg0.getCountry().compareTo(arg1.getCountry());
					}
				});
				ctResult.addColumn(thisColumn,data[0][i]);
			} else if (i==1){
				Column<CountryStat, Number> thisColumn = new Column<CountryStat, Number>(new NumberCell(NumberFormat.getFormat("####"))) {
					@Override
					public Number getValue(CountryStat cstat) {
						return (Number) cstat.getYear();
					}
				};
				thisColumn.setSortable(true);
				ResultHandler.setComparator(thisColumn, new Comparator<CountryStat>() {
					
					@Override
					public int compare(CountryStat arg0, CountryStat arg1) {
						return arg0.getYear()-arg1.getYear();
					}
				});
				thisColumn.setFieldUpdater(new FieldUpdater<CountryStat, Number>() {

					@Override
					public void update(int index, CountryStat object, Number value) {
						object.setYear(value.intValue());
						queryresult.refresh();
					}
				});
				ctResult.addColumn(thisColumn,data[0][i]);
			} else {
				final int idx = i-2;
				Column<CountryStat, Number> thisColumn = new Column<CountryStat, Number>(new NumberCell(NumberFormat.getFormat("#,###.##"))) {
					@Override
					public Number getValue(CountryStat cstat) {
						return (Number) cstat.getVarValue(idx);
					}
				};
				thisColumn.setSortable(true);
				ResultHandler.setComparator(thisColumn, new Comparator<CountryStat>() {
					
					@Override
					public int compare(CountryStat arg0, CountryStat arg1) {
						return Float.compare(arg0.getVarValue(idx), arg1.getVarValue(idx));
					}
				});
				
				thisColumn.setFieldUpdater(new FieldUpdater<CountryStat, Number>() {

					@Override
					public void update(int index, CountryStat object, Number value) {
						object.setVarValue(idx, value.floatValue());
						queryresult.refresh();
					}
				});
				ctResult.addColumn(thisColumn,data[0][i]);
			}
		}				
	    ctResult.setRowCount(queryresult.getList().size(), true);
	    // Push the data into the widget.
	    ctResult.setRowData(0, queryresult.getList());
		
	}
}
