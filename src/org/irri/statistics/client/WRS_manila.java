package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;
import org.irri.statistics.client.ui.WRSResultTable;
//import org.irri.statistics.client.ui.charts.BarChartPanel;
//import org.irri.statistics.client.ui.charts.PieChartPanel;
import org.irri.statistics.client.ui.charts.VizTablePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    DockLayoutPanel MainWrapper = new DockLayoutPanel(Unit.PX);
    		RPFiltering filterPanel = new RPFiltering();
    		WRSResultTable myresult;
    		private DeckPanel ContentPanel;
    		Label lblStatusGoesHere = new Label("Status Goes Here");
    		private VerticalPanel ChartWrapper;
            
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
        
        SplitLayoutPanel dockPanel = new SplitLayoutPanel();
        ContentPanel.add(dockPanel);
        dockPanel.setSize("100%", "100%");
        
        ScrollPanel scrollPanel_1 = new ScrollPanel();
        dockPanel.addEast(scrollPanel_1, 250.0);
        
                ChartWrapper = new VerticalPanel();
                scrollPanel_1.setWidget(ChartWrapper);
                ChartWrapper.setSize("100%", "100%");
                
                CaptionPanel cptnpnlTopProducers = new CaptionPanel("Top Producers");
                ChartWrapper.add(cptnpnlTopProducers);
                cptnpnlTopProducers.setSize("80%", "85%");
                
                VizTablePanel topprod = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicPr-USDA', val, null)) AS 'Production' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010");
                cptnpnlTopProducers.add(topprod);
                
                CaptionPanel cptnpnlBigRiceAreas = new CaptionPanel("Biggest Rice Areas");
                ChartWrapper.add(cptnpnlBigRiceAreas);
                cptnpnlBigRiceAreas.setSize("80%", "85%");
                
                VizTablePanel bigarea = new VizTablePanel("SELECT c.NAME_ENGLISH AS 'country', SUM(IF(s.var_code='RicHa-USDA', val, null)) AS 'Harvested Area' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE yr = YEAR(CURDATE())-1  GROUP BY s.iso3 ASC, s.yr ORDER BY 2 DESC LIMIT 10 ", "2010");
                cptnpnlBigRiceAreas.add(bigarea);
        
        myresult = new WRSResultTable();
        ContentPanel.add(myresult);
        myresult.setSize("100%", "100%");
        
        PushButton pshbtnSelect = new PushButton("Select");
        pshbtnSelect.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		ContentPanel.showWidget(0);
        	}
        });
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
                                
        VerticalPanel vpText = new VerticalPanel();
        dockPanel.addWest(vpText, 250.0);
                                
        CaptionPanel cptnpnlHowToUse = new CaptionPanel("How to use this facility");
        vpText.add(cptnpnlHowToUse);
        cptnpnlHowToUse.setSize("90%", "100%");
                                
        HTML htmlhowToUse = new HTML("<p>The procedure in retrieving data is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol><li>Select the level of geographical extent (i.e. continental, national, or subnational)</li><li>Select region/country/organization of interest.</li><li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        cptnpnlHowToUse.setContentWidget(htmlhowToUse);
        htmlhowToUse.setSize("100%", "94%");
                                
        DisclosurePanel disclosurePanel = new DisclosurePanel("Hint");
        disclosurePanel.setOpen(true);
        vpText.add(disclosurePanel);
        disclosurePanel.setSize("100%", "100%");
                                
        HTML htmlNewHtml = new HTML("<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        disclosurePanel.setContent(htmlNewHtml);
        htmlNewHtml.setSize("90%", "100%");
                                
        ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setTouchScrollingDisabled(false);
        dockPanel.add(scrollPanel);
        scrollPanel.setWidget(filterPanel);
        filterPanel.initListBoxes();
        filterPanel.setSubmitButtonClickHandler(new ClickHandler() {
        	@Override
            public void onClick(ClickEvent event) {
        		String sql = filterPanel.sqlFromItems();
                if (!sql.equalsIgnoreCase("")) {					
                	myresult.sqlPopulateTable(sql);
                    ContentPanel.showWidget(1);
                } else lblStatusGoesHere.setText("Please select a year.");
            }
        });
        
        ContentPanel.showWidget(0);
        
    }
    
	public DeckPanel getDeckPanel() {
		return ContentPanel;
	}
	public VerticalPanel getHorizontalPanel_1() {
		return ChartWrapper;
	}
}
