package org.irri.statistics.client;

import org.irri.statistics.client.ui.Slider;
import org.irri.statistics.client.ui.charts.BarChartPanel;
import org.irri.statistics.client.ui.charts.GeoMapPanel;

import com.google.gwt.user.client.ui.Composite;

public class WRSHome extends Composite {
	private GeoMapPanel yieldMap =new GeoMapPanel("SELECT c.NAME_ENGLISH AS 'country', s.val AS 'Yield' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE s.var_code='RicYldUSDA' AND yr = YEAR(CURDATE())-1", 800, 600);
	private BarChartPanel bigArea = new BarChartPanel("SELECT c.NAME_ENGLISH AS 'country', s.val AS 'Harvested Area' FROM front_data s INNER JOIN countries c ON s.iso3 = c.ISO3  WHERE s.var_code='RicHa-USDA' AND yr = YEAR(CURDATE())-1 AND val IS NOT NULL ORDER BY 2 DESC LIMIT 10;", "Top 10 Largest Harvested Area", 800, 600);
	private Slider Slide = new Slider();
	public WRSHome() {
		Slide.add(yieldMap, "Rice Producers");
		Slide.add(bigArea, "Biggest Rice Areas");
		initWidget(Slide);
	}

}
