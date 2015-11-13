# Percolation 

Estimate the value of the percolation threshold via Monte Carlo simulation. 

## Examples 

Quick Union with 100000 nodes and 200000 random connections:

    ./gen_nodes.py 100000 200000 | java QuickUnion

Visualize a 20x20 grid with default fraction of open sites ([See animation](visualizer1.gif?raw=true)):

    ./gen_grid.py 20 | java PercolationVisualizer

Visualize a 20x20 grid with custom fraction of open sites ([See animation](visualizer2.gif?raw=true)):

    ./gen_grid.py 20 --frac 0.5 | java PercolationVisualizer
