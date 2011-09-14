package org.irri.statistics.client.ui.charts;


import java.util.ArrayList;
import java.util.Collections;

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
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HTML;

public class ChartOptions extends DialogBox {
	private Button btnOk;
	AbstractDataTable BaseData;
	private ListBox cbbChartType;
	private TextBox txtbxChartTitle;
	private ListBox cbbLPosition;
	private ListBox cbbX;
	private ListBox cbbY;
	private ListBox cbbSeries;
	private Image image;
	private HTML htmlChartDesc;
	private CheckBox chckbxInteractive;
	
	private int itemid;
	private int width;
	private int height;
	
	private SimplePanel chart;
	
	private ArrayList<String> catcols;
	private ArrayList<String> regions;
	private ArrayList<String> variables;
	private ArrayList<String> years;
	private Label lblChartType;
	private Label lblSeries;
	private Label lblY;
	private Label lblX;
	
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
		itemid = 0;

	}
	
	public ChartOptions(AbstractDataTable basedata, int w, int h,int index) {
		width = w;
		height = h;
		itemid = index;
		BaseData = basedata;
		setWidth("400px");
		setGlassEnabled(true);
		setHTML("Chart Options");
		initDialog();
		getYears(basedata);
		getRegions(basedata);
		getVariables(basedata);
		getCategorizeColumns(basedata);
		lblSeries.setText("year");
		populateListBox(cbbSeries, years);		
		populateListBox(cbbY, variables);
		lblX.setText("Parts");
		cbbX.clear();
		cbbX.addItem("region");
		cbbX.setEnabled(false);
		center();
		show();

	}

	public void initDialog(){
		DockPanel dockPanel = new DockPanel();
		setWidget(dockPanel);
		dockPanel.setSize("564px", "254px");
		
		chart = new SimplePanel();
		HorizontalPanel hpChartSubmit = new HorizontalPanel();
		hpChartSubmit.setSpacing(5);
		dockPanel.add(hpChartSubmit, DockPanel.SOUTH);
		dockPanel.setCellHorizontalAlignment(hpChartSubmit, HasHorizontalAlignment.ALIGN_RIGHT);
		
		btnOk = new Button("Ok");
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selchart = cbbChartType.getSelectedIndex();
				switch (selchart) {
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;
				case 6:
					
					break;

				default:
							
					final AbstractDataTable ChartData = ChartDataTable.filteredTable(BaseData, 0, cbbY.getSelectedIndex()+2, 1, cbbSeries.getItemText(cbbSeries.getSelectedIndex()));
					if (chckbxInteractive.getValue()){
						Runnable onLoadCallback = new Runnable() {
							
							@Override
							public void run() {
								PieChart pie = new PieChart(ChartData, createCoreChartOptions(txtbxChartTitle.getText(), getLegendPosition(), width, height));
								chart.add(pie);
							}
						};
						VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
					} else {
						
					}
					
					break;
				}
				hide();
			}
		});
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
		
		lblChartType = new Label("Chart Type");
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
					chckbxInteractive.setValue(true);
					lblX.setText("X");
					populateListBox(cbbX, variables);
					cbbX.setEnabled(true);
					lblSeries.setText("Series");
					cbbSeries.clear();
					cbbSeries.addItem("region");
					cbbSeries.setEnabled(false);
					break;

				case 2:
					image.setUrl("images/line.png");
					htmlChartDesc.setHTML("Line Chart");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(true);
					commonChartOptions();
					break;
				case 3:
					image.setUrl("images/column.png");
					htmlChartDesc.setHTML("Column Chart");
					chckbxInteractive.setEnabled(false);
					chckbxInteractive.setValue(true);
					commonChartOptions();
					break;
				case 4:
					image.setUrl("images/bar.png");
					htmlChartDesc.setHTML("Bar Chart");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(true);
					commonChartOptions();
					break;
				case 5:
					image.setUrl("images/area.png");
					htmlChartDesc.setHTML("Area Chart");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(true);
					commonChartOptions();
					break;
				case 6:
					image.setUrl("images/geomap.png");
					htmlChartDesc.setHTML("GeoMap Chart");
					chckbxInteractive.setEnabled(false);
					chckbxInteractive.setValue(true);
					lblSeries.setText("year");
					populateListBox(cbbSeries, years);
					cbbSeries.setEnabled(true);
					lblX.setText("Parts");
					cbbX.clear();
					cbbX.addItem("region");
					cbbX.setEnabled(false);
					break;
				default:
					image.setUrl("images/pie.png");
					htmlChartDesc.setHTML("Shows percentage values as a slice of a pie");
					chckbxInteractive.setEnabled(true);
					chckbxInteractive.setValue(true);
					lblSeries.setText("year");
					populateListBox(cbbSeries, years);
					cbbSeries.setEnabled(true);
					lblX.setText("Parts");
					cbbX.clear();
					cbbX.addItem("region");
					cbbX.setEnabled(false);
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
		chckbxInteractive.setValue(true);
		
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
		cbbLPosition.addItem("Left");
		cbbLPosition.addItem("None");
		gridChartComponents.setWidget(3, 1, cbbLPosition);
		
		lblX = new Label("X");
		gridChartComponents.setWidget(4, 0, lblX);
		
		cbbX = new ListBox();
		gridChartComponents.setWidget(4, 1, cbbX);
		
		lblY = new Label("Value");
		gridChartComponents.setWidget(5, 0, lblY);
		
		cbbY = new ListBox();
		gridChartComponents.setWidget(5, 1, cbbY);
		
		lblSeries = new Label("Series");
		gridChartComponents.setWidget(6, 0, lblSeries);
		
		cbbSeries = new ListBox();
		gridChartComponents.setWidget(6, 1, cbbSeries);
	}
	
	private void commonChartOptions(){
		lblX.setText("X");
		populateListBox(cbbX, catcols);
		cbbX.setEnabled(true);
		lblSeries.setText("Series");
		populateListBox(cbbSeries, catcols);
		cbbSeries.setEnabled(true);
	}
	
	private LegendPosition getLegendPosition(){
		int lp = cbbLPosition.getSelectedIndex();
		LegendPosition lgpos;
		switch (lp) {
		case 1:
			lgpos = LegendPosition.RIGHT; 
			break;
		case 2:
			lgpos = LegendPosition.BOTTOM; 
			break;
		case 3:
			lgpos = LegendPosition.LEFT; 
			break;
		case 4:
			lgpos = LegendPosition.NONE; 
			break;

		default:
			lgpos = LegendPosition.TOP;
			break;
		}
		return lgpos;
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
	
	public int getItemID(){
		return itemid;
	}
	
	public SimplePanel getChart() {
		return chart;
	}
	
	private void getYears(AbstractDataTable basedata){
		years = ChartDataTable.getUniqueColumnVals(basedata, 1);
		Collections.sort(years);
	}
	
	private void getRegions(AbstractDataTable basedata){
		regions = ChartDataTable.getUniqueColumnVals(basedata, 0);
		Collections.sort(regions);
	}
	
	private void getVariables(AbstractDataTable basedata){
		variables = new ArrayList<String>();
		for (int i = 2; i < basedata.getNumberOfColumns(); i++) {
			variables.add(basedata.getColumnLabel(i));
		}
	}
	
	private void getCategorizeColumns(AbstractDataTable basedata){
		catcols = new ArrayList<String>();
		for (int i = 0; i < basedata.getNumberOfColumns(); i++) {
			if(basedata.getColumnType(i)!=ColumnType.NUMBER) catcols.add(basedata.getColumnLabel(i));
		}
	}

	private void populateListBox(ListBox listbox, ArrayList<String> items){
		listbox.clear();
		for (int i = 0; i < items.size(); i++) {
			listbox.addItem(items.get(i));
		}
	}
	
	public Options createCoreChartOptions(String title, LegendPosition legendpos, int w, int h){
		Options options = Options.create();
		options.setWidth(w);
		options.setHeight(h);
		options.setTitle(title);
		options.setLegend(legendpos);
		options.set("is3D", "true");
		return options;
		
	}
	

	public Label getLblChartType() {
		return lblChartType;
	}
	public Label getLblSeries() {
		return lblSeries;
	}
	public Label getLblY() {
		return lblY;
	}
	public Label getLblX() {
		return lblX;
	}
}
