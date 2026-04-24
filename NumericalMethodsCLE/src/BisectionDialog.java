import javax.swing.*;



public class BisectionDialog extends RootFinderBase {
    public BisectionDialog(JFrame parent) {
        super(parent, "Bisection Method");
        addFunctionField();
        txtA = new JTextField("0");
        txtB = new JTextField("1");
        txtTol = new JTextField("1e-6");
        addInputPanel(new JLabel("Interval a:"), txtA,
                new JLabel("Interval b:"), txtB,
                new JLabel("Tolerance:"), txtTol);
        addTable(new String[]{"Iteration", "a", "b", "c = (a+b)/2", "f(c)", "Error (b-a)"});
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

            double fa = eval(a);
            double fb = eval(b);
            if (fa * fb >= 0) {
                JOptionPane.showMessageDialog(this, "f(a) and f(b) must have opposite signs.");
                return;
            }

            int iter = 0;
            double c = a;
            double error = Math.abs(b - a);

            while (error > tol && iter < 100) {
                c = (a + b) / 2;
                double fc = eval(c);
                addRow(++iter, String.format("%.6f", a), String.format("%.6f", b),
                        String.format("%.8f", c), String.format("%.6e", fc), String.format("%.6e", error));

                if (Math.abs(fc) < 1e-12) break;
                if (fa * fc < 0) { b = c; fb = fc; }
                else { a = c; fa = fc; }
                error = Math.abs(b - a);
            }
            JOptionPane.showMessageDialog(this, String.format("Root ≈ %.8f after %d iterations", c, iter));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}