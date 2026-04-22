import javax.swing.*;

public class RegulaFalsiDialog extends RootFinderBase {
    public RegulaFalsiDialog(JFrame parent) {
        super(parent, "Regula-Falsi Method");
        txtA = new JTextField("1");
        txtB = new JTextField("2");
        txtTol = new JTextField("1e-6");
        addInputPanel(new JLabel("Interval a:"), txtA,
                new JLabel("Interval b:"), txtB,
                new JLabel("Tolerance:"), txtTol);
        addTable(new String[]{"Iteration", "a", "b", "c", "f(c)", "Error"});
        addCalculateButton();
        setVisible(true);
    }

    @Override
    protected void doCalculation(java.awt.event.ActionEvent e) {
        try {
            clearTable();
            double a = Double.parseDouble(txtA.getText());
            double b = Double.parseDouble(txtB.getText());
            double tol = Double.parseDouble(txtTol.getText());
            if (f(a) * f(b) >= 0) {
                JOptionPane.showMessageDialog(this, "f(a) and f(b) must have opposite signs.");
                return;
            }
            int iter = 0;
            double c = a;
            for (; iter < 100; iter++) {
                c = (a * f(b) - b * f(a)) / (f(b) - f(a));
                addRow(iter + 1, String.format("%.6f", a), String.format("%.6f", b),
                        String.format("%.8f", c), String.format("%.6e", f(c)), String.format("%.6e", Math.abs(f(c))));
                if (Math.abs(f(c)) < tol) break;
                if (f(a) * f(c) < 0) b = c;
                else a = c;
            }
            JOptionPane.showMessageDialog(this, String.format("Root ≈ %.8f after %d iterations", c, iter + 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}