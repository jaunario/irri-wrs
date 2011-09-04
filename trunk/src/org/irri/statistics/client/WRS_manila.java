package org.irri.statistics.client;

import org.irri.statistics.client.ui.ProgressBar;
import org.irri.statistics.client.ui.RPFiltering;
import org.irri.statistics.client.ui.charts.MultiChartPanel;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    private DockLayoutPanel MainWrapper;
    	private Label lblStatusGoesHere;
    	private ProgressBar pbQueryStatus; 
    	private DeckPanel ContentPanel;
    		private RPFiltering filterPanel;
    		private MultiChartPanel mcpResults;
    		
    	private String[][] resultmatrix;	
    
    	private ToggleButton tglbtnViewResults;
    	private ToggleButton tglbtnSelection;
    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        initWRS();

        DeckPanel WRSPager = new DeckPanel();
        MainWrapper.add(WRSPager);

        DockLayoutPanel dpContentWrapper = new DockLayoutPanel(Unit.PX);
        dpContentWrapper.setSize("100%", "100%");        	
        WRSPager.add(dpContentWrapper);	
        
        WRSHome HomePage = new WRSHome();
        WRSPager.add(HomePage);
        WRSPager.showWidget(0);
        
        // WRS Navigation
        StackLayoutPanel stkpWRSAppSelector = new StackLayoutPanel(Unit.EM);
        stkpWRSAppSelector.setSize("100%", "100%");
        
        SimplePanel simplePanel_1 = new SimplePanel();
        stkpWRSAppSelector.add(simplePanel_1, new HTML("Query"), 2.0);
        
        VerticalPanel vpOnlineQuery = new VerticalPanel();
        simplePanel_1.setWidget(vpOnlineQuery);
        
        tglbtnSelection = new ToggleButton("SELECT");
        tglbtnSelection.setDown(true);
        tglbtnSelection.setSize("95%", "25");
        vpOnlineQuery.add(tglbtnSelection);
        
        DisclosurePanel dclpHowToUse = new DisclosurePanel("How to use this facility");
        dclpHowToUse.setOpen(true);
        vpOnlineQuery.add(dclpHowToUse);
        
        HTML htmlhowToUse = new HTML("<p>The procedure in retrieving data is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol><li>Select the level of geographical extent (i.e. continental, national, or subnational)</li><li>Select region/country/organization of interest.</li><li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        htmlhowToUse.setSize("90%", "100%");
        dclpHowToUse.setContent(htmlhowToUse);
        
        DisclosurePanel dcplHints = new DisclosurePanel("Hint");
        dcplHints.setAnimationEnabled(true);
        vpOnlineQuery.add(dcplHints);
        
        HTML html = new HTML("<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        html.setSize("90%", "100%");
        dcplHints.setContent(html);
        
        tglbtnViewResults = new ToggleButton("RESULTS");
        tglbtnViewResults.setEnabled(false);
        tglbtnViewResults.setSize("95%", "25px");
        vpOnlineQuery.add(tglbtnViewResults);
        
        SimplePanel simplePanel = new SimplePanel();
        stkpWRSAppSelector.add(simplePanel, new HTML("Visualize"), 2.0);
        
        // WRS Visualization Apps                
        VerticalPanel vpVisualize = new VerticalPanel();
        simplePanel.setWidget(vpVisualize);
        
        Hyperlink hplnkMapIt = new Hyperlink("Map It!", false, "newHistoryToken");
        hplnkMapIt.setSize("185", "25");
        vpVisualize.add(hplnkMapIt);
        
        Hyperlink hplnkTrendIt = new Hyperlink("Trend It!", false, "newHistoryToken");
        hplnkTrendIt.setSize("185", "25");
        vpVisualize.add(hplnkTrendIt);
        
        Hyperlink hplnk3D = new Hyperlink("3D", false, "newHistoryToken");
        hplnk3D.setSize("185", "25");
        vpVisualize.add(hplnk3D);
        dpContentWrapper.addWest(stkpWRSAppSelector, 250.0);
                                
        // Put EventHandlers here
        
        tglbtnSelection.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
            	if (ContentPanel.getVisibleWidget()!=0) ContentPanel.showWidget(0);
                tglbtnSelection.setDown(true);
                tglbtnViewResults.setDown(false);
            }
        });
        
        tglbtnViewResults.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		if (ContentPanel.getVisibleWidget()!=1) ContentPanel.showWidget(1);
        		tglbtnSelection.setDown(false);
        		tglbtnViewResults.setDown(true);
        	}
        });
        
        ContentPanel = new DeckPanel();
        dpContentWrapper.add(ContentPanel);
        ContentPanel.setAnimationEnabled(true);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        ContentPanel.add(scrollPanel);
        ContentPanel.showWidget(0);
        
        VerticalPanel vpAlignCenter = new VerticalPanel();
        scrollPanel.setWidget(vpAlignCenter);
        vpAlignCenter.setSize("100%", "100%");
        vpAlignCenter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        
        filterPanel = new RPFiltering();
        filterPanel.initListBoxes();
        vpAlignCenter.add(filterPanel);
        
        filterPanel.setSubmitButtonClickHandler(new ClickHandler() {
        	@Override
            public void onClick(ClickEvent event) {
        		final String sql = filterPanel.sqlFromItems();
                if (!sql.equalsIgnoreCase("")) {					
                	getQueryResult(sql);
                    ContentPanel.showWidget(1);
                    tglbtnViewResults.setEnabled(true);
                    tglbtnViewResults.setDown(true);
                    tglbtnSelection.setDown(false);
                } else lblStatusGoesHere.setText("Please select a year.");
            }
        });
        mcpResults = new MultiChartPanel();
        mcpResults.setSize("100%", "100%");        
        //hpResultTable = new HorizontalPanel();
        ContentPanel.add(mcpResults);
        //hpResultTable.setSize("600px", "480px");
                              
    }
    
	private void getQueryResult(String query){
		lblStatusGoesHere.setText("Running query on the server");
		final AsyncCallback<String[][]> resultAsyncCallback = new AsyncCallback<String[][]>() {
			
			@Override
			public void onSuccess(String[][] result) {
				// TODO Auto-generated method stub
				mcpResults.setBaseData(result);
				lblStatusGoesHere.setText("Fetched " + result.length + "records.");
				//showdata();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		};
		RPCUtils.getService("mysqlservice").RunSELECT(query, resultAsyncCallback);
	}
	
	public void showdata(){
		
		//myresult.populateResultTable(resultmatrix);		
	    //DBLineChart linechart = new DBLineChart(resultmatrix, "Top Producers", 250, 250);
		//final int[] numcols = NumberUtils.createIntSeries(1, filterPanel.selectedItemsCount()+1, 1);
	    //DBLineChart linechart2 = new DBLineChart(ChartDataTable.regroup(resultmatrix, 2), DBLineChart.createOptions());
	    //lblStatusGoesHere.setText("Parsing results.");
	    //if (cptnpnlResultCharts.getContentWidget()!=null) cptnpnlResultCharts.clear();
	    //cptnpnlResultCharts.add(linechart2);
	    GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				mcpResults.setBaseData(resultmatrix);
				//TablePanel viztab = new TablePanel(resultmatrix, numcols, "50em", "35em");
				lblStatusGoesHere.setText("Done.");
			}
			
			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}
		});
	    
    }
	
	public void initWRS(){

		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.setSize("100%", "100%");
        rootLayoutPanel.setStyleName("wrapper");

        // Create main wrapper
		MainWrapper = new DockLayoutPanel(Unit.PX);
        MainWrapper.setStyleName("wrapper");
        MainWrapper.setSize("100%", "100%");
        MainWrapper.getElement().getStyle().setPosition(Position.RELATIVE);
        rootLayoutPanel.add(MainWrapper);
        rootLayoutPanel.setWidgetLeftRight(MainWrapper, 90.0, Unit.PX, 90.0, Unit.PX);
        

        // Create IRRI Banner 
        VerticalPanel vpIRRIBanner = new VerticalPanel();
        vpIRRIBanner.setStyleName("banner");
    	MainWrapper.addNorth(vpIRRIBanner, 80.0);
    	vpIRRIBanner.setSize("100%", "100%");
        
        Label lblIrri = new Label("IRRI");
        lblIrri.setSize("100%", "54px");
        lblIrri.setStyleName("gwt-Label-logo");
    	vpIRRIBanner.add(lblIrri);
    	
        Label lblIRRIFull = new Label("International Rice Research Institute");
        lblIRRIFull.setStyleName("gwt-Label-fullname");
        vpIRRIBanner.add(lblIRRIFull);
        // End of IRRI Banner
        
        // Create AppBanner
        HorizontalPanel hpAppBanner = new HorizontalPanel();
        hpAppBanner.setSize("100%", "100%");
        MainWrapper.addNorth(hpAppBanner, 40.0);
        
        HorizontalPanel hpAppTitle = new HorizontalPanel();
        hpAppBanner.add(hpAppTitle);
        
        Label lblWorldRiceStatistics = new Label("World Rice Statistics");
        lblWorldRiceStatistics.setStyleName("gwt-Label-title");
        lblWorldRiceStatistics.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        hpAppTitle.add(lblWorldRiceStatistics);
        
        Label lblBeta = new Label("[BETA]");
        lblBeta.setStyleName("gwt-Label-version");
        lblBeta.setHeight("22px");
        hpAppTitle.add(lblBeta);
        
        HorizontalPanel hpGlobalNavigation = new HorizontalPanel();
        hpGlobalNavigation.setSpacing(5);
        hpAppBanner.add(hpGlobalNavigation);
        hpAppBanner.setCellVerticalAlignment(hpGlobalNavigation, HasVerticalAlignment.ALIGN_BOTTOM);
    	hpAppBanner.setCellHorizontalAlignment(hpGlobalNavigation, HasHorizontalAlignment.ALIGN_RIGHT);
    	
        HTML htmlIrriHome = new HTML("<a href=\"http://www.irri.org\">IRRI Home</a>", true);
        htmlIrriHome.setStyleName("gwt-HTML-Link");
        htmlIrriHome.setSize("73px", "15px");
        hpGlobalNavigation.add(htmlIrriHome);
        
        HTML htmlFarmHouseholdSurvey = new HTML("<a href=\"http://geo.irri.org:8180/households\">Household Data</a>", true);
        htmlFarmHouseholdSurvey.setStyleName("gwt-HTML-Link");
        htmlFarmHouseholdSurvey.setSize("105px", "15px");
        hpGlobalNavigation.add(htmlFarmHouseholdSurvey);
        
        // End of AppBanner

        // Create Status Panel
	    HorizontalPanel hpStatusPanel = new HorizontalPanel();
	    hpStatusPanel.setStyleName("statuspanel");
	    hpStatusPanel.setSize("100%", "100%");
	    hpStatusPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    MainWrapper.addSouth(hpStatusPanel,35.0);

    	lblStatusGoesHere = new Label("Ready.");
    	lblStatusGoesHere.setStyleName("status");
    	lblStatusGoesHere.setSize("100%", "14px");
    	hpStatusPanel.add(lblStatusGoesHere);
    	hpStatusPanel.setCellHeight(lblStatusGoesHere, "100%");
    	hpStatusPanel.setCellWidth(lblStatusGoesHere, "100%");
    	
	    pbQueryStatus = new ProgressBar();
	    pbQueryStatus.setSize("166px", "15px");
	    hpStatusPanel.add(pbQueryStatus);
	    hpStatusPanel.setCellVerticalAlignment(pbQueryStatus, HasVerticalAlignment.ALIGN_MIDDLE);
	    // End of Status Panel
	    
        
	}
}
