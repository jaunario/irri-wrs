package org.irri.statistics.client.utils;

import org.irri.statistics.client.WRS_filters;

import com.google.gwt.core.client.JsArray;

public class JSONUtils {

	public static final native JsArray<WRS_filters> asArrayOfFilters(String json)/*-{
    	return eval(json);
  	}-*/;

}
