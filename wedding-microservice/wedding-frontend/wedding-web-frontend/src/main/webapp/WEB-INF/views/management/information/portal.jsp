<%@ page contentType="text/html; charset=UTF-8" session="false" %>
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
<title>Information Management Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/portal.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/portal_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/information/portal_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/menu.js"></script>
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
          <div class="formPanel">
            <d:Page page="${page}" requestPath="/management/information/portal" pageSize="10"/>
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ID</th>
                    <th>タイトル</th>
                    <th>公開日時</th>
                    <th>アクション</th>
                  </tr>
                  <c:forEach items="${page.content}" var="information" varStatus="status">
                  <tr>
                    <td>${status.index + 1}</td>
                    <td><c:out value="${information.infoId}" /></td>
                    <td><c:out value="${information.title}" /></td>
                    <td><fmt:formatDate value="${information.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
                    <td>
                      <form id="information_${information.infoId}" action="${pageContext.request.contextPath}/management/information/${information.infoId}" >
                        <button id="edit-delete-button-${information.infoId}" name="type" type="submit" value="detail" >詳細</button>
                        <button id="edit-delete-button-${information.infoId}" name="type" type="submit" value="delete" >削除</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            <form action="${pageContext.request.contextPath}/management/information/new">
              <button id="new-information-button" class="main-button" type="submit">新規作成</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>