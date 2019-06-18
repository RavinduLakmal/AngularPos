<%--
  Created by IntelliJ IDEA.
  User: Ruvil
  Date: 5/9/2019
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>login</title>
</head>
<body>

<form action="api/customers" method="post">
  Enter name : <input type="text" name="user"><br>
  Enter Mobile : <input type="text" name="mobile"><br>
  Enter Address : <input type="text" name="address"><br>
  <input type="submit" value="login">
</form>
</body>
</html>
