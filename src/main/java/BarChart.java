import java.util.List;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import java.awt.Font;
import org.jfree.chart.ChartColor;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.BlockBorder;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RectangleEdge;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.StandardBarPainter;
import java.awt.Paint;
import java.awt.Color;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartPanel;
import java.util.Map;

public class BarChart
{
    private static Map<String, Long> Probs;
    private static String NO_DATA_MSG;
    ChartPanel frame1;

    public BarChart(final Map<String, Long> map) {
        final CategoryDataset dataset = getDataSet(map);
        final JFreeChart chart = ChartFactory.createBarChart("Статистика файлов", "Тип файла",
                "Количество", dataset, PlotOrientation.VERTICAL, true, true, false);
        final CategoryPlot plot = (CategoryPlot)chart.getPlot();
        final StackedBarRenderer renderer1 = new StackedBarRenderer();
        renderer1.setMaximumBarWidth(0.04);
        renderer1.setMinimumBarLength(0.1);
        renderer1.setDrawBarOutline(false);
        renderer1.setDrawBarOutline(true);
        renderer1.setSeriesPaint(0, (Paint)new Color(217, 150, 34));
        renderer1.setSeriesPaint(1, (Paint)new Color(237, 47, 47));
        renderer1.setSeriesPaint(2, (Paint)new Color(16, 118, 196));
        renderer1.setSeriesPaint(3, (Paint)new Color(26, 210, 208));
        renderer1.setSeriesPaint(4, (Paint)new Color(16, 196, 113));
        renderer1.setItemMargin(0.2);
        renderer1.setBarPainter((BarPainter)new StandardBarPainter());
        renderer1.setShadowVisible(false);
        plot.setRenderer((CategoryItemRenderer)renderer1);
        final LegendTitle legendTitle = chart.getLegend();
        legendTitle.setPosition(RectangleEdge.RIGHT);
        legendTitle.setMargin(0.0, 0.0, 0.0, 50.0);
        plot.setNoDataMessage(BarChart.NO_DATA_MSG);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 5.0, 30.0));
        chart.setTextAntiAlias(false);
        chart.getLegend().setFrame((BlockFrame)new BlockBorder((Paint)Color.WHITE));
        chart.setBackgroundPaint((Paint)ChartColor.WHITE);
        plot.setOutlinePaint((Paint)null);
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("Times New Roman", 1, 14));
        domainAxis.setTickLabelFont(new Font("Times New Roman", 1, 12));
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("Times New Roman", 1, 15));
        chart.getLegend().setItemFont(new Font("Times New Roman", 1, 12));
        chart.getTitle().setFont(new Font("Times New Roman", 1, 18));
        this.frame1 = new ChartPanel(chart, true);
    }

    private static CategoryDataset getDataSet(final Map<String, Long> map) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        BarChart.Probs = sortByValueDescending(map);
        for (final Map.Entry<String, Long> entry : BarChart.Probs.entrySet()) {
            dataset.addValue((Number)entry.getValue(), (Comparable)entry.getKey(), (Comparable)entry.getKey());
        }
        return (CategoryDataset)dataset;
    }

    public ChartPanel getChartPanel() {
        return this.frame1;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(final Map<K, V> map) {
        final List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(final Map.Entry<K, V> o1, final Map.Entry<K, V> o2) {
                final int compare = o1.getValue().compareTo(o2.getValue());
                return -compare;
            }
        });
        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    static {
        BarChart.NO_DATA_MSG = "Загрузка данных не удалась";
    }
}
