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
	
	printf("Anzahl der Argumente: %d\n", argc);
  	for(int i = 1; i < argc; ++i)
    	printf("Argument %d: %s\n", i, argv[i]);

	int readFile;
	int writeFile;
	int bytesToRead;

	ssize_t readBytes;
	
	char* fromFile =  argv[1];
	char* toFile =  argv[2];
	char readBuffer[1];

	//Datei an Datei anhängen
	if((writeFile = open(toFile, O_WRONLY | O_CREAT | O_APPEND, 0666)) < 0)
		printf("Fehler beim öffnen von: %s", toFile);
	
	if((readFile = open(fromFile, O_RDONLY)) < 0)
		printf("Fehler beim öffnen von: %s", fromFile);

	bytesToRead = lseek(readFile, 0, SEEK_END);
	
	printf("bytesToRead: %i", bytesToRead);
	
	for(int currentByte = bytesToRead-1; currentByte >= 0; currentByte--){
		lseek(readFile, (off_t) currentByte, SEEK_SET);
		readBytes = read(readFile, readBuffer, 1);
		write(writeFile, readBuffer, readBytes);
		printf("hopefully read a byte?\n");
	}
	
	if(close(readFile) == -1)
		printf("Fehler beim schließen der Datei");

	if(close(writeFile) == -1)
		printf("Fehler beim schließen der Datei");

	
	return EXIT_SUCCESS;
}
