#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

int main(int argc, char *argv[])
{
	printf("Kind_2 ...\n");
	printf("Ich bin der Kindprozess pid2\n");
	for (volatile uint64_t i = 0; i < 10000000000; i++) {}
	printf("Kind2 Ende\n");
	return EXIT_SUCCESS;
}
