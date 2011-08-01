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
        
        final AsyncCallback<String[][]> InitVarBox = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] out) {
                lbxVariable.clear();
                if (out.length<=1){
                	lbxVariable.addItem("No Data Available");
                }
                else{
                    try{
                        for (int i = 1;i<out.length;i++){
                        	lbxVariable.addItem(out[i][0]+" ("+out[i][1] + ")", out[i][2]);
                        }
                    }
                    catch(Exception e){
                        System.err.println(e);
                    }
                }
            }

            public void onFailure(Throwable caught) {
                System.out.println("Communication failed (RDV.InitVarBox)");
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
	        		String rsql = "";
	        		String vgsql = "";
	        		lbxYear.clear();
	        		lbxVariable.clear();

	        		if (lbxExtent.getSelectedIndex()==0){

	        			srctable = "reg_data";
		            	varlisttable = "variables";
		            	rsql =  "SELECT c.name_english, r.iso3 FROM "+ srctable +" r INNER JOIN countries c ON c.iso3=r.iso3 WHERE c.ci=0 GROUP BY 1 ASC";
		            	vgsql =  "SELECT g.group_name, x.group_code FROM (SELECT v.group_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code GROUP BY 1) x, wrs_groups g WHERE x.group_code=g.group_code";
		            } else if (lbxExtent.getSelectedIndex()==1){
		            	srctable = "front_data";
		            	rsql =  "SELECT c.name_english, r.iso3 FROM "+ srctable +" r INNER JOIN countries c ON c.iso3=r.iso3 WHERE c.ci=1 GROUP BY 1 ASC";
		            	vgsql =  "SELECT g.group_name, x.group_code FROM (SELECT v.group_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code GROUP BY 1) x, wrs_groups g WHERE x.group_code=g.group_code";		            } else{
		            	srctable = "pays";
		            	varlisttable = "svar_list";
		            	rsql = "SELECT c.name_english, iso3 FROM sncountry_list ORDER BY country";
		            }
		            UtilsRPC.getService("mysqlservice").RunSELECT(rsql,InitRegionBox);
		            UtilsRPC.getService("mysqlservice").RunSELECT(vgsql,InitVarGroupBox);
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
	        lbxVarGroup.addChangeHandler(new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		String selregion = getSelectedItems(lbxRegion);
	        		String sql = "SELECT v.var_name, v.unit, s.var_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code WHERE v.group_code in (" + getSelectedItems(lbxVarGroup) + ") AND v.show_flag=1";
	        		
	        		if (!selregion.equalsIgnoreCase("")){
	        			sql = sql + " AND s.iso3 in (" + selregion + ")";
	        		}
	        		
	        		sql = sql +  " GROUP BY s.var_code";
	        		UtilsRPC.getService("mysqlservice").RunSELECT(sql,InitVarBox);
	        		
	        	}
	        });
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
			UtilsRPC.getService("mysqlservice").RunSELECT("SELECT c.name_english, r.iso3 FROM "+ srctable +" r INNER JOIN countries c ON c.iso3=r.iso3 WHERE c.ci=0 GROUP BY 1 ASC",InitRegionBox);
			UtilsRPC.getService("mysqlservice").RunSELECT("SELECT g.group_name, x.group_code FROM (SELECT v.group_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code GROUP BY 1) x, wrs_groups g WHERE x.group_code=g.group_code",InitVarGroupBox);			
		}
		
		public String getSelectedItems(ListBox lbx){
			String selitems = "";
			for (int i = 0; i < lbx.getItemCount(); i++) {
				if (lbx.isItemSelected(i)) {
					selitems = selitems + "'" + lbx.getValue(i) + "',";
				}
			}			
			if (selitems.length()>0) selitems = selitems.substring(0, selitems.length()-1);
			return(selitems);
		}
		
		public String sqlFromItems(){
			return "SELECT c.NAME_ENGLISH AS 'country', d.yr AS 'year', sum(if(d.var_code='RicPr-USDA',val,null)) 'RicPr-USDA', sum(if(d.var_code='RicYldUSDA',val,null)) 'RicYldUSDA', sum(if(d.var_code='RicHa-USDA',val,null)) 'RicHa-USDA'" +
			"FROM front_data d inner join countries c on d.iso3 = c.ISO3 " +
            "WHERE d.iso3 in ('PHL') AND yr between 1960 AND 2011 "+
            "GROUP BY d.iso3 ASC, d.yr DESC;";
		}
	}
