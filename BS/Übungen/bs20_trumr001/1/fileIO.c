#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <string.h>
#define MAXBYTES 1024

int main(int argc, char *argv[])
{
	printf("Hallo, BS-Praktikum!\n");
	printf("Kopiere Datei byteweise ...\n");
	
	printf("Anzahl der Argumente: %d\n", argc);
  	for(int i = 1; i < argc; ++i)
    	printf("Argument %d: %s\n", i, argv[i]);
	
	//alle files liegen in ../../etc/
	int openFile;
	int readFile;
	int writeFile;
	int appendFile;
	int createFile;
	int closeFile;
	
	char buffer[MAXBYTES];
	strcpy(buffer, "Roestbarsch\n");

	char* fromFile =  argv[1];
	char*  toFile =  argv[2];

	//bestehende Datei öffnen
	openFile = open(fromFile, O_RDONLY );
	if (openFile == -1) {
		printf("Fehler bei open\n");
		printf("datei nicht gefunden: %s", fromFile);
	}
	

	//neue Datei erstellen
	createFile =  open("../../etc/createMe", O_CREAT | O_RDWR |O_TRUNC, S_IRWXU| S_IRWXG | S_IRWXO);
	if (createFile == -1) {
		printf("Fehler bei open (create)\n");
	}

	//in Datei schreiben
	writeFile = write(createFile, &buffer, sizeof(buffer));
	if (writeFile == -1) {
		printf("Fehler bei write\n");
	}
	

	//aus Datei lesen
	readFile =  read(openFile, buffer, MAXBYTES);
	writeFile = open("../../etc/writeMe", O_RDWR | O_CREAT |O_TRUNC);
	if(readFile == -1 || writeFile == -1){
		printf("fehler beim öffnen von readFile=%i/writeFile=%i\n",readFile,writeFile);
	}
	write(writeFile, buffer, readFile);


	//an Datei appenden
	appendFile = open(toFile, O_CREAT | O_APPEND | O_RDWR, S_IRWXU | S_IRWXG | S_IRWXO);
	if(appendFile == -1)
		printf("Fehler beim öffnen von appendFile: %s", toFile);
	write(appendFile,  buffer, readFile);

	
	
	if((close(appendFile)) == -1)
      	printf("Fehler beim Schliessen der Datei appendFile\n");
	
	if((close(createFile)) == -1)
      	printf("Fehler beim Schliessen der Datei createFile\n");

	if((close(openFile)) == -1)
    	printf("Fehler beim Schliessen der Datei openFile\n");

	printf("openFile: %i\n", openFile);
	printf("createFile: %i\n", createFile);
	printf("writeFile: %i\n", writeFile);
	printf("readFile: %i\n", readFile);

	return EXIT_SUCCESS;
}
