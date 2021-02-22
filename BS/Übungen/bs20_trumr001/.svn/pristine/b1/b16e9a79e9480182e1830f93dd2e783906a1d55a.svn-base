#!/bin/sh

echo "* Aufraeumen alter Prozesse:"
killall create destroy leser schreiber

echo "* Loeschen eines alten SHMs:"
./destroy bsra

echo "* Anlegen eines neuen SHM:"
./create bsra


echo "* Starte Schreiber:"
./schreiber bsra A 3 300 &
./schreiber bsra B 5 100 &
./schreiber bsra C 7 400 &
./schreiber bsra D 8 200 &

echo "* Starte Leser:"
./leser bsra

echo "* Aufraeumen ..."
killall schreiber
./destroy bsra
