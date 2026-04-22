import javax.swing.*;

public class TrapezoidalDialog extends IntegrationBase {
    public TrapezoidalDialog(JFrame parent) {
        super(parent, "Trapezoidal Rule");
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
            double h = (b - a) / n;
            double sum = (f(a) + f(b)) / 2;
            for (int i = 1; i < n; i++) {
                double xi = a + i * h;
                sum += f(xi);
                tableModel.addRow(new Object[]{i, String.format("%.6f", xi), String.format("%.6f", f(xi)), 1.0});
            }
            double integral = sum * h;
            JOptionPane.showMessageDialog(this, String.format("Integral ≈ %.8f\nExact = %.8f", integral, (b*b*b - a*a*a)/3));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}