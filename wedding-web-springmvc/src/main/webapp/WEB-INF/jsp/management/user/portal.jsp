<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <div class="formPanel">
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
              <form id="user_${user.userId}" action="${pageContext.request.contextPath}/management/user/${user.userId}}/edit">
              <input id="userId" name="userId" type="hidden" value="${user.userId}">
              <tr>
              	<td>${status.index + 1}</td>
              	<td>${user.userId}</td>
              	<td>${user.loginId}</td>
              	<td>${user.userName}</td>
              	<td><fmt:formatDate value="${user.lastLoginDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
              	<td><button id="edit-button-${user.userId}" name="edit-button-${user.userId}" type="button">変更</button></td>
              </tr>
              </form>
              </c:forEach>
            </tbody>
          </table>
          <d:Page page="${page}" />
        </div>
     </div>
   </div>
  </div>
</body>
</html>