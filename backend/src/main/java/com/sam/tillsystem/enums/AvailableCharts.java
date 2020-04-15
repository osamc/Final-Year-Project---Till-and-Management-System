package com.sam.tillsystem.enums;

import java.util.HashMap;
import java.util.Map;

public enum AvailableCharts {
	CategoriesOverall("Category Performance Overall by Cost"),
	CategoriesWeek("Category Performance in the last Week by Cost"),
	CategoriesMonthly("Category Performance in the last Month by Cost"),
	CategoriesYearly("Category Performance in the last Year by Cost"),
	CategoriesOverallQuantity("Category Performance Overall by Quantity"),
	CategoriesWeekQuantity("Category Performance in the last Week by Quantity"),
	CategoriesMonthlyQuantity("Category Performance in the last Month by Quantity"),
	CategoriesYearlyQuantity("Category Performance in the last Year by Quantity"),
	top10ItemsOverall("Top 10 Products by Cost"),
	top10ItemsWeek("Top 10 Products in the last Week by Cost"),
	top10ItemsMonthly("Top 10 Products in the last Month by Cost"),
	top10ItemsYearly("Top 10 Products in the last Year by Cost"),
	top10ItemsOverallQuantity("Top 10 Products by Cost"),
	top10ItemsWeekQuantity("Top 10 Products in the last Week by Cost"),
	top10ItemsMonthlyQuantity("Top 10 Products in the last Month by Cost"),
	top10ItemsYearlyQuantity("Top 10 Products in the last Year by Cost");
	
	
	 private String toDisplay;
	 private static final Map<String, AvailableCharts> lookup = new HashMap<String, AvailableCharts>();

	 
	 AvailableCharts(String toDisplay) {
		 this.toDisplay = toDisplay;
	 }
	 
	 public String getDisplay() {
		 return this.toDisplay;
	 }
	 
	 
	 static
	    {
	        for(AvailableCharts chart : AvailableCharts.values())
	        {
	            lookup.put(chart.getDisplay(), chart);
	        }
	    }
	 
	 public static AvailableCharts get(String display) {
		 return lookup.get(display);
	 }
}
 