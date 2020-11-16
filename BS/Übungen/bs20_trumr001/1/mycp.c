#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <string.h>

int is_regular_file(const char *path)
{
    struct stat path_stat;
    stat(path, &path_stat);
    return S_ISREG(path_stat.st_mode);
}

int main(int argc, char *argv[])
{
	struct stat attribut;
	if(stat(argv[1], &attribut) == -1)
		printf("Fehler bei stat %s", argv[1]);
	
	int readFile;
	int writeFile;
	ssize_t readBytes;	
	char* fromFile =  argv[1];
	char* toFile =  argv[2];
	char* buffer = argv[3];

	int bufferSize = 0;
	for(int i=0; buffer[i] != '\0'; i++){
		bufferSize = buffer[i] - '0' + bufferSize*10;
	}	
	char readBuffer[bufferSize];
	
	if(S_ISDIR(attribut.st_mode)){
		printf("ist ein directory, bitte File wählen");
		return EXIT_FAILURE;
	}

	//Datei an Datei anhängen
	if((writeFile = open(toFile, O_WRONLY | O_CREAT | O_TRUNC, 0666)) < 0)
		printf("Fehler beim öffnen von: %s", toFile);
	else
		chmod(toFile, attribut.st_mode);

	if((readFile = open(fromFile, O_RDONLY)) < 0)
		printf("Fehler beim öffnen von: %s", fromFile);

	do{
		readBytes =  read(readFile, readBuffer, bufferSize);
		printf("writing to file: %li\n",write(writeFile, readBuffer, readBytes));
		printf("reading Byte %li\n", readBytes);
	}while(readBytes > 0 );
		
	if(close(readFile) == -1)
		printf("Fehler beim schließen der Datei");

	if(close(writeFile) == -1)
		printf("Fehler beim schließen der Datei");

	return EXIT_SUCCESS;
}
