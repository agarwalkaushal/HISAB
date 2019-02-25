package com.fullertonfinnovatica.Analytics;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.fullertonfinnovatica.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsMain extends AppCompatActivity {


    BarChart dayBasedBarChart;
    PieChart hourBasedPieChart;
    LineChart yearBasedLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_main);

        dayBasedBarChart = findViewById(R.id.chart);
        dayBasedBarChart.setTouchEnabled(true);
        dayBasedBarChart.setScaleEnabled(true);
        hourBasedPieChart = findViewById(R.id.chart2);
        hourBasedPieChart.setTouchEnabled(true);
        yearBasedLineChart = findViewById(R.id.chart3);
        yearBasedLineChart.setScaleEnabled(true);
        yearBasedLineChart.setTouchEnabled(true);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));
        entries.add(new BarEntry(6f, 70f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        final String[] quarters = new String[] { "Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun" };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = dayBasedBarChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        Legend legend = dayBasedBarChart.getLegend();
        legend.setEnabled(false);

        Description description = dayBasedBarChart.getDescription();
        description.setEnabled(false);
        int[] colors_bar = new int[]{Color.parseColor("#3529CF"), Color.parseColor("#1F1977"), Color.parseColor("#2F9CD6"), Color.parseColor("#2FC2D6"), Color.parseColor("#1FA597"), Color.parseColor("#3A645F"), Color.parseColor("#0B2435")};
        set.setColors(colors_bar);
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        dayBasedBarChart.setData(data);
        dayBasedBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        dayBasedBarChart.invalidate(); // refresh


        //Pie Chart

        List<PieEntry> entries2 = new ArrayList<>();

        entries2.add(new PieEntry(31.4f, ""));
        entries2.add(new PieEntry(15.6f, ""));
        entries2.add(new PieEntry(28.4f, ""));
        entries2.add(new PieEntry(24.6f, ""));

        int[] colors_pie = new int[]{Color.parseColor("#F3FF00"), Color.parseColor("#A2B800"), Color.parseColor("#CD7C00"), Color.parseColor("#6C4B00")};

        Legend legend2 = hourBasedPieChart.getLegend();
        legend2.setEnabled(true);
        LegendEntry legendEntry1 = new LegendEntry("Morning (8am-12pm)", Legend.LegendForm.CIRCLE, Float.NaN, Float.NaN, null, Color.parseColor("#F3FF00"));
        LegendEntry legendEntry2 = new LegendEntry("Afternoon (12pm-3pm)", Legend.LegendForm.CIRCLE, Float.NaN, Float.NaN, null, Color.parseColor("#A2B800"));
        LegendEntry legendEntry3 = new LegendEntry("Evening (3pm-6pm)", Legend.LegendForm.CIRCLE, Float.NaN, Float.NaN, null, Color.parseColor("#CD7C00"));
        LegendEntry legendEntry4 = new LegendEntry("Night (6pm-8pm)", Legend.LegendForm.CIRCLE, Float.NaN, Float.NaN, null, Color.parseColor("#6C4B00"));
        LegendEntry[] legendEntries = new LegendEntry[]{legendEntry1, legendEntry2, legendEntry3, legendEntry4};
        legend2.setCustom(legendEntries);
        legend2.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        Description description2 = hourBasedPieChart.getDescription();
        description2.setEnabled(false);

        PieDataSet set2 = new PieDataSet(entries2, "Hour Based Sales");
        set2.setColors(colors_pie);
        PieData data2 = new PieData(set2);
        hourBasedPieChart.setUsePercentValues(true);
        hourBasedPieChart.setData(data2);
        hourBasedPieChart.invalidate();

        //Line chart

        List<Entry> months = new ArrayList<Entry>();

        Entry e1 = new Entry(0f, 100000f); // 0 == quarter 1
        months.add(e1);
        Entry e2 = new Entry(1f, 140000f); // 1 == quarter 2 ...
        months.add(e2);
        Entry e3 = new Entry(2f, 90000f); // 1 == quarter 2 ...
        months.add(e3);
        Entry e4 = new Entry(3f, 150000f); // 1 == quarter 2 ...
        months.add(e4);
        Entry e5 = new Entry(4f, 160000f); // 1 == quarter 2 ...
        months.add(e5);
        Entry e6 = new Entry(5f, 140000f); // 1 == quarter 2 ...
        months.add(e6);
        Entry e7 = new Entry(6f, 190000f); // 1 == quarter 2 ...
        months.add(e7);
        Entry e8 = new Entry(7f, 210000f); // 1 == quarter 2 ...
        months.add(e8);
        Entry e9 = new Entry(8f, 110000f); // 1 == quarter 2 ...
        months.add(e9);
        Entry e10 = new Entry(9f, 250000f); // 1 == quarter 2 ...
        months.add(e10);
        Entry e11 = new Entry(10f, 260000f); // 1 == quarter 2 ...
        months.add(e11);
        Entry e12 = new Entry(11f, 350000f); // 1 == quarter 2 ...
        months.add(e12);


        LineDataSet setComp1 = new LineDataSet(months, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);

        Legend legend3 = yearBasedLineChart.getLegend();
        legend3.setEnabled(false);

        Description description3 = yearBasedLineChart.getDescription();
        description3.setEnabled(false);

        LineData data3 = new LineData(dataSets);
        yearBasedLineChart.setData(data3);
        yearBasedLineChart.invalidate(); // refresh

        final String[] quarters2 = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        IAxisValueFormatter formatter2 = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters2[(int) value];
            }
        };

        XAxis xAxis2 = yearBasedLineChart.getXAxis();
        xAxis2.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis2.setValueFormatter(formatter2);
    }
}
