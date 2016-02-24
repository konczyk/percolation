# Percolation 

Estimate the value of the percolation threshold via Monte Carlo simulation. 

## Examples 

Build project:

    $ ./gradlew assemble

Quick Union with 100000 nodes and 200000 random connections:

    $ java -cp build/libs/percolation.jar QuickUnionClient -n 100000 -c 200000

Quick Union with data read from standard input:

    $ cat data/quickunion.txt | java -cp build/libs/percolation.jar QuickUnionClient -
    
Visualize a 20x20 grid with default fraction of randomly open sites ([see sample animation](data/visualizer1.gif?raw=true)):

    $ java -cp build/libs/percolation.jar PercolationVisualizer -gw 20

Visualize a 20x20 grid with custom fraction of randomly open sites ([see sample animation](data/visualizer2.gif?raw=true)):

    $ java -cp build/libs/percolation.jar PercolationVisualizer -gw 20 -f 0.5

Visualize a 20x20 grid with data read from standard input:

    $ cat data/percolation.txt | java -cp build/libs/percolation.jar PercolationVisualizer -

Estimate percolation threshold for 20x20 grid and series of 10000 trials:

    $ java -cp build/libs/percolation.jar PercolationStatsClient -gw 20 -t 10000

    mean                    = 0.5915002500000021
    stddev                  = 0.04857501495774638
    95% confidence interval = 0.5905481797068303, 0.5924523202931739
