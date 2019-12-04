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
import java.util.regex.Matcher;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import org.jfree.chart.ChartPanel;
import javax.swing.table.JTableHeader;
import javax.swing.JScrollPane;
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
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class SelectMethodDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel jpanel1;
    private JButton downloadButton;
    private JSplitPane jsp;
    private JTable table;
    private DefaultTableModel tableModel;
    public static long sumFile;
    public static long sumMethod;
    public static long number;
    private static ArrayList<ArrayList<String>> arr;
    private List<ArrayList<String>> dataset;

    public SelectMethodDialog() {
        this.$$$setupUI$$$();
        this.dataset = new ArrayList<ArrayList<String>>();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonOK);
        this.setTitle("\u7edf\u8ba1\u51fd\u6570\u4e2a\u6570");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SelectMethodDialog.this.btnDownload();
            }
        });
        this.buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SelectMethodDialog.this.onOK();
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SelectMethodDialog.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                SelectMethodDialog.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SelectMethodDialog.this.onCancel();
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
        SelectMethodDialog.sumFile = 0L;
        SelectMethodDialog.sumMethod = 0L;
        SelectMethodDialog.arr.clear();
        (this.tableModel = (DefaultTableModel)this.table.getModel()).setRowCount(0);
        this.tableModel.setColumnIdentifiers(new Object[] { "Серийный номер", "Имя файла", "Имя функции", "Функциональное положение", "Количество функций" });
        final File inFile = new File(myfile);
        if (inFile.isFile()) {
            this.fileMethod(inFile);
        }
        else {
            this.selectMethod(inFile);
        }
        this.tableModel.addRow(new Object[] { "", "Всего:" + SelectMethodDialog.sumFile + "документы", "", "", SelectMethodDialog.sumMethod + "Функция" });
        final List<String> rowData = new ArrayList<String>();
        rowData.add("");
        rowData.add("Всего:" + String.valueOf(SelectMethodDialog.sumFile) + "документы");
        rowData.add("");
        rowData.add("");
        rowData.add("Всего:" + SelectMethodDialog.sumMethod + "функции");
        this.dataset.add((ArrayList)rowData);
        this.table.setModel(this.tableModel);
        this.table.setRowHeight(30);
        final JTableHeader tableHeader = this.table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tableHeader.setFont(new Font("Times New  Roman", 0, 15));
        this.table.setFont(new Font("Times New  Roman", 0, 15));
        this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(380);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(380);
        this.table.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.table.getColumnModel().getColumn(4).setPreferredWidth(100);
        this.jsp.setDividerLocation(350);
        this.jsp.add(new JScrollPane(this.table), "bottom");
        this.jsp.setDividerSize(1);
        this.jpanel1.setLayout(new BorderLayout());
        this.jpanel1.add(this.jsp);
        final JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(null);
        final ChartPanel cp = new DiscountChart(SelectMethodDialog.arr).getChartPanel();
        cp.setBounds(1, 1, 960, 345);
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
                int line = 0;
                int methodNum = 0;
                final ArrayList<String> sonArr = new ArrayList<String>();
                BufferedReader br = null;
                final Pattern pattern = Pattern.compile("^[ \\t]*(public |protected |private |static)(.*)\\([^;]*$");
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    ++SelectMethodDialog.number;
                    this.tableModel.addRow(new Object[] { SelectMethodDialog.number, file.getName() });
                    final List<String> rowData = new ArrayList<String>();
                    rowData.add(String.valueOf(SelectMethodDialog.number));
                    rowData.add(file.getName());
                    this.dataset.add((ArrayList)rowData);
                    sonArr.add(file.getName());
                    while ((s = br.readLine()) != null) {
                        ++line;
                        final Matcher matcher = pattern.matcher(s);
                        if (matcher.find()) {
                            ++methodNum;
                            ++SelectMethodDialog.number;
                            this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", matcher.group(2), line + " строчка" });
                            final List<String> rowDataModel = new ArrayList<String>();
                            rowDataModel.add(String.valueOf(SelectMethodDialog.number));
                            rowDataModel.add("");
                            rowDataModel.add(matcher.group(2));
                            rowDataModel.add(line + " строчка");
                            this.dataset.add((ArrayList)rowDataModel);
                        }
                        if (line % 50 == 0) {
                            sonArr.add(String.valueOf(methodNum));
                        }
                    }
                    br.close();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
                ++SelectMethodDialog.number;
                this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", "", "", methodNum });
                final List<String> rowDataNum = new ArrayList<String>();
                rowDataNum.add(String.valueOf(SelectMethodDialog.number));
                rowDataNum.add("");
                rowDataNum.add("");
                rowDataNum.add("");
                rowDataNum.add(String.valueOf(methodNum));
                this.dataset.add((ArrayList)rowDataNum);
                ++SelectMethodDialog.sumFile;
                SelectMethodDialog.sumMethod += methodNum;
                if (methodNum != 0 && sonArr.size() > 1 && methodNum != Integer.parseInt(sonArr.get(sonArr.size() - 1))) {
                    sonArr.add(String.valueOf(methodNum));
                }
                if (sonArr != null && sonArr.size() > 0) {
                    SelectMethodDialog.arr.add(sonArr);
                }
            }
            else if (file.isFile() && file.getName().endsWith(".py")) {
                int line = 0;
                int methodNum = 0;
                final ArrayList<String> sonArr = new ArrayList<String>();
                BufferedReader br = null;
                final Pattern pattern = Pattern.compile("^[ \\t]*def(.*)\\([^;]*$");
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    ++SelectMethodDialog.number;
                    this.tableModel.addRow(new Object[] { SelectMethodDialog.number, file.getName() });
                    final List<String> rowData = new ArrayList<String>();
                    rowData.add(String.valueOf(SelectMethodDialog.number));
                    rowData.add(file.getName());
                    this.dataset.add((ArrayList)rowData);
                    sonArr.add(file.getName());
                    while ((s = br.readLine()) != null) {
                        ++line;
                        final Matcher matcher = pattern.matcher(s);
                        if (matcher.find()) {
                            ++methodNum;
                            ++SelectMethodDialog.number;
                            this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", matcher.group(1), line });
                            final List<String> rowDataModel = new ArrayList<String>();
                            rowDataModel.add(String.valueOf(SelectMethodDialog.number));
                            rowDataModel.add("");
                            rowDataModel.add(matcher.group(1));
                            rowDataModel.add(line + " строчка");
                            this.dataset.add((ArrayList)rowDataModel);
                        }
                        if (line % 50 == 0) {
                            sonArr.add(String.valueOf(methodNum));
                        }
                    }
                    br.close();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
                ++SelectMethodDialog.number;
                this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", "", "", methodNum });
                final List<String> rowDataNum = new ArrayList<String>();
                rowDataNum.add(String.valueOf(SelectMethodDialog.number));
                rowDataNum.add("");
                rowDataNum.add("");
                rowDataNum.add("");
                rowDataNum.add(String.valueOf(methodNum));
                this.dataset.add((ArrayList)rowDataNum);
                ++SelectMethodDialog.sumFile;
                SelectMethodDialog.sumMethod += methodNum;
                if (methodNum != 0 && sonArr.size() > 1 && methodNum != Integer.parseInt(sonArr.get(sonArr.size() - 1))) {
                    sonArr.add(String.valueOf(methodNum));
                }
                if (sonArr != null && sonArr.size() > 0) {
                    SelectMethodDialog.arr.add(sonArr);
                }
            }
            else if (file.isDirectory()) {
                this.selectMethod(file);
            }
        }
    }

    public void fileMethod(final File file) {
        if (file.isFile() && file.getName().endsWith(".java")) {
            int line = 0;
            int methodNum = 0;
            final ArrayList<String> sonArr = new ArrayList<String>();
            BufferedReader br = null;
            final Pattern pattern = Pattern.compile("^[ \\t]*(public |protected |private |static)(.*)\\([^;]*$");
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                String s = null;
                ++SelectMethodDialog.number;
                this.tableModel.addRow(new Object[] { SelectMethodDialog.number, file.getName() });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(SelectMethodDialog.number));
                rowData.add(file.getName());
                this.dataset.add((ArrayList)rowData);
                sonArr.add(file.getName());
                while ((s = br.readLine()) != null) {
                    ++line;
                    final Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        ++methodNum;
                        ++SelectMethodDialog.number;
                        this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", matcher.group(2), line });
                        final List<String> rowDataModel = new ArrayList<String>();
                        rowDataModel.add(String.valueOf(SelectMethodDialog.number));
                        rowDataModel.add("");
                        rowDataModel.add(matcher.group(2));
                        rowDataModel.add(line + " строчка");
                        this.dataset.add((ArrayList)rowDataModel);
                    }
                    if (line % 50 == 0) {
                        sonArr.add(String.valueOf(methodNum));
                    }
                }
                br.close();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
            ++SelectMethodDialog.number;
            this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", "", "", methodNum });
            final List<String> rowDataNum = new ArrayList<String>();
            rowDataNum.add(String.valueOf(SelectMethodDialog.number));
            rowDataNum.add("");
            rowDataNum.add("");
            rowDataNum.add("");
            rowDataNum.add(String.valueOf(methodNum));
            this.dataset.add((ArrayList)rowDataNum);
            ++SelectMethodDialog.sumFile;
            SelectMethodDialog.sumMethod += methodNum;
            if (methodNum != 0 && sonArr.size() > 1 && methodNum != Integer.parseInt(sonArr.get(sonArr.size() - 1))) {
                sonArr.add(String.valueOf(methodNum));
            }
            if (sonArr != null && sonArr.size() > 0) {
                SelectMethodDialog.arr.add(sonArr);
            }
        }
        else if (file.isFile() && file.getName().endsWith(".py")) {
            int line = 0;
            int methodNum = 0;
            final ArrayList<String> sonArr = new ArrayList<String>();
            BufferedReader br = null;
            final Pattern pattern = Pattern.compile("^[ \\t]*def(.*)\\([^;]*$");
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                String s = null;
                ++SelectMethodDialog.number;
                this.tableModel.addRow(new Object[] { SelectMethodDialog.number, file.getName() });
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(SelectMethodDialog.number));
                rowData.add(file.getName());
                this.dataset.add((ArrayList)rowData);
                sonArr.add(file.getName());
                while ((s = br.readLine()) != null) {
                    ++line;
                    final Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        ++methodNum;
                        ++SelectMethodDialog.number;
                        this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", matcher.group(1), line });
                        final List<String> rowDataModel = new ArrayList<String>();
                        rowDataModel.add(String.valueOf(SelectMethodDialog.number));
                        rowDataModel.add("");
                        rowDataModel.add(matcher.group(1));
                        rowDataModel.add(line + " строчка");
                        this.dataset.add((ArrayList)rowDataModel);
                    }
                    if (line % 50 == 0) {
                        sonArr.add(String.valueOf(methodNum));
                    }
                }
                br.close();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
            ++SelectMethodDialog.number;
            this.tableModel.addRow(new Object[] { SelectMethodDialog.number, "", "", "", methodNum });
            final List<String> rowDataNum = new ArrayList<String>();
            rowDataNum.add(String.valueOf(SelectMethodDialog.number));
            rowDataNum.add("");
            rowDataNum.add("");
            rowDataNum.add("");
            rowDataNum.add(String.valueOf(methodNum));
            this.dataset.add((ArrayList)rowDataNum);
            ++SelectMethodDialog.sumFile;
            SelectMethodDialog.sumMethod += methodNum;
            if (methodNum != 0 && sonArr.size() > 1 && methodNum != Integer.parseInt(sonArr.get(sonArr.size() - 1))) {
                sonArr.add(String.valueOf(methodNum));
            }
            if (sonArr != null && sonArr.size() > 0) {
                SelectMethodDialog.arr.add(sonArr);
            }
        }
        else if (file.isDirectory()) {}
    }

    private void btnDownload() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(1);
        final Component parent = null;
        final int returnVal = chooser.showSaveDialog(parent);
        if (returnVal == 0) {
            final String selectPath = chooser.getSelectedFile().getPath();
            final String[] headers = { "Серийный номер", "Имя файла", "Имя функции", "Функциональное положение", "Количество функций" };
            final File file = new File(selectPath + "//data.xls");
            if (file.exists()) {
                JOptionPane.showMessageDialog(null, "Файл уже существует. Пожалуйста, удалите файл и попробуйте снова.");
            }
            else {
                final ChartPanel cp = new DiscountChartExcel(SelectMethodDialog.arr, selectPath, headers, this.dataset).getChartPanel();
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
        font.setFontName("\u5b8b\u4f53");
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
        final SelectMethodDialog dialog = new SelectMethodDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    static {
        SelectMethodDialog.sumFile = 0L;
        SelectMethodDialog.sumMethod = 0L;
        SelectMethodDialog.number = 0L;
        SelectMethodDialog.arr = new ArrayList<ArrayList<String>>();
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
        (this.downloadButton = button3).setText("скачать");
        comp2.add(button3, new GridConstraints(0, 2, 1, 1, 0, 1, 3, 0, null, null, null));
        final JPanel panel = new JPanel();
        (this.jpanel1 = panel).setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1, false, false));
        contentPane.add(panel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, null, null, null));
    }
}
