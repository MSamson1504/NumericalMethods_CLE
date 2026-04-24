import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class IntegrationBase extends JDialog {
    protected JTextField txtA, txtB, txtN, txtFunction;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected ExpressionEvaluator evaluator;

    public IntegrationBase(JFrame parent, String title) {
        super(parent, title, true);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setSize(750, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        evaluator = new ExpressionEvaluator();
    }

    protected void addFunctionField() {
        txtFunction = new JTextField("x^2", 20);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("f(x) ="));
        panel.add(txtFunction);
        add(panel, BorderLayout.NORTH);
    }

    protected void addInputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        txtA = new JTextField("0");
        txtB = new JTextField("2");
        txtN = new JTextField("100");
        panel.add(new JLabel("Lower limit a:"));
        panel.add(txtA);
        panel.add(new JLabel("Upper limit b:"));
        panel.add(txtB);
        panel.add(new JLabel("Number of subintervals:"));
        panel.add(txtN);
        add(panel, BorderLayout.CENTER);
    }

    protected void addTable(String[] columns) {
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new TitledBorder("Subinterval Data"));
        add(scroll, BorderLayout.SOUTH);
    }

    protected void addCalculateButton() {
        JButton calc = new JButton("Calculate");
        calc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calc.setBackground(new Color(70, 130, 200));
        calc.setForeground(Color.WHITE);
        calc.addActionListener(this::doCalculation);
        JPanel btnPanel = new JPanel();
        btnPanel.add(calc);
        add(btnPanel, BorderLayout.EAST);
    }

    protected double eval(double x) {
        return evaluator.evaluate(txtFunction.getText(), x);
    }

    protected abstract void doCalculation(java.awt.event.ActionEvent e);
}