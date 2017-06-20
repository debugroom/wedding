<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Sample Cassandra</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/css/sample.css">
</head>
<body>
  <h2>${portalResource.user.userName} さん</h2>
  <p>前回ログイン日時：<fmt:formatDate value="${portalResource.user.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss" /> </p>
<article>
  <div class="panel">
  <div class="infomationPanel">
    <h3>お知らせ</h3>
      <table>
        <thead>
          <tr>
            <th>更新</th>
            <th>お知らせ</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="information" items="${portalResource.informationList}" varStatus="status">
          <tr>
            <td><fmt:formatDate value="${portalResource.user.lastLoginDate}" pattern="yyyy-MM-dd" /> </td>
            <td><a href="${pageContext.request.contextPath}/information/${information.infoId}"><c:out value="${information.title}"/></a></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</article>
  
</body>