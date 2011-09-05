package org.irri.statistics.client.ui.charts;

import org.irri.statistics.client.utils.NumberUtils;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.GeoMap.DataMode;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class MultiChartPanel extends Composite {
	AbstractDataTable basedata;
	public int charttype1f = 0;
	public int charttype2f = 0;
	public int[] numerics;
	public int plotcol = 0;
	private DeckPanel deckChartPager;
	private VerticalPanel vpTablePage;
	private VerticalPanel vp1FactorChart;
	private VerticalPanel vp2FactorChart;
	/**
	 * @wbp.parser.constructor
	 */
	public MultiChartPanel(){
		initPanels();
	}
	public MultiChartPanel(String[][] data) {
		numerics = NumberUtils.createIntSeries(2, data[0].length-2, 1);
		basedata = ChartDataTable.create(data, numerics);
		initPanels();
		
	}
	
	private void initPanels(){
		DockPanel ChartsWrapper = new DockPanel();
		initWidget(ChartsWrapper);
		//ChartsWrapper.setSize("100%", "100%");

		
		DecoratedTabBar decoratedTabBar = new DecoratedTabBar();
		decoratedTabBar.addTab("Table");
		decoratedTabBar.addTab("1F Chart ");
		decoratedTabBar.addTab("2F Chart");
		decoratedTabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				deckChartPager.showWidget(event.getSelectedItem());				
			}
		});
		
		MenuBar mbTableOptions = new MenuBar(false);
		ChartsWrapper.add(mbTableOptions, DockPanel.NORTH);
		mbTableOptions.setAutoOpen(true);
		mbTableOptions.setAnimationEnabled(true);
		MenuBar menuBar = new MenuBar(true);
		
		MenuItem mntmTableOptions = new MenuItem("Table Options", false, menuBar);
		mbTableOptions.addItem(mntmTableOptions);
		MenuBar menuBar_1 = new MenuBar(true);
		
		MenuItem mntmChartOptions = new MenuItem("Chart Options", false, menuBar_1);
		MenuBar menuBar_2 = new MenuBar(true);
		
		MenuItem mntmfactorChart = new MenuItem("1-Factor Chart", false, menuBar_2);
		
		MenuItem mntmPie = new MenuItem("Pie", false, (Command) null);
		menuBar_2.addItem(mntmPie);
		
		MenuItem mntmMap = new MenuItem("Map", false, (Command) null);
		menuBar_2.addItem(mntmMap);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar_2.addSeparator(separator);
		MenuBar menuBar_4 = new MenuBar(true);
		
		MenuItem mntmYear = new MenuItem("Year", false, menuBar_4);
		menuBar_2.addItem(mntmYear);
		MenuBar menuBar_5 = new MenuBar(true);
		
		MenuItem mntmRegion = new MenuItem("Region", false, menuBar_5);
		menuBar_2.addItem(mntmRegion);
		menuBar_1.addItem(mntmfactorChart);
		MenuBar menuBar_3 = new MenuBar(true);
		
		MenuItem mntmfactorChart_1 = new MenuItem("2-Factor Chart", false, menuBar_3);
		MenuBar menuBar_8 = new MenuBar(true);
		
		MenuItem mntmChartType = new MenuItem("Chart Type", false, menuBar_8);
		
		MenuItem mntmLine = new MenuItem("Line", false, (Command) null);
		menuBar_8.addItem(mntmLine);
		
		MenuItem mntmBar = new MenuItem("Bar", false, (Command) null);
		menuBar_8.addItem(mntmBar);
		
		MenuItem mntmScatter = new MenuItem("Scatter", false, (Command) null);
		menuBar_8.addItem(mntmScatter);
		menuBar_3.addItem(mntmChartType);
		
		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar_3.addSeparator(separator_1);
		MenuBar menuBar_6 = new MenuBar(true);
		
		MenuItem mntmSetX = new MenuItem("Set X", false, menuBar_6);
		menuBar_3.addItem(mntmSetX);
		MenuBar menuBar_7 = new MenuBar(true);
		
		MenuItem mntmSetY = new MenuItem("Set Y", false, menuBar_7);
		menuBar_3.addItem(mntmSetY);
		menuBar_1.addItem(mntmfactorChart_1);
		mbTableOptions.addItem(mntmChartOptions);
		
		MenuItem mntmRedraw = new MenuItem("Redraw", false, new Command() {
			public void execute() {
				drawTable();
				switch (charttype1f) {
				case 0:
					
					break;

				default:
					break;
				}
				switch (charttype2f) {
				case 0:
					drawLineChart(1, 2, 0);
					break;

				default:
					break;
				}
			}
		});
		mbTableOptions.addItem(mntmRedraw);
		
		MenuItem mntmDownload = new MenuItem("Download", false, new Command() {
			public void execute() {
				int tab = deckChartPager.getVisibleWidget();
				switch (tab) {
				case 0:
					AsyncCallback<String> downloadAsyncCallback = new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							Frame myframe = new Frame(result);
							deckChartPager.add(myframe);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					};
					RPCUtils.getService("mysqlservice").SaveCSV(ChartDataTable.csvData(basedata), downloadAsyncCallback);
	                                	
					break;

				default:
					break;
				}
			}
		});
		mbTableOptions.addItem(mntmDownload);
		ChartsWrapper.add(decoratedTabBar, DockPanel.SOUTH);
		decoratedTabBar.setWidth("100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		ChartsWrapper.add(verticalPanel, DockPanel.EAST);
		ChartsWrapper.setCellHeight(verticalPanel, "100%");
		ChartsWrapper.setCellWidth(verticalPanel, "35%");
		verticalPanel.setSize("100%", "100%");
		
		deckChartPager = new DeckPanel();
		ChartsWrapper.add(deckChartPager, DockPanel.CENTER);
		ChartsWrapper.setCellHeight(deckChartPager, "100%");
		ChartsWrapper.setCellWidth(deckChartPager, "65%");
		deckChartPager.setSize("100%", "100%");
		deckChartPager.setAnimationEnabled(true);
		
		vpTablePage = new VerticalPanel();
		deckChartPager.add(vpTablePage);
		vpTablePage.setSize("100%", "100%");
		
		vp1FactorChart = new VerticalPanel();
		deckChartPager.add(vp1FactorChart);
		vp1FactorChart.setSize("100%", "100%");
		
		vp2FactorChart = new VerticalPanel();
		deckChartPager.add(vp2FactorChart);
		vp2FactorChart.setSize("100%", "100%");
		decoratedTabBar.selectTab(0);
		
	}
	
	public DeckPanel getDeckPanel() {
		return deckChartPager;
	}
	
	public void setBaseData(String[][] data){
		numerics = NumberUtils.createIntSeries(2, data[0].length-2, 1);
		basedata = ChartDataTable.create(data, numerics);
		if(vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
		if(vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
		drawTable();
		drawLineChart(1,2,0);
		//drawPieChart(0, 2, 1, basedata.getValueString(0, 1));
		drawMap(0, 2, 1, basedata.getValueString(0, 1));
	}
	
	public void drawTable(){
		if(vpTablePage.getWidgetCount()>0) vpTablePage.clear();
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				Widget activewidget = deckChartPager.getWidget(deckChartPager.getVisibleWidget());
				Table table = new Table(basedata, setTableSize(activewidget.getOffsetWidth(), activewidget.getOffsetWidth()));
				vpTablePage.add(table);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
	};
	
	public void drawLineChart(int x, int y, int series){
		if (vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
		final AbstractDataTable lcDataTable = ChartDataTable.dataIntoSeries(basedata, x, y, series, true);
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				Widget activewidget = deckChartPager.getWidget(deckChartPager.getVisibleWidget());
				LineChart line = new LineChart(lcDataTable, createCoreChartOptions(activewidget.getOffsetWidth(), activewidget.getOffsetWidth()));
				vp2FactorChart.add(line);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
	}
	
	public void drawPieChart(int x, int y, int filtercol, String filterval){
		if (vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
		final AbstractDataTable pcDataTable = ChartDataTable.filteredTable(basedata, x, y, filtercol, filterval);
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Widget activewidget = deckChartPager.getWidget(deckChartPager.getVisibleWidget());
				PieChart pie = new PieChart(pcDataTable, createCoreChartOptions(activewidget.getOffsetWidth(), activewidget.getOffsetWidth()));
				vp1FactorChart.add(pie);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
	}
	
	public void drawMap(int loc, int val, int yr, String selectyr){
		if (vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
		final AbstractDataTable gmDataTable = ChartDataTable.filteredTable(basedata, loc, val, yr, selectyr);
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Widget activewidget = deckChartPager.getWidget(deckChartPager.getVisibleWidget());
				GeoMap map = new GeoMap(gmDataTable, createMapOptions(activewidget.getOffsetWidth(), activewidget.getOffsetWidth()));
				vp1FactorChart.add(map);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
	}
	
	public Options createCoreChartOptions(int w, int h){
		Options options = Options.create();
		options.setWidth(w);
		options.setHeight(h);
		return options;
		
	}
	
	public Table.Options setTableSize(int w, int h) {
    	Table.Options options = Table.Options.create();
    	options.setHeight((h-10)+"px");
    	options.setWidth((w-10)+"px");
    	options.setShowRowNumber(true);
    	return options;
    }
    
	public GeoMap.Options createMapOptions(int w, int h){
		GeoMap.Options options = GeoMap.Options.create();
		options.setWidth(w);
        options.setHeight(h);
        options.setShowLegend(true);
        options.setShowZoomOut(true);
        options.setDataMode(DataMode.REGIONS);
        options.setColors(0xDDDDDD,0xCE0000,0xFF9E00,0xF7D708,0x9CCF31);        
        return options;
	}
	public VerticalPanel getVpTablePage() {
		return vpTablePage;
	}
	public VerticalPanel getVp1FactorChart() {
		return vp1FactorChart;
	}
	public VerticalPanel getVp2FactorChart() {
		return vp2FactorChart;
	}
}
