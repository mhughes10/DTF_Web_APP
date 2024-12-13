<%@ page import="java.util.LinkedList, java.util.List, com.lsb.springboot.dtf.records.DriveRecord" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DTF</title>
</head>
<body>
		<ul>
			<%
				List<DriveRecord> myList = (List<DriveRecord>) request.getAttribute("addedDrives");
				if (myList != null)
					{
						for (DriveRecord drive : myList)
							{
								out.println("<li>" + drive + "</li>");
							}
					}
				else
					{
						out.println("<li>No items to display</li>");
					}
			%>
		</ul>
	<form class="button" method="post">
		<button type="submit" name="buttonClicked" value="addDrive">Add Drive</button>
		<button type="submit" name="buttonClicked" value="deleteDrive">Delete Drive</button>
		<button type="submit" name="buttonClicked" value="logOut">Log Out</button>
	</form>
</body>
</html>