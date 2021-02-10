#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include "shared.h"

#include <unistd.h>
#include <sys/types.h>
#include <sys/mman.h>
#include <sys/stat.h>        /* For mode constants */
#include <fcntl.h>           /* For O_* constants */
#include <semaphore.h>

int main(int argc, char *argv[])
{
	printf("create.c\n\n");

	int fd;

	struct shared* myShared;

	if(argc != 2){
		printf("Name und Größe benötigt\n");
		return EXIT_FAILURE;
	}

	fd = shm_open(argv[1], O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
	if(fd < 0){
		printf("Fehler bei erstellen des shared mem");
		return EXIT_FAILURE;
	}

	if(ftruncate(fd, sizeof(struct shared)) < 0 ){
		printf("Fehler in trucate");
		return EXIT_FAILURE;
	}

	
	
	myShared = (struct shared*)mmap(NULL, sizeof(struct shared), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);

	if(myShared == (struct shared*)MAP_FAILED){
		printf("Fehler in mmap");
		return EXIT_FAILURE;
	}


	sem_init(&myShared->semaphore, 1, 1); 
	
	return EXIT_SUCCESS;
}
