package org.irri.statistics.client.ui.charts;

import java.util.Arrays;
import java.util.Vector;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.formatters.NumberFormat;
import com.google.gwt.visualization.client.formatters.NumberFormat.Options;

public class ChartDataTable{
	
	public static AbstractDataTable create(String[][] data, int[] numcols){
		DataTable datatable = DataTable.create();
		boolean isnumeric = false;
		int searchcol = 0;
		Arrays.sort(numcols);
		
		Options options = Options.create();
		options.setFractionDigits(2);
		options.setNegativeColor("red");
		NumberFormat formatter = NumberFormat.create(options);
		
		for (int i = 0; i < data.length; i++) {
			if(i==1) datatable.addRows(data.length); 
			for (int j = 0; j < data[i].length; j++) {
				searchcol = Arrays.binarySearch(numcols, j);
				isnumeric = searchcol  >= 0 & searchcol < numcols.length;
				if (i==0){
					// Add column
					if (isnumeric) {
						datatable.addColumn(ColumnType.NUMBER,data[i][j]);
						formatter.format(datatable, i);						
					} else datatable.addColumn(ColumnType.STRING,data[i][j]);
				} else {
					// Add record row
					if (data[i][j]!= null){
						if (isnumeric) datatable.setValue(i-1, j, Float.parseFloat(data[i][j]));
						else datatable.setValue(i-1, j, data[i][j]);
					}
				}
			}
		}
		return datatable;
	}
	
	public static AbstractDataTable createFilteredTable (String[][] data, String filterval){
		DataTable datatable = DataTable.create();
		for (int i = 0; i < data.length; i++) {
			if(data[i][0].equalsIgnoreCase(filterval)) {
				datatable.addRow();
			}
			for (int j = 1; j < data[i].length; j++) {
				if (i==0){
					if (j==1) datatable.addColumn(ColumnType.STRING, data[i][j]);
					else datatable.addColumn(ColumnType.NUMBER, data[i][j]);
				} else {
					if (j==1) datatable.setValue(i-1, j-1, data[i][j]);
					else datatable.setValue(i-1, j-1, Float.parseFloat(data[i][j]));
				}
			}
		}
		
		return datatable;
	}
	
	public static String[] getUniqueColumnVals(String[][] data, int col){
		
		Vector<String> uniquev = new Vector<String>();
		for (int i = 1; i < data.length; i++) {
			if(!uniquev.contains(data[i][col])) {
				uniquev.add(data[i][col]);
			}
		}
		String[] unique = new String[uniquev.size()];
		for (int i = 0; i < uniquev.size(); i++) {
			unique[i] = uniquev.elementAt(i);
		}
		return(unique);
	}

	public static AbstractDataTable regroup(String[][] data, int valcol){
		String[] grps = getUniqueColumnVals(data, 0);
		Arrays.sort(grps);
		String[] xs = getUniqueColumnVals(data, 1);
		Arrays.sort(xs);
		//xs = Arrays
		 
		DataTable datatable = DataTable.create();
		datatable.addColumn(ColumnType.STRING, data[0][1]);
		for (int i = 0; i < grps.length; i++) {
			datatable.addColumn(ColumnType.NUMBER, grps[i]);
		}
		datatable.addRows(xs.length);
		
		for (int i = 0; i < xs.length; i++) {
			datatable.setValue(i, 0, xs[i]);
		}
		
		int r = 0; 
		int curcol  = 0;

		for (int i = 1; i < data.length; i++) {		
			curcol = Arrays.binarySearch(grps, data[i][0]);
			r = Arrays.binarySearch(xs, data[i][1]);
			if(r<xs.length & r>=0 & data[i][valcol]!=null) {
				datatable.setValue(r, curcol+1, Float.parseFloat(data[i][valcol]));
			} 
		}		
		return datatable;
	}
	
	public static String csvData(String[][] data){
		String csv = "";
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				csv = csv + data[i][j];
				if (j<data[i].length-1) csv = csv + ",";
			}
			csv = csv + "\n";			
		}
		return csv;
	}
}
