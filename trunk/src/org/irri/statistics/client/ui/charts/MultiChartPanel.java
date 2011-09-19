package org.irri.statistics.client.ui.charts;

import org.irri.statistics.client.utils.NumberUtils;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MultiChartPanel extends Composite {
	AbstractDataTable basedata;
	
	public int[] numerics;
	public String selsummary = "";
	private DeckPanel deckChartPager;
	private VerticalPanel vpTablePage;
	private DecoratedTabBar dtbChartPageSelector;
	private HTML htmlSelected;
	private ScrollPanel scrollSelection;
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
				scrollSelection.setHeight(deckChartPager.getOffsetHeight()+"px");
				scrollSelection.setWidth(deckChartPager.getOffsetWidth()*0.35+"px");
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
					RPCUtils.getService("mysqlservice").SaveCSV(ChartDataTable.csvData(basedata)+"\n\n"+ selsummary, downloadAsyncCallback);
	                                	
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
				final ChartOptions newco = new ChartOptions(basedata, deckChartPager.getOffsetWidth(),deckChartPager.getOffsetHeight(), deckChartPager.getWidgetCount());
				deckChartPager.add(newco.getChart());
				HorizontalPanel charttab = new HorizontalPanel();
				charttab.setSpacing(2);				
				final Label tablabel = new Label(("Chart " + newco.itemid));
				tablabel.setWordWrap(false);
				charttab.add(tablabel);
				charttab.setCellVerticalAlignment(tablabel, HasVerticalAlignment.ALIGN_MIDDLE);
				Image closeimg = new Image("images/tab_close.png");
				closeimg.setSize("5px", "5px");
				PushButton closetab = new PushButton(closeimg);
				closetab.setSize("10px", "10px");
				charttab.add(closetab);
				closetab.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						int tab = newco.itemid;
						deckChartPager.remove(tab);
						dtbChartPageSelector.removeTab(tab);
						dtbChartPageSelector.selectTab(tab-1);
					}
				});
				charttab.setWidth("50px");
				dtbChartPageSelector.addTab(charttab);
				dtbChartPageSelector.selectTab(dtbChartPageSelector.getTabCount()-1);
			}
		});
		mbChartClass.addItem(mntmNewChart);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		mbChartClass.addSeparator(separator_2);
		
		MenuItem mntmRemove = new MenuItem("Remove", false, new Command() {
			public void execute() {
				while (deckChartPager.getWidgetCount()>1) {
					dtbChartPageSelector.removeTab(deckChartPager.getWidgetCount()-1);
					deckChartPager.remove(deckChartPager.getWidgetCount()-1);					
				}
			}
		});
		mntmRemove.setHTML("Remove All Charts");
		mbChartClass.addItem(mntmRemove);
		mbTableOptions.addItem(mntmCharts);
		ChartsWrapper.add(dtbChartPageSelector, DockPanel.SOUTH);
		dtbChartPageSelector.setSize("100%", "30px");
		
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

		scrollSelection = new ScrollPanel();
		ChartsWrapper.add(scrollSelection, DockPanel.EAST);
		
		VerticalPanel vpSelectionSummary = new VerticalPanel();
		scrollSelection.setWidget(vpSelectionSummary);
		vpSelectionSummary.setStyleName("selection-panel");
		vpSelectionSummary.setSize("100%", "100%");
		
		htmlSelected = new HTML("New HTML", true);
		vpSelectionSummary.add(htmlSelected);
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
	
	public Table.Options setTableSize(int w, int h) {
    	Table.Options options = Table.Options.create();
    	options.setHeight((h-5)+"px");
    	options.setWidth((w-5)+"px");
    	options.setShowRowNumber(true);
    	return options;
    }
    
	public void setSelSummary(String selsum){		
		String[] ssummary = selsum.split("_");
		String[] thissel;
		selsummary = "";
		for (int i = 0; i < ssummary.length; i++) {
			switch (i) {
			case 0:
				selsummary += "Regions/Countries/Provinces \n";
				break;

			case 1:
				selsummary += "Variables \n";
				break;

			case 2:
				selsummary += "Years \n" + ssummary[i];
				break;
			}
			
			if(i!=2){
				thissel = ssummary[i].split("@");
				for (int j = 0; j < thissel.length; j++) {
					selsummary += thissel[j] + "\n";
				}
				selsummary += "\n";
			}
		}
	}
	public HTML getHtmlSelected() {
		return htmlSelected;
	}
}
