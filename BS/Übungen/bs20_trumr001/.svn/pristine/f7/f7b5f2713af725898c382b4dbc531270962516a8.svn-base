# Makefile Aufgabe "SHM"

CFLAGS = -std=c11 -D_GNU_SOURCE -Og -g -W -Wall -Wno-unused-parameter \
         -Werror=implicit-function-declaration -Wfatal-errors -pthread -lrt
LIBS = -lrt -lpthread

.PHONY: all clean distclean

all: create destroy schreiber leser philosophen

create: create.c shared.h
	$(CC) $(CFLAGS) -o $@ $< $(LIBS)

destroy: destroy.c shared.h
	$(CC) $(CFLAGS) -o $@ $< $(LIBS)

schreiber: schreiber.c shared.h
	$(CC) $(CFLAGS) -o $@ $< $(LIBS)

leser: leser.c shared.h
	$(CC) $(CFLAGS) -o $@ $< $(LIBS)

philosophen: philosophen.c
	$(CC) $(CFLAGS) -o $@ $< $(LIBS)

clean:
	rm -f create destroy schreiber leser philosophen

distclean: clean
