#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>

int main(int argc, char *argv[])
{

	if( access( argv[1], F_OK ) == -1 ) 
		printf("neues file erstellen... %i\n",creat(argv[1], 0600));

	int newSize = 0;
	for(int i=0; argv[2][i] != '\0'; i++){
		newSize = argv[2][i] - '0' + newSize*10;
	}

	if(truncate(argv[1], newSize) == -1)
		printf("Fehler bei stat %s", argv[1]);

	printf("Dateigroesse setzen ...\n");
	
	return EXIT_SUCCESS;
}
