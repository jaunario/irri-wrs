package org.irri.statistics.client.ui.charts;

import org.irri.statistics.client.utils.NumberUtils;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.GeoMap.DataMode;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class MultiChartPanel extends Composite {
	AbstractDataTable basedata;
	
	public int[] numerics;

	private DeckPanel deckChartPager;
	private VerticalPanel vpTablePage;
	private DecoratedTabBar dtbChartPageSelector;
	/**
	 * @wbp.parser.constructor
	 */
	public MultiChartPanel(){
		initPanels();
	}
	public MultiChartPanel(String[][] data) {
		numerics = NumberUtils.createIntSeries(2, data[0].length-1, 1);
		basedata = ChartDataTable.create(data, numerics);
		initPanels();
		
	}
	
	private void initPanels(){
		DockPanel ChartsWrapper = new DockPanel();
		initWidget(ChartsWrapper);
		//ChartsWrapper.setSize("100%", "100%");

		
		dtbChartPageSelector = new DecoratedTabBar();
		dtbChartPageSelector.addTab("Table");
		dtbChartPageSelector.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				deckChartPager.showWidget(event.getSelectedItem());				
			}
		});
		

		MenuBar mbTableOptions = new MenuBar(false);
		ChartsWrapper.add(mbTableOptions, DockPanel.NORTH);
		mbTableOptions.setAutoOpen(true);
		mbTableOptions.setAnimationEnabled(true);
		
		MenuItem mntmRedraw = new MenuItem("Refit to Panel", false, new Command() {
			public void execute() {
				drawTable();
			}
		});
		mntmRedraw.setTitle("Click here refit charts/table into the panel when you resize the browser");
		mbTableOptions.addItem(mntmRedraw);
		
		MenuItem mntmDownload = new MenuItem("Download", false, new Command() {
			public void execute() {
				int tab = deckChartPager.getVisibleWidget();
				switch (tab) {
				case 0:
					AsyncCallback<String> downloadAsyncCallback = new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String result) {
							Frame myframe = new Frame(result);
							deckChartPager.add(myframe);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							PopupPanel msg = new PopupPanel();
							msg.add(new HTML("Operation failed. Please try Again. If the problem persists please contact us. (Indicate the variables chosen)"));
							msg.setGlassEnabled(true);
							msg.center();
							msg.show();
						}
					};
					RPCUtils.getService("mysqlservice").SaveCSV(ChartDataTable.csvData(basedata), downloadAsyncCallback);
	                                	
					break;

				default:
					break;
				}
			}
		});
		mntmDownload.setHTML("Download Data");
		mbTableOptions.addItem(mntmDownload);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		mbTableOptions.addSeparator(separator);
		MenuBar mbChartClass = new MenuBar(true);
		
		MenuItem mntmCharts = new MenuItem("Plot", false, mbChartClass);
		mntmCharts.setHTML("Plot");
		
		MenuItem mntmNewChart = new MenuItem("New Chart", false, new Command() {
			public void execute() {
				ChartOptions newco = new ChartOptions(basedata, deckChartPager.getOffsetWidth(),deckChartPager.getOffsetHeight(), deckChartPager.getWidgetCount());
				deckChartPager.add(newco.getChart());
// TODO: Close chart
//				HorizontalPanel charttab = new HorizontalPanel();
//				charttab.setSpacing(2);				
//				Label tablabel = new Label(("Chart" + (deckChartPager.getWidgetCount()-1)));
//				charttab.add(tablabel);
//				charttab.setCellVerticalAlignment(tablabel, HasVerticalAlignment.ALIGN_MIDDLE);
//				Image closeimg = new Image("images/tab_close.png");
//				closeimg.setSize("10px", "10px");
//				PushButton closetab = new PushButton(closeimg);
//				closetab.setSize("11px", "11px");
//				charttab.add(closetab);
//				closetab.addClickHandler(new ClickHandler() {
//					
//					@Override
//					public void onClick(ClickEvent event) {
//						// TODO Auto-generated method stub
//						int thistab = deckChartPager.getWidgetCount()-1;
//						deckChartPager.remove(thistab);
//						dtbChartPageSelector.removeTab(thistab);
//					}
//				});
//				
				dtbChartPageSelector.addTab("Chart" + dtbChartPageSelector.getTabCount());
				
			}
		});
		mbChartClass.addItem(mntmNewChart);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		mbChartClass.addSeparator(separator_2);
		
		MenuItem mntmRemove = new MenuItem("Remove", false, (Command) null);
		mntmRemove.setHTML("Remove All Charts");
		mbChartClass.addItem(mntmRemove);
		mbTableOptions.addItem(mntmCharts);
		ChartsWrapper.add(dtbChartPageSelector, DockPanel.SOUTH);
		dtbChartPageSelector.setWidth("100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		ChartsWrapper.add(verticalPanel, DockPanel.EAST);
		ChartsWrapper.setCellHeight(verticalPanel, "100%");
		ChartsWrapper.setCellWidth(verticalPanel, "25%");
		verticalPanel.setWidth("100%");
		
		deckChartPager = new DeckPanel();
		ChartsWrapper.add(deckChartPager, DockPanel.CENTER);
		ChartsWrapper.setCellHeight(deckChartPager, "100%");
		ChartsWrapper.setCellWidth(deckChartPager, "65%");
		deckChartPager.setSize("100%", "100%");
		deckChartPager.setAnimationEnabled(true);
		
		vpTablePage = new VerticalPanel();
		deckChartPager.add(vpTablePage);
		vpTablePage.setSize("100%", "100%");
		dtbChartPageSelector.selectTab(0);		
	}
	
	public DeckPanel getDeckPanel() {
		return deckChartPager;
	}
	
	public void setBaseData(String[][] data){
		final String[][] datatable = data;
		numerics = NumberUtils.createIntSeries(2, data[0].length-1, 1);
		Runnable onloadCallback = new Runnable() {
			
			@Override
			public void run() {
				basedata = ChartDataTable.create(datatable, numerics);
				drawTable();
			}
		};
		VisualizationUtils.loadVisualizationApi(onloadCallback, CoreChart.PACKAGE);
//		if(vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
//		if(vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
	}
	
	public void drawTable(){
		if(vpTablePage.getWidgetCount()>0) vpTablePage.clear();
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				Table table = new Table(basedata, setTableSize(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
				vpTablePage.add(table);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
	};
	
//	public void drawLineChart(){
////		if (vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
//		Runnable onLoadCallback1 = new Runnable() {
//			
//			@Override
//			public void run() {
//				LineChart line = new LineChart(chart2fdata, createCoreChartOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
//				vp2FactorChart.add(line);
//			}
//		};
//		
//		Runnable onLoadCallback2 = new Runnable() {
//			
//			@Override
//			public void run() {
//				ImageLineChart.Options imgline = ImageLineChart.Options.create();
//				int w = deckChartPager.getOffsetWidth();
//				int h = deckChartPager.getOffsetHeight();
//				imgline.setWidth(w);
//				imgline.setHeight(h);
//				imgline.setLegend(LegendPosition.BOTTOM);
//				
//				ImageLineChart line = new ImageLineChart(chart2fdata, imgline);
//				vp2FactorChart.add(line);
//			}
//		};
//
//		if (interactive){	VisualizationUtils.loadVisualizationApi(onLoadCallback1, LineChart.PACKAGE);
//		} else {
//			VisualizationUtils.loadVisualizationApi(onLoadCallback2, ImageLineChart.PACKAGE);
//		}
//	}
//	
//	public void drawColumnChart(){
//		if (vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
//		Runnable onLoadCallback = new Runnable() {
//			
//			@Override
//			public void run() {
//				ColumnChart line = new ColumnChart(chart2fdata, createCoreChartOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
//				vp2FactorChart.add(line);
//			}
//		};
//		VisualizationUtils.loadVisualizationApi(onLoadCallback, ColumnChart.PACKAGE);
//	}
//	public void drawPieChart(){
//		if (vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
//		Runnable onLoadCallback = new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				PieChart pie = new PieChart(chart1fdata, createCoreChartOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
//				vp1FactorChart.add(pie);
//			}
//		};
//		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
//	}
//	
//	public void drawMap(){
//		if (vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
//		Runnable onLoadCallback = new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				GeoMap map = new GeoMap(chart1fdata, createMapOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
//				vp1FactorChart.add(map);
//			}
//		};
//		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
//	}
	
	public Table.Options setTableSize(int w, int h) {
    	Table.Options options = Table.Options.create();
    	options.setHeight((h-5)+"px");
    	options.setWidth((w-5)+"px");
    	options.setShowRowNumber(true);
    	return options;
    }
    
	public GeoMap.Options createMapOptions(int w, int h){
		GeoMap.Options options = GeoMap.Options.create();
		options.setWidth(w-5);
        options.setHeight(h-5);
        options.setShowLegend(true);
        options.setShowZoomOut(true);
        options.setShowZoomOut(true);
        options.setDataMode(DataMode.REGIONS);
        options.setColors(0xDDDDDD,0xCE0000,0xFF9E00,0xF7D708,0x9CCF31);        
        return options;
	}

	public DecoratedTabBar getDecoratedTabBar() {
		return dtbChartPageSelector;
	}
}
