import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import java.io.IOException;
import java.io.OutputStream;
import org.jfree.chart.ChartUtilities;
import java.io.ByteArrayOutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;

public class PieChart2
{
    private static String NO_DATA_MSG;
    private static short LEFT_INDEX;
    private static short TOP_INDEX;
    private static short RIGHT_INDEX;
    private static short BOTTOM_INDEX;
    ChartPanel frame1;
    JFreeChart chart;

    public PieChart2(final Long sumline, final Long commentlines, final Long blanklines, final String selectPath, final String[] headers, final List<ArrayList<String>> dataset) {
        final HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFCellStyle style = wb.createCellStyle();
        final HSSFSheet sheet = wb.createSheet("Статистическая строка кода");
        final DefaultPieDataset data = getDataSet(sumline, commentlines, blanklines);
        this.chart = ChartFactory.createPieChart("Общее соотношение строк кода", (PieDataset)data, true,
                false, false);
        final Plot plot = this.chart.getPlot();
        final PiePlot pieplot = (PiePlot)plot;
        final DecimalFormat df = new DecimalFormat("0.00%");
        final NumberFormat nf = NumberFormat.getNumberInstance();
        final StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, (NumberFormat)df);
        pieplot.setLabelGenerator((PieSectionLabelGenerator)sp1);
        final LegendTitle legendTitle = this.chart.getLegend();
        legendTitle.setPosition(RectangleEdge.RIGHT);
        legendTitle.setMargin(0.0, 0.0, 0.0, 250.0);
        pieplot.setNoDataMessage("Данные не отображаются");
        plot.setNoDataMessage(PieChart2.NO_DATA_MSG);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 5.0, 10.0));
        pieplot.setInsets(new RectangleInsets(0.0, 200.0, 0.0, 0.0));
        pieplot.setLabelBackgroundPaint((Paint)null);
        pieplot.setLabelShadowPaint((Paint)null);
        pieplot.setLabelOutlinePaint((Paint)null);
        pieplot.setShadowPaint((Paint)null);
        pieplot.setCircular(true);
        pieplot.setLabelGap(0.01);
        pieplot.setInteriorGap(0.05);
        pieplot.setLegendItemShape((Shape)new Rectangle(10, 10));
        this.chart.setTextAntiAlias(false);
        this.chart.getLegend().setFrame((BlockFrame)new BlockBorder((Paint)Color.WHITE));
        pieplot.setSectionOutlinesVisible(false);
        this.chart.setBackgroundPaint((Paint)ChartColor.WHITE);
        plot.setBackgroundPaint((Paint)ChartColor.WHITE);
        plot.setOutlinePaint((Paint)null);
        pieplot.setIgnoreNullValues(true);
        pieplot.setIgnoreZeroValues(true);
        this.frame1 = new ChartPanel(this.chart, true);
        this.chart.getTitle().setFont(new Font("Times New Roman", 1, 18));
        pieplot.setLabelFont(new Font("Times New Roman", 1, 12));
        this.chart.getLegend().setItemFont(new Font("Times New Roman", 1, 12));
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ChartUtilities.writeChartAsJPEG((OutputStream)bos, this.chart, 800, 345);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            sheet.setColumnWidth(0, 1792);
            sheet.setColumnWidth(1, 12800);
            sheet.setColumnWidth(2, 5120);
            sheet.setColumnWidth(3, 5120);
            sheet.setColumnWidth(4, 5120);
            HSSFRow row = sheet.createRow(14);
            final HSSFFont font = wb.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeightInPoints((short)15);
            final HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setFont(font);
            HSSFCell cell = null;
            for (int i = 0; i < headers.length; ++i) {
                cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(style2);
            }
            for (int i = 0; i < dataset.size(); ++i) {
                row = sheet.createRow(i + 15);
                sheet.createRow(i + 15).setHeight((short)400);
                for (int j = 0; j < dataset.get(i).size(); ++j) {
                    row.createCell(j).setCellValue((String)dataset.get(i).get(j));
                }
            }
            sheet.setDefaultRowHeight((short)400);
            final HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short)1, 1, (short)4, 12);
            anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
            patriarch.createPicture(anchor, wb.addPicture(bos.toByteArray(), 5));
        }
        catch (Exception e2) {
            e2.printStackTrace();
            if (bos != null) {
                try {
                    bos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            if (bos != null) {
                try {
                    bos.close();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(selectPath + "//data.xls");
            try {
                wb.write((OutputStream)fos);
            }
            catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        catch (FileNotFoundException e5) {
            e5.printStackTrace();
        }
        if (fos != null) {
            try {
                fos.close();
            }
            catch (IOException e4) {
                e4.printStackTrace();
            }
        }
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

    public JFreeChart getChart() {
        return this.chart;
    }

    static {
        PieChart2.NO_DATA_MSG = "Загрузка данных не удалась";
        PieChart2.LEFT_INDEX = 1;
        PieChart2.TOP_INDEX = 1;
        PieChart2.RIGHT_INDEX = 12;
        PieChart2.BOTTOM_INDEX = 15;
    }
}
