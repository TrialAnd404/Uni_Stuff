#include "rpcTest.h"

extern void rpcTest_rpc_init(char* hostname);
extern void rpcTest_rpc_terminate(void);




 int main(int argc, char*argv[]){

    rpcTest_rpc_init(hostname);
    
    ret = addProxy();

				if(!check_error(ret, "addProxy()"))
					printf("rpc add called\n");

				break;

    rpcTest_rpc_terminate();
 }
