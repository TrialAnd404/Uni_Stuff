// Client side C/C++ program to demonstrate Socket programming
#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#define PORT 8081
#define SERVERIP "127.0.0.1" 

typedef struct{
    int foo;
    int bar;
} package;


int main(int argc, char const *argv[])
{
    int sock = 0, valread;
    struct sockaddr_in serv_addr;
    //char *hello = "Hello from client";
    char buffer[4] = {0};

    package foobar;
    foobar.foo = 1;
    foobar.bar = 2;

    int n = 10000;
    int tmp = htonl(n);
    int number = 2;
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        printf("\n Socket creation error \n");
        return -1;
    }
   
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);
       
    // Convert IPv4 and IPv6 addresses from text to binary form
    if(inet_pton(AF_INET, "127.9.2.1", &serv_addr.sin_addr)<=0) 
    {
        printf("\nInvalid address/ Address not supported \n");
        return -1;
    }
   
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
        printf("\nConnection Failed \n");
        return -1;
    }
    send(sock , &foobar , sizeof(package) , 0 );
    printf("new package sized %li with %li data sent\n", sizeof(package), sizeof(foobar));
    //valread = read( sock , buffer, 1024);
    //printf("%s\n",buffer );
    return 0;
}
