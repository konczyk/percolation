import java.awt.*;
import java.util.List;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class PercolationVisualizer {

    private static final String TITLE = "Percolation";

    private final int gridSize;
    private final JPanel panel;
    private List<JLabel> sites = new ArrayList<>();

    public PercolationVisualizer(int n) {
        gridSize = n;
        panel = createPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createPanel() {
        JPanel p = new JPanel(new GridLayout(gridSize, gridSize, 1, 1));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.setPreferredSize(new Dimension(500, 500));
        for (int i = 0; i < gridSize * gridSize; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            p.add(label);
            sites.add(label);
        }

        return p;
    }

    private static void showGUI() {
        PercolationVisualizer pv = new PercolationVisualizer(10);

        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pv.getPanel());

        // center on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2, dim.height / 2);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });
    }
}
