package org.irri.statistics.client.ui;

import java.util.Comparator;

import org.irri.statistics.client.UtilsRPC;
import org.irri.statistics.client.WRS_DataClasses.CountryStat;
import org.irri.statistics.client.ui.charts.DBLineChart;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.DeckPanel;


public class WRSResultTable extends Composite {
	private ListDataProvider<CountryStat> queryresult = new ListDataProvider<CountryStat>();
	CellTable<CountryStat> ctResult = new CellTable<CountryStat>();
	ListHandler<CountryStat> ResultHandler;
	private String[][] resultmatrix; 
	private VerticalPanel vpCharts;
	
	public WRSResultTable() {
		// Main Wrapper
		 
		SplitLayoutPanel verticalPanel = new SplitLayoutPanel();
		initWidget(verticalPanel);
		
		// West Panel for chart widgets
		vpCharts = new VerticalPanel();
		vpCharts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.addWest(vpCharts, 350.0);
		vpCharts.setSize("100%", "100%");
		
		CaptionPanel cptnpnlGraphs = new CaptionPanel("Visualize");
		vpCharts.add(cptnpnlGraphs);
		cptnpnlGraphs.setSize("325px", "325px");
		
		ResultHandler = new ListHandler<CountryStat>(queryresult.getList());
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
				
				HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
				verticalPanel.addSouth(horizontalPanel_1, 200.0);
				
				CaptionPanel cptnpnlSource = new CaptionPanel("Source");
				horizontalPanel_1.add(cptnpnlSource);
				
				FlexTable flexTable = new FlexTable();
				cptnpnlSource.setContentWidget(flexTable);
				flexTable.setSize("5cm", "3cm");
				
				HTMLPanel htmlDisclaimer = new HTMLPanel("<h3>Disclaimer </h3>\r\nData and information released from the International Rice Research Institute (IRRI) are provided on an \"AS IS\" basis, without warranty of any kind, including without limitation the warranties of merchantability, fitness for a particular purpose and non-infringement. Availability of this data and information does not constitute scientific publication. Data and/or information may contain errors or be incomplete.");
				htmlDisclaimer.setStyleName("notes");
				horizontalPanel_1.add(htmlDisclaimer);
				htmlDisclaimer.setSize("95%", "100%");
				
				// Center panelfor cell table
				VerticalPanel verticalPanel_1 = new VerticalPanel();
				verticalPanel.add(verticalPanel_1);
				verticalPanel_1.setBorderWidth(0);
				verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				ctResult.addColumnSortHandler(ResultHandler);
				
						ScrollPanel scrollPanel = new ScrollPanel();
						verticalPanel_1.add(scrollPanel);
						scrollPanel.setWidget(ctResult);
						ctResult.setSize("100%", "100%");
						queryresult.addDataDisplay(ctResult);
						
						// Controls for viewing cell table
						HorizontalPanel horizontalPanel = new HorizontalPanel();
						horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
						verticalPanel_1.add(horizontalPanel);
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

			    DBLineChart linechart = new DBLineChart(resultmatrix, "Top Producers", 250, 250);
				vpCharts.add(linechart);

			}
		};
		UtilsRPC.getService("mysqlservice").RunSELECT(query, callback);
	}
	public VerticalPanel getVpCharts() {
		return vpCharts;
	}
}
