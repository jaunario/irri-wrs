package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;
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
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;


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
    		private SplitLayoutPanel ResultPanel;

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
        NavigationBar.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        NavigationBar.setSpacing(2);
        NavigationBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        MainWrapper.addNorth(NavigationBar, 40.0);
        NavigationBar.setSize("100%", "100%");
        
        Label lblWorldRiceStatistics = new Label("World Rice Statistics");
        lblWorldRiceStatistics.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblWorldRiceStatistics.setStyleName("gwt-Label-title");
        NavigationBar.add(lblWorldRiceStatistics);
        lblWorldRiceStatistics.setHeight("100%");
        
        ContentPanel = new DeckPanel();
        MainWrapper.add(ContentPanel);
        
        SplitLayoutPanel SelectionPanel = new SplitLayoutPanel();
        ContentPanel.add(SelectionPanel);
        SelectionPanel.setSize("100%", "100%");
        
        TabBar tabBar = new TabBar();
        tabBar.addTab("Search");
        tabBar.addTab("Result\t");
        tabBar.addTab("Trend It");
        tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
        	public void onSelection(SelectionEvent<Integer> event) {
        		ContentPanel.showWidget(event.getSelectedItem());
        	}
        });
        NavigationBar.add(tabBar);
        NavigationBar.setCellHeight(tabBar, "100%");
        tabBar.setWidth("100");
        NavigationBar.setCellHorizontalAlignment(tabBar, HasHorizontalAlignment.ALIGN_RIGHT);
        NavigationBar.setCellWidth(tabBar, "50%");
        NavigationBar.setCellVerticalAlignment(tabBar, HasVerticalAlignment.ALIGN_BOTTOM);
        
        VerticalPanel vpGenCharts = new VerticalPanel();
        vpGenCharts.setSize("100%", "100%");
        SelectionPanel.addEast(vpGenCharts, 300.0);
        
        
        CaptionPanel cptnpnlTopProducers = new CaptionPanel("Top Producers");
        vpGenCharts.add(cptnpnlTopProducers);
        cptnpnlTopProducers.setSize("80%", "80%");
        
        VizTablePanel topprod = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicPr-USDA', val, null)) AS 'Production' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010", NumberUtils.createIntSeries(1, 1, 1));
        cptnpnlTopProducers.add(topprod);
        topprod.setSize("100%", "90%");
                
        CaptionPanel cptnpnlBigRiceAreas = new CaptionPanel("Biggest Rice Areas");
        vpGenCharts.add(cptnpnlBigRiceAreas);
        cptnpnlBigRiceAreas.setSize("100%", "80%");
                
        VizTablePanel bigarea = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicHa-USDA', val, null)) AS 'Harvested Area' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010", NumberUtils.createIntSeries(1, 1, 1));
        cptnpnlBigRiceAreas.add(bigarea);
        bigarea.setSize("100%", "90%");
        
        Grid grid = new Grid(1, 3);
        SelectionPanel.addSouth(grid, 300.0);
        grid.setSize("100%", "100%");
        
        CaptionPanel cptnpnlFeature = new CaptionPanel("Feature 1");
        grid.setWidget(0, 0, cptnpnlFeature);
        
        HTML panel = new HTML("\tLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        cptnpnlFeature.setContentWidget(panel);
        panel.setSize("100%", "100%");
        
        CaptionPanel cptnpnlFeature_1 = new CaptionPanel("Feature 2");
        grid.setWidget(0, 1, cptnpnlFeature_1);
        
        HTML html = new HTML("\tLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        cptnpnlFeature_1.setContentWidget(html);
        html.setSize("100%", "100%");
        
        CaptionPanel cptnpnlFeature_2 = new CaptionPanel("Feature 3");
        grid.setWidget(0, 2, cptnpnlFeature_2);
        
        HTML html_1 = new HTML("\tLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        cptnpnlFeature_2.setContentWidget(html_1);
        html_1.setSize("100%", "100%");
        
        DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
        SelectionPanel.add(dockPanel);
        
        VerticalPanel vpText = new VerticalPanel();
        dockPanel.addWest(vpText, 250.0);
        
        CaptionPanel cptnpnlHowToUse = new CaptionPanel("How to use this facility");
        vpText.add(cptnpnlHowToUse);
        cptnpnlHowToUse.setSize("90%", "100%");
        
        HTML htmlhowToUse = new HTML("<p>The procedure in retrieving data is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol><li>Select the level of geographical extent (i.e. continental, national, or subnational)</li><li>Select region/country/organization of interest.</li><li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        cptnpnlHowToUse.setContentWidget(htmlhowToUse);
        htmlhowToUse.setSize("100%", "100%");
        
        DisclosurePanel disclosurePanel = new DisclosurePanel("Hint");
        disclosurePanel.setAnimationEnabled(true);
        disclosurePanel.setOpen(true);
        vpText.add(disclosurePanel);
        disclosurePanel.setSize("100%", "100%");
        
        HTML htmlNewHtml = new HTML("<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        disclosurePanel.setContent(htmlNewHtml);
        htmlNewHtml.setSize("90%", "100%");
        
        ScrollPanel scrollPanel = new ScrollPanel();
        dockPanel.add(scrollPanel);
        scrollPanel.setWidget(filterPanel);
        filterPanel.initListBoxes();
        filterPanel.setSubmitButtonClickHandler(new ClickHandler() {
                	@Override
                    public void onClick(ClickEvent event) {
                		final String sql = filterPanel.sqlFromItems();
                        if (!sql.equalsIgnoreCase("")) {					
                        	getQueryResult(sql);
                            ContentPanel.showWidget(1);
                        } else lblStatusGoesHere.setText("Please select a year.");
                    }
                });
        
        ResultPanel = new SplitLayoutPanel();
        ContentPanel.add(ResultPanel);
        
        VerticalPanel vpResultCharts = new VerticalPanel();
        ResultPanel.addWest(vpResultCharts, 350.0);
        
        cptnpnlResultCharts = new CaptionPanel("Visualize");
        vpResultCharts.add(cptnpnlResultCharts);
        cptnpnlResultCharts.setSize("325px", "325px");
        
        
        CaptionPanel cptnpnlSource = new CaptionPanel("Source");
        vpResultCharts.add(cptnpnlSource);
        cptnpnlSource.setSize("325px", "150px");
        
        FlexTable flexTable = new FlexTable();
        cptnpnlSource.setContentWidget(flexTable);
        flexTable.setSize("5cm", "3cm");
        
        CaptionPanel cptnpnlYourSelection = new CaptionPanel("Your Selection");
        vpResultCharts.add(cptnpnlYourSelection);
        cptnpnlYourSelection.setSize("325px", "150px");
        
        Grid grid_1 = new Grid(2, 1);
        ResultPanel.addSouth(grid_1, 100.0);
        
        HTMLPanel htmlDisclaimer = new HTMLPanel("<h3>Disclaimer </h3>\r\nData and information released from the International Rice Research Institute (IRRI) are provided on an \"AS IS\" basis, without warranty of any kind, including without limitation the warranties of merchantability, fitness for a particular purpose and non-infringement. Availability of this data and information does not constitute scientific publication. Data and/or information may contain errors or be incomplete.");
        grid_1.setWidget(1, 0, htmlDisclaimer);
        htmlDisclaimer.setStyleName("notes");
        htmlDisclaimer.setSize("90%", "100%");
        
        DockLayoutPanel dockPanel_1 = new DockLayoutPanel(Unit.PX);
        ResultPanel.add(dockPanel_1);
        
        HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
        horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        horizontalPanel_1.setSpacing(5);
        horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        dockPanel_1.addNorth(horizontalPanel_1, 30.0);
        
        
        Button btnDownload = new Button("Download");
        btnDownload.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		
        	}
        });
        
        Button btnSendEmail = new Button("Send Email");
        horizontalPanel_1.add(btnSendEmail);
        
        Button btnSendToGoogle = new Button("Send to Google Docs");
        horizontalPanel_1.add(btnSendToGoogle);
        horizontalPanel_1.add(btnDownload);
        dockPanel_1.add(myresult);
        myresult.setSize("100%", "100%");
        
        ScrollPanel scrollPanel_1 = new ScrollPanel();
        ContentPanel.add(scrollPanel_1);
        
        Frame frame = new Frame("http://geo.irri.org/vis/wrs_Motion.php");
        scrollPanel_1.setWidget(frame);
        frame.setSize("100%", "100%");
        
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
		myresult.populateResultTable(resultmatrix);		
	    //DBLineChart linechart = new DBLineChart(resultmatrix, "Top Producers", 250, 250);
	    DBLineChart linechart2 = new DBLineChart(ChartDataTable.regroup(resultmatrix, 2), DBLineChart.createOptions());
	    if (cptnpnlResultCharts.getContentWidget()!=null) cptnpnlResultCharts.clear();
	    cptnpnlResultCharts.add(linechart2);    			    
    }
	public SplitLayoutPanel getResultPanel() {
		return ResultPanel;
	}
}
