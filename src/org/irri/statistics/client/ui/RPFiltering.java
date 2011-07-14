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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

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
		
		String selectedCountries = "";    
	    String srctable = "reg_data";
	    String varlisttable = "variables";
	    
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
			VerticalPanel vpWrapper = new VerticalPanel();
			vpWrapper.setSpacing(5);
			vpWrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			initWidget(vpWrapper);
			vpWrapper.setSize("383px", "392px");
	        
	        HorizontalPanel hpGExtent = new HorizontalPanel();
	        hpGExtent.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	        hpGExtent.setSpacing(5);
	        vpWrapper.add(hpGExtent);
	        
	        Label lblGeographicExtent = new Label("Geographic Extent");
	        hpGExtent.add(lblGeographicExtent);
	        lblGeographicExtent.setSize("142px", "18px");
	        hpGExtent.add(lbxExtent);
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
	        lbxExtent.setSize("238px", "22px");
	        
	        HorizontalPanel hpVarL1 = new HorizontalPanel();
	        hpVarL1.setSpacing(5);
	        vpWrapper.add(hpVarL1);
	        
	        VerticalPanel verticalPanel = new VerticalPanel();
	        hpVarL1.add(verticalPanel);
	        
	        Label lblRegion = new Label("Regions");
	        verticalPanel.add(lblRegion);
	        verticalPanel.add(lbxRegion);
	        
	        lbxRegion.setVisibleItemCount(5);
	        lbxRegion.setSize("190px", "100px");
	        
	        VerticalPanel verticalPanel_1 = new VerticalPanel();
	        hpVarL1.add(verticalPanel_1);
	        
	        Label lblVarGroup = new Label("Variable Groups");
	        verticalPanel_1.add(lblVarGroup);
	        verticalPanel_1.add(lbxVarGroup);
	        
	        lbxVarGroup.setVisibleItemCount(5);
	        lbxVarGroup.setSize("190px", "100px");
	        
	        HorizontalPanel hpVarL2 = new HorizontalPanel();
	        hpVarL2.setSpacing(5);
	        vpWrapper.add(hpVarL2);
	        
	        VerticalPanel verticalPanel_2 = new VerticalPanel();
	        hpVarL2.add(verticalPanel_2);
	        
	        Label lblVariables = new Label("Variables");
	        verticalPanel_2.add(lblVariables);
	        verticalPanel_2.add(lbxVariable);
	        lbxVariable.setSize("304px", "195px");
	        lbxVariable.setVisibleItemCount(10);
	        
	        VerticalPanel verticalPanel_3 = new VerticalPanel();
	        hpVarL2.add(verticalPanel_3);
	        
	        Label lblYears = new Label("Years");
	        verticalPanel_3.add(lblYears);
	        verticalPanel_3.add(lbxYear);
	        lbxYear.setSize("77px", "195px");
	        lbxYear.setVisibleItemCount(10);
	        
	        HorizontalPanel hpBtns = new HorizontalPanel();
	        hpBtns.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	        hpBtns.setSpacing(5);
	        vpWrapper.add(hpBtns);
	        hpBtns.setSize("190px", "35px");
	        
	        Button btnClear = new Button("Clear Selection");
	        btnClear.addClickHandler(new ClickHandler() {
	        	public void onClick(ClickEvent event) {
	        		initListBoxes();				}
	        });
	        hpBtns.add(btnClear);
	        btnClear.setHeight("30px");
	        
	        hpBtns.add(btnSubmit);
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
