import javax.swing.*;

public class RegulaFalsiDialog extends RootFinderBase {
    private double previousX2 = 0;

    public RegulaFalsiDialog(JFrame parent) {
        super(parent, "Regula-Falsi Method");
        // Default example: f(x) = x^3 - 4x^2 + x - 10, interval [4,5], tolerance 0.0001
        txtFunction = new JTextField("x^3-4*x^2+x-10", 20);
        txtA = new JTextField("4");
        txtB = new JTextField("5");
        txtTol = new JTextField("0.0001");
        // Add function field to the input panel
        JPanel functionPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        functionPanel.add(new JLabel("f(x) ="));
        functionPanel.add(txtFunction);
        add(functionPanel, java.awt.BorderLayout.NORTH);
        // Add interval and tolerance inputs
        addInputPanel(new JLabel("Interval a:"), txtA,
                new JLabel("Interval b:"), txtB,
                new JLabel("Tolerance:"), txtTol);
        // Table columns: Iteration, x0, x1, x2, f(x0), f(x1), f(x2), Ea
        addTable(new String[]{"Iteration", "x0", "x1", "x2", "f(x0)", "f(x1)", "f(x2)", "Ea"});
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
                JOptionPane.showMessageDialog(this,
                        "f(a) and f(b) must have opposite signs.\n" +
                                "f(a) = " + fa + ", f(b) = " + fb);
                return;
            }

            int iter = 0;
            double x2 = a;
            previousX2 = a;
            double error = Math.abs(b - a);

            while (error > tol && iter < 100) {
                // Regula-Falsi formula
                x2 = a - fa * (b - a) / (fb - fa);
                double f2 = eval(x2);

                // Calculate absolute error (Ea)
                double ea = Math.abs(x2 - previousX2);
                // Add row to table
                addRow(++iter,
                        String.format("%.6f", a),
                        String.format("%.6f", b),
                        String.format("%.6f", x2),
                        String.format("%.6f", fa),
                        String.format("%.6f", fb),
                        String.format("%.6f", f2),
                        (iter == 1) ? "---" : String.format("%.6f", ea));

                // Check tolerance using |f(x2)| or Ea? Your example uses Ea.
                if (Math.abs(f2) < tol || ea < tol) break;

                // Update bracket
                if (fa * f2 < 0) {
                    b = x2;
                    fb = f2;
                } else {
                    a = x2;
                    fa = f2;
                }
                previousX2 = x2;
                error = ea;
            }
            JOptionPane.showMessageDialog(this,
                    String.format("Root ≈ %.6f after %d iterations", x2, iter));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}