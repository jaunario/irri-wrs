package org.irri.statistics.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class QueryOptions extends PopupPanel {
	private IntegerBox ibRegionLimit;
	private IntegerBox ibVarLimit;
	private IntegerBox ibYrLimit;
	private CheckBox chckbxLimitRegion;
	private CheckBox chckbxLimitRecords;
	private CheckBox chckbxLimitYrs;
	private CheckBox chckbxLimitVars;
	private IntegerBox ibRecLimit;
	private Button btnResetDefaults;
	private HorizontalPanel horizontalPanel;
	private Button btnClose;

	public QueryOptions() {
		setGlassEnabled(true);
		
		Grid grid = new Grid(6, 2);
		add(grid);
		grid.setSize("276px", "159px");
		
		chckbxLimitRegion = new CheckBox("Limit no. of selected regions");
		chckbxLimitRegion.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(chckbxLimitRegion.getValue()) ibRegionLimit.setEnabled(true);
				else ibRegionLimit.setEnabled(false);
			}
		});
		grid.setWidget(0, 0, chckbxLimitRegion);
		
		ibRegionLimit = new IntegerBox();
		ibRegionLimit.setText("15");
		ibRegionLimit.setEnabled(false);
		grid.setWidget(0, 1, ibRegionLimit);
		ibRegionLimit.setWidth("50px");
		
		chckbxLimitVars = new CheckBox("Limit no. of selected variables");
		chckbxLimitVars.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(chckbxLimitVars.getValue()) ibVarLimit.setEnabled(true);
				else ibVarLimit.setEnabled(false);
			}			
		});
		chckbxLimitVars.setValue(true, true);
		grid.setWidget(1, 0, chckbxLimitVars);
		
		ibVarLimit = new IntegerBox();
		ibVarLimit.setText("5");
		grid.setWidget(1, 1, ibVarLimit);
		ibVarLimit.setWidth("50px");
		
		chckbxLimitYrs = new CheckBox("Limit no. of selected years");
		chckbxLimitYrs.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(chckbxLimitYrs.getValue()) ibYrLimit.setEnabled(true);
				else ibYrLimit.setEnabled(false);
			}
		});
		grid.setWidget(2, 0, chckbxLimitYrs);
		
		ibYrLimit = new IntegerBox();
		ibYrLimit.setEnabled(false);
		ibYrLimit.setText("20");
		grid.setWidget(2, 1, ibYrLimit);
		ibYrLimit.setWidth("50px");
		
		chckbxLimitRecords = new CheckBox("Limit no. of records");
		chckbxLimitRecords.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(chckbxLimitRecords.getValue()) ibRecLimit.setEnabled(true);
				else ibRecLimit.setEnabled(false);
			}
		});
		chckbxLimitRecords.setValue(true, true);;
		grid.setWidget(3, 0, chckbxLimitRecords);
		
		ibRecLimit = new IntegerBox();
		ibRecLimit.setText("500");
		grid.setWidget(3, 1, ibRecLimit);
		ibRecLimit.setWidth("50px");
		
		horizontalPanel = new HorizontalPanel();
		grid.setWidget(5, 0, horizontalPanel);
		
		btnResetDefaults = new Button("Reset defaults");
		horizontalPanel.add(btnResetDefaults);
		
		btnClose = new Button("Close");
		btnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel.add(btnClose);
		btnResetDefaults.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ibRecLimit.setText("500");
				ibRegionLimit.setText("15");
				ibVarLimit.setText("5");
				ibYrLimit.setText("20");
				chckbxLimitRecords.setValue(true, true);
				chckbxLimitRegion.setValue(false, true);
				chckbxLimitVars.setValue(true, true);
				chckbxLimitYrs.setValue(false, true);
			}
		});
	}

	public IntegerBox getIbRegionLimit() {
		return ibRegionLimit;
	}
	public IntegerBox getIbVarLimit() {
		return ibVarLimit;
	}
	public IntegerBox getIntegerBox() {
		return ibYrLimit;
	}
	public CheckBox getChckbxLimitRegion() {
		return chckbxLimitRegion;
	}
	public CheckBox getChckbxLimitRecords() {
		return chckbxLimitRecords;
	}
	public CheckBox getChckbxLimitYrs() {
		return chckbxLimitYrs;
	}
	public CheckBox getChckbxLimitVars() {
		return chckbxLimitVars;
	}
	public IntegerBox getIbRecLimit() {
		return ibRecLimit;
	}
}
