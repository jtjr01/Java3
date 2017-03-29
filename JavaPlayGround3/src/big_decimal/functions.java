package big_decimal;

import java.math.BigDecimal;
import java.util.*;

public class functions {
	int input = 0;
	int numbers;
	int count = 0;
	int decimals = 0;
	float sum;
	float standardDev;
	ArrayList<Float> numbs = new ArrayList<Float>();
	ArrayList<Integer> freq = new ArrayList<Integer>();
	Scanner scan = new Scanner(System.in);
	void function()
	{
		
	}
	int menu()
	{
		System.out.println("1: exit program");
		System.out.println("2: Set number of decimals");
		System.out.println("3: Recurring Integer");
		System.out.println("4: Standard deviation");
		System.out.println("Enter choice: ");
		input = scan.nextInt();
		if(input == 1)
		{
			System.out.println("Program closing");
			return 1;
		}
		if(input == 2)
		{
			numbs();
		}
		if(input == 3)
		{
			reccurInt();
		}
		if(input == 4)
		{
			deviation();
		}
		return 0;
	}
	void numbs()
	{
		System.out.println("Enter the number of decimals desired: ");
		decimals = scan.nextInt();
		menu();
	}
	void entryNumbs()
	{
		System.out.println("Enter the numbers to be used");
		String temp = scan.next();
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(temp.split("\\s*,\\s*")));
		for(int i = 0; i < list.size(); i++)
		{
			numbs.add(Float.parseFloat(list.get(i)));
		}	
	}
	void reccurInt()
	{	
		numbs.clear();
		freq.clear();
		entryNumbs();
		int i,j;
		float max = 0;
		int count = 0;
	    for(i = 0; i < numbs.size(); i++)
	    {
	        for(j = 0; j < numbs.size(); j++)
	        {
	        	max = numbs.get(i);
	        	if(max == numbs.get(j))
	        	{
	        		count++;
	        	}
	        }
	        freq.add(count);
	        count = 0;
	    }
	    for(i = 0; i < freq.size(); i++)
	    {
	    	for(j = 0; j < freq.size(); j++)
	    	{
	    		if(freq.get(i) > freq.get(j))
	    		{
	    			max = numbs.get(i);
	    		}
	    	}
	    }
	    System.out.println("Most frequent number is: " + (int)max);
		System.out.println("");
	    menu();
	}
	void deviation()
	{
		numbs.clear();
		entryNumbs();
		int i,j;
		sum = 0;
		for(i = 0; i < numbs.size(); i++)
		{
			sum += numbs.get(i);
		}
		sum = sum/numbs.size();
		standardDev = 0;
		for(j = 0; j < numbs.size(); j++)
		{
			standardDev += (float)Math.pow((numbs.get(j)-sum), 2);
		}
		standardDev = (float)Math.sqrt(standardDev/(numbs.size()-1));
		
		BigDecimal avg = new BigDecimal(sum);
		avg = avg.setScale(decimals,BigDecimal.ROUND_CEILING);
		System.out.format("\nAverage is: %s", avg);
		BigDecimal dev = new BigDecimal(standardDev);
		dev = dev.setScale(decimals, BigDecimal.ROUND_CEILING); 
		System.out.format("\nStandard Deviation is: %s", dev);
		System.out.println("");		
		menu();
	}

}

