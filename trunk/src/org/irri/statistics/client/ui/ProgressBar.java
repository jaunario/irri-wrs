package org.irri.statistics.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;

public class ProgressBar extends Composite {

	public ProgressBar() {
		
		Grid grid = new Grid(1, 10);
		initWidget(grid);
		grid.setSize("200px", "18px");
		
		Image image = new Image("images/greenbox.jpg");
		grid.setWidget(0, 0, image);
		image.setSize("15px", "15px");
		
		Image image_1 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 1, image_1);
		image_1.setSize("15px", "15px");
		
		Image image_2 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 2, image_2);
		image_2.setSize("15px", "15px");
		
		Image image_3 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 3, image_3);
		image_3.setSize("15px", "15px");
		
		Image image_4 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 4, image_4);
		image_4.setSize("15px", "15px");
		
		Image image_5 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 5, image_5);
		image_5.setSize("15px", "15px");
		
		Image image_6 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 6, image_6);
		image_6.setSize("15px", "15px");
		
		Image image_7 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 7, image_7);
		image_7.setSize("15px", "15px");
		
		Image image_8 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 8, image_8);
		image_8.setSize("15px", "15px");
		
		Image image_9 = new Image("images/greenbox.jpg");
		grid.setWidget(0, 9, image_9);
		image_9.setSize("15px", "15px");
	}

}
