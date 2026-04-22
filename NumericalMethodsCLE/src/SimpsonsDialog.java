import javax.swing.*;

public class SimpsonsDialog extends IntegrationBase {
    public SimpsonsDialog(JFrame parent) {
        super(parent, "Simpson's Rule");
        addInputPanel();
        addTable(new String[]{"Subinterval", "x_i", "f(x_i)", "Weight"});
        addCalculateButton();
        setVisible(true);
    }

    @Override
    protected void doCalculation(java.awt.event.ActionEvent e) {
        try {
            tableModel.setRowCount(0);
            double a = Double.parseDouble(txtA.getText());
            double b = Double.parseDouble(txtB.getText());
            int n = Integer.parseInt(txtN.getText());
            if (n % 2 != 0) {
                JOptionPane.showMessageDialog(this, "n must be even for Simpson's rule.");
                return;
            }
            double h = (b - a) / n;
            double sum = f(a) + f(b);
            for (int i = 1; i < n; i++) {
                double xi = a + i * h;
                double weight = (i % 2 == 0) ? 2 : 4;
                sum += weight * f(xi);
                tableModel.addRow(new Object[]{i, String.format("%.6f", xi), String.format("%.6f", f(xi)), weight});
            }
            double integral = sum * h / 3;
            JOptionPane.showMessageDialog(this, String.format("Integral ≈ %.8f\nExact = %.8f", integral, (b*b*b - a*a*a)/3));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}