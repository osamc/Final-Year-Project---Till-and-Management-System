package com.sam.tillsystem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.sam.tillsystem.api.ChartApi;
import com.sam.tillsystem.enums.AvailableCharts;

import be.ceau.chart.BarChart;
import be.ceau.chart.Chart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;

@Repository
public class ChartImpl extends BaseImpl implements ChartApi {

	public ChartImpl(JdbcTemplate template) {
		super(template);
	}

	@Override
	public List<String> availableCharts() {

		List<String> charts = new ArrayList<String>();

		for (AvailableCharts chart : AvailableCharts.values()) {
			charts.add(chart.getDisplay());
		}

		return charts;
	}

	@Override
	public Chart getChart(String chart) {

		AvailableCharts chosen = AvailableCharts.get(chart);

		switch (chosen) {
		case CategoriesOverall:
			return categoryPerfAllTimeByPrice();
		case CategoriesMonthly:
			return categoryPerfLastMonthPrice();
		case CategoriesWeek:
			return categoryPerfLastWeekPrice();
		case CategoriesYearly:
			return categoryPerfLastYearPrice();
		case CategoriesOverallQuantity:
			return categoryPerfAllTimeByQuan();
		case CategoriesMonthlyQuantity:
			return categoryPerfLastMonthQuan();
		case CategoriesWeekQuantity:
			return categoryPerfLastWeekQuan();
		case CategoriesYearlyQuantity:
			return categoryPerfLastYearQuan();
		case top10ItemsMonthly:
			return itemPerfLastMonthByPrice();
		case top10ItemsMonthlyQuantity:
			return itemPerfLastMonthByQuan();
		case top10ItemsOverall:
			return itemPerfAllTimeByPrice();
		case top10ItemsOverallQuantity:
			return itemPerfAllTimeByQuan();
		case top10ItemsWeek:
			return itemPerfLastWeekByPrice();
		case top10ItemsWeekQuantity:
			return itemPerfLastWeekByQuan();
		case top10ItemsYearly:
			return itemPerfLastYearByPrice();
		case top10ItemsYearlyQuantity:
			return itemPerfLastYearByQuan();

		default:
			break;
		}

		return null;
	}

	private Chart categoryPerformance(String sum, String datasetLabel, boolean isRestraint, String interval) {

		String sql = "SELECT product_group.group_name as label, sum(" + sum + ") as data FROM transaction_details "
				+ "JOIN product ON product.id = transaction_details.product_id "
				+ "JOIN transaction_record ON transaction_record.transaction_id = transaction_details.trans_id "
				+ "JOIN product_group ON product.groupid = product_group.group_id ";

		if (isRestraint && interval != null) {
			sql += "WHERE transaction_record.date > NOW() - interval '" + interval + "' ";
		}

		sql += "GROUP BY product_group.group_name;";

		return createSimpleBar(sql, datasetLabel, true);
	}

	private Chart categoryPerformance(String sum, String datasetLabel, boolean isRestraint) {
		return categoryPerformance(sum, datasetLabel, isRestraint, null);
	}

	private Chart itemPerformance(String sum, String datasetLabel, boolean isRestraint, String interval) {
		String sql = "SELECT product.name as label, sum(" + sum + ") as data FROM transaction_details "
				+ "JOIN product ON product.id = transaction_details.product_id "
				+ "JOIN transaction_record ON transaction_record.transaction_id = transaction_details.trans_id ";

		if (isRestraint && interval != null) {
			sql += "WHERE transaction_record.date > NOW() - interval '" + interval + "' ";
		}

		sql += "GROUP BY product.name " + "ORDER BY data DESC " + "LIMIT 10;";

		return createSimpleBar(sql, datasetLabel, true);
	}

	private Chart itemPerformance(String sum, String datasetLabel, boolean isRestraint) {
		return this.itemPerformance(sum, datasetLabel, isRestraint, null);
	}

	private Chart itemPerfAllTimeByPrice() {
		return itemPerformance("transaction_details.price", "Sales per Item in £", false);
	}

	private Chart itemPerfAllTimeByQuan() {
		return itemPerformance("transaction_details.quantity", "Quantity of Sales Per Item", false);
	}

	private Chart itemPerfLastYearByPrice() {
		return itemPerformance("transaction_details.price", "Sales per Item in £", true, "1 year");
	}

	private Chart itemPerfLastYearByQuan() {
		return itemPerformance("transaction_details.quantity", "Quantity of Sales Per Item", true, "1 year");
	}

