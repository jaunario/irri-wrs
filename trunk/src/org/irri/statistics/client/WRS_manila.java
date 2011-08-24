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
    	private int activepage = 0;
    	private Frame frame;
    	private HorizontalPanel hpResultTable;

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
        NavigationBar.setSpacing(2);
        MainWrapper.addNorth(NavigationBar, 40.0);
        NavigationBar.setSize("100%", "100%");
        
        Label lblWorldRiceStatistics = new Label("World Rice Statistics");
        lblWorldRiceStatistics.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lblWorldRiceStatistics.setStyleName("gwt-Label-title");
        NavigationBar.add(lblWorldRiceStatistics);
        NavigationBar.setCellHorizontalAlignment(lblWorldRiceStatistics, HasHorizontalAlignment.ALIGN_RIGHT);
        lblWorldRiceStatistics.setHeight("100%");
        
        DockLayoutPanel dockPanel_2 = new DockLayoutPanel(Unit.PX);
        MainWrapper.add(dockPanel_2);
        dockPanel_2.setSize("100%", "100%");
        
        StackPanel decoratedStackPanel = new StackPanel();
        dockPanel_2.addWest(decoratedStackPanel, 250.0);
        decoratedStackPanel.setSize("100%", "100%");
        
        VerticalPanel vpOnlineQuery = new VerticalPanel();
        decoratedStackPanel.add(vpOnlineQuery, "Online Query", false);
        vpOnlineQuery.setSize("100%", "");
        
        ToggleButton tglbtnSelection = new ToggleButton("SELECT");
        tglbtnSelection.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		if (activepage!=0) ContentPanel.showWidget(0);
        	}
        });
        vpOnlineQuery.add(tglbtnSelection);
        tglbtnSelection.setSize("95%", "25");
        
        ToggleButton tglbtnViewResults = new ToggleButton("RESULTS");
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
//        hplnk3D.addAttachHandler(new Handler() {
//        	public void onAttachOrDetach(AttachEvent event) {
//        		frame.setUrl("http://geo.irri.org/vis/wrs_3D.php");
//        		ContentPanel.showWidget(2);
//        	}
//        });
        vpVisualization.add(hplnk3D);
        hplnk3D.setSize("185", "25");
        
        ContentPanel = new DeckPanel();
        ContentPanel.setAnimationEnabled(true);
        dockPanel_2.add(ContentPanel);
        ContentPanel.setSize("100%", "100%");
        
        DockLayoutPanel dockPanel_3 = new DockLayoutPanel(Unit.PCT);
        ContentPanel.add(dockPanel_3);
        dockPanel_3.setSize("100%", "100%");
        
        HorizontalPanel vpGenCharts = new HorizontalPanel();
        vpGenCharts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        dockPanel_3.addSouth(vpGenCharts, 35.0);
        vpGenCharts.setSize("100%", "100%");
        
        
        CaptionPanel cptnpnlTopProducers = new CaptionPanel("Top Producers");
        vpGenCharts.add(cptnpnlTopProducers);
        cptnpnlTopProducers.setSize("50%", "100%");
        
        VizTablePanel topprod = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicPr-USDA', val, null)) AS 'Production' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010", NumberUtils.createIntSeries(1, 1, 1));
        cptnpnlTopProducers.add(topprod);
        topprod.setSize("100%", "90%");
        
        CaptionPanel cptnpnlBigRiceAreas = new CaptionPanel("Biggest Rice Areas");
        vpGenCharts.add(cptnpnlBigRiceAreas);
        cptnpnlBigRiceAreas.setSize("50%", "100%");
        
        VizTablePanel bigarea = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicHa-USDA', val, null)) AS 'Harvested Area' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010", NumberUtils.createIntSeries(1, 1, 1));
        cptnpnlBigRiceAreas.add(bigarea);
        bigarea.setSize("100%", "90%");
        
        ScrollPanel scrollPanel = new ScrollPanel();
        dockPanel_3.add(scrollPanel);
        scrollPanel.setSize("100%", "100%");
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
        
        DockLayoutPanel dockPanel_1 = new DockLayoutPanel(Unit.PCT);
        ContentPanel.add(dockPanel_1);
        
        HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
        horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel_1.setSpacing(5);
        horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        dockPanel_1.addNorth(horizontalPanel_1, 5.0);
        
        
        Button btnDownload = new Button("Download");
        btnDownload.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		
        	}
        });
        
        Button btnSendEmail = new Button("Send Email");
        horizontalPanel_1.add(btnSendEmail);
        horizontalPanel_1.setCellHorizontalAlignment(btnSendEmail, HasHorizontalAlignment.ALIGN_RIGHT);
        
        Button btnSendToGoogle = new Button("Send to Google Docs");
        horizontalPanel_1.add(btnSendToGoogle);
        horizontalPanel_1.setCellHorizontalAlignment(btnSendToGoogle, HasHorizontalAlignment.ALIGN_RIGHT);
        horizontalPanel_1.add(btnDownload);
        horizontalPanel_1.setCellHorizontalAlignment(btnDownload, HasHorizontalAlignment.ALIGN_RIGHT);
        
        HorizontalPanel vpResultCharts = new HorizontalPanel();
        dockPanel_1.addSouth(vpResultCharts, 30.0);
        vpResultCharts.setSize("100%", "100%");
        
        cptnpnlResultCharts = new CaptionPanel("Visualize");
        vpResultCharts.add(cptnpnlResultCharts);
        cptnpnlResultCharts.setSize("50%", "100%");
        
        
        CaptionPanel cptnpnlSource = new CaptionPanel("Source");
        vpResultCharts.add(cptnpnlSource);
        cptnpnlSource.setSize("50%", "100%");
        
        FlexTable flexTable = new FlexTable();
        cptnpnlSource.setContentWidget(flexTable);
        flexTable.setSize("5cm", "3cm");
        
        hpResultTable = new HorizontalPanel();
        dockPanel_1.add(hpResultTable);
        hpResultTable.add(myresult);
        myresult.setSize("100%", "100%");
        
        frame = new Frame("http://geo.irri.org/vis/wrs_Motion.php");
        ContentPanel.add(frame);
        
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
		int[] numcols = NumberUtils.createIntSeries(1, filterPanel.selectedItemsCount(), 1);
	    DBLineChart linechart2 = new DBLineChart(ChartDataTable.regroup(resultmatrix, 2), DBLineChart.createOptions());
	    VizTablePanel viztab = new VizTablePanel(resultmatrix, numcols);
	    hpResultTable.add(viztab);	    
	    if (cptnpnlResultCharts.getContentWidget()!=null) cptnpnlResultCharts.clear();
	    cptnpnlResultCharts.add(linechart2);    			    
    }
	public Frame getFrame() {
		return frame;
	}
	public HorizontalPanel getHorizontalPanel() {
		return hpResultTable;
	}
}
