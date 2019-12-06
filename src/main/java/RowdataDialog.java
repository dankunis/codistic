import com.intellij.uiDesigner.core.Spacer;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Insets;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import com.intellij.util.ui.JBUI;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.OutputStream;
import org.jfree.chart.JFreeChart;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
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

import com.intellij.openapi.actionSystem.AnActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class RowdataDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel jpanel1;
    private JPanel jpanelButton;
    private JButton downloadButton;
    private JSplitPane jsp;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<ArrayList<String>> dataset;
    public static long sumFile;
    public static long sumLine;
    public static long commentLines;
    public static long blankLines;
    public static long number;

    public RowdataDialog(final AnActionEvent anActionEvent) {
        this.setupUI();
        this.dataset = new ArrayList<ArrayList<String>>();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonOK);
        this.setTitle("Code line");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                RowdataDialog.this.btnDownload();
            }
        });
        this.buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                RowdataDialog.this.onOK();
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                RowdataDialog.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                RowdataDialog.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                RowdataDialog.this.onCancel();
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
        RowdataDialog.sumFile = 0L;
        RowdataDialog.sumLine = 0L;
        RowdataDialog.commentLines = 0L;
        RowdataDialog.blankLines = 0L;
        RowdataDialog.number = 0L;
        this.dataset.clear();
        (this.tableModel = (DefaultTableModel)this.table.getModel()).setRowCount(0);
        this.tableModel.setColumnIdentifiers(new Object[] { "Номер", "Имя файла", "Строки кода", "Комментарии", "Пустые строки" });
        final File inFile = new File(myfile);
        if (inFile.isFile()) {
            this.fileStatics(inFile);
        }
        else {
            this.codeStatics(inFile);
        }
        this.tableModel.addRow(new Object[] { "", "Всего:" + RowdataDialog.sumFile, RowdataDialog.sumLine, RowdataDialog.commentLines, RowdataDialog.blankLines });
        final List<String> rowData = new ArrayList<String>();
        rowData.add("");
        rowData.add("Всего:" + String.valueOf(RowdataDialog.sumFile));
        rowData.add(String.valueOf(RowdataDialog.sumLine));
        rowData.add(String.valueOf(RowdataDialog.commentLines));
        rowData.add(String.valueOf(RowdataDialog.blankLines));
        this.dataset.add((ArrayList)rowData);
        this.table.setModel(this.tableModel);
        this.table.setRowHeight(30);
        final JTableHeader tableHeader = this.table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tableHeader.setFont(new Font("Times New Roman", 0, 15));
        this.table.setFont(new Font("Times New Roman", 0, 15));
        this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(560);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(200);
        this.table.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.table.getColumnModel().getColumn(4).setPreferredWidth(100);
        this.jsp.setDividerLocation(350);
        this.jsp.add(new JScrollPane(this.table), "bottom");
        this.jsp.setDividerSize(1);
        this.jpanel1.setLayout(new BorderLayout());
        this.jpanel1.add(this.jsp);
        final JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(null);
        final ChartPanel cp = new PieChart(RowdataDialog.sumLine, RowdataDialog.commentLines, RowdataDialog.blankLines).getChartPanel();
        cp.setBounds(1, 1, 961, 345);
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
        else {
            path = null;
        }
        return path;
    }

    public String scanFile(final File... file) {
        final BufferedReader br = null;
        final CodeFace cf = null;
        if (file == null || file.length <= 0) {
            return "Файл не выбран";
        }
        String name = "";
        final StringBuilder sb = new StringBuilder("");
        for (final File f : file) {
            name = f.getName();
            if (!name.endsWith(".java")) {
                if (!name.endsWith(".xml")) {
                    return name + "[Формат файла не может быть проанализирован]\r\n";
                }
            }
            cf.start(f, br);
            sb.append(name).append("\t");
            sb.append((CharSequence)cf.to_StringBuilder()).append("\r\n");
        }
        return sb.toString();
    }

    private void onOK() {
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    }

    private void btnDownload() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(1);
        final Component parent = null;
        final int returnVal = chooser.showSaveDialog(parent);
        if (returnVal == 0) {
            final String selectPath = chooser.getSelectedFile().getPath();
            final String[] headers = { "Номер", "Имя файла", "Строки кода", "Комментарии", "Пустые строки" };
            final File file = new File(selectPath + "//data.xls");
            if (file.exists()) {
                JOptionPane.showMessageDialog(null, "Файл уже существует! Пожалуйста, удалите файл и попробуйте снова!");
            }
            else {
                final JFreeChart chart = new PieChart2(RowdataDialog.sumLine, RowdataDialog.commentLines, RowdataDialog.blankLines, selectPath, headers, this.dataset).getChart();
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

    public void codeStatics(final File inFile) {
        for (final File file : inFile.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                int line = 0;
                int blankLine = 0;
                int commentLine = 0;
                boolean comment = false;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        s = s.replaceAll("\\s", "");
                        if (s.matches("^[ ]*$")) {
                            ++blankLine;
                        }
                        else if (s.startsWith("//")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("/*") && s.endsWith("*/")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("/*") && !s.endsWith("*/")) {
                            ++commentLine;
                            comment = true;
                        }
                        else if (comment) {
                            ++commentLine;
                            if (!s.endsWith("*/")) {
                                continue;
                            }
                            comment = false;
                        }
                        else {
                            ++line;
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
                ++RowdataDialog.number;
                this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
                ++RowdataDialog.sumFile;
                RowdataDialog.sumLine += line;
                RowdataDialog.commentLines += commentLine;
                RowdataDialog.blankLines += blankLine;
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(RowdataDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(line));
                rowData.add(String.valueOf(commentLine));
                rowData.add(String.valueOf(blankLine));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".py")) {
                int line = 0;
                int blankLine = 0;
                int commentLine = 0;
                boolean comment = false;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        s = s.replaceAll("\\s", "");
                        if (s.matches("^[ ]*$")) {
                            ++blankLine;
                        }
                        else if (s.startsWith("//")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("/*") && s.endsWith("*/")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("/*") && !s.endsWith("*/")) {
                            ++commentLine;
                            comment = true;
                        }
                        else if (comment) {
                            ++commentLine;
                            if (!s.endsWith("*/")) {
                                continue;
                            }
                            comment = false;
                        }
                        else {
                            ++line;
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
                ++RowdataDialog.number;
                this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
                ++RowdataDialog.sumFile;
                RowdataDialog.sumLine += line;
                RowdataDialog.commentLines += commentLine;
                RowdataDialog.blankLines += blankLine;
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(RowdataDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(line));
                rowData.add(String.valueOf(commentLine));
                rowData.add(String.valueOf(blankLine));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".php")) {
                int line = 0;
                int blankLine = 0;
                int commentLine = 0;
                boolean comment = false;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        s = s.replaceAll("\\s", "");
                        if (s.matches("^[ ]*$")) {
                            ++blankLine;
                        }
                        else if (s.startsWith("//")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("/*") && s.endsWith("*/")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("/*") && !s.endsWith("*/")) {
                            ++commentLine;
                            comment = true;
                        }
                        else if (comment) {
                            ++commentLine;
                            if (!s.endsWith("*/")) {
                                continue;
                            }
                            comment = false;
                        }
                        else {
                            ++line;
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
                ++RowdataDialog.number;
                this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
                ++RowdataDialog.sumFile;
                RowdataDialog.sumLine += line;
                RowdataDialog.commentLines += commentLine;
                RowdataDialog.blankLines += blankLine;
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(RowdataDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(line));
                rowData.add(String.valueOf(commentLine));
                rowData.add(String.valueOf(blankLine));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".xml")) {
                int line = 0;
                int blankLine = 0;
                int commentLine = 0;
                boolean comment = false;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        s = s.replaceAll("\\s", "");
                        if (s.matches("^[ ]*$")) {
                            ++blankLine;
                        }
                        else if (s.startsWith("<!--") && s.endsWith("-->")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("<!--") && !s.endsWith("-->")) {
                            ++commentLine;
                            comment = true;
                        }
                        else if (comment) {
                            ++commentLine;
                            if (!s.endsWith("-->")) {
                                continue;
                            }
                            comment = false;
                        }
                        else {
                            ++line;
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
                ++RowdataDialog.number;
                this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
                ++RowdataDialog.sumFile;
                RowdataDialog.sumLine += line;
                RowdataDialog.commentLines += commentLine;
                RowdataDialog.blankLines += blankLine;
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(RowdataDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(line));
                rowData.add(String.valueOf(commentLine));
                rowData.add(String.valueOf(blankLine));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isFile() && file.getName().endsWith(".jsp")) {
                int line = 0;
                int blankLine = 0;
                int commentLine = 0;
                boolean comment = false;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        s = s.replaceAll("\\s", "");
                        if (s.matches("^[ ]*$")) {
                            ++blankLine;
                        }
                        else if (s.startsWith("<!--") && s.endsWith("-->")) {
                            ++commentLine;
                        }
                        else if (s.startsWith("<!--") && !s.endsWith("-->")) {
                            ++commentLine;
                            comment = true;
                        }
                        else if (comment) {
                            ++commentLine;
                            if (!s.endsWith("-->")) {
                                continue;
                            }
                            comment = false;
                        }
                        else {
                            ++line;
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
                ++RowdataDialog.number;
                this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
                ++RowdataDialog.sumFile;
                RowdataDialog.sumLine += line;
                RowdataDialog.commentLines += commentLine;
                RowdataDialog.blankLines += blankLine;
                final List<String> rowData = new ArrayList<String>();
                rowData.add(String.valueOf(RowdataDialog.number));
                rowData.add(file.getName());
                rowData.add(String.valueOf(line));
                rowData.add(String.valueOf(commentLine));
                rowData.add(String.valueOf(blankLine));
                this.dataset.add((ArrayList)rowData);
            }
            else if (file.isDirectory()) {
                this.codeStatics(file);
            }
        }
    }

    public void fileStatics(final File file) {
        if (file.isFile() && (file.getName().endsWith(".java") || file.getName().endsWith(".py") || file.getName().endsWith(".php"))) {
            int line = 0;
            int blankLine = 0;
            int commentLine = 0;
            boolean comment = false;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                String s = null;
                while ((s = br.readLine()) != null) {
                    s = s.replaceAll("\\s", "");
                    if (s.matches("^[ ]*$")) {
                        ++blankLine;
                    }
                    else if (s.startsWith("//")) {
                        ++commentLine;
                    }
                    else if (s.startsWith("/*") && s.endsWith("*/")) {
                        ++commentLine;
                    }
                    else if (s.startsWith("/*") && !s.endsWith("*/")) {
                        ++commentLine;
                        comment = true;
                    }
                    else if (comment) {
                        ++commentLine;
                        if (!s.endsWith("*/")) {
                            continue;
                        }
                        comment = false;
                    }
                    else {
                        ++line;
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
            ++RowdataDialog.number;
            this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
            ++RowdataDialog.sumFile;
            RowdataDialog.sumLine += line;
            RowdataDialog.commentLines += commentLine;
            RowdataDialog.blankLines += blankLine;
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(RowdataDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(line));
            rowData.add(String.valueOf(commentLine));
            rowData.add(String.valueOf(blankLine));
            this.dataset.add((ArrayList)rowData);
        }
        else if (file.isFile() && (file.getName().endsWith(".xml") || file.getName().endsWith(".jsp"))) {
            int line = 0;
            int blankLine = 0;
            int commentLine = 0;
            boolean comment = false;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                String s = null;
                while ((s = br.readLine()) != null) {
                    s = s.replaceAll("\\s", "");
                    if (s.matches("^[ ]*$")) {
                        ++blankLine;
                    }
                    else if (s.startsWith("<!--") && s.endsWith("-->")) {
                        ++commentLine;
                    }
                    else if (s.startsWith("<!--") && !s.endsWith("-->")) {
                        ++commentLine;
                        comment = true;
                    }
                    else if (comment) {
                        ++commentLine;
                        if (!s.endsWith("-->")) {
                            continue;
                        }
                        comment = false;
                    }
                    else {
                        ++line;
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
            ++RowdataDialog.number;
            this.tableModel.addRow(new Object[] { RowdataDialog.number, file.getName(), line, commentLine, blankLine });
            ++RowdataDialog.sumFile;
            RowdataDialog.sumLine += line;
            RowdataDialog.commentLines += commentLine;
            RowdataDialog.blankLines += blankLine;
            final List<String> rowData = new ArrayList<String>();
            rowData.add(String.valueOf(RowdataDialog.number));
            rowData.add(file.getName());
            rowData.add(String.valueOf(line));
            rowData.add(String.valueOf(commentLine));
            rowData.add(String.valueOf(blankLine));
            this.dataset.add((ArrayList)rowData);
        }
    }

    static {
        RowdataDialog.sumFile = 0L;
        RowdataDialog.sumLine = 0L;
        RowdataDialog.commentLines = 0L;
        RowdataDialog.blankLines = 0L;
        RowdataDialog.number = 0L;
    }

    private void setupUI() {
        JPanel contentPane = new JPanel();
        JPanel comp = new JPanel();
        this.contentPane = contentPane;

        contentPane.setLayout(new GridLayoutManager(2, 1, JBUI.insets(10),
                -1, -1, false, false));
        comp.setLayout(new GridLayoutManager(1, 2, JBUI.emptyInsets(),
                -1, -1, false, false));
        contentPane.add(comp, new GridConstraints(1, 0, 1, 1, 0, 3,
                3, 1, null, null, null));
        comp.add(new Spacer(), new GridConstraints(0, 0, 1, 1, 0, 1,
                6, 1, null, null, null));

        JPanel panel = new JPanel();
        this.jpanelButton = panel;

        this.jpanelButton.setLayout(new GridLayoutManager(1, 3, JBUI.emptyInsets(), -1,
                -1, false, false));
        comp.add(panel, new GridConstraints(0, 1, 1, 1, 0, 3,
                3, 3, null, null, null));

        JButton button = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JPanel panel2 = new JPanel();
        this.buttonCancel = button2;
        this.buttonOK = button;
        this.downloadButton = button3;
        this.jpanel1 = panel2;

        this.buttonOK.setText("OK");
        panel.add(button, new GridConstraints(0, 0, 1, 1, 0, 1,
                3, 0, null, null, null));
        this.buttonCancel.setText("Cancel");
        panel.add(button2, new GridConstraints(0, 1, 1, 1, 0, 1,
                3, 0, null, null, null));
        this.downloadButton.setText("Скачать");
        panel.add(button3, new GridConstraints(0, 2, 1, 1, 0, 1,
                3, 0, null, null, null));
        this.jpanel1.setLayout(new GridLayoutManager(1, 1, JBUI.emptyInsets(), -1,
                -1, false, false));
        contentPane.add(panel2, new GridConstraints(0, 0, 1, 1, 0, 3,
                3, 3, null, null, null));
    }

    public interface CodeFace
    {
        void start(final File p0, final BufferedReader p1);
        String to_String();
        StringBuilder to_StringBuilder();
        void clear();
    }
}
