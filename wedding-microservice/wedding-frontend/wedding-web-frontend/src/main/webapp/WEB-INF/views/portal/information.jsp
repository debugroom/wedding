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
<title></title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/portal/information.css">
</head>
<body class="back-img">
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <article>
    <div id="flex-container">
      <div class="flex-item-1">
       <c:import url="/WEB-INF/views/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <h1>お知らせ</h1>
          <h2><c:out value="${information.title}" /></h2>
          最終更新日時：<fmt:formatDate value="${information.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" />
          <div id="messageBody" class="information-panel">
            <c:import url="${information.infoRootPath}" charEncoding="UTF-8"></c:import>
          </div>
          <button class="main-button" type="button"
                  onclick="location.href='${pageContext.request.contextPath}/portal/${user.userId}'" >ポータルに戻る</button>
        </div>
      </div>
    </div>
  </article>
</body>