#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>
#include <time.h>
#include <semaphore.h>
#include <pthread.h>
#include <time.h>


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

sem_t semLock; 

void* gib_function(void* arg){
	char* argv = arg;
	char c = *argv;
	
	//for#####

	sem_wait(&semLock); //LOCK
	
	for(int i = 0; i< 10; i++){
		printf("%c", c);
		fflush(stdout);
		srand(i*c); 
		msleep((rand()%400)+100);
	}
	printf("\n");
	
	sem_post(&semLock); //UNLOCK

	//Sleep
	//#######

	return NULL;
}

int main(int argc, char *argv[])
{
	printf("gibaus.c\n");
	pthread_t threads[6];
	char* chr = "ABCDEF";
	int ret;
	void* ret2;
	sem_init(&semLock, 0, 1); 

	for(int i = 0; i < 6; i++){
		ret = pthread_create(&threads[i], NULL, gib_function, &chr[i]);
		if(ret != 0){
			printf("pthread_create %i\n", ret);
		}
	}
	
	for(int i = 0; i < 6; i++){
		pthread_join(threads[i], &ret2);
		if(ret2!=0){
			printf("something went wrong while joining thread #%i", i);
		}
	}

	sem_destroy(&semLock);
	return EXIT_SUCCESS;
}
