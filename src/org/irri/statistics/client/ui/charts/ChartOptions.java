package org.irri.statistics.client.ui.charts;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class ChartOptions extends DialogBox {
	private Button btnOk;
	AbstractDataTable ChartData;
	private SimplePanel theChart = new SimplePanel();
	private ListBox cbbChartType;
	private TextBox txtbxChartTitle;
	private ListBox cbbLPosition;
	private ListBox cbbX;
	private ListBox cbbY;
	private ListBox cbbSeries;
	/**
	 * @wbp.parser.constructor
	 */
	public ChartOptions() {
		setWidth("400px");
		setGlassEnabled(true);
		setHTML("New Chart Options");
		initDialog();
		center();
		show();

	}
	
	public ChartOptions(AbstractDataTable basedata, int w, int h) {
		setWidth("400px");
		setGlassEnabled(true);
		setHTML("New Chart Options");
		initDialog();
		center();
		show();

	}

	public void initDialog(){
		VerticalPanel dockPanel = new VerticalPanel();
		setWidget(dockPanel);
		dockPanel.setSize("378px", "214px");
		
		Grid gridChartComponents = new Grid(7, 2);
		dockPanel.add(gridChartComponents);
		gridChartComponents.setSize("100%", "100%");
		
		Label lblChartType = new Label("Chart Type");
		gridChartComponents.setWidget(0, 0, lblChartType);
		
		cbbChartType = new ListBox();
		cbbChartType.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int selitem = cbbChartType.getSelectedIndex();
				switch (selitem) {
				case 1:
					
					break;

				default:
					cbbSeries.setEnabled(false);
					break;
				}
			}
		});
		gridChartComponents.setWidget(0, 1, cbbChartType);
		cbbChartType.addItem("Pie Chart");
		cbbChartType.addItem("Scatter Plot");
		cbbChartType.addItem("Line Graph");
		cbbChartType.addItem("Column Chart");
		cbbChartType.addItem("Bar Chart");
		cbbChartType.addItem("Area Chart");
		cbbChartType.addItem("Map");
		cbbChartType.addItem("");
		
		CheckBox chckbxInteractive = new CheckBox("Interactive");
		gridChartComponents.setWidget(1, 1, chckbxInteractive);
		
		Label lblTitle = new Label("Title");
		gridChartComponents.setWidget(2, 0, lblTitle);
		
		txtbxChartTitle = new TextBox();
		txtbxChartTitle.setText("Chart Title");
		gridChartComponents.setWidget(2, 1, txtbxChartTitle);
		txtbxChartTitle.setWidth("274px");
		
		Label lblLegendPosition = new Label("Legend Position");
		lblLegendPosition.setWordWrap(false);
		gridChartComponents.setWidget(3, 0, lblLegendPosition);
		
		cbbLPosition = new ListBox();
		cbbLPosition.addItem("Top");
		cbbLPosition.addItem("Right");
		cbbLPosition.addItem("Bottom");
		cbbLPosition.addItem("Inside");
		cbbLPosition.addItem("None");
		gridChartComponents.setWidget(3, 1, cbbLPosition);
		
		Label lblX = new Label("X");
		gridChartComponents.setWidget(4, 0, lblX);
		
		cbbX = new ListBox();
		gridChartComponents.setWidget(4, 1, cbbX);
		
		Label lblY = new Label("Y");
		gridChartComponents.setWidget(5, 0, lblY);
		
		cbbY = new ListBox();
		gridChartComponents.setWidget(5, 1, cbbY);
		
		Label lblSeries = new Label("Series");
		gridChartComponents.setWidget(6, 0, lblSeries);
		
		cbbSeries = new ListBox();
		gridChartComponents.setWidget(6, 1, cbbSeries);
		
		HorizontalPanel hpChartSubmit = new HorizontalPanel();
		hpChartSubmit.setSpacing(5);
		dockPanel.add(hpChartSubmit);
		dockPanel.setCellHorizontalAlignment(hpChartSubmit, HasHorizontalAlignment.ALIGN_RIGHT);
		
		btnOk = new Button("Ok");
		hpChartSubmit.add(btnOk);
		hpChartSubmit.setCellHorizontalAlignment(btnOk, HasHorizontalAlignment.ALIGN_RIGHT);
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		hpChartSubmit.add(btnCancel);
		hpChartSubmit.setCellHorizontalAlignment(btnCancel, HasHorizontalAlignment.ALIGN_RIGHT);
				
	}
	
	public void setOkButtonClickHandler(ClickHandler handler){
		btnOk.addClickHandler(handler);
	}
	
	public Button getBtnOk() {
		return btnOk;
	}
	public ListBox getCbbChartType() {
		return cbbChartType;
	}
	public TextBox getTxtbxChartTitle() {
		return txtbxChartTitle;
	}
	public ListBox getCbbLPosition() {
		return cbbLPosition;
	}
	public ListBox getCbbX() {
		return cbbX;
	}
	public ListBox getCbbY() {
		return cbbY;
	}
	public ListBox getCbbSeries() {
		return cbbSeries;
	}
}
