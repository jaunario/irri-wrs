/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.irri.statistics.client;

/**
 *
 * @author Jorrel Khalil S. Aunario
 */
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



public class DataViewer extends TabPanel{
    //private static int maxvars = 5;
    //private static int maxregions = 5;
    DockPanel SelectionPanel = new DockPanel();
        VerticalPanel VpControlFilters = new VerticalPanel();
            VerticalPanel VpActionsBox = new VerticalPanel();
                HorizontalPanel HpActionsPan = new HorizontalPanel();
                    ListBox LbRegLevel = new ListBox();
                    Button BtClear = new Button("Clear");
                    Button BtFetchData = new Button("Get Data");
            VerticalPanel VpRegionBox = new VerticalPanel();    
                ListBox RegionFilter = new ListBox(true);
            VerticalPanel VpVariableBox = new VerticalPanel();
                /*
                 TODO InitVarGroupFilter AsyncCallback, Recode InitVarBox to support group filter
                 */
                ListBox VariableFilter = new ListBox(true);
            VerticalPanel VpVarGroupBox = new VerticalPanel();
                ListBox VarGroupFilter = new ListBox(true);

            VerticalPanel VpYearBox = new VerticalPanel();
                ListBox YearFilter = new ListBox(true);
        

        VerticalPanel VpInstructions = new VerticalPanel();
    
    HorizontalPanel ResultsPan = new HorizontalPanel();
        VerticalPanel VpDataContainer = new VerticalPanel();            
            ScrollPanel ScDataScroller = new ScrollPanel();
                FlexTable FtDataContainer = new FlexTable();
            Button BtCSV = new Button("Download CSV");
            VerticalPanel VpMetadata = new VerticalPanel();
            
        
    private String selectedCountries = "";    
    private String srctable = "reg_data";
    private String varlisttable = "variables";

