package org.irri.statistics.client.ui;

import org.irri.statistics.client.utils.RPCUtils;
import org.irri.statistics.client.utils.JSONUtils;
import org.irri.statistics.client.WRS_filters;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public class RPFiltering extends Composite {
	/*
	 * Region-priority Filtering
	 * 
	 * Author : Jorrel Khalil S. Aunario
	 */
	ListBox lbxExtent = new ListBox(false);
	public ListBox lbxRegion = new ListBox(true);
	CheckListBox lbxVarGroup = new CheckListBox();
	public ListBox lbxVariable = new ListBox(true);
	public ListBox lbxYear = new ListBox(true);
	Button btnSubmit = new Button("Get Data");
	public QueryOptions filteropts = new QueryOptions();	
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
            	lbxVarGroup.getVpListBox().clear();
                try{
                	for (int i = 1; i < result.length; i++) {
							lbxVarGroup.addItem(result[i][0], result[i][1]);
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
					lbxYear.addItem(out[i][0], "");
				}
            }

            public void onFailure(Throwable caught) {
                System.out.println("Communication failed (RDV.InitVarBox)");
            }
        };

		
		public RPFiltering() {
			VerticalPanel vpWrapper = new VerticalPanel();
			vpWrapper.setSpacing(5);
			initWidget(vpWrapper);
			vpWrapper.setSize("368px", "350px");
	        
	        HorizontalPanel hpGExtent = new HorizontalPanel();
	        hpGExtent.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	        hpGExtent.setSpacing(5);
	        vpWrapper.add(hpGExtent);
	        
	        Label lblGeographicExtent = new Label("Geographic Extent");
	        hpGExtent.add(lblGeographicExtent);
	        lblGeographicExtent.setSize("175px", "18px");
	        hpGExtent.add(lbxExtent);
	        lbxExtent.addChangeHandler(new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		String rsql = "";
	        		String vgsql = "";
	        		lbxYear.clear();
	        		lbxVariable.clear();
	        		int selected = lbxExtent.getSelectedIndex();
        			rsql = "SELECT c.NAME_ENGLISH, r.iso3 FROM available r INNER JOIN countries c ON c.iso3=r.iso3 WHERE r.ci="+ selected +" GROUP BY 1 ASC";
        			vgsql = "SELECT g.group_name, r.group_code FROM available r INNER JOIN wrs_groups g ON g.group_code=r.group_code WHERE r.ci="+ selected +" GROUP BY 1 ASC";
	        		switch (selected) {
					case 0:
	        			srctable = "reg_data";		            	
		            	//rsql =  "SELECT c.name_english, r.iso3 FROM "+ srctable +" r INNER JOIN countries c ON c.iso3=r.iso3 WHERE c.ci=0 GROUP BY 1 ASC";
		            	//vgsql =  "SELECT g.group_name, x.group_code FROM (SELECT v.group_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code GROUP BY 1) x, wrs_groups g WHERE x.group_code=g.group_code ORDER BY 1";
						break;

					case 1:
		            	srctable = "front_data";
		            	//rsql =  "SELECT c.name_english, r.iso3 FROM "+ srctable +" r INNER JOIN countries c ON c.iso3=r.iso3 WHERE c.ci=1 GROUP BY 1 ASC";
		            	//vgsql =  "SELECT g.group_name, x.group_code FROM (SELECT v.group_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code GROUP BY 1) x, wrs_groups g WHERE x.group_code=g.group_code ORDER BY 1";		            
						break;
					
					case 2:
						srctable = "pays";
		            	varlisttable = "svar_list";
		            	rsql = "SELECT c.name_english, c.iso3 FROM countries c INNER JOIN pays ON LEFT(geo_code,2)=c.iso2 GROUP BY LEFT(geo_code,2)";
		            	vgsql =  "SELECT NULL, NULL";
						break;
					}

		            RPCUtils.getService("mysqlservice").RunSELECT(rsql,InitRegionBox);
		            RPCUtils.getService("mysqlservice").RunSELECT(vgsql,InitVarGroupBox);
		            //else MainPanel.add(new HTML("Under Construction"));
	        	}
	        });
	        
	        lbxExtent.addItem("Continent/Organization");
	        lbxExtent.addItem("Country");
	        lbxExtent.addItem("State/Province");
	        lbxExtent.setSize("369px", "22px");
	        
	        HorizontalPanel hpVarL1 = new HorizontalPanel();
	        hpVarL1.setSpacing(5);
	        vpWrapper.add(hpVarL1);
	        
	        VerticalPanel vpRegion = new VerticalPanel();
	        hpVarL1.add(vpRegion);
	        
	        Label lblRegion = new Label("Regions");
	        vpRegion.add(lblRegion);
	        lbxRegion.addChangeHandler( new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		String selregion = getSelectedItems(lbxRegion, false);
	        		String selvargroups = "";
	        		String sql = "";
	        		String vgsql = "SELECT g.group_name, r.group_code FROM available r INNER JOIN wrs_groups g ON g.group_code=r.group_code WHERE r.ci="+ lbxExtent.getSelectedIndex();
	        		
	        		lbxYear.clear();
	        		btnSubmit.setEnabled(false);
	        		if (!selregion.equals("")){
	        			if (lbxExtent.getSelectedIndex()==2){
		        			sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM svar_list v INNER JOIN subnat_avail s ON v.var_code=s.var_code WHERE s.iso3 in (" + selregion + ")";
		        		} else {
		        			//sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code WHERE v.show_flag=1 AND s.iso3 in (" + selregion + ")";
		        			sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM variables v INNER JOIN available s ON v.var_code=s.var_code WHERE s.ci="+ lbxExtent.getSelectedIndex()+ " AND s.iso3 in (" + selregion + ")";
		        			selvargroups = lbxVarGroup.csSelectedNames(true);
		        			
		        			if (!selvargroups.equals("")){
			        			sql = sql + " AND v.group_code in (" + selvargroups + ")";			        			
			        		}
		        			
		        		}
	        			
	        			sql = sql +  " GROUP BY s.var_code ORDER BY v.var_name";
	        			RPCUtils.getService("mysqlservice").RunSELECT(sql,InitVarBox);
	        		}
        			vgsql = vgsql + " GROUP BY 1 ASC";
		            RPCUtils.getService("mysqlservice").RunSELECT(vgsql,InitVarGroupBox);
	        	}
	        });
	        vpRegion.add(lbxRegion);
	        
	        lbxRegion.setVisibleItemCount(10);
	        lbxRegion.setSize("292px", "215px");
	        
	        VerticalPanel vpVarGroup = new VerticalPanel();
	        hpVarL1.add(vpVarGroup);	        
	        
	        Label lblVarGroup = new Label("Variable Groups");
	        vpVarGroup.add(lblVarGroup);
	        lbxVarGroup.addChangeHandler( new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		String selregion = getSelectedItems(lbxRegion, false);
	        		String selvargroups = "";
	        		String sql = "";
	        		
	        		lbxYear.clear();
	        		btnSubmit.setEnabled(false);
	        		if (!selregion.equals("")){
	        			if (lbxExtent.getSelectedIndex()==2){
		        			sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM svar_list v INNER JOIN subnat_avail s ON v.var_code=s.var_code WHERE s.iso3 in (" + selregion + ")";
		        		} else {
		        			//sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM variables v INNER JOIN " + srctable + " s ON v.var_code=s.var_code WHERE v.show_flag=1 AND s.iso3 in (" + selregion + ")";
		        			sql = "SELECT CONCAT(v.var_name,' (', IF(v.unit IS NULL OR v.unit='','no unit',v.unit),')'), s.var_code FROM variables v INNER JOIN available s ON v.var_code=s.var_code WHERE s.ci="+ lbxExtent.getSelectedIndex()+ " AND s.iso3 in (" + selregion + ")";
		        			selvargroups = lbxVarGroup.csSelectedNames(true);
		        			
		        			if (!selvargroups.equals("")){
			        			sql = sql + " AND v.group_code in (" + selvargroups + ")";			        			
			        		}
		        			
		        		}
	        			
	        			sql = sql +  " GROUP BY s.var_code ORDER BY v.var_name";
	        			RPCUtils.getService("mysqlservice").RunSELECT(sql,InitVarBox);
	        		}
	        	}
	        });
	        vpVarGroup.add(lbxVarGroup);
	        lbxVarGroup.setSize("280px", "188px");
	        
	        vpVarGroup.add(lbxVarGroup.getHpControls());
	        vpVarGroup.setCellHorizontalAlignment(vpVarGroup.getWidget(2), HasHorizontalAlignment.ALIGN_RIGHT);
	        HorizontalPanel hpVarL2 = new HorizontalPanel();
	        hpVarL2.setSpacing(5);
	        vpWrapper.add(hpVarL2);
	        
	        VerticalPanel vpVariable = new VerticalPanel();
	        hpVarL2.add(vpVariable);
	        
	        Label lblVariables = new Label("Variables");
	        vpVariable.add(lblVariables);
	        lbxVariable.addChangeHandler(new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		String selregion = getSelectedItems(lbxRegion, false);
	        		String selvars = getSelectedItems(lbxVariable, false);
	        		String sql = "";
	        		
	        		btnSubmit.setEnabled(false);
	        		
	        		if (!selvars.equals("") && !selregion.equals("")){
		        		if (lbxExtent.getSelectedIndex()==2){
		        			sql = "SELECT yr, yr FROM subnat_avail s";
		        		} else {
		        			sql = "SELECT yr, yr FROM " + srctable + " s";
		        		}
		        		sql = sql + " WHERE s.var_code in (" + selvars + ") AND s.iso3 in (" + selregion + ") GROUP BY 1";
		        		RPCUtils.getService("mysqlservice").RunSELECT(sql,InitYearBox);
	        		} else lbxYear.clear();
	        		
	        	}
	        });
	        
	        vpVariable.add(lbxVariable);
	        lbxVariable.setSize("490px", "270px");
	        lbxVariable.setVisibleItemCount(15);
	        
	        VerticalPanel vpYears = new VerticalPanel();
	        hpVarL2.add(vpYears);
	        
	        Label lblYears = new Label("Years");
	        vpYears.add(lblYears);
	        lbxYear.addChangeHandler(new ChangeHandler() {
	        	public void onChange(ChangeEvent event) {
	        		btnSubmit.setEnabled(true);
	        	}
	        });
	        vpYears.add(lbxYear);
	        lbxYear.setSize("84px", "270px");
	        lbxYear.setVisibleItemCount(15);
	        
	        HorizontalPanel hpBtns = new HorizontalPanel();
	        hpBtns.setSpacing(5);
	        vpWrapper.add(hpBtns);
	        vpWrapper.setCellHorizontalAlignment(hpBtns, HasHorizontalAlignment.ALIGN_RIGHT);
	        
	        Button btnClear = new Button("Clear Selection");
	        btnClear.addClickHandler(new ClickHandler() {
	        	public void onClick(ClickEvent event) {
	    			lbxYear.clear();
	    			lbxVariable.clear();
	    			lbxRegion.setSelectedIndex(-1);

	        	}
	        });
	        
	        Button btnOptions = new Button("Settings");
	        btnOptions.addClickHandler(new ClickHandler() {
	        	public void onClick(ClickEvent event) {
	        		filteropts.center();
	        		filteropts.show();
	        	}
	        });
	        hpBtns.add(btnOptions);
	        btnOptions.setHeight("40");
	        hpBtns.add(btnClear);
	        hpBtns.setCellHorizontalAlignment(btnClear, HasHorizontalAlignment.ALIGN_CENTER);
	        btnClear.setHeight("40px");
	        btnSubmit.setEnabled(false);
	        
	        hpBtns.add(btnSubmit);
	        hpBtns.setCellHorizontalAlignment(btnSubmit, HasHorizontalAlignment.ALIGN_CENTER);
	        btnSubmit.setHeight("40px");
			//initListBoxes();	        
		}
		
		public void setSubmitButtonClickHandler(ClickHandler click){
			btnSubmit.addClickHandler(click);
		}
		
		public void initListBoxes(){
			lbxYear.clear();
			lbxVariable.clear();
			RPCUtils.getService("mysqlservice").RunSELECT("SELECT c.NAME_ENGLISH, r.iso3 FROM available r INNER JOIN countries c ON c.iso3=r.iso3 WHERE r.ci=0 GROUP BY 1 ASC",InitRegionBox);
			//RPCUtils.getService("mysqlservice").RunSELECT("SELECT g.group_name, r.group_code FROM available r INNER JOIN wrs_groups g ON g.group_code=r.group_code WHERE r.ci=0 GROUP BY 1 ASC",InitVarGroupBox);
			populateGroupBox();
		}
		
		private void populateGroupBox(){
			String JSON_URL = "http://localhost/JSON/filters.php?what=3";
			
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JSON_URL);
			try {
				Request request = builder.sendRequest(null, new RequestCallback() {
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode()==200){
							JsArray<WRS_filters> groups = JSONUtils.asArrayOfFilters(response.getText());
							for (int i = 0; i < groups.length(); i++) {
								WRS_filters group = groups.get(i);
								lbxVarGroup.addItem(group.getItem(), group.getCode());
							}
						} else {
							System.out.println("Couldn't retrieve JSON (" + response.getStatusText()
					                + ")");
						}
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						System.out.println("Couldn't retrieve JSON");
					}
				});
			} catch (RequestException e){
				System.out.println("Couldn't retrieve JSON" + e.toString());
			}
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
		
		public String getSelectedLabels(ListBox lbx){
			String selitems = "";
			for (int i = 0; i < lbx.getItemCount(); i++) {
				if (lbx.isItemSelected(i)) {
					selitems = selitems + lbx.getItemText(i) + ",";  
				} 
			}			
			if (selitems.length()>0) selitems = selitems.substring(0, selitems.length()-1);
			return(selitems);
		}
		
		public String[] getVarColumns(){
			String[] selitems = new String[2];
			selitems[0] = "";
			selitems[1] = "";
			int lvl = lbxExtent.getSelectedIndex();
			for (int i = 0; i < lbxVariable.getItemCount(); i++) {
				if (lbxVariable.isItemSelected(i)) {
					if (lvl==2){
						selitems[0] = selitems[0] + " ROUND(" + lbxVariable.getValue(i) + ",2) AS '"+ lbxVariable.getValue(i) +"',";
						selitems[1] = selitems[1] + lbxVariable.getValue(i) + " IS NOT NULL OR ";
					} else {
						selitems[0] = selitems[0] + "SUM(IF(s.var_code='" + lbxVariable.getValue(i) + "', ROUND(val,2), null)) AS '" + lbxVariable.getValue(i) + "',";
						selitems[1] = selitems[1] + "SUM(IF(s.var_code='" + lbxVariable.getValue(i) + "', ROUND(val,2), null)) IS NOT NULL OR ";
					}
				}   
			}			
			if (selitems[0].length()>0) selitems[0] = selitems[0].substring(0, selitems[0].length()-1);
			if (selitems[1].length()>0) selitems[1] = selitems[1].substring(0, selitems[1].length()-3);
			return(selitems);
		}
		
		public String sqlFromItems(){
			String regfilter = getSelectedItems(lbxRegion, false);
			String yrfilter = getSelectedLabels(lbxYear);
			String[] varcols = getVarColumns();
			
			String sql = "";
			if (!regfilter.equals("") && !yrfilter.equals("") && !varcols[0].equals("") && !varcols[1].equals("")){
				if (lbxExtent.getSelectedIndex()==2){
					sql = "SELECT CONCAT(c.name_english,'-',l.geo_name) AS 'region', s.yr AS 'year'," + varcols[0] +
					" FROM " + srctable + " s NATURAL JOIN locales l NATURAL JOIN countries c " +
		            " WHERE c.iso3 in ("+regfilter+") AND yr IN (" +yrfilter +") AND variety='All' AND ecosystem='All' AND season='All' AND ("+ varcols[1] + ")" + 
		            " GROUP BY 1 ASC, s.yr ASC" ;
				} else {
					sql = "SELECT c.NAME_ENGLISH AS 'region', s.yr AS 'year', " + varcols[0] +
					" FROM " + srctable + " s INNER JOIN countries c ON s.iso3 = c.ISO3 " +
		            " WHERE c.iso3=s.iso3 AND c.iso3 in ("+regfilter+") AND yr IN (" +yrfilter +") " +
		            " GROUP BY  s.yr ASC, s.iso3 ASC HAVING " + varcols[1] ;
				}
			}
			if (filteropts.getChckbxLimitRecords().getValue()) sql = sql + " LIMIT " + filteropts.getIbRecLimit().getText();
			return sql;
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
		
		public int selectedItemsCount(){
			int sel = 0;
			for (int i = 0; i < lbxVariable.getItemCount(); i++) {
				if (lbxVariable.isItemSelected(i)) sel++;
			}
			return sel;
		}
		
		public String varDefinition(ListBox lbx, String delim){
			String vdcsv = "";
			for (int i = 0; i < lbx.getItemCount(); i++) {
				if (lbx.isItemSelected(i) && lbx.getValue(i).isEmpty() ){
					vdcsv += lbx.getItemText(i);					
				} else if (lbx.isItemSelected(i) && !lbx.getValue(i).isEmpty()){
					vdcsv += lbx.getValue(i) + "-" + lbx.getItemText(i);
				}
				if(i+1<lbx.getItemCount() && lbx.isItemSelected(i)) vdcsv += delim;
			}
			return vdcsv;
		}
		
		public Grid varDefinitionGrid(ListBox lbx){
			Grid grid = new Grid();
			int counter = 0;
			for (int i = 0; i < lbx.getItemCount(); i++) {
				if (lbx.isItemSelected(i) && !lbx.getValue(i).isEmpty()){
					Label code = new Label(lbx.getValue(i));
					code.setStyleName("code");
					Label fullvalue = new Label(lbx.getItemText(i));
					fullvalue.setStyleName("fullvalue");
					grid.resize(counter+1, 2);
					grid.setWidget(counter, 0, code);
					grid.setWidget(counter, 1, fullvalue);
					counter++;					
				}				
			}
			return grid;
		}


  	}
