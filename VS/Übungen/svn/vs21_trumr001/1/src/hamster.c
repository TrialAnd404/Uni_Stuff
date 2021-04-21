/**
 * @file	hamster.c
 * @brief	Hamsterverwaltungsprogramm
 *
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <assert.h>

#include "hamsterlib.h"

/**
 * @brief Kurze Hilfe ausgeben
 */
static void rtfm(char* argv[])
{
	printf("Usage: %s {<Option>} <param1> {<param2>}\n", argv[0]);
	printf("Function: Hamster management\n");
	printf("Optionen:\n");
	printf("     -l {<owner>}                    - show current list of hamsters\n");
	printf("     -n <owner> <hamster> [<treats>] - add new hamster\n");
	printf("     -f <owner> <hamster> <treats>   - feed treats to hamster\n");
	printf("     -s <owner> <hamster>            - how is my hamster doing?\n");
	printf("     -b <owner>                      - the bill please!\n");
}


/**
 * @brief Main program
 */
int main(int argc, char* argv[])
{

    rtfm(argv);
	
	return 0;
}

