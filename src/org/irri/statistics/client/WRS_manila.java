package org.irri.statistics.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WRS_manila implements EntryPoint {
    VerticalPanel VpSiteBinder = new VerticalPanel();
        VerticalPanel MainPanel = new VerticalPanel();
            DataViewer RDVData = new DataViewer();

    /** Creates a new instance of worldriceEntryPoint */
    public WRS_manila() {

        MainPanel.setSize("100%", "100%");
        MainPanel.add(RDVData);
        VpSiteBinder.add(MainPanel);
        VpSiteBinder.setStyleName("main");
        VpSiteBinder.setSize("640px", "480px");

    }


    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        RootPanel.get().add(VpSiteBinder);

    }
}
