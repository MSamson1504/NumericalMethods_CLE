import javax.swing.*;

public class SecantDialog extends RootFinderBase {
    public SecantDialog(JFrame parent) {
        super(parent, "Secant Method");
        txtInitial = new JTextField("1");
        txtA = new JTextField("2");
        txtTol = new JTextField("1e-6");
        addInputPanel(new JLabel("Initial x0:"), txtInitial,
                new JLabel("Initial x1:"), txtA,
                new JLabel("Tolerance:"), txtTol);
        addTable(new String[]{"Iteration", "x0", "x1", "x2", "f(x2)", "Error"});
        addCalculateButton();
        setVisible(true);
    }

    @Override
    protected void doCalculation(java.awt.event.ActionEvent e) {
        try {
            clearTable();
            double x0 = Double.parseDouble(txtInitial.getText());
            double x1 = Double.parseDouble(txtA.getText());
            double tol = Double.parseDouble(txtTol.getText());
            int iter = 0;
            double x2 = x1;
            for (; iter < 100; iter++) {
                x2 = x1 - f(x1) * (x1 - x0) / (f(x1) - f(x0));
                addRow(iter + 1, String.format("%.6f", x0), String.format("%.6f", x1),
                        String.format("%.8f", x2), String.format("%.6e", f(x2)), String.format("%.6e", Math.abs(x2 - x1)));
                if (Math.abs(f(x2)) < tol) break;
                x0 = x1;
                x1 = x2;
            }
            JOptionPane.showMessageDialog(this, String.format("Root ≈ %.8f after %d iterations", x2, iter + 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}