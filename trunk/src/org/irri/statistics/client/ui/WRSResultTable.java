package org.irri.statistics.client.ui;

import java.util.Comparator;

import org.irri.statistics.client.UtilsRPC;
import org.irri.statistics.client.WRS_DataClasses.CountryStat;
import org.irri.statistics.client.ui.charts.BarChartPanel;

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
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;

public class WRSResultTable extends Composite {
	private ListDataProvider<CountryStat> queryresult = new ListDataProvider<CountryStat>();
	CellTable<CountryStat> ctResult = new CellTable<CountryStat>();
	ListHandler<CountryStat> ResultHandler;
	private String[][] resultmatrix; 
	
	public WRSResultTable(String selectquery) {
		// Main Wrapper
		final String query = selectquery; 
		DockLayoutPanel verticalPanel = new DockLayoutPanel(Unit.PX);
		initWidget(verticalPanel);
		
		// West Panel for chart widgets
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.addWest(verticalPanel_2, 250.0);
		
		CaptionPanel cptnpnlSource = new CaptionPanel("Source");
		verticalPanel_2.add(cptnpnlSource);
		
		FlexTable flexTable = new FlexTable();
		cptnpnlSource.setContentWidget(flexTable);
		flexTable.setSize("5cm", "3cm");
		
		Image image = new Image("images/generichart.gif");
		verticalPanel_2.add(image);
		image.setSize("132px", "120px");
		
		// Center panelfor cell table
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel.add(verticalPanel_1);
		verticalPanel_1.setBorderWidth(0);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		ResultHandler = new ListHandler<CountryStat>(queryresult.getList());
		ctResult.addColumnSortHandler(ResultHandler);

		ScrollPanel scrollPanel = new ScrollPanel();
		verticalPanel_1.add(scrollPanel);
		scrollPanel.setSize("650px", "400px");
		scrollPanel.setWidget(ctResult);
		ctResult.setSize("100%", "100%");
		queryresult.addDataDisplay(ctResult);
		
		// Controls for viewing cell table
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.add(horizontalPanel);
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(ctResult);
		horizontalPanel.add(pager);
		Button btnDownload = new Button("Download");
		btnDownload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Download result
			}
		});
		btnDownload.setText("Download");
		horizontalPanel.add(btnDownload);
		
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				sqlPopulateTable(query);
			}
			
			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}
		});
		
		BarChartPanel topprod = new BarChartPanel(query, "Top Producers", 250, 250);
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
				// TODO Auto-generated method stub
				resultmatrix = result;
				queryresult.getList().clear();
				for (int i = 1; i < result.length; i++) {
					float[] vals = new float[result[i].length-2];					
					for (int j = 0; j < vals.length; j++) {
						if (result[i][j+2]==null){
							vals[j] = -9999;
						} else vals[j] = Float.parseFloat(result[i][j+2]);
					}
					queryresult.getList().add(new CountryStat(result[i][0], Integer.parseInt(result[i][1]), vals));
				}
				clearResultTable();
				for (int i = 0; i < result[0].length; i++) {
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
								// TODO Auto-generated method stub
								object.setCountry(value);
								queryresult.refresh();
							}
						});
						ResultHandler.setComparator(thisColumn, new Comparator<CountryStat>() {
							
							@Override
							public int compare(CountryStat arg0, CountryStat arg1) {
								// TODO Auto-generated method stub
								return arg0.getCountry().compareTo(arg1.getCountry());
							}
						});
						ctResult.addColumn(thisColumn,result[0][i]);
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
								// TODO Auto-generated method stub
								return arg0.getYear()-arg1.getYear();
							}
						});
						thisColumn.setFieldUpdater(new FieldUpdater<CountryStat, Number>() {

							@Override
							public void update(int index, CountryStat object,
									Number value) {
								// TODO Auto-generated method stub
								object.setYear(value.intValue());
								queryresult.refresh();
							}
						});
						ctResult.addColumn(thisColumn,result[0][i]);
					} else {
						final int idx = i-2;
						Column<CountryStat, Number> thisColumn = new Column<CountryStat, Number>(new NumberCell()) {
							@Override
							public Number getValue(CountryStat cstat) {
								return (Number) cstat.getVarValue(idx);
							}
						};
						thisColumn.setSortable(true);
						thisColumn.setFieldUpdater(new FieldUpdater<CountryStat, Number>() {

							@Override
							public void update(int index, CountryStat object,
									Number value) {
								// TODO Auto-generated method stub
								object.setVarValue(idx, value.floatValue());
								queryresult.refresh();
							}
						});
						ctResult.addColumn(thisColumn,result[0][i]);
					}
				}				
			    ctResult.setRowCount(queryresult.getList().size(), true);
			    // Push the data into the widget.
			    ctResult.setRowData(0, queryresult.getList());

			}
		};
		UtilsRPC.getService("mysqlservice").RunSELECT(query, callback);
	}
}
