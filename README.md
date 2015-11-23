# Percolation 

Estimate the value of the percolation threshold via Monte Carlo simulation. 

## Examples 

Build a jar file:

    $ ./gradlew assemble

Quick Union with 100000 nodes and 200000 random connections:

    $ ./gen_nodes.py 100000 200000 | java -cp build/libs/percolation.jar QuickUnion

Visualize a 20x20 grid with default fraction of open sites ([see animation](visualizer1.gif?raw=true)):

    $ ./gen_grid.py 20 | java -cp build/libs/percolation.jar PercolationVisualizer

Visualize a 20x20 grid with custom fraction of open sites ([see animation](visualizer2.gif?raw=true)):

    $ ./gen_grid.py 20 --frac 0.5 | java -cp build/libs/percolation.jar PercolationVisualizer

Estimate percolation threshold for 20x20 grid and series of 10000 computations:

    $ java -cp build/libs/percolation.jar PercolationStats 20 10000

    mean                    = 0.5915002500000021
    stddev                  = 0.04857501495774638
    95% confidence interval = 0.5905481797068303, 0.5924523202931739
