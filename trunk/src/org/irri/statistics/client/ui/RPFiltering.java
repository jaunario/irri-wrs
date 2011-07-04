package org.irri.statistics.client.ui;

import org.irri.statistics.client.UtilsRPC;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;

public class RPFiltering extends Composite {
/*
 * Region-priority Filtering
 * 
 * Author : Jorrel Khalil S. Aunario
 */
	ListBox lbxExtent = new ListBox();
	ListBox lbxRegion = new ListBox();
	ListBox lbxVarGroup = new ListBox();
	ListBox lbxVariable = new ListBox();
	ListBox lbxYear = new ListBox();
	
    private String selectedCountries = "";    
    private String srctable = "reg_data";
    private String varlisttable = "variables";

	public RPFiltering() {
		
		final AsyncCallback<String[][]> InitRegionBox = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] result) {
                lbxRegion.clear();
                try{
                    for (int i = 1;i<result.length;i++){
                    	lbxRegion.addItem(result[i][0],result[i][1]);
                    }
                }
                catch(Exception e){
                    System.err.println(e);
                }
            }
            
            public void onFailure(Throwable caught) {
                 System.out.println("Communication failed (RDV.InitRegionBox)");
            }
        };
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("401px", "438px");
		
		Label lblRegion = new Label("Regions");
		absolutePanel.add(lblRegion, 8, 43);
		
		Label lblVarGroup = new Label("Variable Groups");
		absolutePanel.add(lblVarGroup, 204, 43);
		lbxExtent.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				 String sql = "";
	             lbxYear.clear();
	                lbxVariable.clear();
	                lbxVarGroup.clear();
	                
	                if (lbxExtent.getSelectedIndex()==0){
	                    srctable = "reg_data";
	                    varlisttable = "variables";
	                    sql =  "SELECT region, iso3 FROM region_list ORDER BY region";
	                }
	                else if (lbxExtent.getSelectedIndex()==1){
	                    srctable = "front_data";
	                    varlisttable = "variables";
	                    sql = "SELECT country, iso3 FROM country_list ORDER BY country";

	                } else{
	                    srctable = "pays";
	                    varlisttable = "svar_list";
	                    sql = "SELECT country, iso3 FROM sncountry_list ORDER BY country";
	                }
	                UtilsRPC.getService("mysqlservice").RunSELECT(sql,InitRegionBox);
	                //else MainPanel.add(new HTML("Under Construction"));
			}
		});
		
		lbxExtent.addItem("Continent/Organization");
		lbxExtent.addItem("Country");
		lbxExtent.addItem("State/Province");
		absolutePanel.add(lbxExtent, 134, 10);
		lbxExtent.setSize("260px", "22px");
		
		lbxRegion.setVisibleItemCount(5);
		absolutePanel.add(lbxRegion, 8, 65);
		lbxRegion.setSize("190px", "100px");
		
		lbxVarGroup.setVisibleItemCount(5);
		absolutePanel.add(lbxVarGroup, 204, 65);
		lbxVarGroup.setSize("190px", "100px");
		
		absolutePanel.add(lbxVariable, 8, 192);
		lbxVariable.setSize("304px", "195px");
		lbxVariable.setVisibleItemCount(10);
		
		absolutePanel.add(lbxYear, 317, 192);
		lbxYear.setSize("77px", "195px");
		lbxYear.setVisibleItemCount(10);
		
		Label lblVariables = new Label("Variables");
		absolutePanel.add(lblVariables, 10, 171);
		
		Label lblYears = new Label("Years");
		absolutePanel.add(lblYears, 318, 171);
		
		Label lblGeographicExtent = new Label("Geographic Extent");
		absolutePanel.add(lblGeographicExtent, 8, 10);
		lblGeographicExtent.setSize("120px", "18px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(5);
		absolutePanel.add(horizontalPanel, 264, 393);
		horizontalPanel.setSize("130px", "36px");
		
		Button btnClear = new Button("Clear");
		horizontalPanel.add(btnClear);
		btnClear.setHeight("30px");
		
		Button btnSubmit = new Button("Get Data");
		horizontalPanel.add(btnSubmit);
		
	}
}
