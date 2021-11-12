#!/bin/bash

while true; do
    echo -en "Mehr Arbeit? \t\t: "
    read input
    if [[ $input = "q" ]] || [[ $input = "Q" ]] 
        then break 
    else 
        python3 elasticClient.py s -e $input
    fi
done
