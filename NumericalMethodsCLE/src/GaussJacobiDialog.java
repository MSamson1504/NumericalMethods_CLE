import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GaussJacobiDialog extends JDialog {
    protected JTextArea matrixArea, rhsArea;
    protected JTextField tolField;
    protected JTable table;
    protected DefaultTableModel tableModel;

    public GaussJacobiDialog(JFrame parent) {
        super(parent, "Gauss-Jacobi Iteration", true);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setSize(750, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel input = new JPanel(new GridLayout(1, 2, 10, 10));
        matrixArea = new JTextArea("4  -1   0\n-1   4  -1\n0  -1   4", 5, 15);
        rhsArea = new JTextArea("15\n10\n10", 5, 5);
        input.add(new JScrollPane(matrixArea));
        input.add(new JScrollPane(rhsArea));
        add(input, BorderLayout.NORTH);

        JPanel tolPanel = new JPanel(new FlowLayout());
        tolField = new JTextField("1e-6", 10);
        tolPanel.add(new JLabel("Tolerance:"));
        tolPanel.add(tolField);
        add(tolPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new String[]{"Iteration", "x1", "x2", "x3", "Max Error"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new TitledBorder("Iteration Details"));
        add(scroll, BorderLayout.SOUTH);

        JButton calc = new JButton("Calculate");
        calc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calc.setBackground(new Color(70, 130, 200));
        calc.setForeground(Color.WHITE);
        calc.addActionListener(this::solve);
        JPanel btnPanel = new JPanel();
        btnPanel.add(calc);
        add(btnPanel, BorderLayout.EAST);

        setVisible(true);
    }

    protected void solve(java.awt.event.ActionEvent e) {
        try {
            tableModel.setRowCount(0);
            String[] rows = matrixArea.getText().trim().split("\n");
            int n = rows.length;
            double[][] A = new double[n][n];
            for (int i = 0; i < n; i++) {
                String[] vals = rows[i].trim().split("\\s+");
                for (int j = 0; j < n; j++) A[i][j] = Double.parseDouble(vals[j]);
            }
            double[] b = new double[n];
            String[] bVals = rhsArea.getText().trim().split("\n");
            for (int i = 0; i < n; i++) b[i] = Double.parseDouble(bVals[i]);
            double tol = Double.parseDouble(tolField.getText());

            double[] x = new double[n];
            double[] xnew = new double[n];
            int iter = 0;
            while (iter < 200) {
                for (int i = 0; i < n; i++) {
                    double sum = 0;
                    for (int j = 0; j < n; j++) if (j != i) sum += A[i][j] * x[j];
                    xnew[i] = (b[i] - sum) / A[i][i];
                }
                double err = 0;
                for (int i = 0; i < n; i++) err = Math.max(err, Math.abs(xnew[i] - x[i]));
                Object[] row = new Object[n + 2];
                row[0] = iter + 1;
                for (int i = 0; i < n; i++) row[i + 1] = String.format("%.8f", xnew[i]);
                row[n + 1] = String.format("%.6e", err);
                tableModel.addRow(row);
                if (err < tol) break;
                System.arraycopy(xnew, 0, x, 0, n);
                iter++;
            }
            JOptionPane.showMessageDialog(this, "Solution: " + java.util.Arrays.toString(xnew));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}