#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


/* Beachten Sie den geaenderten Prototypen von main(). Referenz: man 2 execve */
/* Sie koennen "envp" als Environment-Pointer an Kind-Prozesse weiterreichen. */
int main(int argc, char *argv[], char *envp[])
{
	pid_t pid;
	int fds[2];

	if(pipe(fds)<0){
		printf("Fehler beim ausführen von pipe\n");
		return EXIT_FAILURE;
	}

	if((pid = fork())<0){
		printf("Fehler bei Fork\n");
		return EXIT_FAILURE;
	}

	if(pid==0)
	{
    	dup2(fds[1], 1);
    	close(fds[0]);
    	close(fds[1]);
    	execlp("/bin/ls", "ls", "-la", argv[1], NULL, envp);
    	fprintf(stderr, "Fehler beim ausführen von ls\n");
    	exit(1);
	}
	else
	{ 
    	if((pid=fork())<0){
			printf("Fehler beim ausführen von fork\n");
			return EXIT_FAILURE;
		}

    	if(pid==0){
        	dup2(fds[0], 0);
        	close(fds[0]);
        	close(fds[1]);
        	execlp("/bin/grep", "grep", argv[2], NULL, envp);
        	fprintf(stderr, "Fehler bei ausführen von grep\n");
        	exit(1);
    	}
    	else{
        	int status;
        	close(fds[0]);
        	close(fds[1]);
        	waitpid(pid, &status, 0);
    	}
	}
}
