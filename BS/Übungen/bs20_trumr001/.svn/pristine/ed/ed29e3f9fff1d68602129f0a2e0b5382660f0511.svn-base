#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <time.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

/* Beachten Sie den geaenderten Prototypen von main(). Referenz: man 2 execve */
/* Sie koennen "envp" als Environment-Pointer an Kind-Prozesse weiterreichen. */
int main(int argc, char *argv[], char *envp[])
{
    struct timespec time1,time2,timediff;
    int pid1;

    printf("mytime ...\n");

	pid1 = fork();

	if (pid1 == -1) {
		perror("fork()");
		exit(EXIT_FAILURE);
	}

	if (pid1 == 0) { /* child 1*/

		execve(argv[1], &argv[2], envp);

	}
	else { /* parent */
		if(clock_gettime(CLOCK_REALTIME, &time1) < 0){
        	printf("Fehler bei startzeit messung");
			exit(EXIT_FAILURE);
    	}
		printf("startzeit :%ld sekunden, %ld nanosekunden\n",time1.tv_sec, time1.tv_nsec);
		printf("Ich bin Elternprozess von pid1: %d\n", pid1);
	}

    int cstat;

    if(wait(&cstat) < 0){
        exit(EXIT_FAILURE);
    }

    if(clock_gettime(CLOCK_REALTIME, &time2) < 0){
		printf("fehler bei endzeit messung");
        exit(EXIT_FAILURE);
    }
	printf("endzeit :%ld sekunden, %ld nanosekunden\n",time2.tv_sec, time2.tv_nsec);

	int msec1, msec2;
	msec1 = time1.tv_nsec/1000;
	msec2 = time2.tv_nsec/1000;
	
	
    timediff.tv_sec = time2.tv_sec - time1.tv_sec;
    if(msec2<msec1)
		timediff.tv_nsec = 1000000+msec2-msec1;
	else
		timediff.tv_nsec = msec2-msec1;

	printf("differenz :%ld sekunden, %ld mikrosekunden\n",timediff.tv_sec, timediff.tv_nsec);

	//zeitdifferenzen können wegen unterschiedlichen Lasten auftreten, wenn wenig zu tun ist sind die antwortzeiten ggf kürzer

    return EXIT_SUCCESS;
}
