//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class NumericalMethodsUI {
    public NumericalMethodsUI() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            (new NumericalMethodsUI()).createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Numerical Methods - CLE");
        frame.setDefaultCloseOperation(3);
        frame.setSize(750, 350);
        frame.setLocationRelativeTo((Component)null);
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = this.getWidth();
                int h = this.getHeight();
                GradientPaint gp = new GradientPaint(0.0F, 0.0F, new Color(30, 60, 90), (float)w, (float)h, new Color(20, 40, 60));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Numerical Methods CLE - Choose a Topic", 0);
        titleLabel.setFont(new Font("Segoe UI", 1, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, "North");
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        buttonPanel.setOpaque(false);
        String[] methods = new String[]{"Bisection", "Regula-Falsi", "Secant", "Newton", "Gauss-Jacobi", "Gauss-Seidel", "Simpsons Rule", "Trapezoidal Rule"};
        String[] var6 = methods;
        int var7 = methods.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String method = var6[var8];
            RoundedButton btn = new RoundedButton(method);
            btn.addActionListener((e) -> {
                this.openCalculator(method, frame);
            });
            buttonPanel.add(btn);
        }

        mainPanel.add(buttonPanel, "Center");
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private void openCalculator(String method, JFrame parent) {
        switch (method) {
            case "Bisection" -> new BisectionDialog(parent);
            case "Regula-Falsi" -> new RegulaFalsiDialog(parent);
            case "Secant" -> new SecantDialog(parent);
            case "Newton" -> new NewtonDialog(parent);
            case "Gauss-Jacobi" -> new GaussJacobiDialog(parent);
            case "Gauss-Seidel" -> new GaussSeidelDialog(parent);
            case "Simpsons Rule" -> new SimpsonsDialog(parent);
            case "Trapezoidal Rule" -> new TrapezoidalDialog(parent);
        }

    }

    static class RoundedButton extends JButton {
        private Color bgColor = new Color(70, 130, 200);
        private Color hoverColor = new Color(100, 160, 230);
        private boolean hover = false;

        public RoundedButton(String text) {
            super(text);
            this.setFont(new Font("Segoe UI", 1, 14));
            this.setForeground(Color.WHITE);
            this.setContentAreaFilled(false);
            this.setFocusPainted(false);
            this.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            this.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    RoundedButton.this.hover = true;
                    RoundedButton.this.repaint();
                }

                public void mouseExited(MouseEvent evt) {
                    RoundedButton.this.hover = false;
                    RoundedButton.this.repaint();
                }
            });
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.hover ? this.hoverColor : this.bgColor);
            g2.fill(new RoundRectangle2D.Float(0.0F, 0.0F, (float)this.getWidth(), (float)this.getHeight(), 30.0F, 30.0F));
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}
