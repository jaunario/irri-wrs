package org.irri.statistics.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;

public class Slider extends Composite {
	private DeckPanel deckPanel;
	private DecoratedTabBar tabBar;
	private Timer transition = new Timer() {

        @Override
        public void run() {
            int index = deckPanel.getVisibleWidget();
            index++;
            if (index == deckPanel.getWidgetCount()) index = 0;
            showSlide(index);
        }
    };
	private ToggleButton pshbtnPlay;
	
	public Slider() {
		VerticalPanel vpWrapper = new VerticalPanel();
		vpWrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		vpWrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(vpWrapper);
		
		deckPanel = new DeckPanel();
		vpWrapper.add(deckPanel);
		deckPanel.setSize("100%", "100%");
		
		HorizontalPanel hpTabBar = new HorizontalPanel();
		hpTabBar.setSpacing(2);
		vpWrapper.add(hpTabBar);
		
		pshbtnPlay = new ToggleButton("Play");
		pshbtnPlay.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (pshbtnPlay.isDown()) transition.scheduleRepeating(5000);
				else stopSlide();
			}
		});
		pshbtnPlay.setDown(true);
		Image image_1 = new Image("images/play.jpg");
		image_1.setSize("15", "15");
		pshbtnPlay.getUpFace().setImage(image_1);
		Image image = new Image("images/play.jpg");
		image.setSize("15px", "15px");
		hpTabBar.add(pshbtnPlay);
		pshbtnPlay.setSize("15px", "15px");
		
		tabBar = new DecoratedTabBar();
		hpTabBar.add(tabBar);
		hpTabBar.setCellVerticalAlignment(tabBar, HasVerticalAlignment.ALIGN_MIDDLE);
		hpTabBar.setCellHorizontalAlignment(tabBar, HasHorizontalAlignment.ALIGN_CENTER);
		
		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				if (pshbtnPlay.isDown()){
					transition.scheduleRepeating(5000);
				} else {
					deckPanel.showWidget(event.getSelectedItem()-1);
					stopSlide();
				}				
			}
		});
		
		vpWrapper.setCellVerticalAlignment(tabBar, HasVerticalAlignment.ALIGN_MIDDLE);
		vpWrapper.setCellHorizontalAlignment(tabBar, HasHorizontalAlignment.ALIGN_CENTER);
	}

	public DeckPanel getDeckPanel() {
		return deckPanel;
	}
	
	public void stopSlide(){
		transition.cancel();
	}

	
	public void add(Widget widget, String name){
		deckPanel.add(widget);
		tabBar.addTab(name);
		int tabid = tabBar.getTabCount()-1;
		tabBar.getTab(tabid).addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				pshbtnPlay.setDown(false);
			}
		});			
		transition.scheduleRepeating(5000);
	}

	public DecoratedTabBar getTabBar() {
		return tabBar;
	}
	
	public void showSlide(int i){
        deckPanel.showWidget(i);
        tabBar.selectTab(i);
	}
	public ToggleButton getPshbtnPlay() {
		return pshbtnPlay;
	}
}
