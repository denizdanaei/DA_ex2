#!/bin/bash

# Check input arguments
if [ $# -ne 1 ]; then
    printf "Usage: ./run.sh <number of components>\n"
    exit 1
fi

# Run rmiregistry
pgrep -x rmiregistry > /dev/null
if [ $? -ne 0 ]; then
    printf "Starting rmiregistry\n"
    rmiregistry &
else
    printf "Rmiregistry already running\n"
fi

# Compile everything
javac *.java
if [ $? -ne 0 ]; then
    exit 1
fi

# Spawn processes
for pid in $(seq $1); do
    printf "Starting component $pid\n"
    java Component $pid $1 &
done

# Wait for processes to init & register themselves
# FIXME -> how to do this more nicely?
sleep 0.5

# Run simulator
java Simulator