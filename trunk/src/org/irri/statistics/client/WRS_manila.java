package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    DockLayoutPanel dlpWRSHome = new DockLayoutPanel(Unit.EM);
    	RPFiltering filterPanel = new RPFiltering();

    /** Creates a new instance of worldriceEntryPoint */
    public WRS_manila() {

    }


    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        RootPanel rootPanel = RootPanel.get("container");
        rootPanel.setSize("100%", "100%");
        dlpWRSHome.setStyleName("wrapper");
        rootPanel.add(dlpWRSHome, 10, 10);
        
        Image imgBanner = new Image("imgs/top_logo.png");
        dlpWRSHome.addNorth(imgBanner, 8.2);
        dlpWRSHome.setStyleName("main");
        dlpWRSHome.setSize("832px", "624px");
        
        TabLayoutPanel TLPMain = new TabLayoutPanel(2.5, Unit.EM);
        
        DockPanel dpSelectorWrapper = new DockPanel();
        TLPMain.add(dpSelectorWrapper, "Select", false);
        dpSelectorWrapper.setWidth("713px");
        dpSelectorWrapper.add(filterPanel, DockPanel.CENTER);
        filterPanel.setSize("400px", "441px");
        
        HTML htmlhowToUse = new HTML("<h3>How to Use this Facility</h3> <p>The procedure in retrieving data from this facility is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol> <li>Select the level of geographical extent (i.e. continental, national, or subnational)</li> <li>Select region/country/organization of interest.</li> <li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        dpSelectorWrapper.add(htmlhowToUse, DockPanel.WEST);
        htmlhowToUse.setWidth("282px");
        filterPanel.initListBoxes();
        dlpWRSHome.add(TLPMain);
        
        FlexTable flexTable = new FlexTable();
        TLPMain.add(flexTable, "Data", false);
        TLPMain.setSize("100%", "100%");
    }
}
