<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DTF Login</title>
<link href="/resources/css/loginStyle.css" rel="stylesheet"/>
</head>
<body>
	<form class="login" method="post">
		<label class="error"><font color="red">${errorMessage}</font></label>
		<label class="input-field">Username : <input type="text" name="username" /></label>
		<label class="input-field">Password : <input type="password" name="password" /></label>
		<font color="red">${username} ${password}</font>
		<label class="submit"><input type="submit" /></label>
	</form>
</body>
</html>