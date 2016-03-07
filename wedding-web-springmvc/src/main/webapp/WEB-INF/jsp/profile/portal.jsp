<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/profile/portal.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
    <article>
      <div class="panel">
        <div class="formPanel">
          変更したい項目がある場合は、各項目の変更ボタンを押して、変更内容を入力して更新したのち、最後に一番下の更新ボタンを押してください。
          <form:form action="${pageContext.request.contextPath}/profile/edit" modelAttribute="user">
            <form:hidden path="userId"/>
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="userName">ユーザ名</form:label> : </td>
                    <td>${user.userName}<form:hidden path="userName"/>
                      <br><form:errors path="userName" />
                    </td>
                    <td><button id="userName-button" name="userName-button" type="button" value="userName" >変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label> : </td>
                    <td><img src="${pageContext.request.contextPath}/${user.imageFilePath}"><br><form:hidden path="imageFilePath"/></td>
                    <td><button id="imageFile-button" name="imageFile-button" type="button" value="imageFilePath" >変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label> : </td>
                    <td>${user.loginId}<form:hidden path="loginId"/>
                      <br><form:errors path="loginId" />
                    </td>
                    <td><button id="loginId-button" name="loginId-button" type="button" value="loginId" >変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label> : </td>
                    <td>${user.address.postCd}<form:hidden path="address.postCd"/><br><form:errors path="address.postCd" cssStyle="color:red" /></td>
                    <td><button id="address.postCd-button" name="address.postCd-button" type="button" value="address.postCd" >変更</button></td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br><form:errors path="credentials[0].credentialKey" cssStyle="color:red" />
                      <input id="credentials[0].credentialKey" name="credentials[0].credentialKey" type="hidden" value=""/>
                      <input id="credentials[0].id.credentialType" name="credentials[0].id.credentialType" type="hidden" value="PASSWORD"/>
                      <input id="credentials[1].credentialKey" name="credentials[1].credentialKey" type="hidden" value=""/>
                      <input id="credentials[1].id.credentialType" name="credentials[1].id.credentialType" type="hidden" value=""/>
                    </td>
                    <td><button id="password-button" name="password-button" type="button" value="credentials[0].credentialKey">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label> : </td>
                    <td>${user.address.address}<form:hidden path="address.address"/><br><form:errors path="address.address" cssStyle="color:red" /></td>
                    <td><button id="address.address-button" name="address.address-button" type="button" value="address.address" >変更</button></td>
                  </tr>
                  <c:forEach items="${user.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}].email">Email</label>${status.index+1} : </td>
                      <td>${email.email}<br><form:errors path="emails[${status.index}].email" cssStyle="color:red" />
                        <input id="emails[${status.index}].id.emailId" name="emails[${status.index}].id.emailId" type="hidden" value="${email.id.emailId}">
                        <input id="emails[${status.index}].email" name="emails[${status.index}].email" type="hidden" value="${email.email}">
                      </td>
                      <td><button id="emails[${status.index}].email-button" name="emails[${status.index}].email-button" type="button" value="emails[${status.index}].email" >変更</button></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            <form:button class="main-button" name="updateParam" >更新を確定する</form:button>
          </form:form>
        </div>
      </div>
    </article>
  </div>
  </div>
</body>
</html>