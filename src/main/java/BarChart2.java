import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import org.jfree.data.category.DefaultCategoryDataset;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartPanel;
import java.util.Map;

public class BarChart2
{
    private static Map<String, Long> Probs;
    private static String NO_DATA_MSG;
    ChartPanel frame1;

    public BarChart2(final Map<String, Long> map, final String selectPath, final String[] headers, final List<ArrayList<String>> dataset1) {
        final HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFCellStyle style = wb.createCellStyle();
        final HSSFSheet sheet = wb.createSheet("Тип файла и время обновления");
        final CategoryDataset dataset2 = getDataSet(map);
        final JFreeChart chart = ChartFactory.createBarChart("Статистика файлов", "Тип файла",
                "Количество", dataset2, PlotOrientation.VERTICAL, true, true, false);;
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
        plot.setNoDataMessage(BarChart2.NO_DATA_MSG);
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
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ChartUtilities.writeChartAsJPEG((OutputStream)bos, chart, 960, 345);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            sheet.setColumnWidth(0, 1792);
            sheet.setColumnWidth(1, 12800);
            sheet.setColumnWidth(2, 5120);
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
            for (int i = 0; i < dataset1.size(); ++i) {
                row = sheet.createRow(i + 15);
                sheet.createRow(i + 15).setHeight((short)400);
                for (int j = 0; j < dataset1.get(i).size(); ++j) {
                    row.createCell(j).setCellValue((String)dataset1.get(i).get(j));
                }
            }
            sheet.setDefaultRowHeight((short)400);
            final HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short)1, 1, (short)8, 13);
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

    private static CategoryDataset getDataSet(final Map<String, Long> map) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        BarChart2.Probs = sortByValueDescending(map);
        for (final Map.Entry<String, Long> entry : BarChart2.Probs.entrySet()) {
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
        BarChart2.NO_DATA_MSG = "Загрузка данных не удалась";
    }
}