	private Chart itemPerfLastMonthByPrice() {
		return itemPerformance("transaction_details.price", "Sales per Item in £", true, "1 month");
	}

	private Chart itemPerfLastMonthByQuan() {
		return itemPerformance("transaction_details.quantity", "Quantity of Sales Per Item", true, "1 month");
	}

	private Chart itemPerfLastWeekByPrice() {
		return itemPerformance("transaction_details.price", "Sales per Item in £", true, "1 week");
	}

	private Chart itemPerfLastWeekByQuan() {
		return itemPerformance("transaction_details.quantity", "Quantity of Sales Per Item", true, "1 week");
	}

	private Chart categoryPerfAllTimeByPrice() {
		return categoryPerformance("transaction_details.price", "Sales per Category in £", false);
	}

	private Chart categoryPerfAllTimeByQuan() {
		return categoryPerformance("transaction_details.quantity", "Sales per Category by Quantity", false);
	}

	private Chart categoryPerfLastWeekPrice() {
		return categoryPerformance("transaction_details.price", "Sales per Category in £", true, "7 days");
	}

	private Chart categoryPerfLastWeekQuan() {
		return categoryPerformance("transaction_details.quantity", "Sales per Category by Quantity", true, "7 days");
	}

	private Chart categoryPerfLastMonthPrice() {
		return categoryPerformance("transaction_details.price", "Sales per Category in £", true, "1 months");
	}

	private Chart categoryPerfLastMonthQuan() {
		return categoryPerformance("transaction_details.quantity", "Sales per Category by Quantity", true, "1 months");
	}

	private Chart categoryPerfLastYearPrice() {
		return categoryPerformance("transaction_details.price", "Sales per Category in £", true, "1 years");
	}

	private Chart categoryPerfLastYearQuan() {
		return categoryPerformance("transaction_details.quantity", "Sales per Category by Quantity", true, "1 years");
	}

	private Chart productPerformanceAllTimeByPrice() {
		return createSimpleBar("SELECT  product.name, sum(transaction_details.price) as total FROM transaction_details"
				+ "JOIN product ON product.id = transaction_details.product_id "
				+ "JOIN transaction_record ON transaction_record.transaction_id = transaction_details.trans_id "
				+ "JOIN product_group ON product.groupid = product_group.group_id "
				+ "WHERE transaction_record.date > NOW() - interval '1 years' " + "GROUP BY product.name "
				+ "ORDER BY total desc " + "limit 10;", "Sales per Item in £", true);
	}

	private Chart createSimpleBar(String sql, String datasetLabel, boolean startAtZero) {
		List<Double> dataList = new ArrayList<Double>();
		List<String> labelList = new ArrayList<String>();

		SqlRowSet rs = this.template.queryForRowSet(sql);

		if (rs.first()) {
			do {
				labelList.add(rs.getString("label"));
				dataList.add(rs.getDouble("data"));
			} while (rs.next());

		}

		double[] dataArr = dataList.stream().mapToDouble(Double::doubleValue).toArray();

		BarDataset dataset = new BarDataset();
		dataset.setData(dataArr);
		dataset.setLabel(datasetLabel);

		dataset.addBackgroundColors(getColours(dataArr.length));

		BarData data = new BarData();
		data.addLabels(labelList.toArray(new String[0]));
		data.addDataset(dataset);

		BarChart chart = new BarChart(data);

		BarOptions options = new BarOptions();
		options.setResponsive(true);

		Title title = new Title();
		title.setText("test");

		options.setTitle(title);
		options.setMaintainAspectRatio(true);

		if (startAtZero) {
			LinearTicks tick = new LinearTicks();
			tick.setBeginAtZero(true);

			YAxis<LinearTicks> yaxis = new YAxis<LinearTicks>();

			yaxis.setTicks(tick);
			List<YAxis<LinearTicks>> yTicks = new ArrayList<YAxis<LinearTicks>>();
			yTicks.add(yaxis);
			BarScale scale = new BarScale();

			scale.setyAxes(yTicks);
			options.setScales(scale);
		}

		chart.setOptions(options);

		return chart;
	}

	private Color[] getColours(int number) {
		List<Color> colours = new ArrayList<Color>();

		Random r = new Random();

		for (int i = 0; i < number; i++) {
			int red = r.nextInt(255);
			int green = r.nextInt(255);
			int blue = r.nextInt(255);
			colours.add(new Color(red, green, blue, 0.5));
		}

		return colours.toArray(new Color[number]);
	}

}
