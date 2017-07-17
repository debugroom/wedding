<%@ page contentType="text/html; charset=UTF-8" %>
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
<title>Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/portal/portal.css">
</head>
<body>
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <article>
    <div id="flex-container">
      <div class="flex-item-1">
       <c:import url="/WEB-INF/views/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="informationPanel">
            <div class="imgPanel">
              <c:choose>
                <c:when test='${fn:endsWith(portalResource.user.imageFilePath,"png")}'>
                  <img src='/profile/image/<c:out value="${portalResource.user.userId}/xxxx.png" />'>
                </c:when>
                <c:when test='${fn:endsWith(portalResource.user.imageFilePath,"jpg")}'>
                  <img src='/profile/image/<c:out value="${portalResource.user.userId}/xxxx.jpg" />'>
                </c:when>
                <c:when test='${fn:endsWith(portalResource.user.imageFilePath,"jpeg")}'>
                  <img src='/profile/image/<c:out value="${portalResource.user.userId}/xxxx.jpg" />'>
                </c:when>
                <c:when test='${fn:endsWith(portalResource.user.imageFilePath,"gif")}'>
                  <img src='/profile/image/<c:out value="${portalResource.user.userId}/xxxx.gif" />'>
                </c:when>
              </c:choose>
            </div>
            <h2>${portalResource.user.lastName} ${portalResource.user.firstName} さん</h2>
            <p class="profile">前回ログイン日時：<fmt:formatDate value="${portalResource.user.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss" /> </p>
            <h3>お知らせ</h3>
            <table>
              <thead>
                <tr>
                  <th>更新日時</th>
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
      </div>
    </div>
  </article>
</body>