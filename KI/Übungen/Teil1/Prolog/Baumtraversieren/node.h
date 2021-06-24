#ifndef NODE_H
#define NODE_H

#include <stdio.h>

#include <vector>

class Node{
    public: 
        Node(int m[3][3]);

        std::vector<Node> children();
        
        struct{
			int matrix[3][3];
		}state;   

		inline bool operator< (const Node& other) const; 
};

#endif