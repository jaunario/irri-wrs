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
                try{
                	populateListBox(lbxRegion, result);
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
                try{
                	populateListBox(lbxVarGroup, result);
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
                    try{
                    	populateListBox(lbxVariable, out);
                    }
                    catch(Exception e){
                        System.err.println(e);
                    }
            }

            public void onFailure(Throwable caught) {
                System.out.println("Communication failed (RDV.InitVarBox)");
            }
        };
        
        final AsyncCallback<String[][]> InitYearBox = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] out) {
            	populateListBox(lbxYear, out);
            	lbxYear.clear();
            	for (int i = 1; i < out.length; i++) {
					lbxYear.addItem(out[i][0]);
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
			vpWrapper.setSize("521px", "392px");
	        
	        HorizontalPanel hpGExtent = new HorizontalPanel();
	        hpGExtent.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	        hpGExtent.setSpacing(5);
	        vpWrapper.add(hpGExtent);
	        
	        Label lblGeographicExtent = new Label("Geographic Extent");
	        hpGExtent.add(lblGeographicExtent);
	        lblGeographicExtent.setSize("146px", "18px");
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
	        lbxExtent.setSize("310px", "22px");
	        
	        HorizontalPanel hpVarL1 = new HorizontalPanel();
	        hpVarL1.setSpacing(5);
	        vpWrapper.add(hpVarL1);
	        
	        VerticalPanel verticalPanel = new VerticalPanel();
	        hpVarL1.add(verticalPanel);
	        
	        ChangeHandler varChangeHandler = new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		lbxYear.clear();
	        		btnSubmit.setEnabled(false);
	        		String selregion = getSelectedItems(lbxRegion, false);
	        		String selvargroups = getSelectedItems(lbxVarGroup, false);
	        		
	        		String sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code WHERE v.show_flag=1";
	        		if (!selvargroups.equalsIgnoreCase("") & !selregion.equalsIgnoreCase("")){
	        			sql = sql + " AND v.group_code in (" + selvargroups + ") AND s.iso3 in (" + selregion + ")"; 
	        		} else if (!selregion.equalsIgnoreCase("")){
	        			sql = sql + " AND s.iso3 in (" + selregion + ")";
	        		} else if (!selvargroups.equalsIgnoreCase("")){
	        			sql = sql + " AND v.group_code in (" + selvargroups + ")";
	        		} else {
	        			sql = "";
	        		}
	        		if (!sql.equalsIgnoreCase("")){
		        		sql = sql +  " GROUP BY s.var_code";
		        		UtilsRPC.getService("mysqlservice").RunSELECT(sql,InitVarBox);
	        		} else {
	        			lbxVariable.clear();	        			
	        		}
	        		
	        	}
	        };

	        Label lblRegion = new Label("Regions");
	        verticalPanel.add(lblRegion);
	        lbxRegion.addChangeHandler(varChangeHandler);
	        verticalPanel.add(lbxRegion);
	        
	        lbxRegion.setVisibleItemCount(5);
	        lbxRegion.setSize("250px", "100px");
	        
	        VerticalPanel verticalPanel_1 = new VerticalPanel();
	        hpVarL1.add(verticalPanel_1);
	        
	        Label lblVarGroup = new Label("Variable Groups");
	        verticalPanel_1.add(lblVarGroup);
	        lbxVarGroup.addChangeHandler(varChangeHandler);
	        verticalPanel_1.add(lbxVarGroup);
	        
	        lbxVarGroup.setVisibleItemCount(5);
	        lbxVarGroup.setSize("250px", "100px");
	        
	        HorizontalPanel hpVarL2 = new HorizontalPanel();
	        hpVarL2.setSpacing(5);
	        vpWrapper.add(hpVarL2);
	        
	        VerticalPanel verticalPanel_2 = new VerticalPanel();
	        hpVarL2.add(verticalPanel_2);
	        
	        Label lblVariables = new Label("Variables");
	        verticalPanel_2.add(lblVariables);
	        lbxVariable.addChangeHandler(new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		String selregion = getSelectedItems(lbxRegion, false);
	        		String selvars = getSelectedItems(lbxVariable, false);
	        		
	        		String sql = "SELECT yr, yr FROM " + srctable + " s";
	        		if (!selvars.equalsIgnoreCase("")){
	        			sql = sql + " WHERE s.var_code in (" + selvars + ")"; 
	        		} else sql = "";
	        		if (!selregion.equalsIgnoreCase("")){
	        			sql = sql + " AND s.iso3 in (" + selregion + ")";
	        		}
	        		
	        		if (!sql.equalsIgnoreCase("")){
	        			sql = sql +  " GROUP BY 1";
		        		UtilsRPC.getService("mysqlservice").RunSELECT(sql,InitYearBox);
	        		} else lbxYear.clear();
	        		btnSubmit.setEnabled(false);
	        	}
	        });
	        
	        verticalPanel_2.add(lbxVariable);
	        lbxVariable.setSize("420px", "195px");
	        lbxVariable.setVisibleItemCount(10);
	        
	        VerticalPanel verticalPanel_3 = new VerticalPanel();
	        hpVarL2.add(verticalPanel_3);
	        
	        Label lblYears = new Label("Years");
	        verticalPanel_3.add(lblYears);
	        lbxYear.addChangeHandler(new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		btnSubmit.setEnabled(true);
	        	}
	        });
	        verticalPanel_3.add(lbxYear);
	        lbxYear.setSize("80px", "195px");
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
	        btnSubmit.setEnabled(false);
	        
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
		
		public String getSelectedItems(ListBox lbx, boolean noquote){
			String selitems = "";
			for (int i = 0; i < lbx.getItemCount(); i++) {
				if (lbx.isItemSelected(i)) {
					if (noquote){ selitems = selitems + lbx.getValue(i) + ","; } else selitems = selitems + "'" + lbx.getValue(i) + "',"; 
				} 
			}			
			if (selitems.length()>0) selitems = selitems.substring(0, selitems.length()-1);
			return(selitems);
		}
		
		public String getVarColumns(){
			String selitems = "";
			for (int i = 0; i < lbxVariable.getItemCount(); i++) {
				if (lbxVariable.isItemSelected(i)) {
					selitems = selitems + "SUM(IF(s.var_code='" + lbxVariable.getValue(i) + "', val, null)) AS '" + lbxVariable.getValue(i) + "',";
				}   
			}			
			if (selitems.length()>0) selitems = selitems.substring(0, selitems.length()-1);
			return(selitems);
		}
		
		public String sqlFromItems(){
			String regfilter = getSelectedItems(lbxRegion, false);
			String yrfilter = getSelectedItems(lbxYear, true);
			String varcols = getVarColumns();
			
			String sql = "";
			if (!regfilter.equalsIgnoreCase("") && !yrfilter.equalsIgnoreCase("")){
				sql = "SELECT c.NAME_ENGLISH AS 'country', s.yr AS 'year', " + varcols +
						" FROM " + srctable + " s INNER JOIN countries c ON s.iso3 = c.ISO3 " +
			            " WHERE s.iso3 in ("+regfilter+") AND yr IN (" +yrfilter +")" +
			            " GROUP BY s.iso3 ASC, s.yr;";
			} else if (!yrfilter.equalsIgnoreCase("")){
				sql = "SELECT c.NAME_ENGLISH AS 'country', s.yr AS 'year', " + varcols +
						" FROM " + srctable + " s INNER JOIN countries c ON s.iso3 = c.ISO3 " +
			            " WHERE yr IN (" +yrfilter +")" +
			            " GROUP BY s.iso3 ASC, s.yr;";
			}
			return sql;
		}
		
		public String sqlQueryTest(){
			return "SELECT c.NAME_ENGLISH AS 'country', d.yr AS 'year', sum(if(d.var_code='RicPr-USDA',val,null)) 'RicPr-USDA', sum(if(d.var_code='RicYldUSDA',val,null)) 'RicYldUSDA', sum(if(d.var_code='RicHa-USDA',val,null)) 'RicHa-USDA'" +
					"FROM front_data d inner join countries c on d.iso3 = c.ISO3 " +
		            "WHERE d.iso3 in ('PHL') AND yr between 1960 AND 2011 "+
		            "GROUP BY d.iso3 ASC, d.yr DESC;";
		}
		
		private void populateListBox(ListBox listbox, String[][] data){
	        listbox.clear();	     
			if (data==null){
            	listbox.addItem("No data available for selected categories.");
            	listbox.setEnabled(false);
			} else {
	            for (int i = 1;i<data.length;i++){
	            	listbox.addItem(data[i][0],data[i][1]);
	            }
	            listbox.setEnabled(true);
			}
		}

  	}
