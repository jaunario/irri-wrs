package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;
import org.irri.statistics.client.ui.WRSResultTable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    DockLayoutPanel dlpWRSHome = new DockLayoutPanel(Unit.PX);
    	TabLayoutPanel TLPMain = new TabLayoutPanel(30.0, Unit.PX);    
    		RPFiltering filterPanel = new RPFiltering();
    		DockPanel dpResult = new DockPanel();
            
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
        dlpWRSHome.setStyleName("wrapper");
        dlpWRSHome.setSize("80%", "100%");
        dlpWRSHome.getElement().getStyle().setPosition(Position.RELATIVE);
        
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.setSize("100%", "100%");
        rootLayoutPanel.setStyleName("wrapper");
        rootLayoutPanel.add(dlpWRSHome);
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        dlpWRSHome.addSouth(horizontalPanel,25.8);
        horizontalPanel.setSize("100%", "100%");
        
        Label lblStatusGoesHere = new Label("Status Goes Here");
        horizontalPanel.add(lblStatusGoesHere);
        horizontalPanel.setCellVerticalAlignment(lblStatusGoesHere, HasVerticalAlignment.ALIGN_BOTTOM);
        lblStatusGoesHere.setStyleName("status");
        horizontalPanel.setCellHeight(lblStatusGoesHere, "100%");
        horizontalPanel.setCellWidth(lblStatusGoesHere, "100%");
        lblStatusGoesHere.setSize("100%", "14px");
        
        VerticalPanel flowPanel = new VerticalPanel();
        dlpWRSHome.addNorth(flowPanel, 80.0);
        flowPanel.setSize("100%", "100%");
        
        Label lblIrri = new Label("IRRI");
        lblIrri.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        lblIrri.setDirectionEstimator(true);
        lblIrri.setStyleName("gwt-Label-logo");
        flowPanel.add(lblIrri);
        flowPanel.setCellVerticalAlignment(lblIrri, HasVerticalAlignment.ALIGN_BOTTOM);
        flowPanel.setCellHeight(lblIrri, "10%");
        flowPanel.setCellWidth(lblIrri, "100%");
        lblIrri.setSize("100%", "54px");
        
        Label lblInternationalRiceResearch = new Label("International Rice Research Institute");
        lblInternationalRiceResearch.setStyleName("gwt-Label-fullname");
        flowPanel.add(lblInternationalRiceResearch);
        lblInternationalRiceResearch.setSize("100%", "80%");
        
        TLPMain.setAnimationVertical(true);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        TLPMain.add(scrollPanel, "Select", false);
        
        DockPanel dpSelectorWrapper = new DockPanel();
        scrollPanel.setWidget(dpSelectorWrapper);
        dpSelectorWrapper.setSize("100%", "100%");
        VerticalPanel vpText = new VerticalPanel();
        vpText.setSpacing(5);
        dpSelectorWrapper.add(vpText, DockPanel.WEST);
        vpText.setSize("100%", "100%");
        dpSelectorWrapper.setCellHeight(vpText, "100%");
        dpSelectorWrapper.setCellWidth(vpText, "40%");
        
        HTML htmlhowToUse = new HTML("<h3>How to Use this Facility</h3> <p>The procedure in retrieving data from this facility is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol> <li>Select the level of geographical extent (i.e. continental, national, or subnational)</li> <li>Select region/country/organization of interest.</li> <li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        vpText.add(htmlhowToUse);
        htmlhowToUse.setSize("95%", "209px");
        
        HTML htmlNewHtml = new HTML("<h3>Hint:</h3>\r\n<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        vpText.add(htmlNewHtml);
        htmlNewHtml.setSize("95%", "176px");
        
        VerticalPanel vpFilters = new VerticalPanel();
        vpFilters.setSpacing(5);
        dpSelectorWrapper.add(vpFilters, DockPanel.CENTER);
        dpSelectorWrapper.setCellHorizontalAlignment(vpFilters, HasHorizontalAlignment.ALIGN_CENTER);
        vpFilters.setSize("100%", "100%");
        vpFilters.add(filterPanel);
        vpFilters.setCellHeight(filterPanel, "80%");
        vpFilters.setCellHorizontalAlignment(filterPanel, HasHorizontalAlignment.ALIGN_CENTER);
        filterPanel.setSize("400px", "425px");
        
        HTMLPanel htmlDisclaimer = new HTMLPanel("<h3>Disclaimer </h3>\r\nData and information released from the International Rice Research Institute (IRRI) are provided on an \"AS IS\" basis, without warranty of any kind, including without limitation the warranties of merchantability, fitness for a particular purpose and non-infringement. Availability of this data and information does not constitute scientific publication. Data and/or information may contain errors or be incomplete.");
        vpFilters.add(htmlDisclaimer);
        htmlDisclaimer.setStyleName("notes");
        htmlDisclaimer.setSize("93%", "100%");
        
        TLPMain.add(dpResult, "Data", false);
        dpResult.setSize("100%", "100%");
        dpResult.setVisible(false);
        filterPanel.initListBoxes();
        dlpWRSHome.add(TLPMain);
        TLPMain.setSize("100%", "100%");
        
        filterPanel.setSubmitButtonClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				WRSResultTable mydata = new WRSResultTable(	filterPanel.sqlFromItems());
				dpResult.add(mydata,DockPanel.CENTER);
				dpResult.setVisible(true);
				//dlpWRSHome.
				
			}
		});
    }
}
