/*
 * Name: Jose Terrones Jr.
 * Purpose: Allows the user to create a register that has a given
 * length and capacity. Then each value of the register will be incremented
 * individually until capacity is reached. One option is with a timer the
 * second option is with output only.
 */

package register;

import java.util.*;

public class RegisterWork {
	Scanner scan = new Scanner(System.in);
	int[] register;
	int n = 0;
	int k = 0;
	RegisterWork()
	{
		
	}
	void menu()
	{
		System.out.println("1. Register length");
		System.out.println("2. Register capacity");
		System.out.println("3. Increment Register with timer");
		System.out.println("4. Increment Register with output");
		System.out.println("5. Exit");
		System.out.println("Enter option: ");
		int choice = scan.nextInt();
		if(choice == 1)
		{
			registerLength();
		}
		if(choice == 2)
		{
			registerCapacity();
		}
		if(choice == 3)
		{
			incrementRegister();
		}
		if(choice == 4)
		{
			outputRegister();
		}
		if(choice == 5)
		{
			System.out.println("Program closing");
			return;
		}
	}
	void registerLength()
	{
		System.out.println("Enter the register length: ");
		n = scan.nextInt();
		menu();
	}
	void registerCapacity()
	{
		System.out.println("Enter the register capacity");
		k = scan.nextInt();
		menu();
	}
	void incrementRegister()
	{
		int total = 0;
		int count = 0;
		boolean executed = false;
		register = new int[n];
		for(int i = 0; i < n; i++)
		{
			register[i] = 0;
		}
		long time = System.nanoTime();
		while(total < n)
		{
			if(register[total] < k-1)
			{
				count++;
				register[total]++;
				total = 0;
			}
			else if(register[total] == k && total < n)
			{
				total--;
				count--;
			}
			else if(register[n-1] == k-1 && !executed)
			{
				executed = true;
				total = 0;
			}
			else
			{
				register[total] = 0;
				total++;
			}	
		}
		System.out.println("\nProcess took : " + (float)((System.nanoTime()-time)/1000000) + " milliseconds" );
		menu();
	}
	void outputRegister()
	{
		int total = 0;
		int count = 0;
		boolean executed = false;
		register = new int[n];
		for(int i = 0; i < n; i++)
		{
			register[i] = 0;
		}
		System.out.println(count + " " + Arrays.toString(register));
		while(total < n)
		{
			if(register[total] < k-1)
			{
				count++;
				register[total]++;
				System.out.println(count + " " + Arrays.toString(register));
				total = 0;
			}
			else if(register[total] == k && total < n)
			{
				total--;
				count--;
			}
			else if(register[n-1] == k-1 && !executed)
			{
				executed = true;
				total = 0;
			}
			else
			{
				register[total] = 0;
				total++;
			}	
		}
		menu();
	}

}