    public DataViewer(){
        super();
        final AsyncCallback<String[][]> GetDataCallback = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] result) {
                String val;
                NumberFormat f1 = NumberFormat.getFormat("########0.0#");
                
                try{
                    for(int i=0; i<result.length; i++){
                        for(int j=0;j<result[i].length; j++) {
                            val = result[i][j];
                            if (val==null){
                                val=".";
                            }
                            
                            if ((j>1) && (i>0) && !(val.equals("."))){
                                Double tmp = new Double(val);
                                FtDataContainer.setText(i, j, f1.format(tmp.doubleValue()));
                            }                            
                            else {
                                FtDataContainer.setText(i, j, val);
                            }
                        }                        
                    }
                    FtDataContainer.getRowFormatter().addStyleName(0, "table-hdr");
                    FtDataContainer.getColumnFormatter().setWidth(1, "100px");
                }
                catch(Exception e){
                    System.err.println(e);
                }
            }
            
            public void onFailure(Throwable caught) {
                System.out.println("Communication failed (RDV.GetDataCallback)");
            }
        };
                
        final AsyncCallback<String[][]> InitRegionBox = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] result) {
                RegionFilter.clear();
                try{
                    for (int i = 1;i<result.length;i++){
                        RegionFilter.addItem(result[i][0],result[i][1]);
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
        
      
        // Widget Instantiation and Configuration
        HTMLPanel HTInstructions = new HTMLPanel("<h3>How to Use this Facility</h3> " +
                "<p>The procedure in retrieving data from this facility is sequential " +
                "to minimize empty result sets. Please follow the steps enumerated below.</p>" +
                "<ol> " +
                "<li>Select the level of geographical extent (i.e. continental, national, or subnational)</li> " +
                "<li>Select region/country/organization of interest.</li> " +
                "<li>Select variable(s) from either <i>Supply and Demand Variables Box</i> or <i>Other Variables Box</i>.</li> " +
                "<li>Select year(s).</li>" +
                "<li>Click <b><i>Get Data</i>.</b></li></ol>");

        HTMLPanel HTHints = new HTMLPanel("<p><b>Hint: </b></p>" +
                "<ul>" +
                "<li>After clicking on an item, wait for the list boxes to be populated.</li>" +
                "<li>To select multiple items, hold the <b><i>Ctrl</i></b> button on your keyboard and then click on the item.</li>" +
                "<li>If you have multiple selections, deselect an item by just clicking on an item again.</li>" +
                "<li>In <i>Subnational Geographic Extent</i>, selecting a country retrievs data from all the selected country's provices/states.</li>"+
                "</ul>");

        BtFetchData.setTitle("Gets the data with the specifications selected below");
        BtFetchData.setEnabled(false);
        BtCSV.setTitle("Download CSV file of the data");
        BtCSV.setEnabled(false);

        Label LblGeoExt = new Label("Geographic Extent");
        LblGeoExt.addStyleName("filter-lbl");
        LbRegLevel.addItem("Continent/Organization");
        LbRegLevel.addItem("National");
        LbRegLevel.addItem("Subnational");

        RegionFilter.setTitle("Region List");
        RegionFilter.setWidth("300px");
        RegionFilter.setVisibleItemCount(5);

        Label LblRegion = new Label("Region");
        LblRegion.addStyleName("filter-lbl");
        UtilsRPC.getService("mysqlservice").RunSELECT("SELECT region, iso3 FROM region_list ORDER BY region", InitRegionBox);
        
        VariableFilter.setTitle("Variable List");
        VariableFilter.setEnabled(false);
        VariableFilter.setWidth("300px");
        VariableFilter.setVisibleItemCount(5);

        Label LblVariable = new Label("Variables");
        LblVariable.addStyleName("filter-lbl");

        VarGroupFilter.setTitle("Supply and Demand Variables");
        VarGroupFilter.setEnabled(false);
        VarGroupFilter.setWidth("300px");
        VarGroupFilter.setVisibleItemCount(5);

        Label LblSupDem = new Label("Supply and Demand Variables");
        LblSupDem.addStyleName("filter-lbl");

        YearFilter.setWidth("100px");
        YearFilter.setEnabled(false);
        YearFilter.setTitle("Year List");
        YearFilter.setVisibleItemCount(5);
        Label LblYear = new Label("Year");
        LblYear.addStyleName("filter-lbl");        

        VpControlFilters.setStyleName("forms");
        VpControlFilters.setSpacing(5);

        SelectionPanel.setSize("100%", "100%");
        SelectionPanel.setSpacing(5);

        FtDataContainer.setCellSpacing(0);
        FtDataContainer.addStyleName("table");        

        ScDataScroller.setSize("100%", "403px");
        ScDataScroller.setAlwaysShowScrollBars(true);
        
        VpDataContainer.setSize("100%", "100%");
                
        ResultsPan.setSize("100%", "100%");

        // Widget Layouting
        HpActionsPan.add(LbRegLevel);
        HpActionsPan.add(BtClear);
        HpActionsPan.add(BtFetchData);
        
        VpActionsBox.add(LblGeoExt);
        VpActionsBox.add(HpActionsPan);

        VpRegionBox.add(LblRegion);
        VpRegionBox.add(RegionFilter);

        VpVariableBox.add(LblVariable);
        VpVariableBox.add(VariableFilter);

        VpVarGroupBox.add(LblSupDem);
        VpVarGroupBox.add(VarGroupFilter);

        VpYearBox.add(LblYear);
        VpYearBox.add(YearFilter);

        VpControlFilters.add(VpActionsBox);
        VpControlFilters.add(VpRegionBox);
        VpControlFilters.add(VpVarGroupBox);
        VpControlFilters.add(VpVariableBox);
        VpControlFilters.add(VpYearBox);
        
        VpInstructions.add(HTInstructions);
        VpInstructions.add(HTHints);
        
        SelectionPanel.add(VpInstructions, DockPanel.CENTER);
        SelectionPanel.add(VpControlFilters, DockPanel.EAST);
        
        ScDataScroller.add(FtDataContainer);
        VpDataContainer.add(ScDataScroller);
        VpDataContainer.add(BtCSV);                                                
        
        VpMetadata.addStyleName("forms");
        VpMetadata.setVisible(true);
        VpMetadata.setSpacing(0);

        ResultsPan.add(new HTML("<br/><br/><blockquote><H4>This panel will contain the data you require.</H4>" +
                "<p>Please click on the search tab and follow the instructions.</p></blockquote>"));
        add(SelectionPanel, "Search");
        add(ResultsPan, "Results");
        getTabBar().setTabEnabled(1, false);
        selectTab(0);
        setSize("100%", "100%");
        
        LbRegLevel.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                String sql = "";
                YearFilter.clear();
                VariableFilter.clear();
                VarGroupFilter.clear();
                
                if (LbRegLevel.getSelectedIndex()==0){
                    srctable = "reg_data";
                    varlisttable = "variables";
                    sql =  "SELECT region, iso3 FROM region_list ORDER BY region";
                }
                else if (LbRegLevel.getSelectedIndex()==1){
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

        BtClear.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                LbRegLevel.setSelectedIndex(0);
                RegionFilter.clear();
                VariableFilter.clear();
                VarGroupFilter.clear();
                YearFilter.clear();
                BtFetchData.setEnabled(false);
                cleanTabs();
                UtilsRPC.getService("mysqlservice").RunSELECT("SELECT region, iso3 FROM region_list ORDER BY region", InitRegionBox);
            }
        });


        BtFetchData.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent w) {
                ClearFlexTable(FtDataContainer);
                cleanTabs();
                VpMetadata.clear();

                Label LblSelLab =new Label("YOUR CURRENT SELECTION");
                LblSelLab.setStyleName("meta-lab");
                VpMetadata.add(LblSelLab);
                
                Label LblGeoLab =new Label("Level of Geographical Extent: ");
                LblGeoLab.setStyleName("meta-lab");               
                VpMetadata.add(LblGeoLab);
                Label LblMetageo = new Label(LbRegLevel.getItemText(LbRegLevel.getSelectedIndex()));
                LblMetageo.setStyleName("meta-val");
                VpMetadata.add(LblMetageo);

                int cnt = 0;
                String cntry = "";

                Label LblRegLab = new Label("Region(s)");
                LblRegLab.setStyleName("meta-lab");
                VpMetadata.add(LblRegLab);

                FlexTable FTRegSel = new FlexTable();
                FTRegSel.setStyleName("meta-tab");
                FTRegSel.setCellSpacing(0);
                FTRegSel.setText(0, 0, "Region Code");
                FTRegSel.setText(0, 1, "Region/Country");
                FTRegSel.getRowFormatter().addStyleName(0, "meta-hdr");
                FTRegSel.setSize("100%", "100px");
                for(int i=0; i<RegionFilter.getItemCount();i++){
                    if (RegionFilter.isItemSelected(i)){
                        cnt++;
                        FTRegSel.setText(cnt, 0, RegionFilter.getValue(i));
                        FTRegSel.setText(cnt, 1, RegionFilter.getItemText(i));
                        cntry = cntry + "'"+RegionFilter.getValue(i)+"',";
                    }
                }
                ScrollPanel ScRegSel = new ScrollPanel(FTRegSel);
                ScRegSel.setSize("100%", "105px");
                ScRegSel.setAlwaysShowScrollBars(true);
                VpMetadata.add(ScRegSel);
                String vars = "";
                cnt=0;

                Label LblVarLab = new Label("Variable(s)");
                LblVarLab.setStyleName("meta-lab");
                VpMetadata.add(LblVarLab);
                FlexTable FTVarSel = new FlexTable();
                FTVarSel.setStyleName("meta-tab");
                FTVarSel.setCellSpacing(0);
                FTVarSel.setText(0, 0, "Variable Code");
                FTVarSel.setText(0, 1, "Variable Name (unit)");
                FTVarSel.getRowFormatter().addStyleName(0, "meta-hdr");
                FTVarSel.setSize("100%", "105px");
                
                for(int i=0; i<VariableFilter.getItemCount();i++){
                    if (VariableFilter.isItemSelected(i)){
                        cnt++;
                        FTVarSel.setText(cnt, 0, VariableFilter.getValue(i));
                        FTVarSel.setText(cnt, 1, VariableFilter.getItemText(i));
                        if (LbRegLevel.getSelectedIndex()==2){
                            vars = vars + VariableFilter.getValue(i) + ",";
                        } else vars = vars + "sum(if(d.var_code='"+VariableFilter.getValue(i)+"',val,null)) '" +VariableFilter.getValue(i)+ "',";
                   }
                }
                for(int i=0; i<VarGroupFilter.getItemCount();i++){
                    if (VarGroupFilter.isItemSelected(i)){
                        cnt++;
                        FTVarSel.setText(cnt, 0, VarGroupFilter.getValue(i));
                        FTVarSel.setText(cnt, 1, VarGroupFilter.getItemText(i));
                        if (LbRegLevel.getSelectedIndex()==2){
                            vars = vars + VarGroupFilter.getValue(i) + ",";
                        } else vars = vars + "sum(if(d.var_code='"+VarGroupFilter.getValue(i)+"',val,null)) '" +VarGroupFilter.getValue(i)+ "',";
                   }
                }

                ScrollPanel ScVarSel = new ScrollPanel(FTVarSel);
                ScVarSel.setSize("100%", "100px");
                ScVarSel.setStyleName("meta-tab");
                ScVarSel.setAlwaysShowScrollBars(true);
                VpMetadata.add(ScVarSel);
                
                String years = "";
                String metayr = "";

                Label LblYrLab = new Label("Year(s)");
                LblYrLab.setStyleName("meta-lab");
                VpMetadata.add(LblYrLab);

                int hi = 0;
                int low = 0;

                for(int i=0; i<YearFilter.getItemCount();i++){                    
                    if (YearFilter.isItemSelected(i)){
                        int item = new Integer(YearFilter.getItemText(i)).intValue();
                        if (hi==0){
                            hi = item;
                            low = item;
                        }
                        else if (low-item==1){
                            low = item;
                        }
                        else if (low-item>1){
                            if (hi==low){
                                metayr = String.valueOf(hi)+", " + metayr ;
                            } else{
                                metayr = String.valueOf(low)+"-"+String.valueOf(hi)+", " + metayr;
                            }
                            hi = item;
                            low =item;
                        }
                        years += YearFilter.getItemText(i)+",";
                    }
                }
                if (hi==low){
                    metayr = String.valueOf(hi) + ", " + metayr;
                } else{
                    metayr = String.valueOf(low)+"-"+String.valueOf(hi)+ ", " + metayr;
                }                
                Label LblMetayr = new Label(metayr.substring(0, metayr.length()-2));
                LblMetayr.setStyleName("meta-val");
                VpMetadata.add(LblMetayr);

                if ((cntry.equals("")) || (vars.equals("")) || (years.equals(""))){
                    add(new HTML("Missing selection parameters"), "Error");
                } else {
                    //cntry=cntry.substring(0,cntry.length()-1);
                    vars = vars.substring(0,vars.length()-1)+" ";
                    years=years.substring(0,years.length()-1);
                    String query = "";
                    if (LbRegLevel.getSelectedIndex()==0){
                        query = "SELECT c.NAME_ENGLISH AS 'region', d.yr AS 'year', " + vars +
                            "FROM "+srctable+" d inner join countries c on d.iso3 = c.ISO3 " +
                            "WHERE d.iso3 in ("+selectedCountries+") AND yr in ("+years+") "+
                            "GROUP BY d.iso3 ASC, d.yr DESC;";
                    } else if (LbRegLevel.getSelectedIndex()==1){
                        query = "SELECT c.NAME_ENGLISH AS 'country', d.yr AS 'year', " + vars +
                            "FROM "+srctable+" d inner join countries c on d.iso3 = c.ISO3 " +
                            "WHERE d.iso3 in ("+selectedCountries+") AND yr in ("+years+") "+
                            "GROUP BY d.iso3 ASC, d.yr DESC;";
                    } else {
                        query = "SELECT CONCAT(c.NAME_ENGLISH,' - ', l.geo_name) AS 'country-province', d.yr AS 'year', " + vars +
                            "FROM "+srctable+" d " + "natural join locales l, countries c " +
                            "WHERE l.iso3=c.ISO3 AND l.iso3 in ("+selectedCountries+") AND yr in ("+years+") " +
                            "AND season = 'All' AND variety = 'All' AND ecosystem ='All' " +
                            "ORDER BY l.iso3, l.geo_name ASC, d.yr DESC;";
                    }
                    ResultsPan.clear();
                    ResultsPan.add(VpDataContainer);
                    add(ResultsPan, "Results");
                    add(VpMetadata, "Metadata");
                    UtilsRPC.getService("mysqlservice").RunSELECT(query, GetDataCallback);
                    BtCSV.setEnabled(true);
                    selectTab(1);
                }             
            }
        });

        BtCSV.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final AsyncCallback<String> DownloadCSV = new AsyncCallback<String>() {
                    public void onFailure(Throwable arg0) {
                        BtCSV.setTitle("Failed");
                    }

                    public void onSuccess(String url) {                        
                        final Frame dl = new Frame(url);
                        add(dl, "CSV");
                        selectTab(3);
                    }
                };

                String queryContent="";
                if (FtDataContainer.getRowCount()>1){
                    for (int i=0; i<FtDataContainer.getRowCount(); i++){
                        for(int j=0; j<FtDataContainer.getCellCount(i);j++){
                            queryContent = queryContent+FtDataContainer.getText(i, j)+",";
                        }
                        queryContent = queryContent.substring(0, queryContent.length()-1)+"\n";

                    }
                    UtilsRPC.getService("mysqlservice").SaveCSV(queryContent, DownloadCSV);
                }
            }
        });
                                        
        RegionFilter.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                final AsyncCallback<String[][]> InitVarBox = new AsyncCallback<String[][]>() {
                    public void onSuccess(String[][] out) {
                        VariableFilter.clear();
                        if (out.length<=1){
                            VariableFilter.addItem("No Data Available");
                        }
                        else{
                            try{
                                for (int i = 1;i<out.length;i++){
                                    VariableFilter.addItem(out[i][0]+' '+out[i][1], out[i][2]);
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

                final AsyncCallback<String[][]> InitVarGrpBox = new AsyncCallback<String[][]>() {
                    public void onSuccess(String[][] out) {
                        VarGroupFilter.clear();
                        if (out.length<=1){
                            VarGroupFilter.addItem("No Data Available");
                        }
                        else{
                            try{
                                for (int i = 1;i<out.length;i++){
                                    VarGroupFilter.addItem(out[i][0]+" ("+out[i][1]+")", out[i][2]);
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

                if (getSelectedItemCount(RegionFilter)<=142578){
                    String selreg = "";
                    YearFilter.clear();
                    VariableFilter.clear();

                    for (int i=0; i<RegionFilter.getItemCount(); i++){
                        if (RegionFilter.isItemSelected(i)){
                            selreg += "'"+RegionFilter.getValue(i)+"',";
                        }
                    }
                
                    if (!selreg.equals("") && LbRegLevel.getSelectedIndex()!=2){
                        selreg = selreg.substring(0, selreg.length()-1);
                        String que = "SELECT v.var_name, v.unit, d.var_code FROM "+srctable+" d natural join "+varlisttable+" v where iso3 in ("+selreg+") AND v.group_code<>'SND' AND v.show_flag=1 GROUP BY d.var_code ORDER BY v.var_name";
                        UtilsRPC.getService("mysqlservice").RunSELECT(que, InitVarBox);
                        String que2 = "SELECT v.var_name, v.unit, d.var_code FROM "+srctable+" d natural join "+varlisttable+" v where iso3 in ("+selreg+") AND v.group_code='SND' AND v.show_flag=1 GROUP BY d.var_code ORDER BY v.var_name";
                        UtilsRPC.getService("mysqlservice").RunSELECT(que2, InitVarGrpBox);
                        changeSDSelectionStatus(true);
                        changeVarSelectionStatus(true);
                    } else if (!selreg.equals("") && LbRegLevel.getSelectedIndex()==2){
                        selreg = selreg.substring(0, selreg.length()-1);
                        String que = "SELECT v.var_name, v.unit, v.var_code FROM "+varlisttable+" v";
                        UtilsRPC.getService("mysqlservice").RunSELECT(que, InitVarGrpBox);
                        changeSDSelectionStatus(true);
                        changeVarSelectionStatus(false);
                    } else {
                        VariableFilter.clear();
                        changeVarSelectionStatus(false);
                    }
                    BtFetchData.setEnabled(false);
                    BtCSV.setEnabled(false);
                    changeYearSelectionStatus(false);
                    selectedCountries=selreg;
                } else {
                    DecoratedPopupPanel warn = new DecoratedPopupPanel(true);
                    warn.setWidget(new HTML("Maximum selected items exceded.<br /> Ignoring last selected item."));
                    warn.setWidth("150px");
                    warn.show();
                    //RegionFilter.setItemSelected(RegionFilter., false);
                }
            }
        });
        
        VariableFilter.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                YearFilter.clear();
                BtFetchData.setEnabled(false);
                BtCSV.setEnabled(false);
                updateYearBox();
            }            
        });

        VarGroupFilter.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                YearFilter.clear();
                BtFetchData.setEnabled(false);
                BtCSV.setEnabled(false);
                updateYearBox();
            }
        });

        
        YearFilter.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
                BtFetchData.setEnabled(true);
            }
        });        
    }
                
    public void changeVarSelectionStatus(boolean status){
        VariableFilter.setEnabled(status);
    }
    
    public void changeSDSelectionStatus(boolean status){
        VarGroupFilter.setEnabled(status);
    }
    public void changeYearSelectionStatus(boolean status){
        YearFilter.setEnabled(status);
    }

    public void ClearFlexTable(FlexTable tab){
        for (int i=tab.getRowCount()-1;i>=0;i--){
            tab.removeRow(i);
        }
    }
    
    public void ClearTabPanel (TabPanel tp){
        for (int i=tp.getWidgetCount()-1; i>0; i-- ){
            tp.remove(i);
        }
    }

    private void cleanTabs(){
        int widgets = getWidgetCount();
        if (widgets>2){
            for (int i=widgets-2;i>0; i--){
                getWidget(i).removeFromParent();
            }
       }
       
    }
    
    private int getSelectedItemCount(ListBox lb){
        int selected = 0;
        for (int i=0;i<lb.getItemCount()-1;i++){
            if(lb.isItemSelected(i)) selected++;
        }
        return selected;
    }

    private void updateYearBox(){
        final AsyncCallback<String[][]> InitYearBox = new AsyncCallback<String[][]>() {
            public void onSuccess(String[][] out) {
                YearFilter.clear();
                try{
                    for (int i = 1;i<out.length;i++){
                        YearFilter.addItem(out[i][0]);
                    }
                }
                catch(Exception e){
                    System.err.println(e);
                }
            }

            public void onFailure(Throwable caught) {
                 System.out.println("Communication failed (RDV.InitYearBox)");
            }
        };

        String selvars = "";

        for (int i=0; i<VariableFilter.getItemCount(); i++){
                if (VariableFilter.isItemSelected(i)){
                    selvars += "'"+VariableFilter.getValue(i)+"',";
                }
        }

        for (int i=0; i<VarGroupFilter.getItemCount(); i++){
                if (VarGroupFilter.isItemSelected(i)){
                    selvars += "'"+VarGroupFilter.getValue(i)+"',";
                }
        }
        
        if (!selvars.equals("") && LbRegLevel.getSelectedIndex()!=2){
            selvars = selvars.substring(0, selvars.length()-1);
            UtilsRPC.getService("mysqlservice").RunSELECT("SELECT yr FROM "+srctable+" d where iso3 in ("+selectedCountries+") and var_code in ("+selvars +") GROUP BY yr DESC", InitYearBox);
            changeYearSelectionStatus(true);
        } else if (!selvars.equals("") && LbRegLevel.getSelectedIndex()==2){
            selvars = selvars.substring(0, selvars.length()-1);
            UtilsRPC.getService("mysqlservice").RunSELECT("SELECT yr FROM "+srctable+" d natural join locales l where l.iso3 in ("+selectedCountries+") GROUP BY yr DESC", InitYearBox);
            changeYearSelectionStatus(true);
        }
        else {
            changeYearSelectionStatus(false);
        }
    }
}
