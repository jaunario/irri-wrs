package org.irri.statistics.client.ui;

import org.irri.statistics.client.UtilsRPC;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;

public class RPFiltering extends Composite {
	/*
	 * Region-priority Filtering
	 * 
	 * Author : Jorrel Khalil S. Aunario
	 */
		ListBox lbxExtent = new ListBox(false);
		ListBox lbxRegion = new ListBox(true);
		ListBox lbxVarGroup = new ListBox(true);
		ListBox lbxVariable = new ListBox(true);
		ListBox lbxYear = new ListBox(true);
		Button btnSubmit = new Button("Get Data");
		
	    private String selectedCountries = "";    
	    private String srctable = "reg_data";
	    private String varlisttable = "variables";
	    
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
		
        final AsyncCallback<String[][]> InitVarGroupBox = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] result) {
                lbxVarGroup.clear();
                try{
                    for (int i = 1;i<result.length;i++){
                    	lbxVarGroup.addItem(result[i][0],result[i][1]);
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
		
		public RPFiltering() {
			//super();
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

					if (lbxExtent.getSelectedIndex()==0){

						srctable = "reg_data";
		            	varlisttable = "variables";
		            	sql =  "SELECT region, iso3 FROM region_list ORDER BY region";
		            } else if (lbxExtent.getSelectedIndex()==1){
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
			absolutePanel.add(horizontalPanel, 204, 393);
			horizontalPanel.setSize("190px", "35px");
			
			Button btnClear = new Button("Clear Selection");
			btnClear.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					initListBoxes();				}
			});
			horizontalPanel.add(btnClear);
			btnClear.setHeight("30px");
			
			horizontalPanel.add(btnSubmit);
			//initListBoxes();
		}
		
		public void setSubmitButtonClickHandler(ClickHandler click){
			btnSubmit.addClickHandler(click);
		}
		
		public void initListBoxes(){
			lbxYear.clear();
			lbxVariable.clear();
			UtilsRPC.getService("mysqlservice").RunSELECT("SELECT region, iso3 FROM region_list ORDER BY region",InitRegionBox);
			UtilsRPC.getService("mysqlservice").RunSELECT("SELECT group_name, group_code FROM wrs_groups ORDER BY group_code",InitVarGroupBox);			
		}
	}
