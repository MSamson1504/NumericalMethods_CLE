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
            int iter = 0;
            while (iter < 200) {
                double[] xold = x.clone();
                for (int i = 0; i < n; i++) {
                    double sum1 = 0, sum2 = 0;
                    for (int j = 0; j < i; j++) sum1 += A[i][j] * x[j];
                    for (int j = i + 1; j < n; j++) sum2 += A[i][j] * xold[j];
                    x[i] = (b[i] - sum1 - sum2) / A[i][i];
                }
                double err = 0;
                for (int i = 0; i < n; i++) err = Math.max(err, Math.abs(x[i] - xold[i]));
                Object[] row = new Object[n + 2];
                row[0] = iter + 1;
                for (int i = 0; i < n; i++) row[i + 1] = String.format("%.8f", x[i]);
                row[n + 1] = String.format("%.6e", err);
                tableModel.addRow(row);
                if (err < tol) break;
                iter++;
            }
            JOptionPane.showMessageDialog(this, "Solution: " + java.util.Arrays.toString(x));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}