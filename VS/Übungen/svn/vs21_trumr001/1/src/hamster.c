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

#include "../include/hamsterlib.h"

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
 * @brief ReturnValues checken
 */
void checkReturnValue(int checkValue){
	if(checkValue>=0)
		return;
	if(checkValue == HMSTR_ERR_NAMETOOLONG){
		printf("name too long\n");
		return;
	} else if(checkValue == HMSTR_ERR_EXISTS){
		printf("hamster already in database\n");
		return;
	} else if(checkValue == HMSTR_ERR_STORE){
		printf("Storage Error\n");
		return;
	} else if(checkValue == HMSTR_ERR_NOTFOUND){
		printf("hamster not found\n");
		return;
	} else if(checkValue < HMSTR_ERR_STORE){
		printf("HOLY FUCK SOMETHING WENT GLORIOUSLY WRONG!!\n");
		if(checkValue == HMSTR_ERR_CORRUPT){
			printf("JESUS CHRIST THE DATABASE IS CORRUPTED!\n");
		}
		return;
	}
	
	printf("SUCCESS!\n");
}

int getNumberFromString(char argv[]){
	for(int i =0; argv[i] != '\0'; i++){
		if((int)argv[i]<'0' || (int)argv[i]>'9')
			return -1;
	}
	return strtol(argv, NULL, 10);
}

/**
 * @brief Main program
 */
int main(int argc, char* argv[])
{    
	if(argc<2){
		rtfm(argv);
		return 0;
	}

	//check parameters with which program hamster was called
	if (strcmp(argv[1],"-l")==0){
		printf("read command -l\n");

		if(argc>3){
			printf("incorrect amount of arguments\n");
			rtfm(argv);
			return 0;
		}

		//filedescriptor pointer to keep track of where in the file we are currently reading
		int32_t initialFdptr = -1;

		//basic info we can gather from the database without much hassle
		int hamsterID = 0;
		char owner[31] =  "placeholderOwner";
    	char* oP = &owner[0];
		char name[31] =  "placeholderName";
    	char* nP = &name[0];
		int16_t price;
		int treatsLeft;

		int counter =0;
		while(hamsterID >= 0){
			if(argc==3)
				hamsterID = hmstr_directory(&initialFdptr,argv[2],NULL);
			else
				hamsterID = hmstr_directory(&initialFdptr,NULL,NULL);
			//erst fdptr weiterschieben && gucken ob ende erreicht ist...
			if(initialFdptr == -1){
				printf("end of database reached\n");
				break;
			}
			//dann gucken, ob ergebnis ok ist
			if(hamsterID <0){
				printf("something went wrong with entry number %i\n", counter);
				checkReturnValue(hamsterID);
			}			

			else{
				//printf("time to look up the hamster\n");
				//hmstr_howsdoing(hamsterID, &state);
				treatsLeft=hmstr_readentry(hamsterID, oP, nP, &price);
				checkReturnValue(treatsLeft);

			}
			printf("Hamsterinformation ID %i:\n", hamsterID);
			printf("\tName: %s\n", name); 
			printf("\tOwner: %s\n",owner);
			printf("\tPrice: %i\n", price);
			printf("\tTreats: %i\n", treatsLeft);
			counter++;
		}

	} else if(strcmp(argv[1],"-n")==0){
		printf("read command -n\n");

		int treatCount = 0;
		if(argc==5){
			treatCount = getNumberFromString(argv[4]);
			if(treatCount <0){
				printf("NaN: treats %s is not a number\n", argv[4]);
				return 0;
			}
		}
		else if(argc>5 || argc < 4){
			printf("incorrect amount of arguments\n");
			rtfm(argv);
			return 0;
		}

		//TODO : INTEGER CHECK AUF ARGV[4]
		if(argc==5){  			// Treats argument wird mitgegeben
			printf("attempting to add new Hamster to database...\n");
			checkReturnValue(hmstr_new(argv[2], argv[3], treatCount));
		} else if(argc==4){		//Treats argument nicht mitgegeben
			printf("attempting to add new Hamster to database...\n");
			checkReturnValue(hmstr_new(argv[2], argv[3], treatCount));
		} else if(argc <4){
			printf("not enought arguments\n");
		} else if(argc >5){
			printf("too many arguments\n");
		}

	} else if(strcmp(argv[1],"-f")==0){
		printf("read command -f\n");

		int treaties = 0;
		if(argc==5){
			treaties = getNumberFromString(argv[4]);
			if(treaties <0){
				printf("NaN: treats %s is not a number\n", argv[4]);
				return 0;
			}
		}

		if(argc<5 || argc>5){
			printf("incorrect amount of arguments\n");
			rtfm(argv);
			return 0;
		}
		//TODO: hamsterLookup/ID in eigene funktion legen?
		int32_t initialFdptr = -1;
		int hamsterID = hmstr_directory(&initialFdptr,NULL,argv[3]);
		if(hamsterID <0)
			checkReturnValue(hamsterID);
		else{
			//TODO integer parsen
			int treatsLeft = hmstr_givetreats(hamsterID, treaties);
			if(treatsLeft<0)
				checkReturnValue(treatsLeft);
			else
				printf("treats left for hamster: %d\n",treatsLeft);
		}

	} else if(strcmp(argv[1],"-s")==0){
		printf("read command -s\n");

		if(argc<4 || argc>4){
			printf("incorrect amount of arguments\n");
			rtfm(argv);
		}
			
		int32_t initialFdptr = -1;
		struct hmstr_state state;

		int hamsterID = hmstr_directory(&initialFdptr,argv[2],argv[3]);
		if(hamsterID <0)
			checkReturnValue(hamsterID);
		else{
			int rounds = hmstr_howsdoing(hamsterID, &state);
			if(rounds<0)
				checkReturnValue(rounds);
			else{
				printf("thanks for checking in!\n");
				if(state.treats_left>0){
					printf("your hamster %s is doing fine, he still has %d treats left :)\n", argv[3], state.treats_left);
				}
				else
					printf("your hamster %s has no treats left\n", argv[3]);
				
				if(state.rounds<100)
					printf("%s ran %d rounds\n", argv[3], state.rounds);
				else if(state.rounds < 1000)
					printf("HOLY CRAP %s ran %d rounds already!!\n", argv[3], state.rounds);
				else if(state.rounds > 1000)
					printf("Your Hamster %s ran %d rounds.\nAre you sure he is not on crack\n", argv[3], state.rounds);
						
				printf("currently the accumulated costs are %d€\n", state.cost);
			}
		}

	} else if(strcmp(argv[1],"-b")==0){
		printf("read command -b\n");

		if(argc<3 || argc>3){
			printf("incorrect amount of arguments\n");
			rtfm(argv);
			return 0;
		}

		printf("cost for hamster: %d\n",hmstr_collect(argv[2]));
	} else{
		printf("unknown parameter %s, abort mission!!\n", argv[2]);
		rtfm(argv);
	}
        
	return 0;
}