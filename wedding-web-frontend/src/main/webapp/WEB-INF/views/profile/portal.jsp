<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://debugroom.org/tags" prefix="d"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" >
<title>Profile Portal</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/profile/portal.css">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/profile/portal.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/views/common/header.jsp" />
  <article>
    <div id="flex-container">
      <div class="flex-item-1">
       <c:import url="/WEB-INF/views/common/menu.jsp" />
      </div>
      <div class="flex-item-2">
        <div class="panel">
          <div class="formPanel">
          変更したい項目がある場合は、各項目の変更ボタンを押して、変更内容を入力して更新したのち、最後に一番下の更新ボタンを押してください。
          <form:form action="${pageContext.request.contextPath}/profile/${portalResource.user.userId}" modelAttribute="portalResource.user">
            <form:hidden path="userId"/>
              <table>
                <tbody>
                  <tr>
                    <td><form:label path="lastName">ユーザ名</form:label> : </td>
                    <td>
                      <div id="userName">
                        <c:out value="${portalResource.user.lastName}" /> <c:out value="${portalResource.user.firstName}" />
                      </div>
                      <form:hidden path="lastName"/><form:hidden path="firstName"/>
                      <form:errors path="lastName" cssStyle="color:red" />
                      <form:errors path="firstName" cssStyle="color:red" />
                    </td>
                    <td><button id="user.lastName-button" name="user.lastName-button" type="button" value="user.lastName" >変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label> : </td>
                    <td>
                      <img src='/profile/image/<c:out value="${portalResource.user.userId}" />'>
                      <br><form:hidden path="imageFilePath"/>
                    </td>
                    <td><button id="user.imageFile-button" name="user.imageFile-button" type="button" value="user.imageFilePath" >変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label> : </td>
                    <td>
                      <c:out value="${portalResource.user.loginId}" />
                      <form:hidden path="loginId"/><br>
                      <form:errors path="loginId" cssStyle="color:red" />
                    </td>
                    <td><button id="user.loginId-button" name="user.loginId-button" type="button" value="user.loginId" >変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label> : </td>
                    <td>
                      <c:out value="${portalResource.user.address.postCd}" />
                      <form:hidden path="address.postCd"/><br>
                      <form:errors path="address.postCd" cssStyle="color:red" />
                    </td>
                    <td><button id="user.address.postCd-button" name="user.address.postCd-button" type="button" value="user.address.postCd" >変更</button></td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br><form:errors path="credentials[0].credentialKey" cssStyle="color:red" />
                      <input id="user.credentials[0].credentialKey" name="credentials[0].credentialKey" type="hidden" value=""/>
                      <input id="user.credentials[0].id.credentialType" name="credentials[0].id.credentialType" type="hidden" value="PASSWORD"/>
                      <input id="user.credentials[1].credentialKey" name="credentials[1].credentialKey" type="hidden" value=""/>
                      <input id="user.credentials[1].id.credentialType" name="credentials[1].id.credentialType" type="hidden" value=""/>
                    </td>
                    <td><button id="password-button" name="password-button" type="button" value="user.credentials[0].credentialKey">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所 </form:label> : </td>
                    <td>
                      <c:out value="${portalResource.user.address.address}" />
                      <form:hidden path="address.address"/><br>
                      <form:errors path="address.address" cssStyle="color:red" />
                    </td>
                    <td><button id="user.address.address-button" name="user.address.address-button" type="button" value="user.address.address" >変更</button></td>
                  </tr>
                  <c:forEach items="${portalResource.user.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="user.emails[${status.index}].email">Email</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${email.email}" /><br>
                        <form:errors path="emails[${status.index}].email" cssStyle="color:red" />
                        <input id="user.emails[${status.index}].id.emailId" name="user.emails[${status.index}].id.emailId" type="hidden" value='<c:out value="${email.id.emailId}" />' />
                        <input id="user.emails[${status.index}].email" name="user.emails[${status.index}].email" type="hidden" value='<c:out value="${email.email}" />' />
                      </td>
                      <td><button id="user.emails[${status.index}].email-button" name="user.emails[${status.index}].email-button" type="button" value="user.emails[${status.index}].email" >変更</button></td>
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
  </article>
</body>