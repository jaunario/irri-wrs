package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;
import org.irri.statistics.client.ui.WRSResultTable;

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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    DockLayoutPanel MainWrapper = new DockLayoutPanel(Unit.PX);
    		RPFiltering filterPanel = new RPFiltering();
    		WRSResultTable myresult;
    		private DeckPanel ContentPanel;
            
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
        
        Label lblStatusGoesHere = new Label("Status Goes Here");
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
        
        DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
        ContentPanel.add(dockPanel);
        
        ScrollPanel scrollPanel_1 = new ScrollPanel();
        dockPanel.addEast(scrollPanel_1, 300.0);
        VerticalPanel vpText = new VerticalPanel();
        scrollPanel_1.setWidget(vpText);
        vpText.setSize("95%", "100%");
        
        HTML htmlhowToUse = new HTML("<h3>How to Use this Facility</h3> <p>The procedure in retrieving data from this facility is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol> <li>Select the level of geographical extent (i.e. continental, national, or subnational)</li> <li>Select region/country/organization of interest.</li> <li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        vpText.add(htmlhowToUse);
        htmlhowToUse.setSize("90%", "90%");
        
        HTML htmlNewHtml = new HTML("<h3>Hint:</h3>\r\n<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        vpText.add(htmlNewHtml);
        htmlNewHtml.setSize("90%", "90%");
        
        HTMLPanel htmlDisclaimer = new HTMLPanel("<h3>Disclaimer </h3>\r\nData and information released from the International Rice Research Institute (IRRI) are provided on an \"AS IS\" basis, without warranty of any kind, including without limitation the warranties of merchantability, fitness for a particular purpose and non-infringement. Availability of this data and information does not constitute scientific publication. Data and/or information may contain errors or be incomplete.");
        vpText.add(htmlDisclaimer);
        htmlDisclaimer.setStyleName("notes");
        htmlDisclaimer.setSize("90%", "100%");
        
        ScrollPanel scrollPanel = new ScrollPanel();
        dockPanel.add(scrollPanel);
        
        VerticalPanel vpFilters = new VerticalPanel();
        vpFilters.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        scrollPanel.setWidget(vpFilters);
        vpFilters.setSpacing(5);
        vpFilters.setSize("100%", "100%");
        vpFilters.add(filterPanel);
        filterPanel.initListBoxes();
        
        myresult = new WRSResultTable("SELECT iso3, yr, SUM(IF(var_code='RicPr-USDA', val, null)) 'RicPr-USDA' FROM front_data WHERE yr=2010 GROUP BY 1,2 ORDER BY 3 DESC LIMIT 10;");
        ContentPanel.add(myresult);

        filterPanel.setSubmitButtonClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				myresult.sqlPopulateTable(filterPanel.sqlFromItems());				
				//dlpWRSHome.				
			}
		});
        vpFilters.setCellHeight(filterPanel, "80%");
        vpFilters.setCellHorizontalAlignment(filterPanel, HasHorizontalAlignment.ALIGN_CENTER);
        
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

        HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
        vpFilters.add(horizontalPanel_1);
        horizontalPanel_1.setSize("100%", "102px");
        
        Image image = new Image("images/generichart.gif");
        horizontalPanel_1.add(image);
        image.setSize("140px", "100px");
        
        Image image_1 = new Image("images/generichart.gif");
        horizontalPanel_1.add(image_1);
        image_1.setSize("124px", "100px");
        
        Image image_2 = new Image("images/generichart.gif");
        horizontalPanel_1.add(image_2);
        image_2.setSize("129px", "100px");
        
        HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
        vpFilters.add(horizontalPanel_2);
        horizontalPanel_2.setSize("100%", "25%");
        
        Image image_3 = new Image("images/generichart.gif");
        horizontalPanel_2.add(image_3);
        image_3.setSize("140px", "100px");
        
        Image image_4 = new Image("images/generichart.gif");
        horizontalPanel_2.add(image_4);
        image_4.setSize("124px", "100px");
        
        Image image_5 = new Image("images/generichart.gif");
        horizontalPanel_2.add(image_5);
        image_5.setSize("129px", "100px");
        ContentPanel.showWidget(0);
    }
	public DeckPanel getDeckPanel() {
		return ContentPanel;
	}
}
