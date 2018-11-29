
#include <iostream>
#include <cstdlib>
#include <ctime>
#include "SortedArrayList.h"

using namespace std;

	

	SortedArrayList::SortedArrayList()
	{
		n=0;
	}

//////////////////////////////////////////////////////////////////////
	void SortedArrayList::push(int x)
	{
		if(n==100)
		{

		cout << "Lista jest pelna!" << endl;
		
		}
		

		else if (n==0)
		{
		tab[0]=x;
		n++;		
		}
		else
		{
			for(int i=0;i<n;i++)
			{
				
				if(x>tab[n-1])
				{
					tab[n]=x;
					n++;
					break;
				}
				if(x>tab[i])
				{
					continue;
				}
				else
				{
					for(int k=n-1;k>=i;k--)
					{
						tab[k+1]=tab[k];
					}
					tab[i]=x;
					n++;
					break;
					
				}
			}
		}
		
		
		

	}
	/////////////////////////////////////////////////////////
	int SortedArrayList::pop()
	{

		if(n==0)
		{
			cout <<"Lista jest pusta" <<endl;
			

		}
		else
		{
			int r=tab[0];
			for(int i=0;i<n-1;i++)
			{
				tab[i]=tab[i+1];
				
			}
			n--;
			return r;
		
		}

		
	}

////////////////////////////////////////////////////////
	int SortedArrayList::erase(int i)
	{

		if(i>=n)
		{
			cout <<"Brak takiego indeksu w liscie!" <<endl;
		}
		else
		{
			int r=tab[i];
			for(int k=i;k<n-1;k++)
			{
				tab[k]=tab[k+1];
			}
			n--;
			cout << r <<endl;
		}

		
	}

///////////////////////////////////////////////////////////////
	int SortedArrayList::find(int x)
	{

		for(int w=0;w<n;w++)
		{
			if(tab[w]==x)
			{
				cout << w <<endl;
				break;
				
			}
			else if(w==n-1)
			{
				cout << "-1" <<endl;
			}
		}
	
	
	}
	
//////////////////////////////////////////////////////////////////////////
	int SortedArrayList::size()
	{
		return n;
	}
	void SortedArrayList::remove(int x)
	{
	int licznik=0,temp;
		for(int w=0;w<n;w++)
		{
			if(tab[w]==x && licznik==0)
			{
				temp=w;
				licznik++;
				continue;	

			}
			else if(tab[w]==x)
			{
				licznik++;
			}
		}

		for(int k=temp;k<n-licznik;k++)
		{
			tab[k]=tab[k+licznik];
		}		
		n=n-licznik;		


	}
//////////////////////////////////////////////////////////////////////////////////
	void SortedArrayList::print()
	{
		cout << "Tablica : { ";
		for(int i=0;i<n;i++)
		{
		cout << tab[i] << " ";
		}
		cout << " }" <<endl;
	}

////////////////////////////////////////////////////////////////////////////////////////

	void SortedArrayList::unique()
	{
		for(int i=0;i<n;i++)
		{
			if(tab[i]==tab[i+1])
			{
				for(int k=i;k<n-1;k++)
				{
					tab[k]=tab[k+1];
				}
				n--;

			}
			
		}
	}

	int SortedArrayList::getx(int x)
	{
		return tab[x];

	}
	
	

	SortedArrayList SortedArrayList::merge( SortedArrayList &a,  SortedArrayList &b)
	{
	
		SortedArrayList array3;
		
		if(a.size()==0 && b.size()==0)
		{
		
			return array3;
		}
		else if(a.size()>b.size())
		{
			array3=a;

			for(int i=0;i<b.size();i++)
			{
				a.push(b.getx(i));

			}
		}
		else
		{
			array3=b;
			for(int i=0;i<a.size();i++)
			{
				b.push(a.getx(i));

			}

		}
		array3.print();
		return array3;

		
	}

	



