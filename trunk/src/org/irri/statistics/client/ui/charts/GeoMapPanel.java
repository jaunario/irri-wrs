package org.irri.statistics.client.ui.charts;

import org.irri.statistics.client.utils.RPCUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.GeoMap.DataMode;
import com.google.gwt.visualization.client.visualizations.GeoMap.Options;

public class GeoMapPanel extends Composite{
    private VerticalPanel MapBox = new VerticalPanel();

    public GeoMapPanel(String query, int w, int h){
    	final int width = w;
    	final int height = h;
        final AsyncCallback<String[][]> ChartAsyncCallback = new AsyncCallback<String[][]>() {

            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void onSuccess(String[][] result) {
                final String[][] out = result;
                Runnable onLoadCallback = new Runnable() {

                    public void run() {
                        GeoMap map = new  GeoMap(createTable(out), createOptions(width, height));
                        MapBox.add(map);
                    }
                };
                VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
            }
        };
        RPCUtils.getService("mysqlservice").RunSELECT(query, ChartAsyncCallback);
        initWidget(MapBox);
    }

    private Options createOptions(int w, int h) {
        Options options = Options.create();
        options.setWidth(w);
        options.setHeight(h);
        options.setShowLegend(true);
        options.setShowZoomOut(true);
        options.setDataMode(DataMode.REGIONS);
        options.setColors(0xAAAAAA,0xCE0000,0xFF9E00,0xF7D708,0x9CCF31);
        return options;
    }

    private AbstractDataTable createTable(String[][] qdata){
        DataTable data = DataTable.create();
        data.addColumn(AbstractDataTable.ColumnType.STRING, qdata[0][0]);
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, qdata[0][1]);
        data.addRows(qdata.length-1);
        for (int i=1;i<qdata.length;i++){
            data.setValue(i-1, 0, qdata[i][0]);
            data.setValue(i-1, 1, Double.parseDouble(qdata[i][1]));
        }
        return data;
    }

}
