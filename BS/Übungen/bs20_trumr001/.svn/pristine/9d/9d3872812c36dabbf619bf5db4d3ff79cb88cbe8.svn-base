#include <sys/types.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/mman.h>
#include <sys/stat.h>        /* For mode constants */
#include <fcntl.h>           /* For O_* constants */
#include <semaphore.h>
#include <time.h>

//Aus der VL
#define N			5
#define RUNDEN		3
#define LEFT(x)		(((x) -1+N))%N
#define RIGHT(x)	(((x) +1))%N
#define THINKING	0
#define HUNGRY		1
#define EATING		2

/* Die Struktur der im SHM abgelegten Daten */
struct shared_Philo{
	//...
	int state[N];	//Zustand aller Philosophen
	sem_t lockMeUpDaddy;
	sem_t allLocks[N];
};

struct shared_Philo* myShared;

//msleep, yoinked aus der schreiber.c
void msleep(unsigned int ms)
{
	struct timespec ts;

	assert(ms > 0);
	assert(ms <= 10000);

	ts.tv_sec = ms / 1000;
	ts.tv_nsec = (ms * 1000000) % 1000000000;
	nanosleep(&ts, NULL);
}

void gabelTest(int i){
	/*
		Umliegende Philosophen Testen, eigenen Status setzen
	*/

	//Schicken output machen
	for(int tabs = 0; tabs < i; tabs++)
		printf("\t\t");
	printf("Nr%d testet", i);
	printf("\n");

	//testen ob ich essen kann
	if ((myShared->state[i]== HUNGRY) &&
		(myShared->state[LEFT(i)]!=EATING) &&
		(myShared->state[RIGHT(i)]!=EATING))
	{
		//state setzen
		myShared->state[i]=EATING;
		//Lock für Philosoph Nr i öffnen
		if((sem_post(&myShared->allLocks[i]))<0){
			printf("gabelTest: sem_post fehler in Nr%d\n",i);
			exit(EXIT_FAILURE);
		}
		return;
	}
}

void gabelYoink(int i){
	/*
		Gabeln von umliegenden Philosophen yoinken!
	*/

	//Schicken output machen
	for(int tabs = 0; tabs < i; tabs++)
		printf("\t\t");
	printf("Nr%d mopst gabeln", i);
	printf("\n");

	//Mutex Lock
	if((sem_wait(&myShared->lockMeUpDaddy))<0){
		printf("gabelYoink: sem_wait fehler in Nr%d", i);
		exit(EXIT_FAILURE);
	}
	//state setzen
	myShared->state[i] = HUNGRY;
	//testen ob ich essen kann
	gabelTest(i);
	//Mutex Unlock
	if((sem_post(&myShared->lockMeUpDaddy))<0){
		printf("gabelYoink: sem_post fehler bei Nr%d\n", i);
		exit(EXIT_FAILURE);
	}
	//eigenen Mutex locken
	if((sem_wait(&myShared->allLocks[i]))<0){
		printf("gabelYoink: sem_wait fehler bei Nr%d\n", i);
		exit(EXIT_FAILURE);
	}
}

void gabelYeet(int i){
	/*
		Gabeln zurück auf den Tisch yeeten, Leuten bescheid sagen
	*/

	//Schicken output machen
	for(int tabs = 0; tabs < i; tabs++)
		printf("\t\t");
	printf("Nr%d legt gabeln weg", i);
	printf("\n");

	//Mutex Lock
	if((sem_wait(&myShared->lockMeUpDaddy))<0){
		printf("Fehler in sem_eait\n");
		exit(EXIT_FAILURE);
	}
	//State setzen
	myShared->state[i] = THINKING;
	//linken und rechten nachbarn auffordern, zu testen
	gabelTest(LEFT(i));
	gabelTest(RIGHT(i));
	//Mutex unlock
	if((sem_post(&myShared->lockMeUpDaddy))<0){
		printf("Fehler in sem_post\n");
		exit(EXIT_FAILURE);
	}
}

void denkdenk(int i){

	if((sem_wait(&myShared->lockMeUpDaddy))<0){
		printf("Fehler in sem_wait\n");
		exit(EXIT_FAILURE);
	}

	if(myShared->state[i] != THINKING){
		printf("Fehlerhafter Status in Nr%d!\nStatus IST: %d\nStatus SOLL: 0\n", i, myShared->state[i]);
	}
	for(int tabs = 0; tabs < i; tabs++)
		printf("\t\t");
	printf("Nr%d denkt", i);
	printf("\n");

	if((sem_post(&myShared->lockMeUpDaddy))<0){
		printf("Fehler in sem_post\n");
		exit(EXIT_FAILURE);
	}
	//eigentlich sinnlose funktion - einfach nur ein wait
	msleep(5000);
	
}

void mampfmampf(int counter, int i){

	if((sem_wait(&myShared->lockMeUpDaddy))<0){
		printf("Fehler in sem_eait\n");
		exit(EXIT_FAILURE);
	}

	if(myShared->state[i] != 2){
		printf("Fehlerhafter Status in Nr%d!\nStatus IST: %d\nStatus SOLL: 2\n", i, myShared->state[i]);
	}
	for(int tabs = 0; tabs < i; tabs++)
		printf("\t\t");
	printf("Nr%d isst zum %d. mal", i, counter+1);
	printf("\n");

	if((sem_post(&myShared->lockMeUpDaddy))<0){
		printf("Fehler in sem_post\n");
		exit(EXIT_FAILURE);
	}
	//eigetnlich sinnlose funktion - einfach nur ein wait
	msleep(2000);
}

void philosophenSachen(int i) {
	for(int fressCounter = 0; fressCounter < RUNDEN; fressCounter++){
		denkdenk(i);
		gabelYoink(i);
		mampfmampf(fressCounter, i);
		gabelYeet(i);
	}
	for(int tabs = 0; tabs < i; tabs++)
		printf("\t\t");
	printf("Nr%d ist satt\n", i);
	exit(EXIT_SUCCESS);
}

int main(int argc, char *argv[]){
	printf("Dinierende Philosophen mit Prozessen ...\n\n");
	//INITIALISIERUNG

	myShared = (struct shared_Philo*)mmap(NULL, sizeof(struct shared_Philo), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANON,-1, 0);

	sem_init(&myShared->lockMeUpDaddy, 1, 1);

	for(int i =0; i< N; i++){
		sem_init(&myShared->allLocks[i], 1, 0);
	}

	int pid;

	for(int i=0; i<N; i++){
		switch(pid = fork()){
			case 0:
				philosophenSachen(i);
				break;
			case -1:
				printf("Fehler bei forken\n");
				return EXIT_FAILURE;
			default:
				break;
		}
	}

	//yoinked aus Übung 2
	int status;
	int n = N;
	pid_t terminatedChild;
	while (n > 0) {
		printf("Warten auf Philos...");
		terminatedChild = wait(&status);
		printf("Philo mit prozessnummer %ld wurde beendet.\n", (long)terminatedChild);
		--n;
	}

	return EXIT_SUCCESS;
}
