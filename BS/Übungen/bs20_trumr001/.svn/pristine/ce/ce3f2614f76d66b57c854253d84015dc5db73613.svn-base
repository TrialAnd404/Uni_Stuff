#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>
#include <time.h>
#include <pthread.h>
#include <unistd.h>

bool CONTINUE = true;

pthread_cond_t signalLieferanten;
pthread_cond_t signalBaecker;
pthread_mutex_t lock;

void backstubeBetreten(){
	pthread_mutex_lock(&lock); //LOCK
}

void backstubeVerlassen(){
	pthread_mutex_unlock(&lock); //UNLOCK
}

void* eierzwerg(void* arg){
	int* eier = arg;
	int ret;
	while(CONTINUE){
		printf("eierzwerg: arbeit arbeit!\n");
		sleep(1);
		backstubeBetreten();
		*eier = *eier+1;
		printf("eier abgegeben. %i in backstube\n", *eier);	
    	if(*eier == 5){	
			//weck den wichser
			//mutex_lock_wait

			printf("eierzwerg: zeit zu pennen\n");
			if(pthread_cond_signal(&signalBaecker)==0)
				printf("Aufstehen Bäckerboi\n");
			pthread_cond_wait(&signalLieferanten, &lock);
		}
			backstubeVerlassen();
	}
	
	printf("arbeit ist vollbracht\n");

	return NULL;
}

void* milchzwerg(void* arg){
	int* milch = arg;

	while(CONTINUE){
		printf("milchzwerg: arbeit arbeit!\n");
		sleep(2);
		backstubeBetreten();
		*milch = *milch+1;
		printf("milch abgegeben. %i in backstube\n", *milch);	
    	if(*milch == 1){	
			//weck den wichser
			//mutex_lock_wait

			printf("milchzwerg: zeit zu pennen\n");
			if(pthread_cond_signal(&signalBaecker)==0)
				printf("Aufstehen Bäckerboi\n");
			pthread_cond_wait(&signalLieferanten, &lock);
		}
			backstubeVerlassen();
	}
	
	printf("arbeit ist vollbracht\n");

	return NULL;
}

void* mehlzwerg(void* arg){
	int* mehl = arg;

	while(CONTINUE){
		printf("mehlzwerg: arbeit arbeit!\n");
		sleep(3);
		backstubeBetreten();
		*mehl = *mehl+1;
		printf("mehl abgegeben. %i in backstube\n", *mehl);	
    	if(*mehl == 2){	
			//weck den wichser
			//mutex_lock_wait

			printf("mehlzwerg: zeit zu pennen\n");
			if(pthread_cond_signal(&signalBaecker)==0)
				printf("Aufstehen Bäckerboi\n");
			pthread_cond_wait(&signalLieferanten, &lock);
		}
			backstubeVerlassen();
		
	}
	
	printf("arbeit ist vollbracht\n");

	return NULL;
}

void* backzwerg(void* arg){
	int** zutaten = arg;
	for(int i =0; i<10;){
		printf("backzwerg: zeit zu pennen\n");		
		pthread_cond_wait(&signalBaecker, &lock);
		printf("backzwerg: arbeit arbeit!\n");
		if(*zutaten[0] == 5 && *zutaten[1] == 1 && *zutaten[2] == 2){
			printf("genug zutaten gefunden\n");
			*zutaten[0] = 0;
			*zutaten[1] = 0;
			*zutaten[2] = 0;
			i++;
			printf("blech nr %i fertig gebacken\n", i);
			printf("bing bong alle aufwachen\n");
			pthread_cond_broadcast(&signalLieferanten); 

			if(i==10){
				printf("ok bois, reicht\n");
				CONTINUE = false;
			}

			backstubeVerlassen();
		}
		else{
			printf("nicht genug Zeug da. Bestand:\n%i eier %i milch %i mehl\n", *zutaten[0], *zutaten[1], *zutaten[2]);
			backstubeVerlassen();
		}
	}

	printf("arbeit ist vollbracht\n");

	return NULL;
}


int main(int argc, char *argv[])
{
	int eier = 0, mehl = 0, milch = 0;
	int* zutaten[3]; 
	zutaten[0] = &eier;
	zutaten[1] = &milch;
	zutaten[2] = &mehl;

	int ret;
	printf("baeckerei.c\n");
	printf("%i .... ", *zutaten[0]);
	pthread_t threads[4];
	
	if(pthread_mutex_init(&lock, NULL) != 0){
        printf("\n mutex init failed\n");
        return 1;
    }

	if(pthread_cond_init(&signalLieferanten, NULL) != 0){
		printf("\n cond var init failed \n");
		return 1;
	}

	
	ret = pthread_create(&threads[0], NULL, backzwerg, &zutaten);
	if(ret != 0){
		printf("backzwerg thread error %i\n", ret);
	}

	ret = pthread_create(&threads[1], NULL, eierzwerg, &eier);
	if(ret != 0){
		printf("eierzwerg thread error %i\n", ret);
	}

	ret = pthread_create(&threads[2], NULL, milchzwerg, &milch);
	if(ret != 0){
		printf("milchzwerg thread error %i\n", ret);
	}

	ret = pthread_create(&threads[3], NULL, mehlzwerg, &mehl);
	if(ret != 0){
		printf("mehlzwerg thread error %i\n", ret);
	}

	for(int i = 0; i < 3; i++){
		pthread_join(threads[i], NULL);
	}

	pthread_mutex_destroy(&lock);

	return EXIT_SUCCESS;
}
