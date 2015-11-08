#!/usr/bin/env python3

import argparse, random

parser = argparse.ArgumentParser(
            description='Generate input to PercolationVisualizer.')
parser.add_argument('size', metavar='n', type=int,
                    help='grid size [1-50]')

args = parser.parse_args()

# create pairs
pairs = []
for i in range(args.size):
    for j in range(args.size):
        pairs.append((i + 1, j + 1))

# randomize
random.shuffle(pairs)

# output
print(args.size)
for i in pairs:
    print(i[0], i[1])
