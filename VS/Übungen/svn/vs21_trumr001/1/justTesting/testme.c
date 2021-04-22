#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <assert.h>


void testfunc(char* t2){
    
    printf("t2 address :\t\t%p\n",t2);

	strcpy(t2, "kekw");

    
}

int main(int argc, char* argv[])
{    
    char t[] =  "placeholder";
    char* p = &t[0];

    printf("t adress :\t\t%p\n",t);

    testfunc(p);

    printf("t after function:\t%s\n",p);
    
	return 0;
}
