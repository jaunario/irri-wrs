package org.irri.statistics.client.ui.charts;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HTML;

public class ChartOptions extends DialogBox {
	private Button btnOk;
	AbstractDataTable ChartData;
	private ListBox cbbChartType;
	private TextBox txtbxChartTitle;
	private ListBox cbbLPosition;
	private ListBox cbbX;
	private ListBox cbbY;
	private ListBox cbbSeries;
	private Image image;
	private HTML htmlChartDesc;
	private CheckBox chckbxInteractive;
	/**
	 * @wbp.parser.constructor
	 */
	public ChartOptions() {
		setWidth("556px");
		setGlassEnabled(true);
		setHTML("Chart Options");
		initDialog();
		center();
		show();

	}
	
	public ChartOptions(AbstractDataTable basedata, int w, int h) {
		setWidth("400px");
		setGlassEnabled(true);
		setHTML("Chart Options");
		initDialog();
		center();
		show();

	}

	public void initDialog(){
		DockPanel dockPanel = new DockPanel();
		setWidget(dockPanel);
		dockPanel.setSize("564px", "254px");
		
		HorizontalPanel hpChartSubmit = new HorizontalPanel();
		hpChartSubmit.setSpacing(5);
		dockPanel.add(hpChartSubmit, DockPanel.SOUTH);
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
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("notes");
		verticalPanel.setSpacing(2);
		verticalPanel.setBorderWidth(0);
		dockPanel.add(verticalPanel, DockPanel.WEST);
		verticalPanel.setSize("204px", "100%");
		
		image = new Image("images/pie.png");
		verticalPanel.add(image);
		image.setSize("188px", "112px");
		
		htmlChartDesc = new HTML("Shows percentage values as a slice of a pie", true);
		verticalPanel.add(htmlChartDesc);
		htmlChartDesc.setSize("178px", "47px");
		
		Grid gridChartComponents = new Grid(7, 2);
		gridChartComponents.setCellPadding(5);
		dockPanel.add(gridChartComponents, DockPanel.CENTER);
		gridChartComponents.setSize("100%", "100%");
		
		Label lblChartType = new Label("Chart Type");
		gridChartComponents.setWidget(0, 0, lblChartType);
		
		cbbChartType = new ListBox();
		cbbChartType.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int selitem = cbbChartType.getSelectedIndex();
				switch (selitem) {
				case 1:
					image.setUrl("images/scatter.png");
					htmlChartDesc.setHTML("Scatter Plot");
					chckbxInteractive.setEnabled(false);
					chckbxInteractive.setValue(false);
					break;

				case 2:
					image.setUrl("images/line.png");
					htmlChartDesc.setHTML("Line Chart");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(false);
					break;
				case 3:
					image.setUrl("images/column.png");
					htmlChartDesc.setHTML("Column Chart");
					chckbxInteractive.setEnabled(false);
					chckbxInteractive.setValue(false);
					break;
				case 4:
					image.setUrl("images/bar.png");
					htmlChartDesc.setHTML("Bar Chart");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(false);
					break;
				case 5:
					image.setUrl("images/area.png");
					htmlChartDesc.setHTML("Area Chart");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(false);
					break;
				case 6:
					image.setUrl("images/geomap.png");
					htmlChartDesc.setHTML("GeoMap Chart");
					chckbxInteractive.setEnabled(false);
					chckbxInteractive.setValue(false);
					break;
				default:
					image.setUrl("images/pie.png");
					htmlChartDesc.setHTML("Shows percentage values as a slice of a pie");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(false);
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
		
		chckbxInteractive = new CheckBox("Interactive");
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
	public Image getImage() {
		return image;
	}
	public HTML getHtmlChartDesc() {
		return htmlChartDesc;
	}
	public CheckBox getChckbxInteractive() {
		return chckbxInteractive;
	}
}
