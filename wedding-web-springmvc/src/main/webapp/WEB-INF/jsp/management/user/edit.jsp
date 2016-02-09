<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/management/user/edit.js"></script>
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
                    <td><button id="imageFilePath-button" name="imageFilePath-button" type="button" value="imageFilePath">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>${user.loginId}<form:hidden path="loginId" /></td>
                    <td><button id="loginId-button" name="loginId-button" type="button" value="loginId">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="authorityLevel">権限レベル</form:label></td>
                    <td>${user.authorityLevel}<form:hidden path="authorityLevel" /></td>
                    <td><button id="authorityLevel-button" name="authorityLevel-button" type="button" value="authorityLevel">変更</button></td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********
                      <input id="credentials[0].credentialKey" type="hidden" value=""/>
                      <input id="credentials[1].credentialKey" type="hidden" value=""/>
                    </td>
                    <td><button id="password-button" name="password-button" type="button" value="credentials[0].credentialKey">初期化</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label></td>
                    <td>${user.address.postCd}<form:hidden path="address.postCd" /></td>
                    <td><button id="address.postCd-button" name="address.postCd-button" type="button" value="address.postCd">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label></td>
                    <td>${user.address.address}<form:hidden path="address.address" /></td>
                    <td><button id="address.address-button" name="address.address-button" type="button" value="address.address">変更</button></td>
                  </tr>
                  <c:forEach items="${user.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}]">Email</label>${status.index+1} : </td>
                      <td>
                        ${email.email}
                        <input id="emails[${status.index}].id.emailId" name="emails[${status.index}].id.emailId" type="hidden" value="${email.id.emailId}" />
                        <input id="emails[${status.index}].email" name="emails[${status.index}].email" type="hidden" value="${email.email}" />
                      </td>
                      <td><button id="emails[${status.index}].email-button" name="emails[${status.index}].email-button" type="button" value="emails[${status.index}].email">変更</button></td>
                    </tr>
                  </c:forEach>
                  <c:forEach items="${user.grps}" var="group" varStatus="status">
                    <tr>
                      <td><label for="groups[${status.index}]">所属グループ</label>${status.index+1} : </td>
                      <td>
                        ${group.groupName}
                        <input id="groups[${status.index}].groupId" name="groups[${status.index}].groupId" type="hidden" value="${group.groupId}" />
                        <input id="groups[${status.index}].groupName" name="groups[${status.index}].groupName" type="hidden" value="${group.groupName}" />
                      </td>
                      <td><button id="groups[${status.index}].groupName-button" name="groups[${status.index}].groupName-button" type="button" value="groups[${status.index}].groupName">変更</button></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <form:button class="main-button" name="updateParam" >更新を確定する</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
</body>