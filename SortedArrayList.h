
#include <iostream>
#include <cstdlib>
#include <ctime>

using namespace std;

class SortedArrayList 
{

private:
	int *tab = new int [100];
	int n;
	int i;
	


public:
	SortedArrayList();
	void push(int x);
	int pop();
	int erase(int i);
	int find(int x);
	int size();
	void remove(int x);
	void print();
	void unique();
	int getx(int x);
	static SortedArrayList merge( SortedArrayList &a,  SortedArrayList &b);



};
