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
<title>Request Management Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/request/portal.css">
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
            <d:Page page="${page}" requestPath="/management/request/portal" pageSize="10"/>
            <table>
              <tbody>
                <tr>
                  <th>No</th>
                  <th>ID</th>
                  <th>タイトル</th>
                  <th>登録日時</th>
                  <th>アクション</th>
                </tr>
                <c:forEach items="${page.content}" var="request" varStatus="status">
                  <tr>
                    <td>${status.index + 1}</td>
                    <td><c:out value="${request.requestId}" /></td>
                    <td><c:out value="${request.title}" /></td>
                    <td><fmt:formatDate value="${request.registratedDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
                    <td>
                      <form id="request_${request.requestId}" action="${pageContext.request.contextPath}/management/request/${request.requestId}" >
                        <button id="edit-delete-button-${request.requestId}" name="type" type="submit" value="detail" >詳細</button>
                        <button id="edit-delete-button-${request.requestId}" name="type" type="submit" value="delete" >削除</button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            <form action="${pageContext.request.contextPath}/management/request/new">
              <button id="new-request-button" class="main-button" type="submit">新規作成</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>