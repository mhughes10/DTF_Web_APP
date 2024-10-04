package com.lsb.springboot.dtf.records;

import java.io.File;

import jakarta.validation.constraints.NotNull;

public record DriveRecord(
		
		@NotNull
		String driveName,
		
		@NotNull
		File drivePath,
		
		@NotNull
		String printEstDaysTillFull
		
		)
	{

	public DriveRecord(String driveName, File drivePath, String printEstDaysTillFull)
		{
			this.driveName = driveName;
			this.drivePath = drivePath;
			this.printEstDaysTillFull = printEstDaysTillFull;
			
		}
		
	}
