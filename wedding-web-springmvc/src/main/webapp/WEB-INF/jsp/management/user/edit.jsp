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
            変更したい項目がある場合は、各項目の変更ボタンを押して、変更内容を入力して更新した後、最後に一番下の更新ボタンを押してください。
            <form:form action="${pageContext.request.contextPath}/management/user/${user.userId}" 
                modelAttribute="user" method="PUT">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="userId">ユーザID</form:label></td>
                    <td>${user.userId}</td>
                    <td></td>
                  </tr>
                  <tr>
                    <td><form:label path="userName">ユーザ名</form:label></td>
                    <td>${user.userName}<form:hidden path="userName" /></td>
                    <td><button id="userName-button" name="userName-button" type="button" value="userName">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label></td>
                    <td><img src="${pageContext.request.contextPath}/${user.imageFilePath}"><form:hidden path="imageFilePath" /></td>
                    <td><button id="imageFile-button" name="imageFile-button" type="button" value="userName">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>${user.loginId}<form:hidden path="loginId" /></td>
                    <td><button id="userName-button" name="userName-button" type="button" value="userName">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="password">パスワード</form:label></td>
                    <td>${user.loginId}<form:hidden path="password" /></td>
                    <td><button id="userName-button" name="userName-button" type="button" value="userName">変更</button></td>
                  </tr>
                </tbody>
              </table>
            </form:form>
          </div>
        </div>
      </div>
    </div>