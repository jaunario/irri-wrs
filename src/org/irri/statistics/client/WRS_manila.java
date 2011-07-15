package org.irri.statistics.client;

import org.irri.statistics.client.ui.RPFiltering;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HTMLPanel;

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
        rootPanel.setStyleName("wrapper");
        rootPanel.setSize("100%", "100%");
        dpWRSHome.setStyleName("wrapper");
        rootPanel.add(dpWRSHome, 0, 0);
        dpWRSHome.setSize("800px", "800px");
        dpWRSHome.getElement().getStyle().setPosition(Position.RELATIVE);
        
        TabLayoutPanel TLPMain = new TabLayoutPanel(2.5, Unit.EM);
        TLPMain.setAnimationVertical(true);
                
        DockPanel dpSelectorWrapper = new DockPanel();
        TLPMain.add(dpSelectorWrapper, "Select", false);
        dpSelectorWrapper.setSize("100%", "100%");
        
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setSpacing(5);
        dpSelectorWrapper.add(verticalPanel, DockPanel.WEST);
        verticalPanel.setSize("100%", "100%");
        dpSelectorWrapper.setCellHeight(verticalPanel, "100%");
        dpSelectorWrapper.setCellWidth(verticalPanel, "50%");
        
        HTML htmlhowToUse = new HTML("<h3>How to Use this Facility</h3> <p>The procedure in retrieving data from this facility is sequential to minimize empty result sets. Please follow the steps enumerated below.</p><ol> <li>Select the level of geographical extent (i.e. continental, national, or subnational)</li> <li>Select region/country/organization of interest.</li> <li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> <li>Select year(s).</li><li>Click <b><i>Get Data</i>.</b></li></ol>", true);
        verticalPanel.add(htmlhowToUse);
        htmlhowToUse.setSize("325px", "209px");
        
        HTML htmlNewHtml = new HTML("<h3>Hint:</h3>\r\n<ul><li>After clicking on an item, wait for the list boxes to be populated.</li><li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li><li>If you have multiple selections, deselect an item by just clicking on an item again.</li><li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li></ul>", true);
        verticalPanel.add(htmlNewHtml);
        htmlNewHtml.setSize("325px", "176px");
        
        VerticalPanel verticalPanel_1 = new VerticalPanel();
        verticalPanel_1.setSpacing(5);
        dpSelectorWrapper.add(verticalPanel_1, DockPanel.CENTER);
        dpSelectorWrapper.setCellHorizontalAlignment(verticalPanel_1, HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel_1.setSize("100%", "100%");
        verticalPanel_1.add(filterPanel);
        verticalPanel_1.setCellHorizontalAlignment(filterPanel, HasHorizontalAlignment.ALIGN_CENTER);
        filterPanel.setSize("100%", "100%");
        
        HTMLPanel panel = new HTMLPanel("<h4>Disclaimer </h4>\r\nData and information released from the International Rice Research Institute (IRRI) are provided on an \"AS IS\" basis, without warranty of any kind, including without limitation the warranties of merchantability, fitness for a particular purpose and non-infringement. Availability of this data and information does not constitute scientific publication. Data and/or information may contain errors or be incomplete.");
        panel.setStyleName("notes");
        verticalPanel_1.add(panel);
        filterPanel.initListBoxes();
        dpWRSHome.add(TLPMain, DockPanel.CENTER);
        dpWRSHome.setCellHeight(TLPMain, "100%");
        dpWRSHome.setCellWidth(TLPMain, "80%");
        TLPMain.setSize("100%", "100%");
        
        CellTable<Object> cellTable = new CellTable<Object>();
        TLPMain.add(cellTable, "Results", false);
        TLPMain.selectTab(0);
        cellTable.setSize("5cm", "3cm");
        
        VerticalPanel flowPanel = new VerticalPanel();
        flowPanel.setSpacing(5);
        dpWRSHome.add(flowPanel, DockPanel.NORTH);
        flowPanel.setSize("100%", "100%");
        dpWRSHome.setCellHeight(flowPanel, "5%");
        dpWRSHome.setCellWidth(flowPanel, "80%");
        
        Label lblIrri = new Label("IRRI");
        lblIrri.setStyleName("gwt-Label-logo");
        flowPanel.add(lblIrri);
        lblIrri.setSize("150px", "56px");
        
        Label lblInternationalRiceResearch = new Label("International Rice Research Institute");
        lblInternationalRiceResearch.setStyleName("gwt-Label-fullname");
        flowPanel.add(lblInternationalRiceResearch);
        lblInternationalRiceResearch.setWidth("297px");
        
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(5);
        dpWRSHome.add(horizontalPanel, DockPanel.SOUTH);
        horizontalPanel.setSize("100%", "5%");
        dpWRSHome.setCellHeight(horizontalPanel, "20%");
        dpWRSHome.setCellWidth(horizontalPanel, "80%");
        
        Label lblStatusGoesHere = new Label("Status Goes Here");
        lblStatusGoesHere.setStyleName("status");
        horizontalPanel.add(lblStatusGoesHere);
        horizontalPanel.setCellWidth(lblStatusGoesHere, "100%");
        lblStatusGoesHere.setSize("432px", "5%");
    }
}
