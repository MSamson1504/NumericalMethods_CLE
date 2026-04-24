import javax.swing.*;

public class TrapezoidalDialog extends IntegrationBase {
    public TrapezoidalDialog(JFrame parent) {
        super(parent, "Trapezoidal Rule");
        addFunctionField();
        addInputPanel();
        addTable(new String[]{"Subinterval i", "x_i", "f(x_i)"});
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
            double sum = (eval(a) + eval(b)) / 2;
            for (int i = 1; i < n; i++) {
                double xi = a + i * h;
                sum += eval(xi);
                tableModel.addRow(new Object[]{i, String.format("%.6f", xi), String.format("%.6f", eval(xi))});
            }
            double integral = sum * h;
            JOptionPane.showMessageDialog(this, String.format("Integral ≈ %.8f", integral));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}