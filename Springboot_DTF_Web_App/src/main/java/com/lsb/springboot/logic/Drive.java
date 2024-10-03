package com.lsb.springboot.logic;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.LongStream;

public class Drive
	{
		String  name; // name of the drive object
		String units; // holds the suffix for units i.e GiB MiB etc
		//long totalStorage; // Holds Value of total storage in GiB
		//long usableSpace; // Holds Value of total usable space in GiB
		//long usableSpaceSnapshot; // Holds Value of usable space snapshot to be inserted into snapshotArr 
		long fullDayOfUsage; // Holds the difference of the two values in snapshotArr
		long averageUsagePerDay; // Hold value of the average usage per day
		long estDaysTillFull; // Hold Current Calculation results of days before usable space hits 0;
		long[] snapshotArr; // Array that holds values from usableSpaceSnapshot
		long[] dailyUsageMeanArr; // Array that holds values from fullDayOfUsage
		int dailyUsageArrayCount; // Keeps count of index in dailyUsageArr
		int dailyUsageArrayCountOfStoredValues; // Keeps track of how many values are stored in dailyUsageArr
		int period; // Determines the period at which the timer task will run.
		File f; //= new File("C:\\test.txt"); // Holds location of reference file located within the desired drive
		boolean isSnapshotArrayFull; // True or false variable that can be used but isn't being used "laugh out load"
		boolean schedSnap1 = false; // helps identify which snap has been taken
		public DecimalFormat decfor = new DecimalFormat("#.##"); // set to show only two decimal places.
		Date startRecording;

@SuppressWarnings("deprecation")
public Drive()
{
	// set all variables needed immediately throughout the Drive class when created
	snapshotArr = new long[2];
	dailyUsageMeanArr = new long[3];
	dailyUsageArrayCount = 0;
	isSnapshotArrayFull = false;
	startRecording = new java.util.Date();
	//startRecording.setDate(startRecording.getDate()+1);
	//startRecording.setHours(0);
	//startRecording.setMinutes(0);
	startRecording.setHours(startRecording.getHours());
	startRecording.setMinutes(startRecording.getMinutes()+2);
	startRecording.setSeconds(0);
	
	//Starts Scheduler at specified time everyday You can use task to call create task here for testing if you want1
	Timer timer = new Timer();
	
	// here i have created a TimerTask class inside of my constructor. Each drive will have its own TimerTask upon creation.
	TimerTask task = new TimerTask()
			{	
				Date currTime;
				Date execute1;
				Date execute2;
				boolean itsTime = false; // holds whether the scheduled time has been reached or not
		
				@Override
				public void run()
				{
					
					// constant update to current time every second
					currTime = new Date();
					
					if (!itsTime) 
					{
						System.out.println("Record has Started: " + currTime + "\n");
						itsTime = true;
					}
					
					// set first snap shot time
					execute1 = new java.util.Date();
					//execute1.setHours(0);
					//execute1.setMinutes(0);
					execute1.setHours(startRecording.getHours());
					execute1.setMinutes(startRecording.getMinutes());
					execute1.setSeconds(0);
					
					// set second snapshot time
					execute2 = new java.util.Date();
					//execute2.setHours(23);
					//execute2.setMinutes(59);
					execute2.setHours(startRecording.getHours());
					execute2.setMinutes(startRecording.getMinutes()+1);
					execute2.setSeconds(0);
					
					// converts current time and execute time, converts to seconds then compares
					if ((currTime.getTime() / 1000) == (execute1.getTime() / 1000))
					{
						InsertIntoSnapshotArr(currTime,0);
					}
					
					
					if ((currTime.getTime() / 1000) == (execute2.getTime() / 1000))
					{
						InsertIntoSnapshotArr(currTime,1);
						
					}
				}
			};
			
	timer.scheduleAtFixedRate(task, startRecording/*new Date()*/, 1000);
}

public String GetName()
{
	return name;
}

public void AddName(String name)
{
	this.name = name;
}

public void AddFilePath(String filePath)
{
	f = new File(filePath);
}

// Gets total storage in GiB
public long  GetTotalStorage()
{
	//totalStorage = ((f.getTotalSpace() /(1024d*1024d*1024d)));
	//totalStorage = f.getTotalSpace();
	return f.getTotalSpace();
}

//Gets usable space in GiB
public long GetUsableSpace()
{
	//usableSpace = ((f.getUsableSpace() /(1024d*1024d*1024d)));
	//usableSpace = f.getUsableSpace();
	return f.getUsableSpace();
}

// Inserts usableSpace values into snapshotArr
public void InsertIntoSnapshotArr(Date currTime, int i)
{
	
	//usableSpaceSnapshot = GetUsableSpace();
	//snapshotArr[snapshotArrayCount] = usableSpaceSnapshot;
	
	// checks if this snapshot is a 12am snapshot
	if (i == 0)
	{
		snapshotArr[i] = GetUsableSpace();
		
		if (!schedSnap1)
		{
			// sets new snapshot to true and sets 11:59pm snapshot to false so a new day has begun expecting a snapshot 2 to be true
			schedSnap1 = true;
			//schedSnap2 = false;
		}
		
		// checks if a 12am snap has been over written due to not being cleared as result of missing a 11:59pm snapshot
		else
		{						
			System.out.println(name + " PM snapshot was missed. Record was not added to estimates.");
			schedSnap1 = true;
		}
		
		System.out.println(name + " AM snapshot has been taken at: " + currTime + " "
				+ /* decfor.format(FileSizeUnitsConverter(GetUsableSpace())) + " " + units + */ ".\n");
	}
	
	// Checks if this snapshot is a 11:59pm snapshot
	if (i == 1)
	{
		// Checks if snapshot 1 has been taken
		if (schedSnap1)
		{
			snapshotArr[i] = GetUsableSpace();
			
			System.out.println(name + " PM snapshot has been taken at: " + currTime + " "
					+ /* decfor.format(FileSizeUnitsConverter(GetUsableSpace())) + " " + units + */ ".\n");
			InsertIntoDailyUsageMeanArr(snapshotArr);
			schedSnap1 = false;
		}
		
		else
		{
			System.out.println(name + " PM snapshot skipped. AM snapshot is missing. Record not added to estimates");
		}
	
	}
		
}

// Insert fullDayOfUsage into dailyUsageMeanArr in order to calculate the mean of stored data
public void InsertIntoDailyUsageMeanArr(long[] snapshotArr)
{
	//makes a copy of snapshotArr to be used here
	snapshotArr = this.snapshotArr;
	
		if (snapshotArr[0] > snapshotArr[1])
		{
			fullDayOfUsage = snapshotArr[0] - snapshotArr[1];
			
			if (dailyUsageArrayCount <= 2)
			{
				dailyUsageMeanArr[dailyUsageArrayCount] = fullDayOfUsage;
				dailyUsageArrayCount++;
				if(dailyUsageArrayCountOfStoredValues < 3)
				{
					dailyUsageArrayCountOfStoredValues++;
				}
				return;
			}
			
			else
			{
				dailyUsageArrayCount = 0;
				//dailyUsageArrayCountOfStoredValues = 0;
				//snapshotArr[SnapshotArrayCount] = usableSpaceSnapshot;
				dailyUsageMeanArr[dailyUsageArrayCount] = fullDayOfUsage;
				dailyUsageArrayCount++;
				return;
			}
		}
		
		else if (snapshotArr[0] < snapshotArr[1])
		{
			long usableSpaceIncrease = snapshotArr[1] - snapshotArr[0];
			System.out.println("Today "+ name +" usable space has increased by: " + decfor.format(FileSizeUnitsConverter(usableSpaceIncrease)) +" "+ units +". Snap Shot has not been added to estimate.\n");
			return;
		}
		
		else if (snapshotArr[0] == snapshotArr[1])
		{
			System.out.println("Today "+ name +" usable space has not changed. Snap Shot has not been added to estimate.\n");
			return;
		}
}

// Gets the average usage per day by taking the mean array and adding its values and dividing by the number of values to get the mean average usage per day
public Long GetAverageUsagePerDay()
{
	long[] dailyUsageMeanArr = this.dailyUsageMeanArr; // using a local variable to make a copy of the array stored in the class variable
	long sumOfDailyUsageMeanArr = LongStream.of(dailyUsageMeanArr).sum(); // sums the contents of the copied array.
	
	//System.out.println(dailyUsageArrayCountOfStoredValues);
	averageUsagePerDay = sumOfDailyUsageMeanArr / dailyUsageArrayCountOfStoredValues; // divides the sum by the amount of stored days to get an average or median

	return averageUsagePerDay;
}

// Does the main calculation to estimate days till full storage is reached
public int GetDaysTillFull()
{
	
	estDaysTillFull = GetUsableSpace() / (GetAverageUsagePerDay()); // divides the current total usable space by the calculated average space taken up per day 
	
	return (int) estDaysTillFull;
}

// Prints to screen the contents of the snapShotArray
public void PrintSnapShotArray()
{
	System.out.println("Current Two usable space snapshots:" + Arrays.toString(snapshotArr));
}

// Prints to screen the contents of the daily usage mean array
public void PrintDailyUsageMeanArray()
{
	System.out.println(Arrays.toString(dailyUsageMeanArr));
	System.out.println("number of stored value: " + dailyUsageArrayCountOfStoredValues);
	System.out.println("daily usage arr index count: " + dailyUsageArrayCount);
}

// Prints estimate of days till full
@SuppressWarnings("deprecation")
public void PrintEstDaysTillFull()
{
	Date estimatedDate = new java.util.Date();
	
	if (dailyUsageArrayCountOfStoredValues > 0)// checks to make sure there is data before printing estimates
	{
		estimatedDate.setDate(estimatedDate.getDate() + GetDaysTillFull());
	
		System.out.println("Collected " + dailyUsageArrayCountOfStoredValues + " day(s) of data. Average usage: "
				+ decfor.format(FileSizeUnitsConverter(GetAverageUsagePerDay()))  
				+ " " + units + " per day. Total usable space: "
				+ decfor.format(FileSizeUnitsConverter(GetUsableSpace())) + " " + units + ". Estimated "
				+ GetDaysTillFull() + " days till " + name + " becomes full: " + estimatedDate + "\n");
	}
	
	else
	{
		System.out.println(GetName() + " does not have enough data to track so far.\n");
	}
}

// prints the average usage per day
public void PrintAverageUsagePerDay()
{
	System.out.println("The average usage per day is " + GetAverageUsagePerDay());
}

// prints the name of the drive
public void PrintName()
{
	System.out.println("");
	System.out.println(name);
}

public double FileSizeUnitsConverter(long size)
{
	
	double sizeUp = size;
	
	sizeUp = sizeUp / (1024d * 1024d * 1024d);
			
	units = "GB";
	
		if(sizeUp < 1.0d) 
		{ 
			sizeUp = sizeUp * 1024; units = "MB";
		
			if(sizeUp < 1.0d) 
			{ 
				sizeUp = sizeUp * 1024; units = "KB";
	  
				if(sizeUp < 1.0d)
				{ 
					sizeUp = size; units = "Bytes";
		 
				} 
			} 
		}
	
		return sizeUp;
	}
}
