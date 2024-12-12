<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Drive</title>
</head>
<body>
	<h1>Availiable Drives: ${availiableDrives}</h1>
	<form class="addDrive" method="post">
	<label class="input-field">Drive number : <input type="number" name="selectedDrive" /></label>
	<label class="input-field">Drive name : <input type="text" name="nameInput" /></label>
	<label class="submit"><input type="submit" /></label>
	</form>
</body>
</html>