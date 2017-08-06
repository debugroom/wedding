<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/user/confirm-flex.css">
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
            以下の内容でユーザ情報を作成します。よろしければ、下の作成ボタンを押下してください。
            <form:form action="${pageContext.request.contextPath}/management/user/new/${user.userId}" 
                modelAttribute="user">
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="userId">ユーザID</form:label></td>
                    <td>
                      <c:out value="${user.userId}" />
                      <form:hidden path="userId" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="userName">ユーザ名</form:label></td>
                    <td>
                      <c:out value="${user.userName}" />
                      <form:hidden path="userName" /><br>
                      <form:errors path="userName" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label></td>
                    <td>
                      <img src="${pageContext.request.contextPath}/${user.imageFilePath}">
                      <form:hidden path="imageFilePath" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>
                      <c:out value="${user.loginId}" />
                      <form:hidden path="loginId" /><br>
                      <form:errors path="loginId" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="authorityLevel">権限レベル</form:label></td>
                    <td>
                      <c:out value="${user.authorityLevel}" />
                      <form:hidden path="authorityLevel" /><br>
                      <form:errors path="authorityLevel" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br>
                      <form:errors path="credentials[0].credentialKey" cssStyle="color:red" />
                      <form:hidden path="credentials[0].credentialKey" />
                      <form:hidden path="credentials[1].credentialKey" />
                      <form:hidden path="credentials[0].id.credentialType" />
                      <form:hidden path="credentials[1].id.credentialType" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label></td>
                    <td>
                      <c:out value="${user.address.postCd}" />
                      <form:hidden path="address.postCd" /><br>
                      <form:errors path="address.postCd" cssStyle="color:red" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label></td>
                    <td>
                      <c:out value="${user.address.address}" />
                      <form:hidden path="address.address" /><br>
                      <form:errors path="address.address" cssStyle="color:red" />
                    </td>
                  </tr>
                  <c:forEach items="${user.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}]">Email</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${email.email}" /><br>
                        <form:errors path="emails[${status.index}].email" cssStyle="color:red" />
                        <input id="emails[${status.index}].id.emailId" name="emails[${status.index}].id.emailId" type="hidden" value='<c:out value="${email.id.emailId}" />' />
                        <input id="emails[${status.index}].email" name="emails[${status.index}].email" type="hidden" value='<c:out value="${email.email}" />' />
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="save" >ユーザ作成</form:button>
                <form:button class="alternative-last-button" name="type" value="return">入力画面に戻る</form:button>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </div>
</body>