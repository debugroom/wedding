<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://debugroom.org/tags" prefix="d"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/login.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/login_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/login_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
</head>
<body>
  <div class="back-img">
    <div class="logo">
      <img src="${pageContext.request.contextPath}/static/resources/app/img/logo.png">
    </div>
    <form action="${pageContext.request.contextPath}/authenticate" method="post" name="authenticate">
    <div class="login-form">
      <input type="text" placeholder="Login ID" name="username" />
      <input type="password" placeholder="password" name="password" />
      <button type="submit">Login</button>
      <c:if test="${!empty SPRING_SECURITY_LAST_EXCEPTION.message}">
        <div class="errorMessage">
          <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
        </div>
      </c:if>
      <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
    </div>
  </form>
  </div>
</body>