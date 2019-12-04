import java.util.Collections;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartColor;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.BlockBorder;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RectangleEdge;
import java.awt.Stroke;
import java.awt.BasicStroke;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import java.awt.Paint;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.awt.Font;
import org.jfree.chart.StandardChartTheme;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;

public class DiscountChart
{
    private static String NO_DATA_MSG;
    ChartPanel frame1;

    public DiscountChart(final ArrayList<ArrayList<String>> arr) {
        final DefaultCategoryDataset mDatasetline = createDataset(arr);
        final StandardChartTheme mChartThemeline = new StandardChartTheme("CN");
        mChartThemeline.setLargeFont(new Font("Times New Roman", 1, 15));
        mChartThemeline.setExtraLargeFont(new Font("Times New Roman", 1, 18));
        mChartThemeline.setRegularFont(new Font("Times New Roman", 0, 13));
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        ChartFactory.setChartTheme((ChartTheme)mChartThemeline);
        final JFreeChart chart = ChartFactory.createLineChart("Количество файловых функций", "Строки кода", "Количество функций", (CategoryDataset)mDatasetline, PlotOrientation.VERTICAL, true, true, false);
        final Plot plot = chart.getPlot();
        final CategoryPlot mPlotline = (CategoryPlot)chart.getPlot();
        mPlotline.setBackgroundPaint((Paint)Color.LIGHT_GRAY);
        final LineAndShapeRenderer lasp = (LineAndShapeRenderer)mPlotline.getRenderer();
        lasp.setBaseShapesVisible(true);
        lasp.setDrawOutlines(true);
        lasp.setBaseShapesVisible(true);
        lasp.setDrawOutlines(true);
        lasp.setUseFillPaint(true);
        lasp.setSeriesStroke(0, (Stroke)new BasicStroke(2.0f));
        lasp.setSeriesStroke(1, (Stroke)new BasicStroke(2.0f));
        lasp.setSeriesStroke(2, (Stroke)new BasicStroke(2.0f));
        lasp.setSeriesStroke(3, (Stroke)new BasicStroke(2.0f));
        lasp.setSeriesStroke(4, (Stroke)new BasicStroke(2.0f));
        final LegendTitle legendTitle = chart.getLegend();
        legendTitle.setPosition(RectangleEdge.RIGHT);
        legendTitle.setMargin(0.0, 0.0, 0.0, 30.0);
        plot.setNoDataMessage(DiscountChart.NO_DATA_MSG);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 5.0, 10.0));
        chart.setTextAntiAlias(false);
        chart.getLegend().setFrame((BlockFrame)new BlockBorder((Paint)Color.WHITE));
        chart.setBackgroundPaint((Paint)ChartColor.WHITE);
        plot.setOutlinePaint((Paint)null);
        this.frame1 = new ChartPanel(chart, true);
    }

    private static DefaultCategoryDataset createDataset(final ArrayList<ArrayList<String>> arr) {
        final DefaultCategoryDataset mDatasetline = new DefaultCategoryDataset();
        Collections.sort(arr, new CompareId(false));
        final ArrayList<ArrayList<String>> aaa = arr;
        if (arr.size() < 5) {
            for (int i = 0; i < arr.size(); ++i) {
                for (int j = 1; j < arr.get(i).size(); ++j) {
                    mDatasetline.addValue((double)Integer.parseInt(arr.get(i).get(j)), (Comparable)arr.get(i).get(0), (Comparable)String.valueOf(j * 50));
                }
            }
        }
        else {
            for (int i = 0; i < 5; ++i) {
                for (int j = 1; j < arr.get(i).size(); ++j) {
                    mDatasetline.addValue((double)Integer.parseInt(arr.get(i).get(j)), (Comparable)arr.get(i).get(0), (Comparable)String.valueOf(j * 50));
                }
            }
        }
        return mDatasetline;
    }

    public ChartPanel getChartPanel() {
        return this.frame1;
    }

    static {
        DiscountChart.NO_DATA_MSG = "Загрузка данных не удалась";
    }
}