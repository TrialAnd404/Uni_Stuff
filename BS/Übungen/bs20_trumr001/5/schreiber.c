#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>
#include <time.h>
#include "shared.h"

#include <sys/mman.h>
#include <sys/stat.h>        /* For mode constants */
#include <fcntl.h>           /* For O_* constants */
#include <semaphore.h>
#include <sys/types.h>
#include <unistd.h>



/* Warten von 1 bis 10000 Millisekunden (10 Sekunden) */
void msleep(unsigned int ms)
{
	struct timespec ts;

	assert(ms > 0);
	assert(ms <= 10000);

	ts.tv_sec = ms / 1000;
	ts.tv_nsec = (ms * 1000000) % 1000000000;
	nanosleep(&ts, NULL);
}

int main(int argc, char *argv[])
{
	printf("schreiber.c\n");
	
	if(argc != 5){
		printf("Nicht genug argumente (5 benötigt)\n");
		return EXIT_FAILURE;
	}
	
	int fd;

	int length;
	if((length = atoi(argv[3]))== 0){
		printf("length nicht als zahl angegeben\n");
		return EXIT_FAILURE;
	}
	if(length > 40){
		printf("Max Länge 40, Gesetzte Länge: 40\n");
		length = 40;
	}
	if(length < 0){
		printf("Min Länge 0, Gesetzte Länge: 0\n");
		length = 0;
	}

	int time;
	if((time = atoi(argv[4]))== 0){
		printf("time nicht als zahl angegeben\n");
		return EXIT_FAILURE;
	}
	
	char c = *argv[2];

	struct shared *myShared;

	//TODO: ALLE TESTEN

	if((fd = shm_open(argv[1], O_RDWR, 0600))<0){
		printf("Fehler in shm_open\n");
		return EXIT_FAILURE;
	}

	myShared = (struct shared*)mmap(NULL, sizeof(struct shared), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);

	if(myShared == (struct shared*)MAP_FAILED){
		printf("Fehler in mmap\n");
		return EXIT_FAILURE;
	}	

	for(int i = 0; i < 10; i++){
		if((sem_wait(&myShared->semaphore))<0){
			printf("schreiber: sem_wait Fehler\n");
			exit(EXIT_FAILURE);
		} //LOCK
		
		myShared->pid = getpid();
		myShared->length = length;
		myShared->i = i;
		for(int count = 0; count < length; count++){
			myShared->msg[count] = c;
			msleep(time);
		}

		if((sem_post(&myShared->semaphore))<0){
			printf("schreiber: sem_post Fehler\n");
			exit(EXIT_FAILURE);
		} //UNLOCK
		
		msleep(time);
	}

	return EXIT_SUCCESS;
}
