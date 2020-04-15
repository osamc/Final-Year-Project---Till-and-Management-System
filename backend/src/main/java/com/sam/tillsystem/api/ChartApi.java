package com.sam.tillsystem.api;

import java.util.List;

import be.ceau.chart.Chart;

public interface ChartApi {

	public List<String> availableCharts();
	
	public Chart getChart(String chart);
	
}
