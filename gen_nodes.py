#!/usr/bin/env python3

import argparse, random

parser = argparse.ArgumentParser(description='Generate input to QuickUnion.')
parser.add_argument('nodes', metavar='n', type=int,
                    help='number of nodes')
parser.add_argument('conn', metavar='c', type=int,
                    help='number of connections')

args = parser.parse_args()

print(args.nodes)
for i in range(args.conn):
    print(random.randint(0, args.nodes - 1),
          random.randint(0, args.nodes - 1))
