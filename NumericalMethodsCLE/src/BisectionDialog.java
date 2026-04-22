import javax.swing.*;

public class BisectionDialog extends RootFinderBase {
    public BisectionDialog(JFrame parent) {
        super(parent, "Bisection Method");
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
            while ((b - a) / 2 > tol) {
                c = (a + b) / 2;
                addRow(++iter, String.format("%.6f", a), String.format("%.6f", b),
                        String.format("%.8f", c), String.format("%.6e", f(c)), String.format("%.6e", (b - a) / 2));
                if (f(c) == 0) break;
                if (f(a) * f(c) < 0) b = c;
                else a = c;
            }
            JOptionPane.showMessageDialog(this, String.format("Root ≈ %.8f after %d iterations", c, iter));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}