import com.intellij.ui.JBColor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.List;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;

public class DiscountChartExcel
{
    private static String NO_DATA_MSG;
    ChartPanel frame1;

    public DiscountChartExcel(ArrayList<ArrayList<String>> arr, String selectPath, String[] headers, List<ArrayList<String>> dataset) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        HSSFSheet sheet = wb.createSheet("Количество статистических функций");
        DefaultCategoryDataset mDatasetline = createDataset(arr);
        StandardChartTheme mChartThemeline = new StandardChartTheme("CN");

        mChartThemeline.setLargeFont(new Font("Times New Roman", 1, 15));
        mChartThemeline.setExtraLargeFont(new Font("Times New Roman", 1, 18));
        mChartThemeline.setRegularFont(new Font("Times New Roman", 0, 13));
        ChartFactory.setChartTheme(mChartThemeline);

        JFreeChart chart = ChartFactory.createLineChart("Зависимость колличества функций от строки",
                "Строка", "Количество функций", (CategoryDataset)mDatasetline,
                PlotOrientation.VERTICAL, true, true, false);
        Plot plot = chart.getPlot();
        CategoryPlot mPlotline = (CategoryPlot)chart.getPlot();
        LineAndShapeRenderer lasp = (LineAndShapeRenderer)mPlotline.getRenderer();
        LegendTitle legendTitle = chart.getLegend();

        mPlotline.setBackgroundPaint(JBColor.LIGHT_GRAY);

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

        plot.setNoDataMessage(DiscountChartExcel.NO_DATA_MSG);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 5.0, 10.0));
        chart.setTextAntiAlias(false);
        chart.getLegend().setFrame((BlockFrame)new BlockBorder((Paint) JBColor.WHITE));
        chart.setBackgroundPaint((Paint) JBColor.WHITE);
        plot.setOutlinePaint((Paint)null);

        this.frame1 = new ChartPanel(chart, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ChartUtilities.writeChartAsJPEG((OutputStream)bos, chart, 960, 345);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

            sheet.setColumnWidth(0, 1792);
            sheet.setColumnWidth(1, 10240);
            sheet.setColumnWidth(2, 10240);
            sheet.setColumnWidth(3, 5120);
            sheet.setColumnWidth(4, 5120);

            HSSFRow row = sheet.createRow(14);
            HSSFFont font = wb.createFont();

            font.setFontName("Times New Roman");
            font.setFontHeightInPoints((short)15);

            HSSFCellStyle style2 = wb.createCellStyle();
            HSSFCell cell = null;

            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setFont(font);

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

            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short)1, 1, (short)6, 13);

            anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
            patriarch.createPicture(anchor, wb.addPicture(bos.toByteArray(), 5));
        }
        catch (Exception e2) {
            e2.printStackTrace();
            try {
                bos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                bos.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
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

    private static DefaultCategoryDataset createDataset(final ArrayList<ArrayList<String>> arr) {
        return DiscountChart.getDefaultCategoryDataset(arr);
    }

    public ChartPanel getChartPanel() {
        return this.frame1;
    }

    static {
        DiscountChartExcel.NO_DATA_MSG = "Загрузка данных не удалась";
    }
}
