#!/bin/bash

make clean
make
fuser 2323/tcp -k
./hamster_server
