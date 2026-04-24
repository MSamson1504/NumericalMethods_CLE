import javax.swing.*;

public class SecantDialog extends RootFinderBase {
    private double previousX2 = 0;

    public SecantDialog(JFrame parent) {
        super(parent, "Secant Method");
        // Default example: f(x) = x^3 - 4x^2 + x - 10, initial guesses 3 and 4, tolerance 0.0001
        txtFunction = new JTextField("x^3-4*x^2+x-10", 20);
        txtInitial = new JTextField("3");   // x0
        txtA = new JTextField("4");         // x1
        txtTol = new JTextField("0.0001");
        // Add function field to top
        JPanel functionPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        functionPanel.add(new JLabel("f(x) ="));
        functionPanel.add(txtFunction);
        add(functionPanel, java.awt.BorderLayout.NORTH);
        // Add inputs for x0, x1, tolerance
        addInputPanel(new JLabel("Initial x0:"), txtInitial,
                new JLabel("Initial x1:"), txtA,
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
            double x0 = Double.parseDouble(txtInitial.getText());
            double x1 = Double.parseDouble(txtA.getText());
            double tol = Double.parseDouble(txtTol.getText());

            double f0 = eval(x0);
            double f1 = eval(x1);
            int iter = 0;
            double x2 = x1;
            previousX2 = x1;
            double error = Math.abs(x1 - x0);

            while (error > tol && iter < 100) {
                // Secant formula
                if (Math.abs(f1 - f0) < 1e-12) {
                    JOptionPane.showMessageDialog(this, "Division by near zero in Secant method.");
                    return;
                }
                x2 = x1 - f1 * (x1 - x0) / (f1 - f0);
                double f2 = eval(x2);
                double ea = (iter == 0) ? 0 : Math.abs(x2 - previousX2);
                // Add row: iteration, x0, x1, x2, f(x0), f(x1), f(x2), Ea
                addRow(iter + 1,
                        String.format("%.6f", x0),
                        String.format("%.6f", x1),
                        String.format("%.6f", x2),
                        String.format("%.6f", f0),
                        String.format("%.6f", f1),
                        String.format("%.6f", f2),
                        (iter == 0) ? "---" : String.format("%.6f", ea));

                // Check tolerance
                if (Math.abs(f2) < tol || (iter > 0 && ea < tol)) break;

                // Update for next iteration
                x0 = x1;
                f0 = f1;
                x1 = x2;
                f1 = f2;
                previousX2 = x2;
                error = ea;
                iter++;
            }
            JOptionPane.showMessageDialog(this,
                    String.format("Root ≈ %.6f after %d iterations", x2, iter + 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}