#!/usr/bin/env python3

import argparse, random

def grid_type(x):
    x = int(x)
    if x < 1 or x > 50:
        raise argparse.ArgumentTypeError(
            'Choose grid size between 1 and 50')
    return x

def frac_type(x):
    x = float(x)
    if x < 0 or x > 1:
        raise argparse.ArgumentTypeError(
            'Choose a fraction of open sites between 0 and 1')
    return x

parser = argparse.ArgumentParser(
            description='Generate input to PercolationVisualizer.')
parser.add_argument('size', metavar='n', type=grid_type,
                    help='grid size 1-50')
parser.add_argument('--frac', metavar='f', type=frac_type, default=0.7,
                    help='fraction of open sites (default: 0.7)')

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
for i in pairs[:(int(len(pairs) * args.frac))]:
    print(i[0], i[1])
