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
<title>Create new user</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/flex_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/form.css" media="(min-width: 1280px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/form_mobile.css" media="(min-width: 320px) and (max-width: 767px)">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/resources/app/css/management/user/form_tablet.css" media="(min-width: 768px) and (max-width: 1279px)">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/management/user/form.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script type="text/javascript" 
    src="${pageContext.request.contextPath}/static/resources/app/js/common/menu.js"></script>
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
          <div class="userFormPanel">
          <p>新規ユーザ情報を入力してください。</p>
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
                    <form:label path="lastName">
                      苗字　名前
                    </form:label>
                  </td>
                  <td>
                    <form:input class="short" path="lastName" />
                    <form:input class="short" path="firstName" />
                    <br>(半角全角英数字日本語50文字以内)
                    <br><form:errors path="lastName" cssClass="errorMessage"/>
                        <form:errors path="firstName" cssClass="errorMessage" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <form:label path="loginId">
                     ログインID
                    </form:label>
                  </td>
                  <td>
                    <form:input class="middle" path="loginId" />
                    <button id="confirm-loginId-button" name="confirm-loginId-button" 
                    class="middle-button" type="button" value="${pageContext.request.contextPath}/search/user?loginId=">ID確認</button>
                    <br>(半角英数字32文字以内)
                    <br><form:errors path="loginId" cssClass="errorMessage"/>
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
                    <br><form:errors path="newImageFile" cssClass="errorMessage" />
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
                    <br><form:errors path="authorityLevel" cssClass="errorMessage"/>
                  </td>
                </tr>
                <tr>
                  <td><form:label path="credentials[0].credentialKey">
                  パスワード
                  </form:label></td>
                  <td>
                    <form:input class="middle" path="credentials[0].credentialKey" type="password" />
                    <form:hidden path="credentials[0].id.credentialType" value="PASSWORD" />
                    <form:errors path="credentials[0].credentialKey" cssClass="errorMessage" />
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
                    <br><form:errors path="credentials[1].credentialKey" cssClass="errorMessage" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <form:label path="address.postCd">
                    郵便番号
                    </form:label>
                  </td>
                  <td>
                    <form:input class="short" path="address.postCd" />
                    (7桁の半角数字でXXX-YYYY形式)
                    <button id="get-address-button" name="get-address-button" 
                    class="middle-button" type="button" value="${pageContext.request.contextPath}/address/">住所取得</button>
                    <br><form:errors path="address.postCd" cssClass="errorMessage"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <form:label path="address.address">
                    住所
                    </form:label>
                  </td>
                  <td>
                    <form:input class="long" path="address.address" />
                    <br>(半角全角英数字日本語256文字以内)
                    <br><form:errors path="address.address" cssClass="errorMessage" />
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
                    <br><form:errors path="emails[0].email" cssClass="errorMessage" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <form:label path="emails[1].email">
                      Email-2
                    </form:label></td>
                  <td>
                    <form:input class="long" type="email" path="emails[1].email" />
                    <form:hidden path="emails[1].id.emailId" value="1"/>
                    <br>(半角英数字256文字以内)
                    <br><form:errors path="emails[1].email" cssClass="errorMessage" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <form:label path="BrideSide">
                    列席
                    </form:label>
                  </td>
                  <td>
                    <form:radiobutton path="BrideSide" label="新婦側" value="true"/>
                    <form:radiobutton path="BrideSide" label="新郎側" value="false"/>
                    <br><form:errors path="BrideSide" cssClass="errorMessage" />
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
  </article>
</body>