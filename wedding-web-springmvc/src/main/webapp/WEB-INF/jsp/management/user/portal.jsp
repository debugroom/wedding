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
          <d:Page page="${page}" />
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
              	<td><c:out value="${user.userName}" /></td>
              	<td><fmt:formatDate value="${user.lastLoginDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
              	<td>
                    <form id="user_${user.userId}" action="${pageContext.request.contextPath}/management/user/${user.userId}" >
              	      <button id="edit-button-${user.userId}" type="submit">変更</button>
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
</body>
</html>