import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

public class PercolationVisualizer {

    private static final int DELAY = 500;

    private static final String TITLE = "Percolation";

    private List<JLabel> sites = new ArrayList<>();

    private final int gridWidth;

    private final JPanel panel;

    private final Percolation percolation;

    // track the number of open sites
    private int openSites = 0;
    // all available sites
    private final int allSites;

    private final Random rand = new Random();

    public PercolationVisualizer(Percolation p) {
        percolation = p;
        gridWidth = percolation.getGridWidth();
        allSites = gridWidth * gridWidth;
        panel = createPanel();
    }

    private JPanel createPanel() {
        JPanel p = new JPanel(new GridLayout(gridWidth, gridWidth));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.setPreferredSize(new Dimension(600, 600));
        for (int i = 0; i < allSites; i++) {
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

    public JPanel getPanel() {
        return panel;
    }

    public void generate() {
        Timer timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = rand.nextInt(gridWidth) + 1;
                int col = rand.nextInt(gridWidth) + 1;
                percolation.open(row, col);
                redraw();
                if (openSites == allSites || percolation.percolates()) {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.setInitialDelay(DELAY);
        timer.start();
    }

    // update the grid view based on the percolation grid status
    private void redraw() {
        int open = 0;
        for (int row = 0; row < gridWidth; row++) {
            for (int col = 0; col < gridWidth; col ++) {
                // index of the label in the visual grid
                int idx = row * gridWidth + col;
                // percolation grid starts from 1
                if (percolation.isFull(row + 1, col + 1)) {
                    sites.get(idx).setBackground(Color.CYAN);
                    open++;
                } else if (percolation.isOpen(row + 1, col + 1)) {
                    sites.get(idx).setBackground(Color.WHITE);
                    open++;
                }
            }
        }

        openSites = open;
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

        pv.generate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });
    }
}
