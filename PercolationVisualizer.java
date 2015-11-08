import java.awt.*;
import java.util.List;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class PercolationVisualizer {

    private static final String TITLE = "Percolation";

    private List<JLabel> sites = new ArrayList<>();

    private final int gridWidth;

    private final JPanel panel;

    private final Percolation percolation;

    public PercolationVisualizer(Percolation p) {
        percolation = p;
        gridWidth = percolation.getGridWidth();
        panel = createPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createPanel() {
        JPanel p = new JPanel(new GridLayout(gridWidth, gridWidth));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.setPreferredSize(new Dimension(600, 600));
        for (int i = 0; i < gridWidth * gridWidth; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            p.add(label);
            sites.add(label);
        }

        return p;
    }

    private static void showGUI() {
        Percolation p = new Percolation(10);
        PercolationVisualizer pv = new PercolationVisualizer(p);

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
