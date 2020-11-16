#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <string.h>

int main(int argc, char *argv[])
{
	int readFile;
	int writeFile;
	ssize_t readBytes;	
	char* fromFile =  argv[1];
	char* toFile =  argv[2];
	char readBuffer[1];

	//Datei an Datei anhängen
	if((writeFile = open(toFile, O_WRONLY | O_CREAT | O_TRUNC, 0666)) < 0)
		printf("Fehler beim öffnen von: %s", toFile);
	
	if((readFile = open(fromFile, O_RDONLY)) < 0)
		printf("Fehler beim öffnen von: %s", fromFile);

	do{
		readBytes =  read(readFile, readBuffer, 1);
		write(writeFile, readBuffer, readBytes);
		printf("reading File ...");
	}while(readBytes > 0 );
	
	if(close(readFile) == -1)
		printf("Fehler beim schließen der Datei");

	if(close(writeFile) == -1)
		printf("Fehler beim schließen der Datei");
	
	return EXIT_SUCCESS;
}
