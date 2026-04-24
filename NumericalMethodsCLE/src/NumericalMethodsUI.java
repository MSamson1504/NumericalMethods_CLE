import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class NumericalMethodsUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumericalMethodsUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Numerical Methods - CLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 350);
        frame.setLocationRelativeTo(null);

        // Gradient background panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 60, 90), w, h, new Color(20, 40, 60));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title label
        JLabel titleLabel = new JLabel("Numerical Methods CLE - Choose a Topic", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Button panel with 2x4 grid
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        buttonPanel.setOpaque(false);
        String[] methods = {
                "Bisection", "Regula-Falsi", "Secant", "Newton",
                "Gauss-Jacobi", "Gauss-Seidel", "Simpsons Rule", "Trapezoidal Rule"
        };

        for (String method : methods) {
            RoundedButton btn = new RoundedButton(method);
            btn.addActionListener(e -> openCalculator(method, frame));
            buttonPanel.add(btn);
        }
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private void openCalculator(String method, JFrame parent) {
        switch (method) {
            case "Bisection":       new BisectionDialog(parent); break;
            case "Regula-Falsi":    new RegulaFalsiDialog(parent); break;
            case "Secant":          new SecantDialog(parent); break;
            case "Newton":          new NewtonDialog(parent); break;
            case "Gauss-Jacobi":    new GaussJacobiDialog(parent); break;
            case "Gauss-Seidel":    new GaussSeidelDialog(parent); break;
            case "Simpsons Rule":   new SimpsonsDialog(parent); break;
            case "Trapezoidal Rule":new TrapezoidalDialog(parent); break;
        }
    }

    // Custom rounded button with hover effect
    static class RoundedButton extends JButton {
        private Color bgColor = new Color(70, 130, 200);
        private Color hoverColor = new Color(100, 160, 230);
        private boolean hover = false;

        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) { hover = true; repaint(); }
                public void mouseExited(java.awt.event.MouseEvent evt) { hover = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hover ? hoverColor : bgColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}