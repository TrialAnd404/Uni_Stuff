#include "node.h"

Node::Node(int m[3][3]){
    for(int x = 0; x<3; x++){
		for(int y = 0; y<3; y++){
			state.matrix[x][y] = m[x][y];
		}
	}  
}

std::vector<Node> Node::children(){
    //find coordinates of 0 in the matrix
	std::vector<Node> result; 
    int x,y = 0;
    while(state.matrix[x][y] != 0){
        if(x%3 == 0){
            x=0;
            y=y+1;
        }
		if(y==3){
			printf("yikes 0 element nicht gefunden\n");
			break;
		}
		x++;
	}
	//do some swappies + generate up to 4 children
	int newMatrix[3][3];
	
	if(x-1>=0){
		for(int x = 0; x<3; x++){
			for(int y = 0; y<3; y++){
				newMatrix[x][y] = state.matrix[x][y];
			}
		}
		newMatrix[x][y] = newMatrix[x-1][y];
		newMatrix[x-1][y] = 0;
		Node newNode = Node(newMatrix);
		result.push_back(newNode);
	}
	if(x+1<=2){
		for(int x = 0; x<3; x++){
			for(int y = 0; y<3; y++){
				newMatrix[x][y] = state.matrix[x][y];
			}
		}
		newMatrix[x][y] = newMatrix[x+1][y];
		newMatrix[x+1][y] = 0;
		Node newNode = Node(newMatrix);
		result.push_back(newNode);
	}
	if(y-1>=0){
		for(int x = 0; x<3; x++){
			for(int y = 0; y<3; y++){
				newMatrix[x][y] = state.matrix[x][y];
			}
		}
		newMatrix[x][y] = newMatrix[x][y-1];
		newMatrix[x][y-1] = 0;
		Node newNode = Node(newMatrix);
		result.push_back(newNode);
	}
	if(y+1<=2){
		for(int x = 0; x<3; x++){
			for(int y = 0; y<3; y++){
				newMatrix[x][y] = state.matrix[x][y];
			}
		}
		newMatrix[x][y] = newMatrix[x][y+1];
		newMatrix[x][y+1] = 0;
		Node newNode = Node(newMatrix);
		result.push_back(newNode);
	}

	return result;
}
