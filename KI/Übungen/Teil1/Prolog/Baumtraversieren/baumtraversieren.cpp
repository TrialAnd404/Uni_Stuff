#include <iostream>
#include <string>

#include <vector>
#include <set>
#include <algorithm>

#include "node.h"

int start[3][3] = {{8,6,7}, {5,4,0}, {1,2,3}};
int goal[3][3] = {{8,7,6},{5,0,4},{3,2,1}};

std::vector<Node> traversedNodes;

bool isGoal(Node n){
	for(int x=0; x<3; x++){
		for(int y=0; y<3; y++){
			if(n.state.matrix[x][y] != goal[x][y])
				return false;
		}
	}
	return true;
};
    
void printNode(Node n){
	printf("Node: \n");
	for(int x=0; x<3; x++){
		for(int y=0; x<3; x++){
			printf("%d ", n.state.matrix[x][y]);
		}
		printf("\n");
	}
	printf("\n--------\n");
};

void breitensuche(Node start, std::set<Node> visited){
		printNode(start);
		visited.insert(start);// Fügt den Startenknoten der Menge der markierten Knoten hinzu
		std::vector<Node> queue; // Warteschlange für die Breitensuche
		queue.push_back(start); // Fügt den Startenknoten der Warteschlange hinzu
		while (queue.size() != 0) // So lange die Warteschlange nicht leer ist
		{
			start = queue.at(0); // Erster Knoten der Warteschlange
			queue.erase(queue.begin()); // Entfernt den ersten Knoten aus der Warteschlange
			for (auto const& i : start.children()) // foreach-Schleife, die alle benachbarten Knoten des Knotens durchläuft
			{
				printNode(i);
				if(isGoal(i)){
					traversedNodes.push_back(i); // Fügt den Knoten der Liste hinzu
					visited.insert(i); // Markiert den Knoten
					return;
				}
					
				if (!(visited.count(i))) // Wenn der Knoten noch nicht markiert wurde
				{
					traversedNodes.push_back(i); // Fügt den Knoten der Liste hinzu
					visited.insert(i); // Markiert den Knoten
					queue.push_back(i); // Fügt den Knoten der Warteschlange für die Breitensuche hinzu
				}
			}
		}
};

int main(){

	Node startNode = Node(start);
	std::set<Node> visitedNodes;
	breitensuche(start, visitedNodes);
		
    return 0;
};



