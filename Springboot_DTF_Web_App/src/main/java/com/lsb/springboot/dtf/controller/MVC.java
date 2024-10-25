package com.lsb.springboot.dtf.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lsb.springboot.dtf.records.DriveRecord;
import com.lsb.springboot.logic.DriveList;
import com.lsb.springboot.logic.LoginVerification;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/dtf")
public class MVC
	{
		private final DriveList driveL;
		
		@Autowired
		LoginVerification lVeri;
		
		public MVC(DriveList driveL)
			{
				this.driveL = driveL;
			}
		
		@GetMapping("/login")
		public String ShowLoginPage(Model model)
		{
			return "Login";
		}
		
		@PostMapping("/login")
		public String ShowMainPage(ModelMap model, @RequestParam String username, @RequestParam String password)
		{
			boolean isValidUser = lVeri.verifyUserPass(username, password);
			
			if (!isValidUser)
				{
					model.put("errorMessage","Invalid Credentials");
					return "Login";
				}
			
			else
				{
					model.put("username", username);
					model.put("password", password);
					return "page";
				}
			
			
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
			
			// gets a list of all drive paths on the host computer
			@GetMapping("/availiable/drives/paths")
			public String ListDrivePaths(Model model)
				{	// gets list of drive paths and returns them as a string.	
					model.addAttribute("availiableDrives", Arrays.toString(File.listRoots()));
					return "page";
				}
			
			// gets a list of all drives currently added to the list along with relevant info
			@GetMapping("/all/drives")
			public String ListAddedDrives(Model model)
			{	
				model.addAttribute("addedDrives",driveL.PrintListOfDrives());
				return "page";
			}
			
			@ResponseStatus(HttpStatus.CREATED)
			@GetMapping("/{selectedDrive}/{nameInput}")
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
