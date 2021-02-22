#ifndef SHARED_H
#define SHARED_H
#include <semaphore.h>

/* Die Struktur der im SHM abgelegten Daten */
struct shared {

        int pid;
        int i;
        int length;
        char msg[40];

        sem_t semaphore;
};

#endif