<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Wedding Portal</title>
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/flex.css">
<link rel="stylesheet"
  href="${pageContext.request.contextPath}/resources/app/css/management/user/form-flex.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/js/management/user/form.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/jsp/common/header.jsp" />
  <div id="flex-container">
    <div class="flex-item-1">
      <c:import url="/WEB-INF/jsp/common/menu.jsp" />
    </div>
    <div class="flex-item-2">
      <div class="panel">
        <div class="userFormPanel">
        新規ユーザ情報を入力してください。
        <form:form action="${pageContext.request.contextPath}/management/user/profile/new"
            modelAttribute="newUserForm" enctype="multipart/form-data">
          <table>
            <c:if test="${!empty errorMessage}">
              <div class="errorMessage">
                <c:out value="${errorMessage}" />
              </div>
            </c:if>
            <tbody>
              <tr>
                <td>
                  <form:label path="userName">
                    ユーザ名
                  </form:label>
                </td>
                <td>
                  <form:input class="middle" path="userName" />
                  <br>(半角全角英数字日本語50文字以内)
                  <br><form:errors path="userName" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="loginId">
                    ログインID
                  </form:label></td>
                <td>
                  <form:input class="middle" path="loginId" />
                  <button id="confirm-loginId-button" name="confirm-loginId-button" class="middle-button" type="button" value="">ID確認</button>
                  <br>(半角英数字32文字以内)
                  <br><form:errors path="loginId" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="imageFilePath">
                    ピクチャ
                  </form:label>
                </td>
                <td>
                  <form:input type="file" path="newImageFile" />
                  <br>(10MBまで)
                  <br><form:errors path="newImageFile" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="authorityLevel">
                    権限レベル
                  </form:label>
                </td>
                <td>
                  <form:input class="micro" path="authorityLevel" />
                  <br>(0-9の半角数字)
                  <br><form:errors path="authorityLevel" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td><form:label path="credentials[0].credentialKey">
                パスワード
                </form:label></td>
                <td>
                  <form:input class="middle" path="credentials[0].credentialKey" type="password" />
                  <form:hidden path="credentials[0].id.credentialType" value="PASSWORD" />
                  <br><form:errors path="credentials[0].credentialKey" cssStyle="color:red" />
                  <br>(半角英数字32文字以内)
                </td>
              </tr>
              <tr>
                <td>
                </td>
                <td>
                  確認のため、再入力してください。
                  <br><form:input class="middle" path="credentials[1].credentialKey" type="password" />
                  <form:hidden path="credentials[1].id.credentialType" value="PASSWORD" />
                  <br><form:errors path="credentials[1].credentialKey" cssStyle="color:red" />
                </td>
              <tr>
                <td>
                  <form:label path="address.postCd">
                    郵便番号
                  </form:label>
                </td>
                <td>
                  <form:input class="short" path="address.postCd" />
                  (7桁の半角数字でXXX-YYYY形式)
                  <button id="get-address-button" name="get-address-button" class="middle-button" type="button" value="">住所取得</button>
                  <br><form:errors path="address.postCd" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="authorityLevel">
                  住所
                  </form:label>
                </td>
                <td>
                  <form:input class="long" path="address.address" />
                  <br>(半角全角英数字日本語256文字以内)
                  <br><form:errors path="address.address" cssStyle="color:red" />
                </td>
              </tr>
              <tr>
                <td>
                  <form:label path="emails[0].email">
                    Email-1
                  </form:label>
                </td>
                <td>
                  <form:input class="long" type="email" path="emails[0].email" />
                  <form:hidden path="emails[0].id.emailId" value="0"/>
                  <br>(半角英数字256文字以内)
                  <br><form:errors path="emails[0].email" cssStyle="color:red" />
                </td>
              <tr>
                <td>
                  <form:label path="emails[1].email">
                    Email-2
                  </form:label></td>
                <td>
                  <form:input class="long" type="email" path="emails[1].email" />
                  <form:hidden path="emails[1].id.emailId" value="1"/>
                  <br>(半角英数字256文字以内)
                  <br><form:errors path="emails[1].email" cssStyle="color:red" />
                </td>
              </tr>
            </tbody>
          </table>
          <form:button class="main-button" name="confrim" >入力確認画面へ</form:button>
        </form:form>
        </div>
      </div>
    </div>
  </div>
</body>
