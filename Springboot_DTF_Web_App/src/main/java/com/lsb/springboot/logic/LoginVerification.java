package com.lsb.springboot.logic;

import org.springframework.stereotype.Service;

@Service
public class LoginVerification
	{
		public boolean verifyUserPass(String username, String password)
		{
			return username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin1200");
		}
	}
