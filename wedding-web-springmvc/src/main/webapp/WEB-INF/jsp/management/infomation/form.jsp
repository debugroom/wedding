<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/infomation/form-flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/management/infomation/form.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <div class="infomationFormPanel">
        <form:form action="${pageContext.request.contextPath}/management/infomation/draft/new"
            modelAttribute="newInfomationForm" >
          <table>
            <c:if test="${!empty errorMessage}">
              <div class="errorMessage">
                <c:out value="${errorMessage}" />
              </div>
            </c:if>
            <tbody>
              <tr>
                <td>
                  <form:label path="title">
                  タイトル
                  </form:label>
                </td>
                <td>
                  <form:input class="long" path="title" />
                  <br>(256文字以内)
                  <br><form:errors path="title" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="infoName">
                  インフォメーション名
                  </form:label></td>
                <td>
                  <form:input class="middle" path="infoName" />
                  <br>(半角英数字50文字以内)
                  <br><form:errors path="infoName" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="releaseDate">
                  公開日時
                  </form:label></td>
                <td>
                  <form:input class="middle" path="releaseDate" type="datetime" />
                  <br>(50文字以内)
                  <br><form:errors path="releaseDate" cssStyle="color:red" />
                </td>
              </tr>
            </tbody>
          </table>
          <h3>メッセージ本文</h3>
          <div class="editMessagePanel">
          <form:textarea path="messageBody" wrap="hard" rows="20" cols="60" />
          </div>
          <h3>メッセージ通知先</h3>
          <div class="userListPanel">
          <table>
            <tbody>
              <tr>
                <th>No</th>
                <th>ユーザID</th>
                <th>ユーザ名</th>
                <th>表示</th>
              </tr>
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
          <form:button class="main-button" name="confrim" >情報登録</form:button>
        </form:form>
        </div>
      </div>
    </div>
  </div>
</body>
