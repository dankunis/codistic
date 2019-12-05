import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.BlockBorder;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RectangleEdge;
import java.awt.Stroke;
import java.awt.BasicStroke;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import java.awt.Paint;
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

    public DiscountChart(final ArrayList<ArrayList<String>> array) {
        DefaultCategoryDataset mDatasetline = createDataset(array);
        StandardChartTheme mChartThemeline = new StandardChartTheme("CN");

        mChartThemeline.setLargeFont(new Font("Times New Roman", 1, 15));
        mChartThemeline.setExtraLargeFont(new Font("Times New Roman", 1, 18));
        mChartThemeline.setRegularFont(new Font("Times New Roman", 0, 13));
        ChartFactory.setChartTheme((ChartTheme)mChartThemeline);

        JFreeChart chart = ChartFactory.createLineChart("Зависимость колличества функций от строки",
                "Строка", "Количество функций", (CategoryDataset)mDatasetline,
                PlotOrientation.VERTICAL, true, true, false);
        Plot plot = chart.getPlot();
        CategoryPlot mPlotline = (CategoryPlot)chart.getPlot();
        LineAndShapeRenderer lasp = (LineAndShapeRenderer)mPlotline.getRenderer();
        LegendTitle legendTitle = chart.getLegend();

        mPlotline.setBackgroundPaint((Paint) JBColor.LIGHT_GRAY);

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

        legendTitle.setPosition(RectangleEdge.RIGHT);
        legendTitle.setMargin(0.0, 0.0, 0.0, 30.0);

        plot.setNoDataMessage(DiscountChart.NO_DATA_MSG);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 5.0, 10.0));

        chart.setTextAntiAlias(false);
        chart.getLegend().setFrame((BlockFrame)new BlockBorder((Paint) JBColor.WHITE));
        chart.setBackgroundPaint((Paint) JBColor.WHITE);

        plot.setOutlinePaint((Paint)null);

        this.frame1 = new ChartPanel(chart, true);
    }

    private static DefaultCategoryDataset createDataset(ArrayList<ArrayList<String>> array) {
        return getDefaultCategoryDataset(array);
    }

    public ChartPanel getChartPanel() {
        return this.frame1;
    }

    @NotNull
    static DefaultCategoryDataset getDefaultCategoryDataset(ArrayList<ArrayList<String>> array) {
        DefaultCategoryDataset datasetLine = new DefaultCategoryDataset();

        array.sort(new CompareSize(false));

        if (array.size() < 5) {
            for (ArrayList<String> strings : array) {
                for (int j = 1; j < strings.size(); ++j) {
                    datasetLine.addValue(Integer.parseInt(strings.get(j)), strings.get(0), String.valueOf(j * 50));
                }
            }
        }
        else {
            for (int i = 0; i < 5; ++i) {
                for (int j = 1; j < array.get(i).size(); ++j) {
                    datasetLine.addValue(Integer.parseInt(array.get(i).get(j)), array.get(i).get(0), String.valueOf(j * 50));
                }
            }
        }
        return datasetLine;
    }

    static {
        DiscountChart.NO_DATA_MSG = "Загрузка данных не удалась";
    }
}