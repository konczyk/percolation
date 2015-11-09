import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

public class PercolationVisualizer {

    // delay between subsequent openings
    private static final int DELAY = 100;

    private static final String TITLE = "Percolation";

    // list of grid sites
    private final List<JLabel> sites = new ArrayList<>();

    // track the number of open sites
    private int openSites = 0;

    // main panel with the percolation grid
    private final JPanel gridPanel;

    // status panel with basic statistics
    private final JPanel statusPanel;
    private final JLabel openLabel;
    private final JLabel thresholdLabel;
    private final JLabel percolationLabel;

    // list of subsequent openings (from stdin)
    private final List<int[]> openQueue = new ArrayList<>();

    public PercolationVisualizer() {
        gridPanel = new JPanel();
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        gridPanel.setPreferredSize(new Dimension(600, 600));

        statusPanel = new JPanel(new GridLayout(1, 3));

        openLabel = new JLabel();
        openLabel.setSize(openLabel.getPreferredSize());
        thresholdLabel = new JLabel();
        percolationLabel = new JLabel();

        statusPanel.add(openLabel);
        statusPanel.add(thresholdLabel);
        statusPanel.add(percolationLabel);
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JPanel getStatusPanel() {
        return statusPanel;
    }

    public JPanel getPanel() {
        return panel;
    }

    // create percolation grid, sites black (blocked) by default
    private void createGrid(int width) {
        gridPanel.setLayout(new GridLayout(width, width));
        for (int i = 0; i < width * width; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(label);
            sites.add(label);
        }
    }

    // update the grid view based on the percolation grid status
    private void draw(Percolation p) {
        // count open sites in the current Percolation state
        int open = 0;
        int gridWidth = p.getGridWidth();
        for (int row = 0; row < gridWidth; row++) {
            for (int col = 0; col < gridWidth; col ++) {
                // index of the label in the visual grid
                int idx = row * gridWidth + col;
                // percolation grid starts from 1
                if (p.isFull(row + 1, col + 1)) {
                    sites.get(idx).setBackground(Color.CYAN);
                    open++;
                } else if (p.isOpen(row + 1, col + 1)) {
                    sites.get(idx).setBackground(Color.WHITE);
                    open++;
                }
            }
        }
        openSites = open;
    }

    // read user input and continously open sites with a delay
    public void run() {
        Scanner sc = new Scanner(System.in);
        int gridWidth = sc.nextInt();
        this.createGrid(gridWidth);
        while (sc.hasNext()) {
            openQueue.add(new int[]{sc.nextInt(), sc.nextInt()});
        }
        final Percolation p = new Percolation(gridWidth);
        final ListIterator<int[]> it = openQueue.listIterator();

        Timer timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] item = it.next();
                p.open(item[0], item[1]);
                // redraw percolation
                draw(p);
                // check if we shoud stop
                if (openSites == sites.size() || p.percolates()) {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.setInitialDelay(DELAY);
        timer.start();
    }

    private static void showGUI() {
        PercolationVisualizer pv = new PercolationVisualizer();

        JFrame frame = new JFrame(TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pv.getGridPanel(), BorderLayout.CENTER);
        frame.add(pv.getStatusPanel(), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        pv.run();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });
    }
}
