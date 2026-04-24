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
        setSize(900, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Default matrix and RHS for the example:
        // 6x1 - 2x2 +  x3 = 11
        // -2x1 + 7x2 + 2x3 = 5
        //  x1 + 2x2 - 5x3 = -1
        JPanel input = new JPanel(new GridLayout(1, 2, 10, 10));
        matrixArea = new JTextArea("6  -2   1\n-2   7   2\n1   2  -5", 5, 15);
        rhsArea = new JTextArea("11\n5\n-1", 5, 5);
        input.add(new JScrollPane(matrixArea));
        input.add(new JScrollPane(rhsArea));
        add(input, BorderLayout.NORTH);

        JPanel tolPanel = new JPanel(new FlowLayout());
        tolField = new JTextField("0.0001", 10);
        tolPanel.add(new JLabel("Tolerance (Ea ≤):"));
        tolPanel.add(tolField);
        add(tolPanel, BorderLayout.CENTER);

        // Table columns: Iteration, x1, x2, x3, x1', x2', x3', Ea1, Ea2, Ea3
        tableModel = new DefaultTableModel(new String[]{
                "Iteration", "x1", "x2", "x3", "x1'", "x2'", "x3'", "Ea1", "Ea2", "Ea3"
        }, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
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
            // Parse matrix A
            String[] rows = matrixArea.getText().trim().split("\n");
            int n = rows.length;
            double[][] A = new double[n][n];
            for (int i = 0; i < n; i++) {
                String[] vals = rows[i].trim().split("\\s+");
                for (int j = 0; j < n; j++) A[i][j] = Double.parseDouble(vals[j]);
            }
            // Parse RHS b
            double[] b = new double[n];
            String[] bVals = rhsArea.getText().trim().split("\n");
            for (int i = 0; i < n; i++) b[i] = Double.parseDouble(bVals[i]);
            double tol = Double.parseDouble(tolField.getText());

            // Initial guess: all zeros
            double[] x = new double[n];
            double[] xnew = new double[n];
            int iter = 0;
            double maxErr = tol + 1;

            // Add initial guess row (iteration 0 or 1? The example starts with iteration 1 showing old x=0 and computed x')
            // We'll follow the example: first row shows x1,x2,x3 = 0,0,0 and then x1',x2',x3' computed.
            while (maxErr > tol && iter < 100) {
                // Compute new values using Jacobi formula: xnew_i = (b_i - sum_{j!=i} A_ij * x_j) / A_ii
                for (int i = 0; i < n; i++) {
                    double sum = 0;
                    for (int j = 0; j < n; j++) if (j != i) sum += A[i][j] * x[j];
                    xnew[i] = (b[i] - sum) / A[i][i];
                }
                // Calculate absolute errors for each variable
                double[] errors = new double[n];
                for (int i = 0; i < n; i++) errors[i] = Math.abs(xnew[i] - x[i]);
                maxErr = 0;
                for (double err : errors) maxErr = Math.max(maxErr, err);
                // Add row to table
                Object[] row = new Object[3*n + 2]; // iter + x (n) + xnew (n) + errors (n) = 1+3n
                row[0] = iter + 1;
                for (int i = 0; i < n; i++) row[1 + i] = String.format("%.6f", x[i]);
                for (int i = 0; i < n; i++) row[1 + n + i] = String.format("%.6f", xnew[i]);
                for (int i = 0; i < n; i++) row[1 + 2*n + i] = (iter == 0) ? "---" : String.format("%.6f", errors[i]);
                tableModel.addRow(row);
                // Check convergence
                if (maxErr < tol) break;
                // Update x for next iteration
                System.arraycopy(xnew, 0, x, 0, n);
                iter++;
            }
            JOptionPane.showMessageDialog(this,
                    String.format("Solution after %d iterations:\nx1 = %.4f\nx2 = %.4f\nx3 = %.4f",
                            iter+1, xnew[0], xnew[1], xnew[2]));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}