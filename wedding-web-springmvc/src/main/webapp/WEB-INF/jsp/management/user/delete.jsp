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
            以下のユーザを削除します。よろしければ、確定ボタンを押してください。
            <form:form action="${pageContext.request.contextPath}/management/user/delete/${user.userId}" 
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
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label></td>
                    <td>
                      <img src="${pageContext.request.contextPath}/${user.imageFilePath}">
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>
                      <c:out value="${user.loginId}" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="authorityLevel">権限レベル</form:label></td>
                    <td>
                      <c:out value="${user.authorityLevel}" />
                    </td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br>
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label></td>
                    <td>
                      <c:out value="${user.address.postCd}" />
                    </td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label></td>
                    <td>
                      <c:out value="${user.address.address}" />
                    </td>
                  </tr>
                  <c:forEach items="${user.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}]">Email</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${email.email}" /><br>
                      </td>
                    </tr>
                  </c:forEach>
                  <c:forEach items="${user.grps}" var="group" varStatus="status">
                    <tr>
                      <td><label for="groups[${status.index}]">所属グループ</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${group.groupName}" />
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <form:button class="alternative-first-button" name="type" value="delete">削除する</form:button>
              <form:button class="alternative-last-button" name="type" value="return">戻る</form:button>
            </form:form>
          </div>
        </div>
      </div>
    </div>
</body>