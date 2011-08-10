package org.irri.statistics.client;




public class WRS_DataClasses {

	public static class CountryStat implements java.lang.Comparable<CountryStat>{
		private String country;
		private int year;		
		private float[] values;
		
		@Override
		public int compareTo(CountryStat o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public CountryStat(String countryname, int yr, float[] varvalues){
			country = countryname;
			year = yr;
			values = varvalues;
		}
		
		public String getCountry(){
			return country;
		}
		
		public int getYear(){
			return year;
		}
		
		public float getVarValue(int i){
			return values[i];
		}
		
		public void setCountry(String cntry){
			country = cntry;
		}
		
		public void setYear(int yr){
			year = yr;
		}
		
		public void setVarValue(int idx, float val){
			values[idx]=val;
		}

	}
}
