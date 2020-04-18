package com.sam.tillsystem.api;

import java.util.List;

import be.ceau.chart.Chart;

/**
 * The charting Service API 
 * @author Sam
 *
 */
public interface ChartApi {

	/**
	 * Used to see what charts are able to be created within the system
	 * @return a list of strings that can be used to get chart data
	 */
	public List<String> availableCharts();
	
	/**
	 * Takes a given string and returns the chart associated with it
	 * @param chart the string representing the chart
	 * @return the generated {@link Chart}
	 */
	public Chart getChart(String chart);
	
}
