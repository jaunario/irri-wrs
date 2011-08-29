package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;
import org.irri.statistics.client.ui.Slider;
import org.irri.statistics.client.ui.WRSResultTable;
import org.irri.statistics.client.ui.charts.DBLineChart;
import org.irri.statistics.client.ui.charts.ChartDataTable;
import org.irri.statistics.client.ui.charts.VizTablePanel;
import org.irri.statistics.client.utils.NumberUtils;
import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.StackPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    private DockLayoutPanel MainWrapper = new DockLayoutPanel(Unit.PX);
    	private DeckPanel ContentPanel;
    	private RPFiltering filterPanel = new RPFiltering();
    	private	WRSResultTable myresult = new WRSResultTable();
    	private String[][] resultmatrix;	
    	private Label lblStatusGoesHere = new Label("Status Goes Here");
    		private CaptionPanel cptnpnlResultCharts;
    	private Frame fVizWrapper;
    	private HorizontalPanel hpResultTable;
    	

    	private ToggleButton tglbtnViewResults = new ToggleButton("RESULTS");
        private ToggleButton tglbtnSelection = new ToggleButton("SELECT");
        
    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        //RootPanel rootPanel = RootPanel.get("container");
        //rootPanel.setStyleName("wrapper");
        //rootPanel.setSize("100%", "100%");
        MainWrapper.setStyleName("wrapper");
        MainWrapper.setSize("100%", "100%");
        MainWrapper.getElement().getStyle().setPosition(Position.RELATIVE);
        
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.setSize("100%", "100%");
        rootLayoutPanel.setStyleName("wrapper");
        rootLayoutPanel.add(MainWrapper);
        rootLayoutPanel.setWidgetLeftRight(MainWrapper, 105.0, Unit.PX, 105.0, Unit.PX);
        
        HorizontalPanel StatusPanel = new HorizontalPanel();
        StatusPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        MainWrapper.addSouth(StatusPanel,25.8);
        StatusPanel.setSize("100%", "100%");
                
        StatusPanel.add(lblStatusGoesHere);
        StatusPanel.setCellVerticalAlignment(lblStatusGoesHere, HasVerticalAlignment.ALIGN_BOTTOM);
        lblStatusGoesHere.setStyleName("status");
        StatusPanel.setCellHeight(lblStatusGoesHere, "100%");
        StatusPanel.setCellWidth(lblStatusGoesHere, "100%");
        lblStatusGoesHere.setSize("100%", "14px");
        
        VerticalPanel IRRIBanner = new VerticalPanel();
        IRRIBanner.setStyleName("banner");
        MainWrapper.addNorth(IRRIBanner, 80.0);
        IRRIBanner.setSize("100%", "100%");
        
        Label lblIrri = new Label("IRRI");
        lblIrri.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblIrri.setDirectionEstimator(true);
        lblIrri.setStyleName("gwt-Label-logo");
        IRRIBanner.add(lblIrri);
        IRRIBanner.setCellVerticalAlignment(lblIrri, HasVerticalAlignment.ALIGN_BOTTOM);
        IRRIBanner.setCellHeight(lblIrri, "10%");
        IRRIBanner.setCellWidth(lblIrri, "100%");
        lblIrri.setSize("100%", "54px");
        
        Label lblInternationalRiceResearch = new Label("International Rice Research Institute");
        lblInternationalRiceResearch.setStyleName("gwt-Label-fullname");
        IRRIBanner.add(lblInternationalRiceResearch);
        lblInternationalRiceResearch.setSize("100%", "80%");
        
        HorizontalPanel NavigationBar = new HorizontalPanel();
        NavigationBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        NavigationBar.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        NavigationBar.setSpacing(5);
        MainWrapper.addNorth(NavigationBar, 40.0);
        NavigationBar.setSize("100%", "100%");
        
        Label lblWorldRiceStatistics = new Label("World Rice Statistics");
        lblWorldRiceStatistics.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblWorldRiceStatistics.setStyleName("gwt-Label-title");
        NavigationBar.add(lblWorldRiceStatistics);
        NavigationBar.setCellHorizontalAlignment(lblWorldRiceStatistics, HasHorizontalAlignment.ALIGN_RIGHT);
        lblWorldRiceStatistics.setSize("100%", "100%");
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(5);
        NavigationBar.add(horizontalPanel);
        
        HTML htmlIrriHome = new HTML("<a href=\"http://www.irri.org\">IRRI Home</a>", true);
        horizontalPanel.add(htmlIrriHome);
        htmlIrriHome.setStyleName("gwt-HTML-Link");
        htmlIrriHome.setSize("73px", "15px");
        
        HTML htmlFarmHouseholdSurvey = new HTML("<a href=\"http://geo.irri.org:8180/households\">Household Data</a>", true);
        horizontalPanel.add(htmlFarmHouseholdSurvey);
        htmlFarmHouseholdSurvey.setStyleName("gwt-HTML-Link");
        htmlFarmHouseholdSurvey.setSize("105px", "15px");
        
        DockLayoutPanel dpContentWrapper = new DockLayoutPanel(Unit.PX);
        MainWrapper.add(dpContentWrapper);
        dpContentWrapper.setSize("100%", "100%");
        
        StackPanel decoratedStackPanel = new StackPanel();
        dpContentWrapper.addWest(decoratedStackPanel, 250.0);
        decoratedStackPanel.setSize("100%", "100%");
        
        VerticalPanel vpOnlineQuery = new VerticalPanel();
        decoratedStackPanel.add(vpOnlineQuery, "Online Query", false);
        vpOnlineQuery.setSize("100%", "");
        
        tglbtnSelection.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		if (ContentPanel.getVisibleWidget()!=0) ContentPanel.showWidget(0);
        		tglbtnSelection.setDown(true);
        		tglbtnViewResults.setDown(false);
        	}
        });
        
        vpOnlineQuery.add(tglbtnSelection);
        tglbtnSelection.setSize("95%", "25");
        
        tglbtnViewResults.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		if (ContentPanel.getVisibleWidget()!=1) ContentPanel.showWidget(1);
        		tglbtnSelection.setDown(false);
        		tglbtnViewResults.setDown(true);
        	}
        });
        tglbtnViewResults.setEnabled(false);
        vpOnlineQuery.add(tglbtnViewResults);
        tglbtnViewResults.setSize("95%", "25");
        
        DisclosurePanel dclpHowToUse = new DisclosurePanel("How to use this facility");
        dclpHowToUse.setOpen(true);
        vpOnlineQuery.add(dclpHowToUse);
        dclpHowToUse.setSize("100%", "100%");
        
        HTML htmlhowToUse = new HTML("<p>The procedure in retrieving data is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol><li>Select the level of geographical extent (i.e. continental, national, or subnational)</li><li>Select region/country/organization of interest.</li><li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        dclpHowToUse.add(htmlhowToUse);
        htmlhowToUse.setSize("90%", "100%");
        
        DisclosurePanel dcplHints = new DisclosurePanel("Hint");
        dcplHints.setAnimationEnabled(true);
        vpOnlineQuery.add(dcplHints);
        dcplHints.setSize("100%", "100%");
        
        HTML html = new HTML("<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        dcplHints.setContent(html);
        html.setSize("90%", "100%");
        
        VerticalPanel vpVisualization = new VerticalPanel();
        decoratedStackPanel.add(vpVisualization, "Visualize", false);
        vpVisualization.setSize("100%", "");
        
        Hyperlink hplnkMapIt = new Hyperlink("Map It!", false, "newHistoryToken");
        vpVisualization.add(hplnkMapIt);
        hplnkMapIt.setSize("185", "25");
        
        Hyperlink hplnkTrendIt = new Hyperlink("Trend It!", false, "newHistoryToken");
        vpVisualization.add(hplnkTrendIt);
        hplnkTrendIt.setSize("185", "25");
        
        Hyperlink hplnk3D = new Hyperlink("3D", false, "newHistoryToken");
        vpVisualization.add(hplnk3D);
        hplnk3D.setSize("185", "25");
        
        ContentPanel = new DeckPanel();
        ContentPanel.setAnimationEnabled(true);
        dpContentWrapper.add(ContentPanel);
        ContentPanel.setSize("100%", "100%");
        
        DockLayoutPanel dpSelectionWrapper = new DockLayoutPanel(Unit.PCT);
        ContentPanel.add(dpSelectionWrapper);
        dpSelectionWrapper.setSize("100%", "100%");
        
        HorizontalPanel vpGenCharts = new HorizontalPanel();
        vpGenCharts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        dpSelectionWrapper.addSouth(vpGenCharts, 35.0);
        vpGenCharts.setSize("100%", "100%");
        
        
        CaptionPanel cptnpnlTopProducers = new CaptionPanel("Global Stats");
        vpGenCharts.add(cptnpnlTopProducers);
        cptnpnlTopProducers.setSize("19.5em", "19.5em");
        
        Slider sldGlobalStats = new Slider();
        sldGlobalStats.getDeckPanel().setSize("100%", "100%");
        sldGlobalStats.getDeckPanel().setAnimationEnabled(true);
        cptnpnlTopProducers.add(sldGlobalStats);
        sldGlobalStats.setSize("16em", "15em");
        
        VizTablePanel topprod = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicPr-USDA', val, null)) AS 'Production' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010", NumberUtils.createIntSeries(1, 1, 1), "18em", "15em");
        sldGlobalStats.add(topprod, "1");
        
        VizTablePanel bigarea = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicHa-USDA', val, null)) AS 'Harvested Area' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010", NumberUtils.createIntSeries(1, 1, 1), "18em", "15em");
        sldGlobalStats.add(bigarea, "2");
        
        CaptionPanel cptnpnlBigRiceAreas = new CaptionPanel("Biggest Rice Areas");
        vpGenCharts.add(cptnpnlBigRiceAreas);
        cptnpnlBigRiceAreas.setSize("50%", "100%");

        ScrollPanel scrollPanel = new ScrollPanel();
        dpSelectionWrapper.add(scrollPanel);
        
        VerticalPanel vpAlignCenter = new VerticalPanel();
        scrollPanel.setWidget(vpAlignCenter);
        vpAlignCenter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        vpAlignCenter.setSize("100%", "100%");
        vpAlignCenter.add(filterPanel);
        filterPanel.initListBoxes();
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
        
        DockLayoutPanel dpResultWrapper = new DockLayoutPanel(Unit.PX);
        ContentPanel.add(dpResultWrapper);
        
        HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
        horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel_1.setSpacing(5);
        horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        dpResultWrapper.addNorth(horizontalPanel_1, 39.6);
        
        
        Button btnDownload = new Button("Download");
        btnDownload.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		
        	}
        });
        
        Button btnSendEmail = new Button("Send Email");
        horizontalPanel_1.add(btnSendEmail);
        btnSendEmail.setSize("85", "30");
        horizontalPanel_1.setCellHorizontalAlignment(btnSendEmail, HasHorizontalAlignment.ALIGN_RIGHT);
        
        Button btnSendToGoogle = new Button("Send to Google Docs");
        horizontalPanel_1.add(btnSendToGoogle);
        btnSendToGoogle.setSize("140", "30");
        horizontalPanel_1.setCellHorizontalAlignment(btnSendToGoogle, HasHorizontalAlignment.ALIGN_RIGHT);
        horizontalPanel_1.add(btnDownload);
        btnDownload.setSize("75", "30");
        horizontalPanel_1.setCellHorizontalAlignment(btnDownload, HasHorizontalAlignment.ALIGN_RIGHT);
        
        HorizontalPanel vpResultCharts = new HorizontalPanel();
        dpResultWrapper.addSouth(vpResultCharts, 277.6);
        vpResultCharts.setSize("100%", "100%");
        
        cptnpnlResultCharts = new CaptionPanel("Visualize");
        vpResultCharts.add(cptnpnlResultCharts);
        cptnpnlResultCharts.setSize("100%", "100%");
        
        
        CaptionPanel cptnpnlSource = new CaptionPanel("Source");
        vpResultCharts.add(cptnpnlSource);
        cptnpnlSource.setSize("100%", "100%");
        
        FlexTable flexTable = new FlexTable();
        cptnpnlSource.setContentWidget(flexTable);
        flexTable.setSize("5cm", "3cm");
        
        hpResultTable = new HorizontalPanel();
        dpResultWrapper.add(hpResultTable);
        hpResultTable.setSize("100%", "100%");
        hpResultTable.add(myresult);
        myresult.setSize("100%", "100%");
        
        fVizWrapper = new Frame("http://geo.irri.org/vis/wrs_Motion.php");
        ContentPanel.add(fVizWrapper);
        
        ContentPanel.showWidget(0);
        
    }
    
	public DeckPanel getDeckPanel() {
		return ContentPanel;
	}
	
	private void getQueryResult(String query){
		final AsyncCallback<String[][]> resultAsyncCallback = new AsyncCallback<String[][]>() {
			
			@Override
			public void onSuccess(String[][] result) {
				// TODO Auto-generated method stub
				resultmatrix = result;
				showdata();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		};
		RPCUtils.getService("mysqlservice").RunSELECT(query, resultAsyncCallback);
	}
	public CaptionPanel getCptnpnlResultCharts() {
		return cptnpnlResultCharts;
	}
	
	public void showdata(){
		//myresult.populateResultTable(resultmatrix);		
	    //DBLineChart linechart = new DBLineChart(resultmatrix, "Top Producers", 250, 250);
		int[] numcols = NumberUtils.createIntSeries(1, filterPanel.selectedItemsCount(), 1);
	    DBLineChart linechart2 = new DBLineChart(ChartDataTable.regroup(resultmatrix, 2), DBLineChart.createOptions());
	    VizTablePanel viztab = new VizTablePanel(resultmatrix, numcols, "35em", "35em");
	    if (hpResultTable.getWidget(0)!=null) hpResultTable.clear();
	    hpResultTable.add(viztab);	    
	    if (cptnpnlResultCharts.getContentWidget()!=null) cptnpnlResultCharts.clear();
	    cptnpnlResultCharts.add(linechart2);    			    
    }
	public Frame getFrame() {
		return fVizWrapper;
	}
	public HorizontalPanel getHorizontalPanel() {
		return hpResultTable;
	}
}
