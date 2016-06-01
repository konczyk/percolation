# Percolation 

Estimate the value of the percolation threshold via Monte Carlo simulation. 

## Goal
Design a percolation system represented by a grid of sites, with each site
either open or blocked (default). A full site is an open site that can be
connected to an open site in the top row. System percolates when there is a
full site in the bottom row of the grid.

## Implementation constraints
- Fixed public API for `QuickUnion`, `Percolation` and `PercolationStats`
- `QuickUnion`, `Percolation` and `PercolationStats` should not call library
functions except those in `java.lang` and `java.util.Random`

## Performance requirements
- `Percolation` constructor should take time proportional to n<sup>2</sup> and
all its other methods should take constant time plus constant number of calls to
`QuickUnion` methods

## Sample clients 

Build project:

    $ ./gradlew assemble

All clients have built-in help invoked with `-h` or `--help`, e.g.

    $ java -cp build/libs/percolation.jar QuickUnionClient -h

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
