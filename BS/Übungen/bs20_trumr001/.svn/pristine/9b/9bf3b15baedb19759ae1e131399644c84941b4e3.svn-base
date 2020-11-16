#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
	struct stat attribut;

	if(stat(argv[1], &attribut) == -1)
		printf("Fehler bei stat %s", argv[1]);
	
	printf("Die Laenge der Datei ist %ld\n", attribut.st_size);
	
	return EXIT_SUCCESS;
}
