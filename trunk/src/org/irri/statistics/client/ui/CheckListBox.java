package org.irri.statistics.client.ui;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class CheckListBox extends Composite implements HasChangeHandlers {
	private VerticalPanel vpListBox;
	private HorizontalPanel hpControls;

	public CheckListBox() {
		
		
		ScrollPanel scroller = new ScrollPanel();
		initWidget(scroller);
		
		vpListBox = new VerticalPanel();
		scroller.setWidget(vpListBox);
		vpListBox.setSize("100%", "80%");
		setStyleName("wrs-checklistbox");
		
		hpControls = new HorizontalPanel();
		hpControls.setSpacing(1);
		
		Button btnCheckAll = new Button("Check All");
		btnCheckAll.setStyleName("wrs-checklistbox-button");
		btnCheckAll.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (vpListBox.getWidgetCount()>0){
					for (int i = 0; i < vpListBox.getWidgetCount(); i++) {
						CheckBox thiscb = (CheckBox) vpListBox.getWidget(i);
						thiscb.setValue(true,true);
					}
				}
				
			}
		});
		hpControls.add(btnCheckAll);
		
		Button btnUncheckAll = new Button("Uncheck All");
		btnUncheckAll.setStyleName("wrs-checklistbox-button");
		btnUncheckAll.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (vpListBox.getWidgetCount()>0){
					for (int i = 0; i < vpListBox.getWidgetCount(); i++) {
						CheckBox thiscb = (CheckBox) vpListBox.getWidget(i);
						thiscb.setValue(false,true);
					}
				}
			}
		});
		hpControls.add(btnUncheckAll);
		hpControls.setCellHeight(btnUncheckAll, "10%");		
	}
	
	public void addItem(String item, String val){
		CheckBox cbItem = new CheckBox(item);
		cbItem.setName(val);
		cbItem.setValue(true);
		vpListBox.add(cbItem);
	}

	protected VerticalPanel getVpListBox() {
		return vpListBox;
	}
	
	public int getItemCount(){
		return vpListBox.getWidgetCount();
	}

	public CheckBox getItem(int index){
		return (CheckBox) vpListBox.getWidget(index);
	}
	
	public boolean isItemChecked(int index){
		CheckBox thisitem = getItem(index);
		return thisitem.getValue();
	}
	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}
	
	public String csSelectedNames(boolean quote){
		String selitems = "";
		for (int i = 0; i < vpListBox.getWidgetCount(); i++) {
			CheckBox chkbox = (CheckBox) vpListBox.getWidget(i);
			if (chkbox.getValue()){
				if (quote) 
					selitems += ("'" + chkbox.getName() + "',");
				else 
					selitems += chkbox.getName();					
			}
		}		
		return selitems.substring(0, selitems.length()-1);
	}
	public HorizontalPanel getHpControls() {
		return hpControls;
	}
}
