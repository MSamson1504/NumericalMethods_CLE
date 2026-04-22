import javax.swing.*;

public class NewtonDialog extends RootFinderBase {
    public NewtonDialog(JFrame parent) {
        super(parent, "Newton-Raphson Method");
        txtInitial = new JTextField("1.5");
        txtTol = new JTextField("1e-6");
        addInputPanel(new JLabel("Initial guess:"), txtInitial,
                new JLabel("Tolerance:"), txtTol);
        addTable(new String[]{"Iteration", "x", "f(x)", "f'(x)", "x_new", "Error"});
        addCalculateButton();
        setVisible(true);
    }

    private double df(double x) { return 2 * x; }

    @Override
    protected void doCalculation(java.awt.event.ActionEvent e) {
        try {
            clearTable();
            double x = Double.parseDouble(txtInitial.getText());
            double tol = Double.parseDouble(txtTol.getText());
            int iter = 0;
            for (; iter < 100; iter++) {
                double fx = f(x);
                double dfx = df(x);
                if (Math.abs(dfx) < 1e-12) throw new ArithmeticException("Derivative near zero");
                double xnew = x - fx / dfx;
                addRow(iter + 1, String.format("%.6f", x), String.format("%.6e", fx),
                        String.format("%.6e", dfx), String.format("%.8f", xnew), String.format("%.6e", Math.abs(xnew - x)));
                if (Math.abs(xnew - x) < tol) {
                    x = xnew;
                    break;
                }
                x = xnew;
            }
            JOptionPane.showMessageDialog(this, String.format("Root ≈ %.8f after %d iterations", x, iter + 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}