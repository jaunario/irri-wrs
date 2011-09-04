package org.irri.statistics.client.ui.charts;

import org.irri.statistics.client.utils.NumberUtils;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;

public class MultiChartPanel extends Composite {
	AbstractDataTable basedata;
	public int charttype = 1;
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
		
		MenuItem mntmDownload = new MenuItem("Download", false, (Command) null);
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
		drawTable();
	}
	
	public void drawTable(){
		Runnable onLoadCallback = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Table table = new Table(basedata, setTableSize("100%", "100%"));
				vpTablePage.add(table);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
	};
	
	public Table.Options setTableSize(String w, String h) {
    	Table.Options options = Table.Options.create();
    	options.setHeight(h);
    	options.setWidth(w);
    	options.setShowRowNumber(true);
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
