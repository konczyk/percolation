# Percolation 

Estimate the value of the percolation threshold via Monte Carlo simulation. 

## Examples 

Build project:

    $ ./gradlew assemble

Quick Union with 100000 nodes and 200000 random connections:

    $ java -cp build/libs/percolation.jar QuickUnionClient -n 100000 -c 200000

Quick Union with data read from standard input:

    $ java -cp build/libs/percolation.jar QuickUnionClient - < data/quickunion.txt

Visualize a 20x20 grid with default fraction of open sites ([see animation](visualizer1.gif?raw=true)):

    $ ./gen_grid.py 20 | java -cp build/libs/percolation.jar PercolationVisualizer

Visualize a 20x20 grid with custom fraction of open sites ([see animation](visualizer2.gif?raw=true)):

    $ ./gen_grid.py 20 --frac 0.5 | java -cp build/libs/percolation.jar PercolationVisualizer

Estimate percolation threshold for 20x20 grid and series of 10000 trials:

    $ java -cp build/libs/percolation.jar PercolationStatsClient -gw 20 -t 10000

    mean                    = 0.5915002500000021
    stddev                  = 0.04857501495774638
    95% confidence interval = 0.5905481797068303, 0.5924523202931739
