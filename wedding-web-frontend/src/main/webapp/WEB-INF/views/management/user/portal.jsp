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
<title>User Management Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/portal.css">
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
            <d:Page page="${page}" requestPath="/management/user/portal" pageSize="10"/>
            <table>
              <tbody>
                <tr>
                  <th>No</th>
                  <th>ユーザID</th>
                  <th>ログインID</th>
                  <th>ユーザ名</th>
                  <th>最終ログイン日時</th>
                  <th>アクション</th>
                </tr>
                <c:forEach items="${page.content}" var="user" varStatus="status">
                <tr>
                  <td>${status.index + 1}</td>
                  <td><c:out value="${user.userId}" /></td>
                  <td><c:out value="${user.loginId}" /></td>
                  <td><c:out value="${user.lastName}" /> <c:out value="${user.firstName}" /></td>
                  <td><fmt:formatDate value="${user.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                  <td>
                      <form id="user_${user.userId}" action="${pageContext.request.contextPath}/management/user/${user.userId}" >
                        <button id="edit-update-button-${user.userId}" name="type" type="submit" value="update" >変更</button>
                        <button id="edit-delete-button-${user.userId}" name="type" type="submit" value="delete" >削除</button>
                      </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <form action="${pageContext.request.contextPath}/management/user/new">
            <button id="new-user-button" class="main-button" type="submit">ユーザ新規作成</button>
          </form>
          </div>
        </div>
      </div>
    </div>
  </article>
</body>