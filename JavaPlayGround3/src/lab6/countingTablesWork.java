/*
 * Name: Jose Terrones Jr.
 * Purpose: This program will control the creation of the table.
 * Using a hash type method to add value to proper locations. 
 * Add, delete and place holders are possible. Mode calculation is 
 * also handle in two different manners. A third option is the entering
 * of a number that will create a table with that many items inserted and
 * calculate the mode.
 */

package lab6;

import java.util.*;

public class countingTablesWork {
	static int[] hashtable; 
	static int[] counttable;
	static int[] mode1;
	static int tableSize = 0;
	int input;
	int input2;
	Scanner userIn = new Scanner(System.in);

	void menu()
	{
		System.out.println("1: Create a table with defined size");
		System.out.println("2: Functions to use with option 1");
		System.out.println("3: Calculate mode");
		System.out.println("4: Give the amount of elements of the array");
		System.out.println("5: exit program");
		System.out.println("Enter choice: ");
		input = userIn.nextInt();
		if(input == 1)
		{
			test();
		}
		if(input == 2)
		{
			System.out.println("Pick one of the following options");
			System.out.println("1: add a value to the table");
			System.out.println("2: subtract a value from the tale");
			System.out.println("3: placeholder");
			System.out.println("4: exit");
			input2 = userIn.nextInt();
			int value;
			if(input2 == 1)
			{
				System.out.println("Enter number to add");
				value = userIn.nextInt();
				add(value);
				print();
			}
			if(input2 == 2)
			{
				System.out.println("Enter number to subtract");
				value = userIn.nextInt();
				sub(value);
				print();
			}

		}
		if(input == 3)
		{
			System.out.println();
			enterNumbs();
			print();
			System.out.println("The mode of method 1 is " + mode(tableSize/2));
			mode2();
		}
		if(input == 4)
		{
			randNumbs();
			long time = System.nanoTime();
			System.out.println("The mode of method 1 is " + mode(tableSize/2));
			System.out.println("method 1 took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");
			long time2 = System.nanoTime();
			mode2();
			System.out.println("method 2 took " + (float)((System.nanoTime()-time2)/1000000) + " milliseconds");
		}
		if(input == 5)
		{
			System.out.println("Program closing");
			return;
		}
		menu();
	}
	void test()
	{
		System.out.println("Enter size of the table");
		tableSize = userIn.nextInt();
		int nextPrime = nextPrime(tableSize);
		tableSize = nextPrime;
		hashtable = new int[nextPrime];
		counttable = new int[nextPrime];
		print();
	}
	void enterNumbs()
	{
		System.out.println("Enter the numbers to be added");
		String temp = userIn.next();
		String[] temp1 = temp.split("\\s*,\\s*");
		tableSize = temp1.length;
		int nextPrime = nextPrime(tableSize*2);
		tableSize = nextPrime;
		hashtable = new int[nextPrime];
		counttable = new int[nextPrime];
		mode1 = new int[nextPrime];
		for(int i = 0; i < temp1.length; i++)
		{
			int num = Integer.parseInt(temp1[i]);
			mode1[i] = num;
			add(num);
		}	
	}
	public int nextPrime( int n )
	{
		if( n % 2 == 0 )
			n++;

		for( ; !isPrime( n ); n += 2 ){
			
		}

		return n;
	}
	private static boolean isPrime( int n )
	{
		if( n == 2 || n == 3 )
			return true;

		if( n == 1 || n % 2 == 0 )
			return false;

		for( int i = 3; i * i <= n; i += 2 )
			if( n % i == 0 )
				return false;

		return true;
	}
	void randNumbs()
	{
		System.out.println("Enter size of the array");
		int size = userIn.nextInt();
		tableSize = size*2;
		hashtable = new int[tableSize];
		counttable = new int[tableSize];
		mode1 = new int[size];
		for(int i = 0; i < tableSize/2; i++)
		{
			int num = (int)((Math.random()*1000)%1000+1)-1000;
			mode1[i] = num;
			add(num);
		}	
	}
	public int hash(int x)
	{
		int i;
		String temp = "" + x;
		int hashed = 0;

		for(i = 0; i < temp.length(); i++)
		{
			hashed += temp.charAt(i)*Math.pow(37, temp.length()-i);
		}

		return hashed;
	}	
	int add(int x) {
		int index = hash(x) % tableSize;
		int i = 0;
		int next_index, new_count;
		while(true)
		{
			next_index = (int)(index + (i*i)) % tableSize;
			if(counttable[next_index] == 0) //this cell is unoccupied
			{
				hashtable[next_index] = x;
				counttable[next_index] = 1;
				return 1;
			}
			else if(x == hashtable[next_index])
			{
				new_count = counttable[next_index] + 1;
				counttable[next_index] = new_count;
				return new_count;
			}
			i++; 
		}
	}
	int sub(int x) {
		int i = 0;
		while(true) 
		{
			if(hashtable[i] != 0 && hashtable[i] == x)
			{
				counttable[i]--;
				if(counttable[i] == 0)
					counttable[i] = 0;
				return 1;
			}
			i++;
		}
	}
	int mode(int n)
	{
		int mode = 0;
		int max_count = 0;
		int i, candidate, count, j;
		for(i=0; i < n; i++)
		{
			candidate = mode1[i];
			count = 1;
			for(j=i+1; j < n; j++)
				if(mode1[j] == candidate)
					count++;
			if(count > max_count)
			{
				max_count = count;
				mode = candidate;
			}
		}
		return mode;
	}
	int mode2()
	{
		int i;
		int mode = 0;
		int index = 0;
		int count = counttable[0];
		System.out.print("The modes for 2 is ");
		for(i=0; i < counttable.length; i++)
		{
			if(count < counttable[i])
			{
				count = counttable[i];
				index = i;
			}
		}
		for(i=0; i < counttable.length; i++)
		{
			if(count == counttable[i])
			{
				count = counttable[i];
				index = i;
				mode = hashtable[index];
				System.out.print(mode + " ");
			}
		}
		System.out.println("");
		return mode;
	}
	public static void print() {
		System.out.println();
		System.out.print("Hash  | ");
		for(int i = 0; i < tableSize; i++) {
			if(hashtable[i] == 0) {
				System.out.print("UD | ");
			} else {
				System.out.print(hashtable[i] + " | ");
			}
		}
		System.out.print("\n--------------------------------------------------");
		System.out.print("\ncount | ");
		for(int i = 0; i < tableSize; i++) {
			if(counttable[i] == 0) {
				System.out.print(counttable[i] + " |  ");
			} else {
				System.out.print(counttable[i] + " | ");
			}
		}
		System.out.print("\n--------------------------------------------------");
		System.out.print("\nindex | ");
		for(int i = 0; i < tableSize; i++) {
			System.out.print(i + "  | ");
		}
		System.out.println("\n--------------------------------------------------");
	}


}
