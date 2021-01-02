#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>
#include <time.h>
#include <pthread.h>

pthread_mutex_t lock;

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

void* gib_function(void* arg){
	char* argv = arg;
	char c = *argv;

	for(int i =0; i<5; i++){	
		pthread_mutex_lock(&lock); //LOCK

		for(int i = 0; i< 10; i++){
			printf("%c", c);	
			fflush(stdout);
			srand(i*c); 
			msleep((rand()%400)+100);
		}
		printf("\n");

		pthread_mutex_unlock(&lock); //UNLOCK
		msleep(10);
	}
	

	return NULL;
}

int main(int argc, char *argv[])
{
	printf("gibaus.c\n");
	pthread_t threads[6];
	char* chr = "ABCDEF";
	int ret;

	if (pthread_mutex_init(&lock, NULL) != 0){
        printf("\n mutex init failed\n");
        return 1;
    }
	

	for(int i = 0; i < 6; i++){
		ret = pthread_create(&threads[i], NULL, gib_function, &chr[i]);
		if(ret != 0){
			printf("pthread_create %i\n", ret);
		}
	}
	
	for(int i = 0; i < 6; i++){
		pthread_join(threads[i], NULL);
	}

	pthread_mutex_destroy(&lock);

	return EXIT_SUCCESS;
}

