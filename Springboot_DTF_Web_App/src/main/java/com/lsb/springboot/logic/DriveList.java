package com.lsb.springboot.logic;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lsb.springboot.dtf.records.DriveRecord;

@Repository
public class DriveList
{
	LinkedList<Drive> driveList = new LinkedList<Drive>();

	// Creates a new drive with a chosen name then adds it to drive list
	public void CreateNewDrive(String driveName, String filePath)
	{
		Drive Drive = new Drive();
			
		Drive.AddName(driveName);
		
		Drive.AddFilePath(filePath);
			
		driveList.add(Drive);
		System.out.println(driveName + " will start recording tomorow at " + Drive.startRecording + "\n");
	}

	public List<DriveRecord> PrintListOfDrives()
	{
		List<DriveRecord> driveRecordList = new LinkedList<>();
		// perfect for printing a linked list
		//StringBuilder driveLString = new StringBuilder();
		
//		if (driveList.isEmpty() == false)
//		{
			// for each drive in drivelist do something
			for (Drive drive : driveList)
				{
				driveRecordList.add(new DriveRecord(drive.driveName, drive.f, drive.PrintEstDaysTillFull()));
				//driveLString.append(drive.driveName).append(", ");
				}
			
			return driveRecordList;
	}
			
//		else 
//		{
//			return "There are no drives added to the list yet.\n";
//
//		}
			
	
	public void deleteDrive(String name)
	{
	
		if (driveList.isEmpty() == false)
		{
			for (int i = 0; i < driveList.size(); i++)
			{
				if(driveList.get(i).GetName().equals(name))
				{
					driveList.remove(i);
					System.out.println(name +" Has been removed.\n");
					System.out.println();

					return;
				}
				
			}
				System.out.println("Drive name not found.\n");
				

				return;
		}
		
		else 
		{
			System.out.println("There are no drives added to the list yet.\n");
			return;
		}
		
		
	}
	
	public void deleteAllDrive()
	{
		
		if (driveList.isEmpty() == false)
		{
			driveList.removeAll(driveList);
			System.out.println("All drives have been deleted.\n");
		}
		
		else 
		{
			System.out.println("There are no drives added to the list yet.\n");
		}
		
		
	}
	
	public void PrintdaysTillFullOfallDrives()
	{
		if (driveList.isEmpty() == false)
		{
			for (int i = 0; i < driveList.size(); i++)
			{
				driveList.get(i).PrintEstDaysTillFull();
			}
			
		}
		
		else 
		{
			System.out.println("There are no drives added to the list yet.\n");
		}
		
		
	}
}
