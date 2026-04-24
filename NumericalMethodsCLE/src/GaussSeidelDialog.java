import javax.swing.*;

public class GaussSeidelDialog extends GaussJacobiDialog {
    public GaussSeidelDialog(JFrame parent) {
        super(parent);
        setTitle("Gauss-Seidel Iteration");
    }

    @Override
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
            double[] x = new double[n];        // current values (will be updated in place)
            double[] xold = new double[n];    // to hold previous iteration values
            int iter = 0;
            double maxErr = tol + 1;

            while (maxErr > tol && iter < 100) {
                // Save previous values for error calculation
                System.arraycopy(x, 0, xold, 0, n);
                // Perform one Gauss-Seidel iteration
                for (int i = 0; i < n; i++) {
                    double sum = 0;
                    for (int j = 0; j < n; j++) {
                        if (j != i) sum += A[i][j] * x[j]; // uses already updated x[j] for j < i (Seidel)
                    }
                    x[i] = (b[i] - sum) / A[i][i];
                }
                // Compute absolute errors
                double[] errors = new double[n];
                double maxErrLocal = 0;
                for (int i = 0; i < n; i++) {
                    errors[i] = Math.abs(x[i] - xold[i]);
                    if (errors[i] > maxErrLocal) maxErrLocal = errors[i];
                }
                maxErr = maxErrLocal;
                // Add row to table
                Object[] row = new Object[3*n + 2]; // iter, x (n), xnew (n), errors (n)
                row[0] = iter + 1;
                for (int i = 0; i < n; i++) row[1 + i] = String.format("%.6f", xold[i]);
                for (int i = 0; i < n; i++) row[1 + n + i] = String.format("%.6f", x[i]);
                for (int i = 0; i < n; i++) row[1 + 2*n + i] = (iter == 0) ? "---" : String.format("%.6f", errors[i]);
                tableModel.addRow(row);
                if (maxErr < tol) break;
                iter++;
            }
            JOptionPane.showMessageDialog(this,
                    String.format("Solution after %d iterations:\nx1 = %.4f\nx2 = %.4f\nx3 = %.4f",
                            iter+1, x[0], x[1], x[2]));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}