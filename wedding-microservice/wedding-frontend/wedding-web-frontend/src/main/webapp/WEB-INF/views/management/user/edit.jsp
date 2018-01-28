<%@ page contentType="text/html; charset=UTF-8" session="false" %>
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
<title>Edit User</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/edit.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/edit_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/edit_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/menu.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/UpdateDialog.js"></script>
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/management/user/edit.js"></script>
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
            <p>変更したい項目がある場合は、各項目の変更ボタンを押して、変更内容を入力して更新した後、最後に一番下の更新ボタンを押してください。</p>
            <form:form action="${pageContext.request.contextPath}/management/edit/user/${editUser.userId}" 
                modelAttribute="editUser">
              <table>
                <tbody>
                  <tr>
                    <th>変更対象</th>
                    <th>現在の設定値</th>
                    <th>アクション</th>
                  </tr>
                  <tr>
                    <td><form:label path="userId">ユーザID</form:label></td>
                    <td>
                      <c:out value="${editUser.userId}" />
                      <form:hidden path="userId" />
                    </td>
                    <td></td>
                  </tr>
                  <tr>
                    <td><form:label path="lastName">ユーザ名</form:label></td>
                    <td>
                      <div id="userName">
                        <c:out value="${editUser.lastName}" /> <c:out value="${editUser.firstName}" />
                      </div>
                      <form:hidden path="firstName" /><form:hidden path="lastName" />
                      <form:errors path="firstName" cssClass="errorMessage"/>
                      <form:errors path="lastName" cssClass="errorMessage" />
                    </td>
                    <td><button id="lastName-button" name="lastName-button" type="button" value="lastName">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="imageFilePath">ピクチャ</form:label></td>
                    <td>
                      <div class="imgPanel">
                        <c:choose>
                          <c:when test='${fn:endsWith(editUser.imageFilePath,"png")}'>
                            <img src='${pageContext.request.contextPath}/image/<c:out value="${editUser.userId}" />/xxxx.png?imageFilePath=<c:out value="${editUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(editUser.imageFilePath,"jpeg")}'>
                            <img src='${pageContext.request.contextPath}/image/<c:out value="${editUser.userId}" />/xxxx.jpeg?imageFilePath=<c:out value="${editUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(editUser.imageFilePath,"jpg")}'>
                            <img src='${pageContext.request.contextPath}/image/<c:out value="${editUser.userId}" />/xxxx.jpg?imageFilePath=<c:out value="${editUser.imageFilePath}" />'>
                          </c:when>
                          <c:when test='${fn:endsWith(editUser.imageFilePath,"gif")}'>
                            <img src='${pageContext.request.contextPath}/image/<c:out value="${editUser.userId}" />/xxxx.gif?imageFilePath=<c:out value="${editUser.imageFilePath}" />'>
                          </c:when>
                        </c:choose>
                      </div>
                      <form:hidden path="imageFilePath" />
                    </td>
                    <td><button id="imageFilePath-button" name="imageFilePath-button" type="button" value="imageFilePath">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="loginId">ログインID</form:label></td>
                    <td>
                      <c:out value="${editUser.loginId}" />
                      <form:hidden path="loginId" /><br>
                      <form:errors path="loginId" cssClass="errorMessage" />
                    </td>
                    <td><button id="loginId-button" name="loginId-button" type="button" value="loginId">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="authorityLevel">権限レベル</form:label></td>
                    <td>
                      <c:out value="${editUser.authorityLevel}" />
                      <form:hidden path="authorityLevel" /><br>
                      <form:errors path="authorityLevel" cssClass="errorMessage"/>
                    </td>
                    <td><button id="authorityLevel-button" name="authorityLevel-button" type="button" value="authorityLevel">変更</button></td>
                  </tr>
                  <tr>
                    <td><label for="credentials[0].credentialKey">パスワード</label></td>
                    <td>
                      **********<br>
                      <form:errors path="credentials[0].credentialKey" cssClass="errorMessage" />
                      <input id="credentials[0].credentialKey" name="credentials[0].credentialKey" type="hidden" value=""/>
                      <input id="credentials[0].id.credentialType" name="credentials[0].id.credentialType" type="hidden" value="PASSWORD"/>
                      <input id="credentials[1].credentialKey" name="credentials[1].credentialKey" type="hidden" value=""/>
                      <input id="credentials[1].id.credentialType" name="credentials[1].id.credentialType" type="hidden" value=""/>
                    </td>
                    <td><button id="password-button" name="password-button" type="button" value="credentials[0].credentialKey">初期化</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.postCd">郵便番号</form:label></td>
                    <td>
                    <c:out value="${editUser.address.postCd}" />
                    <form:hidden path="address.postCd" /><br>
                    <form:errors path="address.postCd" cssClass="errorMessage" /></td>
                    <td><button id="address.postCd-button" name="address.postCd-button" type="button" value="address.postCd">変更</button></td>
                  </tr>
                  <tr>
                    <td><form:label path="address.address">住所</form:label></td>
                    <td>
                      <c:out value="${editUser.address.address}" />
                      <form:hidden path="address.address" /><br>
                      <form:errors path="address.address" cssClass="errorMessage"/>
                    </td>
                    <td><button id="address.address-button" name="address.address-button" type="button" value="address.address">変更</button></td>
                  </tr>
                  <c:forEach items="${editUser.emails}" var="email" varStatus="status">
                    <tr>
                      <td><label for="emails[${status.index}]">Email</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${email.email}" /><br>
                        <form:errors path="emails[${status.index}].email" cssClass="errorMessage"/>
                        <input id="emails[${status.index}].id.emailId" name="emails[${status.index}].id.emailId" type="hidden" value="<c:out value='${email.id.emailId}' />" />
                        <input id="emails[${status.index}].email" name="emails[${status.index}].email" type="hidden" value='<c:out value="${email.email}" />' />
                      </td>
                      <td><button id="emails[${status.index}].email-button" name="emails[${status.index}].email-button" type="button" value="emails[${status.index}].email">変更</button></td>
                    </tr>
                  </c:forEach>
                  <tr>
                    <td><form:label path="BrideSide">列席</form:label></td>
                    <td>
                      <c:choose>
                        <c:when test='${editUser.isBrideSide() == true}'>
                          <form:radiobutton path="BrideSide" label="新婦側" value="true" checked="checked"/>
                          <form:radiobutton path="BrideSide" label="新郎側" value="false"/>
                        </c:when>
                        <c:when test='${editUser.isBrideSide() == false}'>
                          <form:radiobutton path="BrideSide" label="新婦側" value="true" />
                          <form:radiobutton path="BrideSide" label="新郎側" value="false" checked="checked"/>
                        </c:when>
                      </c:choose>
                    </td>
                    <td></td>
                  </tr>
                  <c:forEach items="${editUser.grps}" var="group" varStatus="status">
                    <tr>
                      <td><label for="groups[${status.index}]">所属グループ</label>${status.index+1} : </td>
                      <td>
                        <c:out value="${group.groupName}" />
                        <input id="groups[${status.index}].groupId" name="groups[${status.index}].groupId" type="hidden" value='<c:out value="${group.groupName}" />' />
                        <input id="groups[${status.index}].groupName" name="groups[${status.index}].groupName" type="hidden" value='<c:out value="${group.groupName}" />' />
                      </td>
                      <td><button id="groups[${status.index}].groupName-button" name="groups[${status.index}].groupName-button" type="button" value="groups[${status.index}].groupName">変更</button></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <div class="alternative-button">
                <form:button class="alternative-first-button" name="type" value="update">更新を確定する</form:button>
                <form:button class="alternative-last-button" name="type" value="return">ユーザ一覧へ戻る</form:button>
              </div>
            </form:form>
        </div>
      </div>
    </div>
  </article>
</body>