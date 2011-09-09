package org.irri.statistics.client.ui.charts;

import java.util.ArrayList;
import java.util.Collections;

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
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.ImageLineChart;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.GeoMap.DataMode;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
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
	AbstractDataTable chart1fdata;
	AbstractDataTable chart2fdata;
	private int charttype1f = 0;
	private int charttype2f = 0;
	private int series = 0;
	private int x = 1;
	private int y = 2;
	
	private boolean interactive = true;
	
	public int[] numerics;

	private DeckPanel deckChartPager;
	private VerticalPanel vpTablePage;
	private VerticalPanel vp1FactorChart;
	private VerticalPanel vp2FactorChart;
	private MenuBar mbChart1Year;
	private MenuBar mbChart1Region;
	private MenuItem mntmInteractive;
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
		menuBar.addItem(mntmDownload);
		mbTableOptions.addItem(mntmTableOptions);
		MenuBar mbChartClass = new MenuBar(true);
		
		MenuItem mntmChartOptions = new MenuItem("Chart Options", false, mbChartClass);
		
		mntmInteractive = new MenuItem("Image Only", false, new Command() {
			public void execute() {
				if(interactive){
					interactive = false;
					mntmInteractive.setText("Interactive");
					mntmInteractive.setTitle("Plot interactive chart");
				} else {
					interactive = true;
					mntmInteractive.setText("Image Only");
					mntmInteractive.setTitle("Plot downloadable image");
				}
				draw2fChart();
			}
		});
		mntmInteractive.setTitle("Plot downloadable image");
		mbChartClass.addItem(mntmInteractive);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		mbChartClass.addSeparator(separator_2);
		MenuBar mbChart1Type = new MenuBar(true);
		
		MenuItem mntm1factorChart = new MenuItem("1-Factor Chart", false, mbChart1Type);
		
		MenuItem mntmPie = new MenuItem("Pie", false, new Command() {
			public void execute() {
				charttype1f = 0;
			}
		});
		mbChart1Type.addItem(mntmPie);
		
		MenuItem mntmMap = new MenuItem("Map", false, new Command() {
			public void execute() {
				charttype1f = 1;
				draw1fChart();
			}
		});
		mbChart1Type.addItem(mntmMap);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		mbChart1Type.addSeparator(separator);
		mbChart1Year = new MenuBar(true);
		
		MenuItem mntmYear = new MenuItem("Year", false, mbChart1Year);
		mbChart1Type.addItem(mntmYear);
		mbChart1Region = new MenuBar(true);
		
		MenuItem mntmRegion = new MenuItem("Region", false, mbChart1Region);
		mbChart1Type.addItem(mntmRegion);
		mbChartClass.addItem(mntm1factorChart);
		MenuBar mbChart2Type = new MenuBar(true);
		
		MenuItem mntm2factorChart = new MenuItem("2-Factor Chart", false, mbChart2Type);
		MenuBar menuBar_8 = new MenuBar(true);
		
		MenuItem mntmChartType = new MenuItem("Chart Type", false, menuBar_8);
		
		MenuItem mntmLine = new MenuItem("Line", false, new Command() {
			public void execute() {
				charttype2f = 0;
				draw2fChart();
			}
		});
		menuBar_8.addItem(mntmLine);
		
		MenuItem mntmColumn = new MenuItem("Column", false, new Command() {
			public void execute() {
				charttype2f = 1;
				draw2fChart();
			}
		});
		menuBar_8.addItem(mntmColumn);
		
		MenuItem mntmBar = new MenuItem("Bar", false, new Command() {
			public void execute() {
				charttype2f = 2;
				draw2fChart();
			}
		});
		menuBar_8.addItem(mntmBar);
		
		MenuItem mntmScatter = new MenuItem("Scatter", false, new Command() {
			public void execute() {
				charttype2f = 3;
				draw2fChart();
			}
		});
		menuBar_8.addItem(mntmScatter);
		mbChart2Type.addItem(mntmChartType);
		
		MenuItemSeparator separator_1 = new MenuItemSeparator();
		mbChart2Type.addSeparator(separator_1);
		MenuBar mbChart2X = new MenuBar(true);
		
		MenuItem mntmSetX = new MenuItem("Set X", false, mbChart2X);
		mbChart2Type.addItem(mntmSetX);
		MenuBar mbChart2Y = new MenuBar(true);
		
		MenuItem mntmSetY = new MenuItem("Set Y", false, mbChart2Y);
		mbChart2Type.addItem(mntmSetY);
		mbChartClass.addItem(mntm2factorChart);
		mbTableOptions.addItem(mntmChartOptions);
		
		MenuItem mntmRedraw = new MenuItem("Redraw", false, new Command() {
			public void execute() {
				drawTable();
				draw1fChart();
				draw2fChart();
			}
		});
		mntmRedraw.setTitle("Refit charts/table into the panel");
		mbTableOptions.addItem(mntmRedraw);
		ChartsWrapper.add(decoratedTabBar, DockPanel.SOUTH);
		decoratedTabBar.setWidth("100%");
		
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
		final String[][] datatable = data;
		numerics = NumberUtils.createIntSeries(2, data[0].length-1, 1);
		Runnable onloadCallback = new Runnable() {
			
			@Override
			public void run() {
				basedata = ChartDataTable.create(datatable, numerics);
				chart1fdata = ChartDataTable.filteredTable(basedata, series, y, x, basedata.getValueString(series, x));
				chart2fdata = ChartDataTable.dataIntoSeries(basedata, x, y, series, true);
				populateChart1Yr();
				drawTable();
				draw1fChart();
				draw2fChart();
				
			}
		};
		VisualizationUtils.loadVisualizationApi(onloadCallback, CoreChart.PACKAGE);
		if(vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
		if(vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
	}
	
	public void draw1fChart(){
		switch (charttype1f) {
		case 1:
			drawMap();
			break;

		default:
			drawPieChart();
			break;
		}
	}
	
	public void draw2fChart(){
		switch (charttype2f) {
		case 1:
			drawColumnChart();
			break;

		default:
			drawLineChart();
			break;
		}
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
	
	public void drawLineChart(){
		if (vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
		Runnable onLoadCallback1 = new Runnable() {
			
			@Override
			public void run() {
				LineChart line = new LineChart(chart2fdata, createCoreChartOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
				vp2FactorChart.add(line);
			}
		};
		
		Runnable onLoadCallback2 = new Runnable() {
			
			@Override
			public void run() {
				ImageLineChart.Options imgline = ImageLineChart.Options.create();
				int w = deckChartPager.getOffsetWidth();
				int h = deckChartPager.getOffsetHeight();
				imgline.setWidth(w);
				imgline.setHeight(h);
				imgline.setLegend(LegendPosition.BOTTOM);
				
				ImageLineChart line = new ImageLineChart(chart2fdata, imgline);
				vp2FactorChart.add(line);
			}
		};

		if (interactive){	VisualizationUtils.loadVisualizationApi(onLoadCallback1, LineChart.PACKAGE);
		} else {
			VisualizationUtils.loadVisualizationApi(onLoadCallback2, ImageLineChart.PACKAGE);
		}
	}
	
	public void drawColumnChart(){
		if (vp2FactorChart.getWidgetCount()>0) vp2FactorChart.clear();
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				ColumnChart line = new ColumnChart(chart2fdata, createCoreChartOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
				vp2FactorChart.add(line);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, ColumnChart.PACKAGE);
	}
	public void drawPieChart(){
		if (vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PieChart pie = new PieChart(chart1fdata, createCoreChartOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
				vp1FactorChart.add(pie);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
	}
	
	public void drawMap(){
		if (vp1FactorChart.getWidgetCount()>0) vp1FactorChart.clear();
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GeoMap map = new GeoMap(chart1fdata, createMapOptions(deckChartPager.getOffsetWidth(), deckChartPager.getOffsetHeight()));
				vp1FactorChart.add(map);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
	}
	
	public Options createCoreChartOptions(int w, int h){
		Options options = Options.create();
		options.setWidth(w-5);
		options.setHeight(h-5);
		options.set("is3D", "true");
		return options;
		
	}
	
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

	public MenuBar getMbChart1Year() {
		return mbChart1Year;
	}
	
	private void populateChart1Yr(){
		ArrayList<String> yrs = ChartDataTable.getUniqueColumnVals(basedata, 1);
		Collections.sort(yrs);
		for (int i = 0; i < yrs.size(); i++) {
			mbChart1Year.addItem(yrs.get(i), new Command() {
				
				@Override
				public void execute() {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	public MenuBar getMbChart1Region() {
		return mbChart1Region;
	}
}
