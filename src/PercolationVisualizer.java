import com.beust.jcommander.*;

import java.util.Scanner;

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
    private static final String OPEN_INFO = "%d/%d open sites";
    private static final String THRESHOLD_INFO = "%.1f%% threshold";

    private final List<JLabel> gridSites = new ArrayList<>();
    private int openSites = 0;
    private int allSites;
    private int gridWidth;
    private boolean percolates = false;
    private List<Site> sitesToOpen = new ArrayList<>();

    private final JPanel gridPanel;
    private final JPanel statusPanel;
    private final JLabel openSitesLabel;
    private final JLabel thresholdLabel;
    private final JLabel percolationLabel;

    public PercolationVisualizer(int gridWidth) {
        this.gridWidth = gridWidth;
        allSites = gridWidth * gridWidth;

        gridPanel = new JPanel();
        gridPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        gridPanel.setPreferredSize(new Dimension(600, 600));

        statusPanel = new JPanel(new GridLayout(1, 3));
        statusPanel.setBorder(new EmptyBorder(5, 10, 10, 10));

        openSitesLabel = new JLabel();
        openSitesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        thresholdLabel = new JLabel();
        thresholdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        percolationLabel = new JLabel();
        percolationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusPanel.add(openSitesLabel);
        statusPanel.add(thresholdLabel);
        statusPanel.add(percolationLabel);
    }

    // data structure representing site on the grid
    private class Site {
        public int row;
        public int col;

        public Site(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private void createGrid() {
        gridPanel.setLayout(new GridLayout(gridWidth, gridWidth));
        for (int i = 0; i < allSites; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(label);
            gridSites.add(label);
        }
        updateStatusPanel();
    }

    public void run() {
        final Percolation p = new Percolation(gridWidth);
        final ListIterator<Site> it = sitesToOpen.listIterator();

        Timer timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!it.hasNext()) {
                    ((Timer)e.getSource()).stop();
                } else {
                    Site site = it.next();
                    p.open(site.row, site.col);
                    updateGrid(p);
                    Toolkit.getDefaultToolkit().sync();
                    if (openSites == gridSites.size() || p.percolates()) {
                        percolates = p.percolates();
                    }
                    updateStatusPanel();
                }
            }
        });
        timer.setInitialDelay(DELAY);
        timer.start();
    }

    private void updateGrid(Percolation p) {
        int open = 0;
        for (int row = 1; row <= gridWidth; row++) {
            for (int col = 1; col <= gridWidth; col ++) {
                int siteLabelIndex = (row-1) * gridWidth + (col-1);
                if (p.isFull(row, col)) {
                    gridSites.get(siteLabelIndex).setBackground(Color.CYAN);
                    open++;
                } else if (p.isOpen(row, col)) {
                    gridSites.get(siteLabelIndex).setBackground(Color.WHITE);
                    open++;
                }
            }
        }
        openSites = open;
    }

    private void updateStatusPanel() {
        openSitesLabel.setText(
            String.format(OPEN_INFO, openSites, allSites));
        thresholdLabel.setText(
            String.format(THRESHOLD_INFO, openSites/(double) allSites * 100));
        percolationLabel.setText(
            percolates ? "percolates" : "does not percolate");
    }

    private static void showGUI(PercolationVisualizerArgs args) {
        PercolationVisualizer pv;
        if (!args.stdin) {
            pv = new PercolationVisualizer(args.gridWidth);
            pv.loadSitesFromRandom(args.fraction);
        } else {
            int width = 0;
            try (Scanner scanner = new Scanner(System.in)) {
                width = scanner.nextInt();
                pv = new PercolationVisualizer(width);
                pv.loadSitesFromStdIn(scanner);
            }

        }

        JFrame frame = new JFrame(TITLE);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pv.gridPanel, BorderLayout.CENTER);
        frame.add(pv.statusPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        pv.createGrid();
        pv.run();
    }

    private void loadSitesFromStdIn(Scanner scanner) {
        while (scanner.hasNext()) {
            sitesToOpen.add(new Site(scanner.nextInt(), scanner.nextInt()));
        }
    }

    private void loadSitesFromRandom(double fraction) {
        List<Site> sites = new ArrayList<>();
        for (int i = 1; i <= gridWidth; ++i) {
            for (int j = 1; j <= gridWidth; ++j) {
                sites.add(new Site(i, j));
            }
        }
        Collections.shuffle(sites);
        sitesToOpen = sites.subList(0, (int) (allSites * fraction));
    }

    public static void main(String[] args) {
        final PercolationVisualizerArgs visualizerArgs
              = new PercolationVisualizerArgs();
        JCommander jc = new JCommander(visualizerArgs);
        try {
            jc.parse(args);
            visualizerArgs.validate();
            if (visualizerArgs.help || args.length == 0) {
                jc.usage();
                return;
            }
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI(visualizerArgs);
            }
        });
    }

}
