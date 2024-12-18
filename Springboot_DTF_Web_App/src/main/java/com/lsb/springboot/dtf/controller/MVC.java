package com.lsb.springboot.dtf.controller;

import java.io.File;
import java.io.IOException;
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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("/dtf")
public class MVC
	{
		private final DriveList driveL;
		boolean isValidUser;
		
		@Autowired
		LoginVerification lVeri;
		
		public MVC(DriveList driveL)
			{
				this.driveL = driveL;
			}
		
		// code to show login page.
		@GetMapping("/login")
		public String ShowLoginPage(Model model)
		{
			return "login";
		}
		
		//code to take input from login page verify it then redirect user to main page
		@PostMapping("/login")
		public String ShowLoginPage(ModelMap model, HttpServletResponse response, @RequestParam String username, @RequestParam String password) throws IOException
		{
			isValidUser = lVeri.verifyUserPass(username, password);
			
			if (!isValidUser)
				{
					model.put("errorMessage","Invalid Credentials");
					return "login";
				}
			
			else
				{
					response.sendRedirect("/dtf/mainPage");
					//return "mainPage";
					return null;
				}
			
			
		}
		
		// returns list of drive paths on host computer
		public static String DriveSelector(int selectedDrive)
			{
				// creates an empty array of type file called paths
				File[] paths;
				
				// returns array of drive paths and sets them to paths to be used later
				paths = File.listRoots();
				
				//returns string of drive path based on user input i.e select a number
				return paths[selectedDrive].toString();
			}
		
		// gets a list of all drives currently added to the list along with relevant info
		@GetMapping("/mainPage")
		public String showMainPage(Model model, HttpServletResponse response) throws IOException
		{
			// Check to make sure password and username have been entered correctly
			if (isValidUser)
				{
					if(driveL.PrintListOfDrives() == null)
						{
							model.addAttribute("addedDrives","There are no drives added yet");
							return "mainPage";
						}
					else
						{
							model.addAttribute("addedDrives",driveL.PrintListOfDrives());
							return "mainPage";
						}
				}
			else
				{
					response.sendRedirect("/dtf/login");
					return null;
				}
		}
		
		@PostMapping("/mainPage")
		public String showMainPage(ModelMap model, HttpServletResponse response, @RequestParam String buttonClicked) throws IOException
		{	
			if(isValidUser)
				{
					// performs an action based on what value a particular button was assigned
					if(buttonClicked.equals("addDrive"))
						{
							response.sendRedirect("/dtf/add/drive");
							return null;
						}
					else if(buttonClicked.equals("logOut"))
						{
							isValidUser=false;
							response.sendRedirect("/dtf/login");
							return null;
						}
					else
						{
							return null;
						}
				}
			else
				{
					response.sendRedirect("/dtf/login");
					return null;
				}
			
		}
					
			// gets a list of all drive paths on the host computer
			@GetMapping("/availiable/drives/paths")
			public String ListDrivePaths(Model model)
				{	// gets list of drive paths and returns them as a string.	
					model.addAttribute("availiableDrives", Arrays.toString(File.listRoots()));
					return "mainPage";
				}
			
			// code to show add drive page.
			@GetMapping("/add/drive")
			public String ShowAddDrivePage(Model model)
			{
				model.addAttribute("availiableDrives", Arrays.toString(File.listRoots()));
				return "addDrive";
			}
			
			// adds a drive object to the drive list to begin tracking drive usage and predicting days till full.
			@ResponseStatus(HttpStatus.CREATED)
			@PostMapping("/add/drive")
			public String AddDrive(ModelMap model, HttpServletResponse response, @RequestParam int selectedDrive, @RequestParam String nameInput) throws IOException
				{
					
									int driveNumber = selectedDrive;
									String filePath = DriveSelector(driveNumber);
									 
									String driveName;
									driveName = nameInput;
									driveL.CreateNewDrive(driveName, filePath);
									
									response.sendRedirect("/dtf/mainPage");
									return null;
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
