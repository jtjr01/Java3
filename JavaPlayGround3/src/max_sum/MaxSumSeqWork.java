/*
 * Name: Jose Terrones Jr.
 * Purpose: This program will determine the max sum using different
 * method that get progressively more efficient that the previous method.
 */

package max_sum;

import java.util.*;

public class MaxSumSeqWork {
	Scanner scan = new Scanner(System.in);
	int input = 0;
	static int[] nums;
	String numbers;
	static int max_sum;
	static int this_sum;
	int size1;
	int enterSize;
	MaxSumSeqWork()
	{
		
	}
	void menu()
	{
		System.out.println("1: Enter numbers and run all four algorithms");
		System.out.println("2: Enter size of array and then chose which algorithms to run");
		System.out.println("3: exit program");
		System.out.println("Enter choice: ");
		input = scan.nextInt();
		if(input == 1)
		{
			numbsEnter();
			freshmen();
			sophmore();
			System.out.println("The junior's mss is " + this.junior(nums, 0, size1-1));
			senior();
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
			//System.out.print(nums[i] + ", ");
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
			nums[i] = (int)((Math.random()*100)%200+1)-50;
			//System.out.print(nums[i] + ", ");
		}
		System.out.println("Enter the algoriths to be used");
		String temp2 = scan.next();
		for(int i = 0; i < temp2.length(); i++)
		{
			if(temp2.charAt(i) == '1')
			{
				long time = System.nanoTime();
				this.freshmen();
				System.out.println("Freshmen took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");
			}
			if(temp2.charAt(i) == '2')
			{
				long time = System.nanoTime();
				this.sophmore();
				System.out.println("Sophmore took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");
			}
			if(temp2.charAt(i) == '3')
			{
				long time = System.nanoTime();
				System.out.println("The junior's mss is " + this.junior(nums, 0, size1-1));
				System.out.println("Junior took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");
			}
			if(temp2.charAt(i) == '4')
			{
				long time = System.nanoTime();
				this.senior();
				System.out.println("Senior took " + (float)((System.nanoTime()-time)/1000000) + " milliseconds");
			}
				
		}
	}
	int freshmen()
	{
		int i,j,k;
		max_sum = 0;
		this_sum = 0;
		for(i=0; i< size1; i++)
		{
		    for(j=i; j< size1; j++)
		    {
		    	this_sum=0;
		    	for(k=i; k <= j; k++)
		    		this_sum += nums[k];
		    	if(this_sum > max_sum)
		    		max_sum = this_sum;
		    }
		}
		System.out.println("The freshman mss is " + max_sum);
		return max_sum;
	}
	int sophmore()
	{
		max_sum = 0;
		for(int i = 0; i < size1; i++)
		{
			this_sum = 0;
			for(int j = i; j < size1; j++)
			{
				this_sum += nums[j];
				if(this_sum > max_sum)
					max_sum = this_sum;
			}
		}
		System.out.println("The sophmore mss is " + max_sum);
		return max_sum;
	}
	int junior(int nums[], int left, int right)
	{
		//System.out.println(left + " " + right);
		if(right-left <= 2)
		{
			return mssBaseCase(nums, left, right);
		}
		int mid = (int)((1.0*(right+left))/2);
		int mssL = junior(nums,left,mid);
		int mssR = junior(nums,(mid+1),right);
		int mssM = mss_middle(nums,left,mid,right);
		//System.out.print("The junior's mss is ");
		return max(mssL,mssR,mssM);
	}
	int max(int mssL,int mssR, int mssM)
	{
		
		int mssMax;
		mssMax = mssL;
		if(mssMax < mssR)
		{
			mssMax = mssR;
		}
		if(mssMax < mssM)
		{
			mssMax = mssM;
		}
		//System.out.println(mssMax);
		return mssMax;
	}
	int mssBaseCase(int nums[], int left, int right)
	{
		int mss;
		
		if(nums[left] < 0 && nums[right] < 0)
		{
			mss = 0;
		}
		else if(nums[left] > 0 && nums[right] > 0)
		{
			mss = nums[left] + nums[right];
		}
		else if(nums[left] > nums[right])
		{
			mss = nums[left];
		}
		else if(nums[right] > nums[left])
		{
			mss = nums[right];
		}
		else if(nums[left] == nums[right])
		{
			mss = nums[left];
		}
		else 
		{
			mss = 0;
		}
		//System.out.println(mss);
		return mss;
	}
	int mss_middle(int nums[], int left,int mid, int right)
	{
		int k;
		int sum = 0;
		int sumLeft = 0;
		for(k = mid; k >= left; k--)
		{
			sum += nums[k];
			if(sum > sumLeft)
			{
				sumLeft = sum;
			}
		}
		sum = 0;
		int sumRight = 0;
		for(k = mid+1; k <= right; k++)
		{
			sum += nums[k];
			if(sum > sumRight)
			{
				sumRight = sum;
			}
		}
		
		return (sumLeft+sumRight);
	}
	int senior()
	{
		max_sum = 0;
		this_sum = 0;
		for(int i = 0; i < size1; i++)
		{
			this_sum += nums[i];
			if(this_sum > max_sum)
				max_sum = this_sum;
			else if (this_sum < 0)
				this_sum = 0;
		}
		System.out.println("The senior mss is " + max_sum);
		return max_sum;
	}
}