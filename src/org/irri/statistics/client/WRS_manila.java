package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;
import org.irri.statistics.client.ui.ResultDataTable;
import org.irri.statistics.client.ui.WRSResultTable;
//import org.irri.statistics.client.ui.charts.BarChartPanel;
//import org.irri.statistics.client.ui.charts.PieChartPanel;
import org.irri.statistics.client.ui.charts.DBLineChart;
import org.irri.statistics.client.ui.charts.VizTablePanel;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
//import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DeckPanel;
//import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Grid;

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
            
    /** Creates a new instance of worldriceEntryPoint */
    public WRS_manila() {

    }


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
        NavigationBar.setCellVerticalAlignment(lblWorldRiceStatistics, HasVerticalAlignment.ALIGN_BOTTOM);
        
        ContentPanel = new DeckPanel();
        MainWrapper.add(ContentPanel);
        
        SplitLayoutPanel SelectionPanel = new SplitLayoutPanel();
        ContentPanel.add(SelectionPanel);
        SelectionPanel.setSize("100%", "100%");
        
        PushButton pshbtnSelect = new PushButton("Select");
        pshbtnSelect.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		ContentPanel.showWidget(0);
        	}
        });
        Image image = new Image("images/search.png");
        image.setPixelSize(25, 25);
        pshbtnSelect.getUpFace().setImage(image);
        NavigationBar.add(pshbtnSelect);
        NavigationBar.setCellHorizontalAlignment(pshbtnSelect, HasHorizontalAlignment.ALIGN_CENTER);
        
        PushButton pshbtnResults = new PushButton("Results");
        pshbtnResults.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		ContentPanel.showWidget(1);
        	}
        });
        
        NavigationBar.add(pshbtnResults);
        NavigationBar.setCellHorizontalAlignment(pshbtnResults, HasHorizontalAlignment.ALIGN_CENTER);
        
        ScrollPanel scpnlGenChart = new ScrollPanel();
        SelectionPanel.addEast(scpnlGenChart, 300.0);
        
        VerticalPanel vpGenCharts = new VerticalPanel();
        scpnlGenChart.setWidget(vpGenCharts);
        vpGenCharts.setSize("100%", "100%");
        
        CaptionPanel cptnpnlTopProducers = new CaptionPanel("Top Producers");
        vpGenCharts.add(cptnpnlTopProducers);
        cptnpnlTopProducers.setSize("80%", "80%");
        
        ScrollPanel scrpnlChartScroll = new ScrollPanel();
        cptnpnlTopProducers.setContentWidget(scrpnlChartScroll);
        
        VizTablePanel topprod = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicPr-USDA', val, null)) AS 'Production' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010");
        scrpnlChartScroll.setWidget(topprod);
        topprod.setSize("100%", "90%");
                
        CaptionPanel cptnpnlBigRiceAreas = new CaptionPanel("Biggest Rice Areas");
        vpGenCharts.add(cptnpnlBigRiceAreas);
        cptnpnlBigRiceAreas.setSize("80%", "80%");
                
        VizTablePanel bigarea = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicHa-USDA', val, null)) AS 'Harvested Area' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010");
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
                        	GWT.runAsync(new RunAsyncCallback() {
						
						@Override
						public void onSuccess() {
							
							
						}
						
						@Override
						public void onFailure(Throwable reason) {
							// TODO Auto-generated method stub
							
						}
					});
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
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        ResultPanel.addSouth(horizontalPanel, 300.0);
        
        HTMLPanel htmlDisclaimer = new HTMLPanel("<h3>Disclaimer </h3>\r\nData and information released from the International Rice Research Institute (IRRI) are provided on an \"AS IS\" basis, without warranty of any kind, including without limitation the warranties of merchantability, fitness for a particular purpose and non-infringement. Availability of this data and information does not constitute scientific publication. Data and/or information may contain errors or be incomplete.");
        htmlDisclaimer.setStyleName("notes");
        horizontalPanel.add(htmlDisclaimer);
        htmlDisclaimer.setSize("90%", "100%");
        
        ResultPanel.add(myresult);
        
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
		UtilsRPC.getService("mysqlservice").RunSELECT(query, resultAsyncCallback);
	}
	public CaptionPanel getCptnpnlResultCharts() {
		return cptnpnlResultCharts;
	}
	
	public void showdata(){
		myresult.populateResultTable(resultmatrix);		
	    //DBLineChart linechart = new DBLineChart(resultmatrix, "Top Producers", 250, 250);
	    DBLineChart linechart2 = new DBLineChart(ResultDataTable.regroup(resultmatrix, 2), DBLineChart.createOptions());
	    if (cptnpnlResultCharts.getContentWidget()!=null) cptnpnlResultCharts.clear();
	    cptnpnlResultCharts.add(linechart2);    			    
    }
	public SplitLayoutPanel getResultPanel() {
		return ResultPanel;
	}
}
