#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <sys/wait.h>

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
		printf("Ich bin der Kindprozess pid1\n");
		sleep(10);
		printf("Kind1 Ende\n");
	}
	else { /* parent */
		printf("Ich bin Elternprozess von pid1: %d\n", pid1);
		pid2 = fork();
		if (pid2 == -1) {
			perror("fork()");
			exit(EXIT_FAILURE);
		}
		if (pid2 == 0) { /* child 2*/
			printf("Ich bin der Kindprozess pid2\n");
			for (volatile uint64_t i = 0; i < 100000; i++) {}
			printf("Kind2 Ende\n");
		}
		else { /* parent */
			printf("Ich bin Elternprozess von pid2: %d\n", pid2);

			printf("meine kindprozesse sind pid1:%d und pid2:%d\n", pid1, pid2);
		}
	}

	for (int i = 0; i < 15; i++) {
		sleep(1);
		system("date");
		system("ps f");
	}

	int status;
	pid_t terminatedChild;
	int n = 2;
	while (n > 0) {
		terminatedChild = wait(&status);
		printf("Child with PID %ld exited with status 0x%x.\n", (long)terminatedChild, status);
		--n;
	}
	
	printf("n=%d\nTschuess von pid %d\n", n, getpid());

	return EXIT_SUCCESS;
}
