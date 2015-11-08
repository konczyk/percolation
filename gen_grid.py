#!/usr/bin/env python3

import argparse, random

def grid_type(x):
    x = int(x)
    if x < 1 or x > 50:
        raise argparse.ArgumentTypeError("Choose grid size between 1 and 50")
    return x

parser = argparse.ArgumentParser(
            description='Generate input to PercolationVisualizer.')
parser.add_argument('size', metavar='n', type=grid_type,
                    help='grid size 1-50')

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
