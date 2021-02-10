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
	printf("destroy.c\n");
	
	if(argc != 2){
		printf("Benötigt namen des Shared mem");
		return EXIT_FAILURE;
	}

	if(shm_unlink(argv[1])<0){
		printf("Unlink Fehler\n");
		return EXIT_FAILURE;
	}

	return EXIT_SUCCESS;
}
