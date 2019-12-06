import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.Font;
import org.jfree.chart.ChartColor;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.BlockBorder;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Paint;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RectangleEdge;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;

public class PieChart
{
    private static String NO_DATA_MSG;
    ChartPanel frame1;

    public PieChart(final Long sumline, final Long commentlines, final Long blanklines) {
        final DefaultPieDataset data = getDataSet(sumline, commentlines, blanklines);
        final JFreeChart chart = ChartFactory.createPieChart("Общее соотношение строк кода", (PieDataset)data,
                true, false, false);
        final Plot plot = chart.getPlot();
        final PiePlot pieplot = (PiePlot)plot;
        final DecimalFormat df = new DecimalFormat("0.00%");
        final NumberFormat nf = NumberFormat.getNumberInstance();
        final StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, (NumberFormat)df);
        pieplot.setLabelGenerator((PieSectionLabelGenerator)sp1);
        final LegendTitle legendTitle = chart.getLegend();
        legendTitle.setPosition(RectangleEdge.RIGHT);
        legendTitle.setMargin(0.0, 0.0, 0.0, 250.0);
        pieplot.setNoDataMessage("Данные не отображаются");
        plot.setNoDataMessage(PieChart.NO_DATA_MSG);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 5.0, 10.0));
        pieplot.setInsets(new RectangleInsets(0.0, 200.0, 0.0, 0.0));
        pieplot.setLabelBackgroundPaint((Paint)null);
        pieplot.setLabelShadowPaint((Paint)null);
        pieplot.setLabelOutlinePaint((Paint)null);
        pieplot.setShadowPaint((Paint)null);
        pieplot.setLabelGap(0.01);
        pieplot.setInteriorGap(0.05);
        pieplot.setLegendItemShape((Shape)new Rectangle(10, 10));
        chart.setTextAntiAlias(false);
        chart.getLegend().setFrame((BlockFrame)new BlockBorder((Paint)Color.WHITE));
        pieplot.setSectionOutlinesVisible(false);
        chart.setBackgroundPaint((Paint)ChartColor.WHITE);
        plot.setBackgroundPaint((Paint)ChartColor.WHITE);
        plot.setOutlinePaint((Paint)null);
        pieplot.setIgnoreNullValues(true);
        pieplot.setIgnoreZeroValues(true);
        this.frame1 = new ChartPanel(chart, true);
        chart.getTitle().setFont(new Font("Times New Roman", 1, 18));
        pieplot.setLabelFont(new Font("Times New Roman", 1, 12));
        chart.getLegend().setItemFont(new Font("Times New Roman", 1, 12));
    }

    private static DefaultPieDataset getDataSet(final Long sumline, final Long commentlines, final Long blanklines) {
        final DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue((Comparable)"Строка кода", (Number)sumline);
        dataset.setValue((Comparable)"Строка комментария", (Number)commentlines);
        dataset.setValue((Comparable)"Пустая строка", (Number)blanklines);
        return dataset;
    }

    public ChartPanel getChartPanel() {
        return this.frame1;
    }

    static {
        PieChart.NO_DATA_MSG = "Загрузка данных не удалась";
    }
}
