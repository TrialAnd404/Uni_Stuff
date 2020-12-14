#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>


/* Beachten Sie den geaenderten Prototypen von main(). Referenz: man 2 execve */
/* Sie koennen "envp" als Environment-Pointer an Kind-Prozesse weiterreichen. */
int main(int argc, char *argv[], char *envp[])
{
	int pid1, pid2;

	pid1 = fork();

	if (pid1 == -1) {
		perror("fork()");
		exit(EXIT_FAILURE);
	}

	if (pid1 == 0) { /* child 1*/
		execve("./child1", argv, envp);
		//printf("Ich bin der Kindprozess pid1\n");
		//sleep(25);
		//printf("Kind1 Ende\n");
		//return EXIT_SUCCESS;
	}
	else { /* parent */
		printf("Ich bin Elternprozess von pid1: %d\n", pid1);
		pid2 = fork();
		if (pid2 == -1) {
			perror("fork()");
			exit(EXIT_FAILURE);
		}
		if (pid2 == 0) { /* child 2*/
			execve("./child2", argv, envp);
			//printf("Ich bin der Kindprozess pid2\n");
			//for (volatile uint64_t i = 0; i < 10000000000; i++) {}
			//printf("Kind2 Ende\n");
			//return EXIT_SUCCESS;
		}
		else { /* parent */
			printf("Ich bin Elternprozess von pid2: %d\n", pid2);

			printf("meine kindprozesse sind pid1:%d und pid2:%d\n", pid1, pid2);
		}
	}

	for (int i = 0; i < 15; i++) {
		sleep(1);
		if(system("date")<0){
			printf("system date Error");
			exit(EXIT_FAILURE);
		}
		if(system("ps f")<0){
			printf("ps f Error");
			exit(EXIT_FAILURE);
		}
	}

	int status;
	pid_t terminatedChild;
	int n = 2;
	while (n > 0) {
		printf("Warten auf kinder...");
		terminatedChild = wait(&status);
		printf("kind mit prozessnummer %ld wurde beendet.\n", (long)terminatedChild);
		--n;
	}
	
	return EXIT_SUCCESS;
}
