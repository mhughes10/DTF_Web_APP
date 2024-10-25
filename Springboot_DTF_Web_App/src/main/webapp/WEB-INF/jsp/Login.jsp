<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DTF Login</title>
</head>
<body>
	<font color="red">${errorMessage}</font>
	<form method="post">
		Username : <input type="text" name="username" />
		Password : <input type="password" name="password" />
		<font color="red">${username} ${password}</font>
		<input type="submit" />
	</form>
</body>
</html>