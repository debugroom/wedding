<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Sample Cassandra</title>
<link rel="stylesheet"
    href="pageContext.request.contextPath}/static/css/sample.css">
</head>
<body>
    <form action="${pageContext.request.contextPath}/authenticate" method="post" name="authenticate">
    <h2>Sample Cassandra Admin Login</h2>
    <input type="text" placeholder="Login ID" name="username" />
    <input type="password" placeholder="password" name="password" />
    <button type="submit">Login</button>
  </form>
</body>