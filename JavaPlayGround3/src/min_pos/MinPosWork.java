/*
 * Name: Jose Terrones Jr.
 * Purpose: Using two different methods will calculate the smallest positive
 * number. One method involves checking each number individually, while the other
 * will recursively check for the smallest till the base case is reached.
 */

package min_pos;

import java.util.*;

public class MinPosWork {
	Scanner scan = new Scanner(System.in);
	int input = 0;
	int min_sum = 0;
	int this_sum;
	int[] nums;
	int[] leftsum;
	int[] rightsum;
	String numbers;
	int finalmin = 500;
	int size1;
	int size2;
	int enterSize;
	
	void menu()
	{
		System.out.println("1: Enter numbers and run all two algorithms");
		System.out.println("2: Enter size of array and then chose which algorithms to run");
		System.out.println("3: exit program");
		System.out.println("Enter choice: ");
		input = scan.nextInt();
		if(input == 1)
		{
			numbsEnter();
			sophmore();
			System.out.println("The junior mss is " + junior(nums, 0, size1-1));
		}
		if(input == 2)
		{
			numbsRand();
		}
		if(input == 3)
		{
			System.out.println("Program closing");
			return;
		}
		menu();
	}
	void numbsEnter()
	{
		System.out.println("Enter the numbers to be used");
		String temp = scan.next();
		String[] temp1 = temp.split("\\s*,\\s*");
		nums = new int[temp1.length];
		size1 = temp1.length;
		for(int i = 0; i < size1; i++)
		{
			nums[i] = Integer.parseInt(temp1[i]);
		}	
	}
	void numbsRand()
	{
		System.out.println("Enter the size of the arrray");
		enterSize = scan.nextInt();
		nums = new int[enterSize];
		size1 = enterSize;
		for(int i = 0; i < enterSize; i++)
		{
			nums[i] = (int)((Math.random()*500)%500+1)-250;
			System.out.print(nums[i] + ", ");
		}
		System.out.println("");
		System.out.println("Enter the algoriths to be used");
		String temp2 = scan.next();		
		for(int i = 0; i < temp2.length(); i++)
		{
			if(temp2.charAt(i) == '1')
			{
				long time = System.nanoTime();
				this.sophmore();
				System.out.println("Sophmore took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");
			}
			if(temp2.charAt(i) == '2')
			{
				long time = System.nanoTime();
				System.out.println("The junior's mss is " + this.junior(nums, 0, size1-1));
				System.out.println("Junior took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");			
			}	
		}
	}
	int sophmore()
	{
		min_sum = 500;
		for(int i = 0; i < size1; i++)
		{
			this_sum = 0;
			for(int j = i; j < size1; j++)
			{
				this_sum += nums[j];
				if(this_sum < min_sum && this_sum > 0)
				{
						min_sum = this_sum;
				}
			}
		}
		if(min_sum == 500)
		{
			min_sum = nums[0];
		}
		System.out.println("\nThe sophmore mss is " + min_sum);
		return min_sum;
	}
	int junior(int nums[], int left, int right)
	{
		if(right-left < 1)
		{
			return nums[left];
		}
		int mid = (right+left)/2;
		int mssleft = junior(nums,left,mid);
		int mssright = junior(nums,(mid+1),right);
		int mssM = mss_middle(nums,left,mid,right);		
		return mssBaseCase(mssleft,mssright,mssM);
	}
	int mssBaseCase(int left, int right, int mid)
	{
		if(left <= 0)
		{
			left = 500;
		}
		if(right <= 0)
		{
			right = 500;
		}
		if(mid <= 0)
		{
			mid = 500;
		}
			if(left <= right && left <= mid)
			{
				return left;
			}
			if(right <= left && right <= mid)
			{
				return right;
			}
			if(mid <= left && mid <= right)
			{
				return mid;
			}
		return left;
	}
	int mss_middle(int nums[], int left,int mid, int right)
	{
		leftsum = new int[(mid-left)+1];
		rightsum = new int[right-mid];
		int i;
		int sum = 0;
		int count = 0;
		for(i = mid; i >= left; i--)
		{
			sum += nums[i];
			leftsum[count] = sum;
			count++;
		}
		
		sum = 0;
		count = 0;
		for(i = mid+1; i <= right; i++)
		{
			sum += nums[i];
			rightsum[count] = sum;
			count++;
		}
		Arrays.sort(leftsum);
		Arrays.sort(rightsum);
		
		i = 0;
		int j = rightsum.length-1;
		int sum1;
		while(j >= 0 && i < leftsum.length)
		{
			sum1 = leftsum[i] + rightsum[j];
			if(sum1 < finalmin && sum1 > 0)  
			{
				finalmin = sum1;
			}
			if(sum1 <= 0)
			{
				i++;
			}
			else 
			{
				j--;
			}
		}
		if(finalmin == 500)
		{
			return nums[0];
		}
		return finalmin;
	}
}
