package Banker;

import java.util.ArrayList;
import java.util.Scanner;

public class Detection
{
	
	public static void main(String[] args)
	{
		int no_pro = 0;
		Scanner read = new Scanner(System.in);
		System.out.print("Enter Number of Process : ");
		no_pro = read.nextInt();
		
		ArrayList<Banker> obj = new ArrayList<>();
		for(int i = 0; i < no_pro; i++)
		{
			Banker b = new Banker();
			System.out.print("Enter Pro Name : ");
			b.name = read.next();
			
			System.out.print("Enter Pro Allocated : ");
			for(int j = 0; j < 3; j++)
			{
				b.allocate[j] = read.nextInt();
				b.sum += b.allocate[j];
			}
			
			System.out.print("Enter Pro request : ");
			for(int j = 0; j < 3; j++)
			{
				b.request[j] = read.nextInt();
			}
			
			obj.add(b);
		}
		
		int available[] = new int[3];
		ArrayList<String> safety_state = new ArrayList<>();
		System.out.print("Enter Available : ");
		for(int i = 0; i < 3; i++)
		{
			available[i] = read.nextInt();
		}
		for(int n = 0; n < no_pro; n++)
		{
			for(int i = 0; i < no_pro; i++)
			{
				int counter = 0;
				if(obj.get(i).flag == false)
				{
					for(int j = 0; j < 3; j++)
					{
						if(obj.get(i).request[j] <= available[j])
						{
							counter++;
						}
					}
					if(counter == 3)
					{
						safety_state.add(obj.get(i).name);
						obj.get(i).flag = true;
						for(int j = 0; j < 3; j++)
						{
							available[j] += obj.get(i).allocate[j];
						}
					}
				}
			}
		}
		System.out.println("Before Recovery : ");
		System.out.println(safety_state);
		if(safety_state.size() != no_pro)
		{
			Recovery(obj, no_pro, available);
			for(int i = 0; i < no_pro-1; i++)
			{
				int counter = 0;
				
				for(int j = 0; j < 3; j++)
				{
					if(obj.get(i).request[j] <= available[j])
					{
						counter++;
					}
				}
				if(counter == 3)
				{
					safety_state.add(obj.get(i).name);
					obj.get(i).flag = true;
					for(int j = 0; j < 3; j++)
					{
						available[j] += obj.get(i).allocate[j];
					}
				}
			}
		}
		System.out.println("After Recovery : ");
		System.out.println(safety_state);
	}
	
	public static void Recovery(ArrayList<Banker> obj, int no_pro, int available[])
	{
		int index = 0;
		int mini = 0;
		for(int i = 0; i < no_pro; i++)
		{
			if(!obj.get(i).flag)
			{
				mini = obj.get(i).sum;
				index = i;
				System.out.println(mini);
				break;
			}
		}
		for(int i = 0; i < no_pro; i++)
		{
			if(obj.get(i).sum <= mini && !obj.get(i).flag)
			{
				mini = obj.get(i).sum;
				index = i;
				System.out.println(mini);
			}
		}
		for(int i = 0; i < 3; i++)
		{
			available[i] += obj.get(index).allocate[i];
		}
		System.out.println(index);
		obj.remove(index+1);
	}
	
	static class Banker
	{
		public String name;
		public int request[] = new int[3];
		public int allocate[] = new int[3];
		public boolean flag = false;
		public int sum = 0;
	}
	
}
