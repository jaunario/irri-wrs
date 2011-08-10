package org.irri.statistics.client.ui;

import org.irri.statistics.client.UtilsRPC;
import org.irri.statistics.client.WRS_DataClasses.CountryStat;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

public class WRSResultTable extends Composite {
	private ListDataProvider<CountryStat> queryresult = new ListDataProvider<CountryStat>();
	CellTable<CountryStat> ctResult = new CellTable<CountryStat>();
	
	public WRSResultTable(String selectquery) {
		
		DockLayoutPanel verticalPanel = new DockLayoutPanel(Unit.PX);
		initWidget(verticalPanel);
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel.addWest(verticalPanel_2, 300.0);
		
		Image image = new Image("images/generichart.gif");
		verticalPanel_2.add(image);
		image.setSize("132px", "120px");
		
		ScrollPanel scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel_1);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_1.setSize("100%", "100%");
		
		verticalPanel_1.add(ctResult);
		ctResult.setSize("100%", "100%");
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setSize("300px", "30px");
		pager.setDisplay(ctResult);
		verticalPanel_1.add(pager);
		sqlPopulateTable(selectquery);
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
				for (int i = 1; i < result.length; i++) {
					float[] vals = new float[result[i].length-2];					
					for (int j = 0; j < vals.length; j++) {
						vals[j] = Float.parseFloat(result[i][j+2]);
					}
					queryresult.getList().add(new CountryStat(result[i][0], Integer.parseInt(result[i][1]), vals));
				}
				
				for (int i = 0; i < result[0].length; i++) {
					if (i==0){
						Column<CountryStat, String> thisColumn = new Column<CountryStat, String>(new TextCell()) {
							@Override
							public String getValue(CountryStat cstat) {
								return cstat.getCountry();
							}
						};
						ctResult.addColumn(thisColumn,result[0][i]);
					} else if (i==1){
						Column<CountryStat, Number> thisColumn = new Column<CountryStat, Number>(new NumberCell(NumberFormat.getFormat("####"))) {
							@Override
							public Number getValue(CountryStat cstat) {
								return (Number) cstat.getYear();
							}
						};
						ctResult.addColumn(thisColumn,result[0][i]);
					} else {
						final int idx = i-2;
						Column<CountryStat, Number> thisColumn = new Column<CountryStat, Number>(new NumberCell()) {
							@Override
							public Number getValue(CountryStat cstat) {
								return (Number) cstat.getVarValue(idx);
							}
						};
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
