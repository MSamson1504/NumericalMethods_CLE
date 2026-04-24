import javax.swing.*;

public class SimpsonsDialog extends IntegrationBase {
    public SimpsonsDialog(JFrame parent) {
        super(parent, "Simpson's Rule");
        addFunctionField();
        addInputPanel();
        addTable(new String[]{"Subinterval i", "x_i", "f(x_i)", "Weight"});
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
            double sum = eval(a) + eval(b);
            for (int i = 1; i < n; i++) {
                double xi = a + i * h;
                double fi = eval(xi);
                int weight = (i % 2 == 0) ? 2 : 4;
                sum += weight * fi;
                tableModel.addRow(new Object[]{i, String.format("%.6f", xi), String.format("%.6f", fi), weight});
            }
            double integral = sum * h / 3;
            JOptionPane.showMessageDialog(this, String.format("Integral ≈ %.8f", integral));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}