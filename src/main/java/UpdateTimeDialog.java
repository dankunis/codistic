import com.intellij.uiDesigner.core.Spacer;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Insets;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.OutputStream;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import org.jfree.chart.ChartPanel;
import java.util.Map;
import javax.swing.table.JTableHeader;
import java.util.HashMap;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Dimension;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.KeyStroke;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class UpdateTimeDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel jpanel1;
    private JButton downloadButton;
    private JScrollPane scrollPane1;
    private JSplitPane jsp;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<ArrayList<String>> dataset;
    public static long sumFile;
    public static long sumMethod;
    public static long sumJava;
    public static long sumPy;
    public static long sumPhp;
    public static long sumJsp;
    public static long sumXml;
    public static long number;

    public UpdateTimeDialog() {
        this.$$$setupUI$$$();
        this.dataset = new ArrayList<ArrayList<String>>();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonOK);
        this.setTitle("Тип файла и время обновления");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                UpdateTimeDialog.this.btnDownload();
            }
        });
        this.buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                UpdateTimeDialog.this.onOK();
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                UpdateTimeDialog.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                UpdateTimeDialog.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                UpdateTimeDialog.this.onCancel();
            }
        }, KeyStroke.getKeyStroke(27, 0), 1);
        this.jsp = new JSplitPane(0);
        this.table = new JTable();
        final String myfile = this.init_2("C:\\");
        if (myfile != null) {
            this.stat(myfile);
        }
        else {
            final TextArea textArea1 = new TextArea();
            textArea1.append("Файл не выбран");
            textArea1.setForeground(Color.BLACK);
            textArea1.setFont(new Font("курсив", 1, 30));
            textArea1.setBackground(Color.GRAY);
            this.jpanel1.setLayout(new BorderLayout());
            this.jpanel1.add(textArea1);
        }
    }

    private void stat(final String myfile) {
        UpdateTimeDialog.sumFile = 0L;
        UpdateTimeDialog.sumMethod = 0L;
        UpdateTimeDialog.sumJava = 0L;
        UpdateTimeDialog.sumPy = 0L;
        UpdateTimeDialog.sumPhp = 0L;
        UpdateTimeDialog.sumJsp = 0L;
        UpdateTimeDialog.sumXml = 0L;
        UpdateTimeDialog.number = 0L;
        this.dataset.clear();
        (this.tableModel = (DefaultTableModel)this.table.getModel()).setRowCount(0);
        this.tableModel.setColumnIdentifiers(new Object[] { "Номер", "Имя файла", "Время обновления" });
        final File inFile = new File(myfile);
        if (inFile.isFile()) {
            this.fileMethod(inFile);
        }
        else {
            this.selectMethod(inFile);
        }
        this.tableModel.addRow(new Object[] { "", "Всего: " + UpdateTimeDialog.sumFile + "документов" });
        final List<String> rowData = new ArrayList<String>();
        rowData.add("");
        rowData.add("Всего: " + String.valueOf(UpdateTimeDialog.sumFile) + "документов");
        this.dataset.add((ArrayList)rowData);
        if (UpdateTimeDialog.sumJava > 0L) {
            this.tableModel.addRow(new Object[] { "", UpdateTimeDialog.sumJava + " java файлов" });
            final List<String> rowDataJa = new ArrayList<String>();
            rowDataJa.add("");
            rowDataJa.add("Всего:" + String.valueOf(UpdateTimeDialog.sumJava) + " java файлов");
            this.dataset.add((ArrayList)rowDataJa);
        }
        if (UpdateTimeDialog.sumPy > 0L) {
            this.tableModel.addRow(new Object[] { "", UpdateTimeDialog.sumPy + " py файлов" });
            final List<String> rowDataPy = new ArrayList<String>();
            rowDataPy.add("");
            rowDataPy.add("Всего:" + String.valueOf(UpdateTimeDialog.sumPy + " py файлов"));
            this.dataset.add((ArrayList)rowDataPy);
        }
        if (UpdateTimeDialog.sumPhp > 0L) {
            this.tableModel.addRow(new Object[] { "", UpdateTimeDialog.sumPhp + " php файлов" });
            final List<String> rowDataPhp = new ArrayList<String>();
            rowDataPhp.add("");
            rowDataPhp.add("Всего:" + String.valueOf(UpdateTimeDialog.sumPhp) + " php файлов");
            this.dataset.add((ArrayList)rowDataPhp);
        }
        if (UpdateTimeDialog.sumJsp > 0L) {
            this.tableModel.addRow(new Object[] { "", UpdateTimeDialog.sumJsp + " jsp файлов" });
            final List<String> rowDataJsp = new ArrayList<String>();
            rowDataJsp.add("");
            rowDataJsp.add("Всего:" + String.valueOf(UpdateTimeDialog.sumJsp) + " jsp файлов");
            this.dataset.add((ArrayList)rowDataJsp);
        }
        if (UpdateTimeDialog.sumXml > 0L) {
            this.tableModel.addRow(new Object[] { "", UpdateTimeDialog.sumXml + " xml файлов" });
            final List<String> rowDataXml = new ArrayList<String>();
            rowDataXml.add("");
            rowDataXml.add("Всего:" + String.valueOf(UpdateTimeDialog.sumXml) + " xml файлов");
            this.dataset.add((ArrayList)rowDataXml);
        }
        this.table.setModel(this.tableModel);
        this.table.setRowHeight(30);
        final JTableHeader tableHeader = this.table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tableHeader.setFont(new Font("Times New Roman", 0, 15));
        this.table.setFont(new Font("Times New Roman", 0, 15));
        this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(660);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(300);
        final DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(4);
        this.table.getColumn("Время обновления").setCellRenderer(r);
        this.jsp.setDividerLocation(350);
        this.jsp.add(new JScrollPane(this.table), "bottom");
        this.jsp.setDividerSize(1);
        this.jpanel1.setLayout(new BorderLayout());
        this.jpanel1.add(this.jsp);
        final JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(null);
        final Map<String, Long> map = new HashMap<String, Long>();
        map.put("java", UpdateTimeDialog.sumJava);
        map.put("py", UpdateTimeDialog.sumPy);
        map.put("php", UpdateTimeDialog.sumPhp);
        map.put("jsp", UpdateTimeDialog.sumJsp);
        map.put("xml", UpdateTimeDialog.sumXml);
        final ChartPanel cp = new BarChart(map).getChartPanel();
        cp.setBounds(1, 1, 960, 350);
        jpanel2.add((Component)cp);
        this.jsp.add(jpanel2, "top");
    }

    public String init_2(final String fName) {
        String path = null;
        final JFileChooser jdir = new JFileChooser();
        jdir.setFileSelectionMode(1);
        jdir.setDialogTitle("Пожалуйста, выберите путь");
        jdir.setFileSelectionMode(2);
        if (0 == jdir.showOpenDialog(null)) {
            path = jdir.getSelectedFile().getAbsolutePath();
        }
        return path;
    }

    public void selectMethod(final File inFile) {
        for (final File file : inFile.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                final String info = sdf.format(cal.getTime());
                final Date currentTime = new Date();
                final String timeDiffer = getDatePoor(currentTime, cal.getTime());
                ++UpdateTimeDialog.sumFile;
                ++UpdateTimeDialog.sumJava;
                ++UpdateTimeDialog.number;
                this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(UpdateTimeDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(timeDiffer));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".py")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                final String info = sdf.format(cal.getTime());
                final Date currentTime = new Date();
                final String timeDiffer = getDatePoor(currentTime, cal.getTime());
                ++UpdateTimeDialog.sumFile;
                ++UpdateTimeDialog.sumPy;
                ++UpdateTimeDialog.number;
                this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(UpdateTimeDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(timeDiffer));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".php")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                final String info = sdf.format(cal.getTime());
                final Date currentTime = new Date();
                final String timeDiffer = getDatePoor(currentTime, cal.getTime());
                ++UpdateTimeDialog.sumFile;
                ++UpdateTimeDialog.sumPhp;
                ++UpdateTimeDialog.number;
                this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(UpdateTimeDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(timeDiffer));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".jsp")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                final String info = sdf.format(cal.getTime());
                final Date currentTime = new Date();
                final String timeDiffer = getDatePoor(currentTime, cal.getTime());
                ++UpdateTimeDialog.sumFile;
                ++UpdateTimeDialog.sumJsp;
                ++UpdateTimeDialog.number;
                this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(UpdateTimeDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(timeDiffer));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".xml")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                final String info = sdf.format(cal.getTime());
                final Date currentTime = new Date();
                final String timeDiffer = getDatePoor(currentTime, cal.getTime());
                ++UpdateTimeDialog.sumFile;
                ++UpdateTimeDialog.sumXml;
                ++UpdateTimeDialog.number;
                this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(UpdateTimeDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(timeDiffer));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isDirectory()) {
                this.selectMethod(file);
            }
        }
    }

    public void fileMethod(final File file) {
        if (file.isFile() && file.getName().endsWith(".java")) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(file.lastModified());
            final String info = sdf.format(cal.getTime());
            final Date currentTime = new Date();
            final String timeDiffer = getDatePoor(currentTime, cal.getTime());
            ++UpdateTimeDialog.sumFile;
            ++UpdateTimeDialog.sumJava;
            ++UpdateTimeDialog.number;
            this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(UpdateTimeDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(timeDiffer));
            this.dataset.add((ArrayList)rowData);
        }
        else if (file.isFile() && file.getName().endsWith(".py")) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(file.lastModified());
            final String info = sdf.format(cal.getTime());
            final Date currentTime = new Date();
            final String timeDiffer = getDatePoor(currentTime, cal.getTime());
            ++UpdateTimeDialog.sumFile;
            ++UpdateTimeDialog.sumPy;
            ++UpdateTimeDialog.number;
            this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(UpdateTimeDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(timeDiffer));
            this.dataset.add((ArrayList)rowData);
        }
        else if (file.isFile() && file.getName().endsWith(".php")) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(file.lastModified());
            final String info = sdf.format(cal.getTime());
            final Date currentTime = new Date();
            final String timeDiffer = getDatePoor(currentTime, cal.getTime());
            ++UpdateTimeDialog.sumFile;
            ++UpdateTimeDialog.sumPhp;
            ++UpdateTimeDialog.number;
            this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(UpdateTimeDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(timeDiffer));
            this.dataset.add((ArrayList)rowData);
        }
        else if (file.isFile() && file.getName().endsWith(".jsp")) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(file.lastModified());
            final String info = sdf.format(cal.getTime());
            final Date currentTime = new Date();
            final String timeDiffer = getDatePoor(currentTime, cal.getTime());
            ++UpdateTimeDialog.sumFile;
            ++UpdateTimeDialog.sumJsp;
            ++UpdateTimeDialog.number;
            this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(UpdateTimeDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(timeDiffer));
            this.dataset.add((ArrayList)rowData);
        }
        else if (file.isFile() && file.getName().endsWith(".xml")) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(file.lastModified());
            final String info = sdf.format(cal.getTime());
            final Date currentTime = new Date();
            final String timeDiffer = getDatePoor(currentTime, cal.getTime());
            ++UpdateTimeDialog.sumFile;
            ++UpdateTimeDialog.sumXml;
            ++UpdateTimeDialog.number;
            this.tableModel.addRow(new Object[] { UpdateTimeDialog.number, file.getName(), timeDiffer });
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(UpdateTimeDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(timeDiffer));
            this.dataset.add((ArrayList)rowData);
        }
        else if (file.isDirectory()) {
            this.selectMethod(file);
        }
    }

    public static String getDatePoor(final Date endDate, final Date nowDate) {
        final long nd = 86400000L;
        final long nh = 3600000L;
        final long nm = 60000L;
        final long diff = endDate.getTime() - nowDate.getTime();
        final long day = diff / nd;
        final long hour = diff % nd / nh;
        final long min = diff % nd % nh / nm;
        if (day <= 0L && hour <= 0L) {
            return min + " минут назад";
        }
        if (day <= 0L && hour > 0L) {
            return hour + " часов назад";
        }
        if (day > 0L) {
            return day + " дней назад";
        }
        return day + " дней" + hour + " часов" + min + " минут";
    }

    public static long getDays(final String date1, final String date2) {
        if (date1 == null || date1.equals("")) {
            return 0L;
        }
        if (date2 == null || date2.equals("")) {
            return 0L;
        }
        final SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date3 = null;
        Date mydate = null;
        try {
            date3 = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        }
        catch (Exception ex) {}
        final long day = (date3.getTime() - mydate.getTime()) / 86400000L;
        return day;
    }

    public static int getMiao(final String date1, final String date2) {
        if (date1 == null || date1.equals("")) {
            return 0;
        }
        if (date2 == null || date2.equals("")) {
            return 0;
        }
        final SimpleDateFormat myFormatter = new SimpleDateFormat("HH:mm:ss");
        Date date3 = null;
        Date mydate = null;
        try {
            date3 = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        }
        catch (Exception ex) {}
        final long day = (date3.getTime() - mydate.getTime()) / 1000L;
        return (int)day;
    }

    public static String getTimeShort(final Date currentTime) {
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        final String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringDateShort(final Date currentTime) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String dateString = formatter.format(currentTime);
        return dateString;
    }

    private void btnDownload() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(1);
        final Component parent = null;
        final int returnVal = chooser.showSaveDialog(parent);
        if (returnVal == 0) {
            final String selectPath = chooser.getSelectedFile().getPath();
            final String[] headers = { "Номер", "Имя файла", "Время обновления" };
            final File file = new File(selectPath + "//data.xls");
            if (file.exists()) {
                JOptionPane.showMessageDialog(null, "Файл уже существует! Пожалуйста, удалите файл и попробуйте снова!");
            }
            else {
                final Map<String, Long> map = new HashMap<String, Long>();
                map.put("java", UpdateTimeDialog.sumJava);
                map.put("py", UpdateTimeDialog.sumPy);
                map.put("php", UpdateTimeDialog.sumPhp);
                map.put("jsp", UpdateTimeDialog.sumJsp);
                map.put("xml", UpdateTimeDialog.sumXml);
                final ChartPanel cp = new BarChart2(map, selectPath, headers, this.dataset).getChartPanel();
                JOptionPane.showMessageDialog(null, "Экспорт успешно завершен");
            }
        }
    }

    public HSSFWorkbook exportExcel(final String title, final String[] headers, final List<ArrayList<String>> dataset, final OutputStream out) {
        HSSFWorkbook wb = null;
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        final HSSFSheet sheet = wb.createSheet(title);
        sheet.setColumnWidth(0, 1792);
        sheet.setColumnWidth(1, 12800);
        sheet.setColumnWidth(2, 5120);
        sheet.setColumnWidth(3, 5120);
        sheet.setColumnWidth(4, 5120);
        HSSFRow row = sheet.createRow(0);
        final HSSFFont font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)15);
        final HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        HSSFCell cell = null;
        for (int i = 0; i < headers.length; ++i) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < dataset.size(); ++i) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < dataset.get(i).size(); ++j) {
                row.createCell(j).setCellValue((String)dataset.get(i).get(j));
            }
        }
        return wb;
    }

    private void onOK() {
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    }

    public static void main(final String[] args) {
        final UpdateTimeDialog dialog = new UpdateTimeDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    static {
        UpdateTimeDialog.sumFile = 0L;
        UpdateTimeDialog.sumMethod = 0L;
        UpdateTimeDialog.sumJava = 0L;
        UpdateTimeDialog.sumPy = 0L;
        UpdateTimeDialog.sumPhp = 0L;
        UpdateTimeDialog.sumJsp = 0L;
        UpdateTimeDialog.sumXml = 0L;
        UpdateTimeDialog.number = 0L;
    }

    private /* synthetic */ void $$$setupUI$$$() {
        final JPanel contentPane = new JPanel();
        (this.contentPane = contentPane).setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1, false, false));
        final JPanel comp = new JPanel();
        comp.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, false, false));
        contentPane.add(comp, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 1, null, null, null));
        comp.add(new Spacer(), new GridConstraints(0, 0, 1, 1, 0, 1, 6, 1, null, null, null));
        final JPanel comp2 = new JPanel();
        comp2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1, false, false));
        comp.add(comp2, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, null, null, null));
        final JButton button = new JButton();
        (this.buttonOK = button).setText("OK");
        comp2.add(button, new GridConstraints(0, 0, 1, 1, 0, 1, 3, 0, null, null, null));
        final JButton button2 = new JButton();
        (this.buttonCancel = button2).setText("Cancel");
        comp2.add(button2, new GridConstraints(0, 1, 1, 1, 0, 1, 3, 0, null, null, null));
        final JButton button3 = new JButton();
        (this.downloadButton = button3).setText("Скачать");
        comp2.add(button3, new GridConstraints(0, 2, 1, 1, 0, 1, 3, 0, null, null, null));
        final JPanel panel = new JPanel();
        (this.jpanel1 = panel).setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1, false, false));
        contentPane.add(panel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, null, null, null));
    }
}
