#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include "shared.h"

#include <sys/mman.h>
#include <sys/stat.h>        /* For mode constants */
#include <fcntl.h>           /* For O_* constants */


int main(int argc, char *argv[])
{
	printf("leser.c\n");
	
	int fd;
	struct shared *myShared;

	if(argc != 2){
		printf("Falsche anzahl argumente\n");
		return EXIT_FAILURE;
	}

	if((fd = shm_open(argv[1], O_RDONLY, S_IRUSR)) <0){
		printf("Fehler in shm_open\n");
		return EXIT_FAILURE;
	}

	myShared = (struct shared*)mmap(NULL, sizeof(struct shared), PROT_READ, MAP_SHARED, fd, 0);

	if(myShared == (struct shared*)MAP_FAILED){
		printf("Fehler bei mmap\n");
		return EXIT_FAILURE;
	}

	for(;;){
		int c;
		printf("<ENTER> drücken zum weitermachen, andere Taste zum abbrechen\n");
		c = getchar();
		if(c != '\n')
			break;
		printf("pid=%d, i=%d\n", myShared->pid, myShared->length);
		for(int count=0; count<myShared->length; count++)
			printf("%c", myShared->msg[count]);
		
		printf("\n");
	}

	if(munmap((void*)myShared, sizeof(*myShared))<0){
		printf("Fehler bei munmap\n");
		return EXIT_FAILURE;
	}
	
	return EXIT_SUCCESS;
}
