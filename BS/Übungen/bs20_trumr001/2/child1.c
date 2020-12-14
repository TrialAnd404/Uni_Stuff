#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
	printf("Kind_1 ...\n");
	sleep(10);
	//sleep(5); //test für zeitmessung, 10sek ist so lange...
	printf("Kind1 Ende\n");
	return EXIT_SUCCESS;
}
