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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
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
    	private Frame frameVisualize;
    	private MenuItem mntmResults;
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
        WRSPager.showWidget(0);
        
        VerticalPanel vpOnlineQuery = new VerticalPanel();
        dpContentWrapper.addWest(vpOnlineQuery, 250.0);
        
        DisclosurePanel dclpHowToUse = new DisclosurePanel("How to use this facility");
        dclpHowToUse.setOpen(true);
        vpOnlineQuery.add(dclpHowToUse);
        
        HTML htmlhowToUse = new HTML("<p>The procedure in retrieving data is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol><li>Select the level of geographical extent (i.e. continental, national, or subnational)</li><li>Select region/country/organization of interest.</li><li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        htmlhowToUse.setSize("90%", "100%");
        dclpHowToUse.setContent(htmlhowToUse);
        
        DisclosurePanel dcplHints = new DisclosurePanel("Hint");
        dcplHints.setOpen(true);
        dcplHints.setAnimationEnabled(true);
        vpOnlineQuery.add(dcplHints);
        
        HTML html = new HTML("<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        html.setSize("90%", "100%");
        dcplHints.setContent(html);
        
        // WRS Navigation
        
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
                	ContentPanel.showWidget(1);
                    mntmResults.setEnabled(true);
                    selectionSummary();
                    getQueryResult(sql);                    
                } else lblStatusGoesHere.setText("Please select a year.");
            }
        });
        mcpResults = new MultiChartPanel();
        mcpResults.setSize("100%", "100%");        
        ContentPanel.add(mcpResults);
        
        frameVisualize = new Frame("http://geo.irri.org/vis/wrs_Motion.php");
        ContentPanel.add(frameVisualize);
        frameVisualize.setSize("100%", "100%");
                              
    }
    
	private void getQueryResult(String query){
		lblStatusGoesHere.setText("Running query on the server");
		final AsyncCallback<String[][]> resultAsyncCallback = new AsyncCallback<String[][]>() {
			
			@Override
			public void onSuccess(String[][] result) {
				mcpResults.setBaseData(result);
				lblStatusGoesHere.setText("Fetched " + (result.length-1) + " records.");
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
		
	    GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				mcpResults.setBaseData(resultmatrix);
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
        
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        MainWrapper.addNorth(verticalPanel, 26.0);
        verticalPanel.setSize("100%", "100%");
        
        HorizontalPanel hpGlobalNavigation = new HorizontalPanel();
        verticalPanel.add(hpGlobalNavigation);
        hpGlobalNavigation.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        hpGlobalNavigation.setSize("250px", "25px");
        hpGlobalNavigation.setSpacing(5);
        
        InlineHTML nlnhtmlirriHome = new InlineHTML("<a href=\"http://www.irri.org\">IRRI Home</a>");
        nlnhtmlirriHome.setStyleName("gwt-HTML-Link");
        hpGlobalNavigation.add(nlnhtmlirriHome);
        hpGlobalNavigation.setCellHorizontalAlignment(nlnhtmlirriHome, HasHorizontalAlignment.ALIGN_RIGHT);
        
        InlineHTML nlnhtmlfarmHouseholdsSurvey = new InlineHTML("<a href=\"http://ricestat.irri.org:8080/households\">Farm Households Survey</a>");
        nlnhtmlfarmHouseholdsSurvey.setStyleName("gwt-HTML-Link");
        hpGlobalNavigation.add(nlnhtmlfarmHouseholdsSurvey);
        hpGlobalNavigation.setCellHorizontalAlignment(nlnhtmlfarmHouseholdsSurvey, HasHorizontalAlignment.ALIGN_RIGHT);
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        horizontalPanel.setStyleName("banner");
        MainWrapper.addNorth(horizontalPanel, 80.0);
        horizontalPanel.setSize("100%", "100%");
        
        // Create IRRI Banner 
        VerticalPanel vpIRRIBanner = new VerticalPanel();
        horizontalPanel.add(vpIRRIBanner);
        vpIRRIBanner.setSize("100%", "100%");
        
        Label lblIrri = new Label("IRRI");
        lblIrri.setSize("100%", "54px");
        lblIrri.setStyleName("gwt-Label-logo");
    	vpIRRIBanner.add(lblIrri);
    	
        Label lblIRRIFull = new Label("International Rice Research Institute");
        lblIRRIFull.setStyleName("gwt-Label-fullname");
        vpIRRIBanner.add(lblIRRIFull);
        
        VerticalPanel vpExternal = new VerticalPanel();
        vpExternal.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        vpExternal.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel.add(vpExternal);
        horizontalPanel.setCellVerticalAlignment(vpExternal, HasVerticalAlignment.ALIGN_BOTTOM);
        horizontalPanel.setCellHorizontalAlignment(vpExternal, HasHorizontalAlignment.ALIGN_RIGHT);
        vpExternal.setSize("100%", "100%");
        
        HorizontalPanel hpAppTitle = new HorizontalPanel();
        vpExternal.add(hpAppTitle);
        vpExternal.setCellHorizontalAlignment(hpAppTitle, HasHorizontalAlignment.ALIGN_RIGHT);
        hpAppTitle.setSize("305px", "37px");
        hpAppTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        
        InlineLabel nlnlblWorldRiceStatistics = new InlineLabel("World Rice Statistics");
        nlnlblWorldRiceStatistics.setStyleName("gwt-Label-title");
        hpAppTitle.add(nlnlblWorldRiceStatistics);
        hpAppTitle.setCellHorizontalAlignment(nlnlblWorldRiceStatistics, HasHorizontalAlignment.ALIGN_RIGHT);
        hpAppTitle.setCellVerticalAlignment(nlnlblWorldRiceStatistics, HasVerticalAlignment.ALIGN_BOTTOM);
        
        InlineLabel nlnlblVersion = new InlineLabel("[BETA]");
        nlnlblVersion.setStyleName("gwt-Label-version");
        hpAppTitle.add(nlnlblVersion);
        
        MenuBar menuBar = new MenuBar(false);
        MainWrapper.addNorth(menuBar, 35.0);
        menuBar.setAnimationEnabled(true);
        menuBar.setAutoOpen(true);
        
        MenuItem mntmHome = new MenuItem("Home", false, (Command) null);
        mntmHome.setVisible(false);
        menuBar.addItem(mntmHome);
        MenuBar menuBar_2 = new MenuBar(true);
        
        MenuItem mntmQuery = new MenuItem("Query", false, menuBar_2);
        
        MenuItem mntmSelect = new MenuItem("Select", false, new Command() {
        	public void execute() {
            	if (ContentPanel.getVisibleWidget()!=0) ContentPanel.showWidget(0);
            	History.newItem("Select");
        	}
        });
        menuBar_2.addItem(mntmSelect);
        
        mntmResults = new MenuItem("Results", false, (Command) null);
        mntmResults.setEnabled(false);
        menuBar_2.addItem(mntmResults);
        menuBar.addItem(mntmQuery);
        MenuBar menuBar_1 = new MenuBar(true);
        
        MenuItem mntmVisualize = new MenuItem("Visualize", false, menuBar_1);
        
        MenuItem mntmMapIt = new MenuItem("Map It!", false, new Command() {
        	public void execute() {
    			String url = frameVisualize.getUrl();
    			if(!url.equalsIgnoreCase("http://ricestat.irri.org/vis/wrsMap_main.html")){
    				frameVisualize.setUrl("http://ricestat.irri.org/vis/wrsMap_main.html");
    			}        			
        		ContentPanel.showWidget(2);
        		History.newItem("MapIt");
        	}
        });
        menuBar_1.addItem(mntmMapIt);
        
        MenuItem mntmTrendIt = new MenuItem("Trend It!", false, new Command() {
        	public void execute() {
        		String url = frameVisualize.getUrl();
    			if(!url.equalsIgnoreCase("http://ricestat.irri.org/vis/wrs_Motion.php")){
    				frameVisualize.setUrl("http://ricestat.irri.org/vis/wrs_Motion.php");
    			}        			
        		ContentPanel.showWidget(2);
        		History.newItem("TrendIt");
        	}
        });
        menuBar_1.addItem(mntmTrendIt);
        
        MenuItem mntmd = new MenuItem("3D", false, new Command() {
        	public void execute() {
        		String url = frameVisualize.getUrl();
    			if(!url.equalsIgnoreCase("http://ricestat.irri.org/vis/wrs3D_main.html")){
    				frameVisualize.setUrl("http://ricestat.irri.org/vis/wrs3D_main.html");
    			}        			
        		ContentPanel.showWidget(2);
        		History.newItem("3D");
        	}
        });
        menuBar_1.addItem(mntmd);
        menuBar.addItem(mntmVisualize);
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
	
	public void selectionSummary(){
		String vars = filterPanel.varDefinition(filterPanel.lbxVariable, "@");
		String regs = filterPanel.varDefinition(filterPanel.lbxRegion, "@" );
		String yrs = filterPanel.varDefinition(filterPanel.lbxYear,",");
		mcpResults.setSelSummary(regs + "_" + vars + "_" + yrs );
		if (mcpResults.getVpSelectionSummary().getWidgetCount()>0) mcpResults.getVpSelectionSummary().clear();
		mcpResults.getVpSelectionSummary().add(new HTML("<h3>Regions/Countries/Provinces</h3>"));
		mcpResults.getVpSelectionSummary().add(filterPanel.varDefinitionGrid(filterPanel.lbxRegion));
		mcpResults.getVpSelectionSummary().add(new HTML("<h3>Variables</h3>"));
		mcpResults.getVpSelectionSummary().add(filterPanel.varDefinitionGrid(filterPanel.lbxVariable));
		mcpResults.getVpSelectionSummary().add(new HTML("<h3>Years</h3><p>" + filterPanel.varDefinition(filterPanel.lbxYear, ", ")+"</p>"));
	}
}
