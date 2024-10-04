package com.lsb.springboot.dtf.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lsb.springboot.dtf.records.DriveRecord;
import com.lsb.springboot.logic.DriveList;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/dtf")
public class MVC
	{
		private final DriveList driveL;
		
		public MVC(DriveList driveL)
			{
				this.driveL = driveL;
			}
		
		public static String DriveSelector(int selectedDrive)
			{
				// creates an empty array of type file called paths
				File[] paths;
				
				// returns array of drive paths and sets them to paths to be used later
				paths = File.listRoots();
				
				//returns string of drive path based on user input i.e select a number
				return paths[selectedDrive].toString();
			}
			
			@GetMapping("/availiable/drives/paths")
			public String ListDrivePaths()
				{	// gets list of drive paths and returns them as a string.			
					return Arrays.toString(File.listRoots());
				}
			
			@GetMapping("/all/drives")
			public List<DriveRecord> ListAddedDrives()
			{	
				// gets list of drives already in the drive list and returns them as a string.
				return driveL.PrintListOfDrives();
			}
			
			@ResponseStatus(HttpStatus.CREATED)
			@PostMapping("/{selectedDrive}/{nameInput}")
			public void AddDrive(@Valid @PathVariable int selectedDrive, @Valid @PathVariable String nameInput)
				{
					
									int driveNumber = selectedDrive;
									String filePath = DriveSelector(driveNumber);
									 
									String driveName;
									driveName = nameInput;
									driveL.CreateNewDrive(driveName, filePath);
				}
			/*
			public static void Quit(Scanner aYSure, int option)
			{
				while(true)
				{
					String answer;
					System.out.println("\nAre you sure? y/n \n");
					answer = aYSure.next();
					if(answer.equals("y"))
					{
						System.exit(option);
					}
					else if(answer.equals("n"))
					{
						return;
					}
					
					System.out.println("\nError: enter y or n as input. \n");
					aYSure.nextLine();
				}
			}
			
			public static void DeleteDrive(Scanner delNameInput, DriveList list)
			{
				String delDriveName;
				System.out.println("\nEnter the name of the drive then press enter.\n");
				System.out.println();
				delDriveName = delNameInput.nextLine();
				System.out.println();
				System.out.println("\nYouve chosen to delete " +  delDriveName + "\n");
				System.out.println();
				list.deleteDrive(delDriveName);
			}
			*/
	}
