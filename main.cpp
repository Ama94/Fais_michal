#include <iostream>
#include <algorithm>
#include <cstdlib>
#include <ctime>
#include "SortedArrayList.cpp"

using namespace std;

int main(int argc, char const *argv[])
{


  char S;
  int x;

  SortedArrayList array1;
  SortedArrayList array2;
 
  while(cin >> S)
  {
	
    switch(S)
    {
      case 'F':
          cin >> x;
          array1.push(x);
	  array2.push(x);
          break;
      case 'P':
       
          cout <<array1.pop()<<endl;
          break;
      case 'C':
          cin >> x;
         array1.find(x);
          break;
      case 'E':
	  cin >> x;
      		array1.erase(x);
          break;
      case 'w':
		array1.print();
		array2.print();
           
		break;
	case 'S':
		cout<< array1.size()<<endl;
        break;
	case 'r':
		cin >> x;
		 array1.remove(x);
		break;
	case 'M':
		 array1.merge(array1,array2);
		break;

	case 'U':
			
		 array1.unique();
		
        break;
      default:
          break;
    }
  }
  return 0;
}
