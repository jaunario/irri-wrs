package org.irri.statistics.client.ui;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;

public class ResultDataTable{
	
	public static AbstractDataTable create(String[][] data, int[] numcols){
		DataTable datatable = GWT.create(null);
		Arrays.sort(numcols);
		for (int i = 0; i < data.length; i++) {
			if(i==1) datatable.addRows(data.length); 
			for (int j = 0; j < data[i].length; j++) {
				boolean isnumeric = Arrays.binarySearch(numcols, j) < numcols.length;
				if (i==0){
					// Add column
					if (isnumeric) datatable.addColumn(ColumnType.NUMBER,data[i][j]);
					else datatable.addColumn(ColumnType.STRING,data[i][j]);
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
		DataTable datatable = GWT.create(null);
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
	
	public static AbstractDataTable regroup(String[][] data, int grpcol, int xcol, int ycol){
		DataTable datatable = GWT.create(null);
				
		return datatable;
	}
	
	
	
	
}
