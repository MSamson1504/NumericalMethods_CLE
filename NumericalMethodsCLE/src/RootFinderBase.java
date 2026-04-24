import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class RootFinderBase extends JDialog {
    protected JTextField txtA, txtB, txtInitial, txtTol, txtFunction;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected JButton calcBtn;
    protected ExpressionEvaluator evaluator;

    public RootFinderBase(JFrame parent, String title) {
        super(parent, title, true);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        evaluator = new ExpressionEvaluator();
    }

    protected void addInputPanel(Component... comps) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        for (Component c : comps) panel.add(c);
        add(panel, BorderLayout.NORTH);
    }

    protected void addFunctionField() {
        txtFunction = new JTextField("sqrt(x)-cos(x)", 20);
        addInputPanel(new JLabel("f(x) ="), txtFunction);
    }

    protected void addTable(String[] columns) {
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new TitledBorder("Iteration Details"));
        add(scroll, BorderLayout.CENTER);
    }

    protected void addCalculateButton() {
        calcBtn = new JButton("Calculate");
        calcBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calcBtn.setBackground(new Color(70, 130, 200));
        calcBtn.setForeground(Color.WHITE);
        calcBtn.setFocusPainted(false);
        calcBtn.addActionListener(this::doCalculation);
        JPanel btnPanel = new JPanel();
        btnPanel.add(calcBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    protected double eval(double x) {
        return evaluator.evaluate(txtFunction.getText(), x);
    }

    protected abstract void doCalculation(java.awt.event.ActionEvent e);
    protected void clearTable() { tableModel.setRowCount(0); }
    protected void addRow(Object... row) { tableModel.addRow(row); }
}