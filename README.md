# Percolation 

Estimate the value of the percolation threshold via Monte Carlo simulation. 

## Examples 

Quick Union with 100000 nodes and 200000 random connections:

    ./gen_nodes.py 100000 200000 | java QuickUnion

Generate and animate a grid of size 20 x 20:

    ./gen_grid.py 20 | java PercolationVisualizer
