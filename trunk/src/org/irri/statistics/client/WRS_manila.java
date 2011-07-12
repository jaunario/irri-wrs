package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Hidden;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    DockPanel dpWRSHome = new DockPanel();
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
        rootPanel.setStyleName("root");
        rootPanel.setSize("100%", "100%");
        dpWRSHome.getElement().getStyle().setPosition(Position.RELATIVE);
        dpWRSHome.setStyleName("wrapper");
        rootPanel.add(dpWRSHome, -10, -10);
        dpWRSHome.setSize("1044px", "768px");
        
        TabLayoutPanel TLPMain = new TabLayoutPanel(3.0, Unit.EM);
        TLPMain.setAnimationVertical(true);
                
        DockPanel dpSelectorWrapper = new DockPanel();
        TLPMain.add(dpSelectorWrapper, "Select", false);
        dpSelectorWrapper.setSize("100%", "100%");
        dpSelectorWrapper.add(filterPanel, DockPanel.CENTER);
        filterPanel.setSize("400px", "438px");
        
        HTML htmlhowToUse = new HTML("<h3>How to Use this Facility</h3> <p>The procedure in retrieving data from this facility is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol> <li>Select the level of geographical extent (i.e. continental, national, or subnational)</li> <li>Select region/country/organization of interest.</li> <li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        dpSelectorWrapper.add(htmlhowToUse, DockPanel.WEST);
        htmlhowToUse.setSize("282px", "279px");
        filterPanel.initListBoxes();
        dpWRSHome.add(TLPMain, DockPanel.CENTER);
        dpWRSHome.setCellHeight(TLPMain, "80%");
        dpWRSHome.setCellWidth(TLPMain, "80%");
        TLPMain.setSize("100%", "100%");
        
        CellTable<Object> cellTable = new CellTable<Object>();
        TLPMain.add(cellTable, "Results", false);
        TLPMain.selectTab(0);
        cellTable.setSize("5cm", "3cm");
        
        AbsolutePanel flowPanel = new AbsolutePanel();
        dpWRSHome.add(flowPanel, DockPanel.NORTH);
        flowPanel.setHeight("136px");
        dpWRSHome.setCellHeight(flowPanel, "20%");
        dpWRSHome.setCellWidth(flowPanel, "80%");
        
        Image image = new Image("imgs/top_left_image.png");
        flowPanel.add(image, 0, 0);
        image.setSize("29px", "112px");
        
        Image imgLogo = new Image("imgs/top_logo.png");
        flowPanel.add(imgLogo, 28, 0);
        imgLogo.setSize("323px", "85px");
        
        Image imgBannerBG = new Image("imgs/top-header.png");
        flowPanel.add(imgBannerBG, 351, 0);
        imgBannerBG.setSize("693px", "85px");
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        dpWRSHome.add(horizontalPanel, DockPanel.SOUTH);
        dpWRSHome.setCellHeight(horizontalPanel, "20%");
        dpWRSHome.setCellWidth(horizontalPanel, "80%");
        
        Label lblStatusGoesHere = new Label("Status Goes Here");
        lblStatusGoesHere.setStyleName("status");
        horizontalPanel.add(lblStatusGoesHere);
        
        VerticalPanel simplePanel = new VerticalPanel();
        dpWRSHome.add(simplePanel, DockPanel.WEST);
        
        VerticalPanel simplePanel_1 = new VerticalPanel();
        dpWRSHome.add(simplePanel_1, DockPanel.EAST);
    }
}
