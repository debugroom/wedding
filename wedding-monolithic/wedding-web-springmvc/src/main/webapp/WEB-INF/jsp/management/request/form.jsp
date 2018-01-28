<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/request/form-flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UserTableHelper.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/management/request/form.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <div class="requestFormPanel">
        <form:form action="${pageContext.request.contextPath}/management/request/draft/new"
            modelAttribute="newRequestForm">
            <c:if test="${!empty errorMessage}">
              <div class="errorMessage">
                <c:out value="${errorMessage}" />
              </div>
            </c:if>
            <table>
              <tbody>
                <tr>
                  <td><form:label path="title">タイトル</form:label></td>
                  <td>
                    <form:input class="long" path="title" />
                    <br>256文字以内
                    <br><form:errors path="title" cssStyle="color:red" />
                  </td>
                </tr>
              </tbody>
            </table>
            <h3>依頼内容</h3>
            <div class="editMessagePanel">
              <form:textarea path="requestContents" wrap="hard" rows="20" cols="60"/>
            </div>
            <h3>依頼ユーザ</h3>
            <div class="userListPanel">
              <table>
                <thead>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                    <th>追加</th>
                  </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="status">
                  <tr>
                    <td>${status.index + 1}</td>
                    <td>
                      <c:out value="${user.userId}" />
                      <input id="users[${status.index}].userId" type="hidden" name="users[${status.index}].userId" value="${user.userId}" />
                    </td>
                    <td>
                      <c:out value="${user.userName}" />
                      <input id="users[${status.index}].userName" type="hidden" name="users[${status.index}].userName" value="${user.userName}" />
                    </td>
                    <td>
                      <input type="checkbox" name="${status.index}" value="off" autocomplete="off" />
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
            <form:button class="main-button" name="confirm">依頼登録</form:button>
        </form:form>
        </div>
      </div>
    </div>
  </div>
</body>