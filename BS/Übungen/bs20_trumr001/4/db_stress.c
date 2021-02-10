#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>
#include "db.h"
#include <signal.h>
#include <unistd.h>

/*
 * Durchfuehrung einer Transaktion:
 * 1. Zufaelligen Betrag von einem zufaelligen Konto abheben
 * 2. Diesen Betrag auf ein zufaelliges Konto wieder einzahlen
 */

void sighandler(int sig){
	switch(sig){
		case SIGINT:
			printf("Abbruch!\nBilazsumme: %d Goldstücke", db_get_balance());
			exit(EXIT_SUCCESS);
			break;
		case SIGUSR1:
			printf("Info!\nBilanzsumme: %d Golstücke\n", db_get_balance());
			break;
		case SIGALRM:
		{
			int balance;
			printf("*");
			if((balance = db_get_balance()) != 10000)
				printf("Falsche Bilanz: %d Golstücke\n", balance);
			alarm(5);
			break;
		}
		default:
			printf("unhandled sigNR %d\n", sig);
	}
}

void random_transaction(void)
{

	sigset_t sigint_block, oldset;



	sigemptyset(&sigint_block);
	sigaddset(&sigint_block, SIGINT);
	sigaddset(&sigint_block, SIGUSR1);
	sigaddset(&sigint_block, SIGALRM);
	

	unsigned int from_customer = rand() % DB_ENTRIES;
	unsigned int to_customer = rand() % DB_ENTRIES;
	int amount = 1 + rand() % DB_MAX_TRANSFER;

	if(sigprocmask(SIG_BLOCK, &sigint_block, &oldset)<0){
		printf("Fehler in sigprocmask()\n");
		exit(EXIT_FAILURE);
	}
	
	db_money_transfer(from_customer, to_customer, amount);

	if(sigprocmask(SIG_SETMASK, &oldset, NULL)<0){
		printf("Fehler in sigprocmask()\n");
		exit(EXIT_FAILURE);
	}
}

int main(int argc, char *argv[])
{
	printf("Datenbank Stresstest\n");
	

	struct sigaction block, old;
	block.sa_flags = 0;

	sigemptyset(&block.sa_mask);


	//SIGHANDLER ERSETZEN	
	block.sa_handler = sighandler;


	sigaction(SIGINT, &block, &old);
	sigaction(SIGUSR1, &block, NULL);
	sigaction(SIGALRM, &block, NULL);



	if (!db_check()) {
		printf("Datenbank nicht gefunden!\n");
		printf("Legen Sie eine Datenbank mit $ ./db_new an!\n");
		exit(EXIT_FAILURE);
	}
	alarm(5);
	/* Stresstest: 1000 zufaellige Transaktionen */
	for (size_t i = 0; i < 10000; ++i) {
		random_transaction();
		printf(".");
		fflush(stdout);
	}

	printf("Bilanz am Ende: %d\n", db_get_balance());

	return EXIT_SUCCESS;
}
